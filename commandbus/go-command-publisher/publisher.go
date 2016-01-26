package main

import (
	//	"encoding/json"
	"fmt"
	"log"
	"net/http"
	//	"os"
	//	"strings"

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
	body := "{\"vehicle\": \"44e128a5-ac7a-4c9a-be4c-224b6bf81b20\", \"active\": true, \"lat\": -34.397,  \"lng\": 150.644}"

	err = ch.Publish(
		"",     // exchange
		q.Name, // routing key
		false,  // mandatory
		false,
		amqp.Publishing{
			DeliveryMode: amqp.Persistent,
			ContentType:  "text/plain",
			Body:         []byte(body),
		})
	if err != nil {
		log.Printf(" error publishing message. %s", err)
	}

	log.Printf(" [x] Sent %s", body)
	w.WriteHeader(http.StatusAccepted)
}

func main() {
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
