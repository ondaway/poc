Command Bus PoC
===============

Decoupled command bus implemented with RabbitMQ proof of concept. 


# Goals

// TODO Hablar de los tres obnjetivos de la prueba (arquitectónico, poliglotismo , orquestación de la solución)

## Architecture diagram

     --------          --------- -------------                                                      ----------- --------------     
    |  POST  L        |  REST   |   Command   |             --------------------------             |  Command  |  Bounded     >    
    |  JSON   |---(o--|  entry  |  Publisher  |--(amqp)--> ()       Command bus       )--(amqp)--> |  Handler  |  Context    <     
    | command |       |  point  |             |             --------------------------             |           |  Aggregate   >    
     ---------         --------- -------------                                                      ----------- --------------     
                       [Publisher]                           [queue name: task_queue]                [Worker]


# Requirements

Has ben developed with:

  - Java 8 JDK 
  - Maven 3.2.1
  - Go 1.5.1
  - Docker 1.9.1 
  - Docker compose 1.5.1


# Building

// TODO (describir el proceso de construcción)

## Building the publisher

    $ CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o publisher .

Contruir en contenedor del publisher

    $ docker build -t ondaway/publisher .

# Run

## Por separado

// TODO (completar cuanto se haya probado y esté descrito el proceso de construcción)

Levantar el servidor RabbitMQ que actua como *command bus*. Desde la raiz del proyecto:

    $ docker-compose up commandbus

Ejecutar el *command handler*: desde la raiz del proyecto:

    $ java -jar java-command-handler/target/handler-0.1.0-SNAPSHOT.jar


Ejecutar el *command publisher*. Desde el directorio del proyecto:

    $ publisher


## Orchestrate with docker-compose

    $ docker-compose up


## Testing

Once all sistems are up and running, to test behavour will send a JSON StatusReport message to publisher. For example, with curl:

    $ curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" -d @sample_command.json http://localhost:8080/vehicle/{id}/status


//TODO (describir como verificar que la prueba ha funcionado. Básicamente es mirar la consola del proceso Java)


# Command message format

The command used in the poc will be **ReportStatus**, which notifies the vehicle status. The format is JSON, wiht the syntax:

    {
      "vehicle": <vehicle_id>,
      "active": <active_status>,
      "lat": <latitude>, 
      "lng": <longitude>
    }

Fields description:

  - *vechicle*: Vehicle identifier. Format: UUID (pendiente de estudio).
  - *active*: Active status. Boolean.
  - *lat*: Latitude. Double.
  - *lng*: Longitud. Double.

Example:

    {
      "vehicle": "44e128a5-ac7a-4c9a-be4c-224b6bf81b20",
      "active": true,
      "lat": -34.397, 
      "lng": 150.644
    }

