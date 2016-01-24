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

