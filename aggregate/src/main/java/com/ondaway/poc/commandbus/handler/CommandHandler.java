package com.ondaway.poc.commandbus.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ondaway.poc.commandbus.command.ReportStatus;
import com.ondaway.poc.commandbus.command.ReportStatusDeserializer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandHandler {
    private static final String COMMAND_QUEUE_NAME = "task_queue";
    
    private static final ObjectMapper REPORT_STATUS_CMD_MAPPER;

    static {
        REPORT_STATUS_CMD_MAPPER = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ReportStatus.class, new ReportStatusDeserializer());
        REPORT_STATUS_CMD_MAPPER.registerModule(module);
    }
    
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel    channel    = connection.createChannel();

        channel.queueDeclare(COMMAND_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for commands. To exit press CTRL+C");

        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    ReportStatus reportStatusCmd = REPORT_STATUS_CMD_MAPPER.readValue(body, ReportStatus.class);

                    System.out.println(" [x] Received '" + reportStatusCmd.vehicle + "'");
                    doWork(reportStatusCmd);
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(COMMAND_QUEUE_NAME, false, consumer);
    }

    private static void doWork(ReportStatus reportStatusCmd) {
        try {
            System.out.printf("Performing command: %s\n", REPORT_STATUS_CMD_MAPPER.writeValueAsString(reportStatusCmd));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(CommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
