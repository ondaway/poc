Command Bus PoC
===============

Ejemplo de un command bus implementado con RabbitMQ. 


## Diagrama de arquitectura

                 -----------                                        ---------
                |  Command  |      --------------------------      | Command |
    App ---(o---| Publisher |---> |  Command bus (RabbitMQ)  |---> | Handler |
                |   (Go)    |      --------------------------      |  (Java) |
                 -----------                                        ---------
    
                 (Publisher)                 (Queue)                 (Worker)


# Comando de ejemplo

El comando que se va a implementar de ejemplo es **ReportStatus**, para notificar el estado
de un vehículo. El formato es:

    {
    	"vehicle": "44e128a5-ac7a-4c9a-be4c-224b6bf81b20",
    	"active", true,
    	"lat": -34.397, 
    	"lng": 150.644
    }

