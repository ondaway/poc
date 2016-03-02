package com.ondaway.poc.rest;

import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.cqrs.CommandSender;
import com.ondaway.poc.cqrs.InvalidCommandException;
import com.ondaway.poc.vehicle.command.Register;
import com.ondaway.poc.vehicle.command.ReportStatus;
import java.util.UUID;
import spark.Response;
import static spark.Spark.*;

/**
 *
 * @author jeroldan
 */
public class API {

    private final CommandSender bus;

    public API(CommandSender bus) {
        this.bus = bus;
    }

    public void listen() {

        get("/ondaway", (req, res) -> "OndaWay up and running...");

        get("/ondaway/vehicles", (req, res) -> {
            return _processCommand(new Register(UUID.randomUUID()), res);
        });

        get("/ondaway/vehicles/:id/status", (req, res) -> {
            return _processCommand(new ReportStatus(UUID.randomUUID(), true, 1f, 1f), res);
        });
    }

    private String _processCommand(Command command, Response response) {
        try {
            bus.emit(command);
            response.status(202);
            return "Operation in progress";
        } catch (InvalidCommandException ex) {
            response.status(500);
            return "ERROR";
        }
    }
}
