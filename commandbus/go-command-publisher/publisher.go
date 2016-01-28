package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/streadway/amqp"
)

// Environment variable names
const COMMNAND_BUS_URL_ENV = "command_bus"
const QUEUE_NAME_ENV = "queue_name"
const LISTENER_PORT_ENV = "listener_port"

// Environment variable default values
const COMMNAND_BUS_URL_DEFAULT = "amqp://guest:guest@localhost:5672/"
const QUEUE_NAME_DEFAULT = "task_queue"
const LISTENER_PORT_DEFAULT = ":8080"

const LISTENER_URI string = "/vehicles/status"

type CommandPublisher struct {
	channel amqp.Channel
	queue   amqp.Queue
}

type ReportStatus struct {
	Vehicle   string  `json:"vehicle"`
	Active    bool    `json:"active"`
	Latitude  float64 `json:"lat"`
	Longitude float64 `json:"lng"`
}

// Config data
var commandBusUrl string = COMMNAND_BUS_URL_DEFAULT
var queueName string = QUEUE_NAME_DEFAULT
var queueDurable bool = true
var queueDeleteWhenUsed bool = false
var queueExclusive bool = false
var queueNoWait bool = false
var listenerPort string = LISTENER_PORT_DEFAULT

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

func processEnv() {
	if val, exist := os.LookupEnv(COMMNAND_BUS_URL_ENV); exist {
		commandBusUrl = val
	}
	if val, exist := os.LookupEnv(QUEUE_NAME_ENV); exist {
		queueName = val
	}
	if val, exist := os.LookupEnv(LISTENER_PORT_ENV); exist {
		listenerPort = val
	}
}

func main() {
	processEnv()

	conn, err := amqp.Dial(commandBusUrl)
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	c, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer c.Close()

	q, err := c.QueueDeclare(queueName, queueDurable, queueDeleteWhenUsed, queueExclusive, queueNoWait, nil)
	failOnError(err, "Failed to declare a queue")

	commandPublisher = CommandPublisher{*c, q}

	// prepare rest entry point
	http.HandleFunc(LISTENER_URI, vehiclesStatusHandler)
	log.Println("Listening on port " + listenerPort + "...")
	log.Fatal(http.ListenAndServe(listenerPort, nil))
}
