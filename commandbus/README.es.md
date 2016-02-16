Command Bus PoC
===============

*Proof of Concept* de un sistema de command bus desacoplado.


# Objetivos

// TODO Hablar de los tres obnjetivos de la prueba (arquitectónico, poliglotismo , orquestación de la solución)

## Diagrama de arquitectura

     --------          --------- -----------                                                        ----------- --------------     
    |  POST  L        |  REST   |   Command   |             --------------------------             |  Command  |  Bounded     >    
    |  JSON   |---(o--|  entry  |  Publisher  |--(amqp)--> ()       Command bus       )--(amqp)--> |  Handler  |  Context    <     
    | command |       |  point  |             |             --------------------------             |           |  Aggregate   >    
     ---------         --------- -----------                                                        ----------- --------------     
                       [Publisher]                           [queue name: task_queue]                [Worker]


# Requisitos

Ha sido probado con:
  - Java 8 JDK 
  - Maven 3.2.1
  - Go 1.5.1
  - Docker 1.9.1 
  - Docker compose 1.5.1


# Como construir

// TODO (describir el proceso de construcción)

Construir en publicador 

CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o publisher .

Contruir en contenedor del publisher

    $ docker build -t ondaway/publisher .

# Como ejecutar

## Por separado

// TODO (completar cuanto se haya probado y esté descrito el proceso de construcción)

Levantar el servidor RabbitMQ que actua como *command bus*. Desde la raiz del proyecto:

    $ docker-compose up commandbus

Ejecutar el *command handler*: desde la raiz del proyecto:

    $ java -jar java-command-handler/target/handler-0.1.0-SNAPSHOT.jar


Ejecutar el *command publisher*. Desde el directorio del proyecto:

    $ publisher


## Orquestación

    $ docker-compose up


## Probar

Ejemplo de como realizar la petición POST con *curl* y unos datos de prueba.

    $ curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" -d @sample_command.json http://localhost:8080/vehicle/{id}/status

//TODO (describir como verificar que la prueba ha funcionado. Básicamente es mirar la consola del proceso Java)


# Formato del comando

El comando que se va a implementar de ejemplo es **ReportStatus**, para notificar el estado
de un vehículo. Será un JSON con en siguiente formato:

    {
    	"vehicle": "44e128a5-ac7a-4c9a-be4c-224b6bf81b20",
    	"active": true,
    	"lat": -34.397, 
    	"lng": 150.644
    }

Descripción de los campos:

  - *vechicle*: identificador del vehículo. Formato UUID (pendiente de estudio).
  - *active*: flag indicador de si el vehículo está activo o no
  - *lat*: Latitud
  - *lng*: Longitud
