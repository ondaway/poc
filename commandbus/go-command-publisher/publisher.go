package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"
	"strings"

	"github.com/streadway/amqp"
)

type ReportStatus struct {
	Vehicle   string  `json:"vehicle"`
	Active    bool    `json:"active"`
	Latitude  float64 `json:"lat"`
	Longitude float64 `json:"lng"`
}

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
		panic(fmt.Sprintf("%s: %s", msg, err))
	}
}

func vehiclesHandler(w http.ResponseWriter, r *http.Request) {
	// POST /vehicles/{id}/status
	fmt.Fprintf(w, "Vehicle command handler")

	err = ch.Publish(
		"",     // exchange
		q.Name, // routing key
		false,  // mandatory
		false,
		amqp.Publishing{
			DeliveryMode: amqp.Persistent,
			ContentType:  "application/json",
			Body:         []byte(body),
		})
	if err != nil {
		log.Printf(" error publishing message")
	}
	log.Printf(" [x] Sent %s", body)
}

func main() {
	// prepare amqp connection
	conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()

	q, err := ch.QueueDeclare(
		"task_queue", // name
		true,         // durable
		false,        // delete when unused
		false,        // exclusive
		false,        // no-wait
		nil,          // arguments
	)
	failOnError(err, "Failed to declare a queue")

	// prepare rest entry point
	http.HandleFunc("/vehicles", vehiclesHandler)
	log.Println("Listening...")
	http.ListenAndServe(":8080", nil)
}

//func main2() {
//	conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
//	failOnError(err, "Failed to connect to RabbitMQ")
//	defer conn.Close()
//
//	ch, err := conn.Channel()
//	failOnError(err, "Failed to open a channel")
//	defer ch.Close()
//
//	q, err := ch.QueueDeclare(
//		"task_queue", // name
//		true,         // durable
//		false,        // delete when unused
//		false,        // exclusive
//		false,        // no-wait
//		nil,          // arguments
//	)
//	failOnError(err, "Failed to declare a queue")
//
//	//body := bodyFrom(os.Args)

//	reportStatus := ReportStatus{
//		Vehicle:   "38400000-8cf0-11bd-b23e-10b96e4ef00d",
//		Active:    true,
//		Latitude:  1.1,
//		Longitude: 2.2,
//	}
//	body, _ := json.Marshal(reportStatus)
//
//	err = ch.Publish(
//		"",     // exchange
//		q.Name, // routing key
//		false,  // mandatory
//		false,
//		amqp.Publishing{
//			DeliveryMode: amqp.Persistent,
//			ContentType:  "text/plain",
//			Body:         []byte(body),
//		})
//	failOnError(err, "Failed to publish a message")
//	log.Printf(" [x] Sent %s", body)
//}
