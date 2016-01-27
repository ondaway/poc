package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"

	"github.com/streadway/amqp"
)

// Command bus
const COMMAND_BUS_URL = "amqp://guest:guest@localhost:5672/"
const QUEUE_NAME = "task_queue"
const QUEUE_DURABLE = true
const QUEUE_DELETE_WHEN_USED = false
const QUEUE_EXCLUSIVE = false
const QUEUE_NO_WAIT = false

// "REST" listener
const LISTENER_PORT = ":8082"
const LISTENER_URI = "/vehicles/status"

type ReportStatus struct {
	Vehicle   string  `json:"vehicle"`
	Active    bool    `json:"active"`
	Latitude  float64 `json:"lat"`
	Longitude float64 `json:"lng"`
}

type CommandPublisher struct {
	channel amqp.Channel
	queue   amqp.Queue
}

var commandPublisher CommandPublisher

func (publisher *CommandPublisher) publish(status ReportStatus) error {
	message, err := json.Marshal(status)
	if err != nil {
		return err
	}
	return publisher.channel.Publish(
		"",                   // exchange
		publisher.queue.Name, // routing key
		false,                // mandatory
		false,
		amqp.Publishing{
			DeliveryMode: amqp.Persistent,
			ContentType:  "text/plain",
			Body:         message,
		})
}

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
		panic(fmt.Sprintf("%s: %s", msg, err))
	}
}

func vehiclesStatusHandler(w http.ResponseWriter, r *http.Request) {
	// POST /vehicles/{id}/status
	status := &ReportStatus{
		Vehicle:   "44e128a5-ac7a-4c9a-be4c-224b6bf81b20",
		Active:    true,
		Latitude:  -34.397,
		Longitude: 150.644,
	}

	err := commandPublisher.publish(*status)
	if err != nil {
		log.Printf(" error publishing message. %s", err)
		w.WriteHeader(500) // TODO change it
		return
	}

	log.Printf(" [x] Sent %s", status)
	w.WriteHeader(http.StatusAccepted)
}

func main() {
	conn, err := amqp.Dial(COMMAND_BUS_URL)
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	c, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer c.Close()

	q, err := c.QueueDeclare(QUEUE_NAME, QUEUE_DURABLE, QUEUE_DELETE_WHEN_USED, QUEUE_EXCLUSIVE, QUEUE_NO_WAIT, nil)
	failOnError(err, "Failed to declare a queue")

	commandPublisher = CommandPublisher{*c, q}

	// prepare rest entry point
	http.HandleFunc(LISTENER_URI, vehiclesStatusHandler)
	log.Println("Listening...")
	log.Fatal(http.ListenAndServe(LISTENER_PORT, nil))
}
