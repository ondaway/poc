package com.ondaway.poc.cqrs.stack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ondaway.poc.commandbus.command.ReportStatus;
import com.ondaway.poc.commandbus.command.ReportStatusDeserializer;
import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.ddd.Event;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 *
 * @author jeroldan
 */
public class BusRabbitMQ {

    private static final String COMMAND_QUEUE_NAME = "task_queue";
    private static final ObjectMapper REPORT_STATUS_CMD_MAPPER;
    private Channel channel;

    static {
        REPORT_STATUS_CMD_MAPPER = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ReportStatus.class, new ReportStatusDeserializer());
        REPORT_STATUS_CMD_MAPPER.registerModule(module);
    }

    public void init() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();

        channel = connection.createChannel();
        channel.queueDeclare(COMMAND_QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);

        final com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    ReportStatus reportStatusCmd = REPORT_STATUS_CMD_MAPPER.readValue(body, ReportStatus.class);
                    //send(reportStatusCmd);
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(COMMAND_QUEUE_NAME, false, consumer);
    }
}
