OnDaWay all together now
========================

# Description

## Architecture diagram

                         {Commands adaptor}                                                           {Bounded context}
    ┌────────         ┌─────────┬─────────────┐                                                    ┌───────────┬─────────────            
    │  POST  L        │  REST   │   Command   │             ──────────────────────────             │  Command  │  Bounded    )           
    │  JSON   ├───(o──┤  entry  │  Publisher  ├───(msg)───>()  CommandBus [RabbitMQ]  )───(msg)───>│  Handler  │  Context   (            
    │ command │       │  point  │             │             ──────────────────────────             │           │  Aggregate  )           
    └─────────┘       └─────────┴─────────────┘                                                    └───────────┴───┬─────────            
                                                                                                                   │        _______    
                                                                                                                (event)    /       \   
                                                                                                                   │      │\_______/│  
                                                                                                                   ├─────>│  Event  │  
                                                    {Views}                              ──────────────────────    │      │  Store  │  
    ┌───────────         {Queries schemas}         _________                            () EventBus [rabbitmq] )<──┘       \_______/   
    │  GraphQL  L      ┌───────────────────┐      /_________\       {Event handlers}     ────────────┬─────────                                 
    │   JSON     │<────┤  GraphQL Schemas  |<─┬──┤   View    │     ┌─────────────────┐               │
    │  queries   │     └───────────────────┘  |  │  [Redis]  │ <───┤  event handler  │<───(event)────┤
    └────────────┘                            |   \_________/      └─────────────────┘               │
                                              |    _________                                         │
                                              |   /_________\                                        │
                                              └──┤   View    |     ┌─────────────────┐               │
                                                 │  [Neo4J]  | <───┤  event handler  │<───(event)────┘
                                                  \_________/      └─────────────────┘


## Services diagram

    Application                                                                       │ Support
    ===========                                                                       │ =======
        ┌──────────────┐    ┌─────────────┐    ┌────────────┐      ┌────────────┐     │  
        │   Commands   │    │    Query    │    │   Event    |─┐    │  Bounded   |─┐   │    ┌─────────────┐
        │   adaptors   │    │   schemas   │    │  Handlers  │ │    │  contexts  | │   │    │     ELK     │
        │    <REST>    │    │  <GraphQL>  │    └────────────┘ │    └────────────┘ │   │    │  <logging>  │
        └──────────────┘    └─────────────┘      └────────────┘      └────────────┘   │    └─────────────┘
    ──────────────────────────────────────────────────────────────────────────────────┤
    Persitence                                                                        │    ┌────────────────┐
    ==========                                                                        │    │   ¿Graphana?   │
        ┌─────────────┐    ┌─────────────────┐    ┌───────────┐    ┌───────────┐      │    │  ¿Prometheus?  │
        │   RabbitMQ  │    │    EventStore   │    │  ¿Redis?  │    │  ¿Neo4J?  │      │    │  <monitoring>  │   
        │    <bus>    │    │  <event store>  │    │  <views>  │    │   <view>  │      │    └────────────────┘
        └─────────────┘    └─────────────────┘    └───────────┘    └───────────┘      │
                                                                                      │


The **persistence layer** is composed of the services:

  - [RabbitMQ](https://www.rabbitmq.com/) as buses support
  - [Event Store](https://geteventstore.com/) as event store
  - [Redis](http://redis.io) to store views. For the *views* another services can be used, as Neo4J for graph models, MongoDb for document-based data storage, or relational databases, for transactional support systems.

The **Support layer** is composed of the services:

  - ELK (Elastic + Logstach + Kibana)stack as logging solution.
  - ¿Graphana/Graphite/Prometheus? for system monitoring.


# Persistence layer 

This layer is used to store application state, data and information. 

## Management

Start, stop and other layer management: 

    docker-compose --file persistence.yml up -d
    docker-compose --file persistence.yml stop
    docker-compose --file persistence.yml kill
    docker-compose --file persistence.yml ps 
    docker-compose --file persistence.yml log 

## Consoles and administration

  - [RabbitMQ Console](http://localhost:15672): User: *admin*, password: *changeit*
  - [Event store Console](http://localhost:2113): User: *admin*, password: *changeit*


# Support layer 

This layer contains all support services needed for application and persistence services, as logging, monitoring, configuration, etc.

## Management

Start, stop and other layer management: 

    docker-compose --file support.yml up -d
    docker-compose --file support.yml stop
    docker-compose --file support.yml kill
    docker-compose --file support.yml ps 
    docker-compose --file support.yml log 

## Consoles and administration

  - [Elastic rest entrypoint](http://localhost:9200/) 
  - [Kibana](http://localhost:5601)


# Documentation

## Docker images

  - [RabbitMQ](https://hub.docker.com/_/rabbitmq/)
  - [Event Store](https://hub.docker.com/r/madkom/eventstore-docker/)
  - [Redis](https://hub.docker.com/_/redis/)
  - [Elastic Search](https://hub.docker.com/_/elasticsearch/)
  - [Logstash](https://hub.docker.com/_/logstash/)
  - [Kibana](https://hub.docker.com/_/kibana/)


## APIs

  - [Redis Go API](https://github.com/go-redis/redis)
