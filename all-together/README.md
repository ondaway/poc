OnDaWay all together now
========================

# Description

## Architecture diagram

     --------          --------- -------------                                                      ----------- --------------     
    |  POST  L        |  REST   |   Command   |             --------------------------             |  Command  |  Bounded     >    
    |  JSON   |---(o--|  entry  |  Publisher  |--(amqp)--> ()  CommandBus [RabbitMQ]  )--(amqp)--> |  Handler  |  Context    <     
    | command |       |  point  |             |             --------------------------             |           |  Aggregate   >    
     ---------         --------- -------------                                                      ----------- --------------     
                                                                                                                  |            _______ 
                                                                                                                (amqp)        /       \
                                                                                                                  |--------> |\_______/|  
                                                                                                                  |          |  Event  |
                                                                                 ------------------------         |          |  Store  |
    -----------                                  ___________                    ()  EventBus [rabbitmq]  )<-------            \_______/
   |  GraphQL  L       -------------------      |___________|                    ------------------------
   |   JSON     |     |   GraphQL Schema  |<----|   View    |                                |
   |  response  |      -------------------      |  [Redis]  | <-------------------------------
    ------------                                |___________| 


The infrastructure is composed of the services:

  - [RabbitMQ](https://www.rabbitmq.com/) as buses support
  - [Event Store](https://geteventstore.com/) as event store
  - [Redis](http://redis.io) to store views


# Infrastructure management

Start: 

    docker-compose --file infrastructure.yml up -d

Status & log:

    docker-compose --file infrastructure.yml log 

Stop or kill:

    docker-compose --file infrastructure.yml stop
    docker-compose --file infrastructure.yml kill


# RabbitMQ

## Access admin console

    http://localhost:15672
    [admin/changeit]


# Event store

## Access admin console

    http://localhost:2113
    [admin/changeit]


# Documentation

## Docker images

    - [RabbitMQ](https://hub.docker.com/_/rabbitmq/)
    - [Redis](https://hub.docker.com/_/redis/)
    - [Event Store](https://hub.docker.com/r/madkom/eventstore-docker/)


## APIs

  - [Redis Go API](https://github.com/go-redis/redis)

