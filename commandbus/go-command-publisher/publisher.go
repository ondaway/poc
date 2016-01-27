package main

import (
	//	"encoding/json"
	"fmt"
	"log"
	"net/http"
	//	"os"
	//	"strings"
	"encoding/json"

	"github.com/streadway/amqp"
)

type ReportStatus struct {
	Vehicle   string  `json:"vehicle"`
	Active    bool    `json:"active"`
	Latitude  float64 `json:"lat"`
	Longitude float64 `json:"lng"`
}

var (
	channel amqp.Channel
	queue amqp.Queue
)

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
		panic(fmt.Sprintf("%s: %s", msg, err))
	}
}

func sendCommand(status ReportStatus) error {
	message, err := json.Marshal(status)
	if err != nil {
		return err
	}
	err = channel.Publish(
		"",     // exchange
		queue.Name, // routing key
		false,  // mandatory
		false,
		amqp.Publishing{
			DeliveryMode: amqp.Persistent,
			ContentType:  "text/plain",
			Body:         message,
		})
	return err
}


func vehiclesStatusHandler(w http.ResponseWriter, r *http.Request) {
	// POST /vehicles/{id}/status
	status := &ReportStatus{
		Vehicle: "44e128a5-ac7a-4c9a-be4c-224b6bf81b20", 
		Active: true,
		Latitude: -34.397,
		Longitude: 150.644,
	}

	err := sendCommand(*status)
	if err != nil {
		log.Printf(" error publishing message. %s", err)
		w.WriteHeader(500) // TODO change it
		return 
	}

	log.Printf(" [x] Sent %s", status)
	w.WriteHeader(http.StatusAccepted)
}


func main() {
	conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	channel, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer channel.Close()

	queue, err := channel.QueueDeclare(
		"task_queue", // name
		true,         // durable
		false,        // delete when unused
		false,        // exclusive
		false,        // no-wait
		nil,          // arguments
	)
	failOnError(err, "Failed to declare a queue")
	
	// prepare rest entry point
	http.HandleFunc("/vehicles/status", vehiclesStatusHandler)
	log.Println("Listening...")
	http.ListenAndServe(":8080", nil)
}
