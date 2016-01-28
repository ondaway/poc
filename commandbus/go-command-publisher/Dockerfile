FROM scratch
MAINTAINER OnDaWay Team
LABEL Description="OnDaWay Command Bus PoC - command publisher"

# these are the defaults
ENV command_bus=amqp://guest:guest@localhost:5672/    \
    queue_name=task_queue     \
    listener_port=:8080

ADD publisher /
CMD ["/publisher"]