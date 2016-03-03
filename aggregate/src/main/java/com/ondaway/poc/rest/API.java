package com.ondaway.poc.rest;

import com.ondaway.poc.cqrs.Command;
import com.ondaway.poc.cqrs.CommandSender;
import com.ondaway.poc.cqrs.InvalidCommandException;
import com.ondaway.poc.vehicle.command.Register;
import com.ondaway.poc.vehicle.command.ReportStatus;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            Optional<String> error = bus.emit(command).get(5,TimeUnit.SECONDS);
            if (error.isPresent()) {
                response.status(202);
                return "Operation in progress";
            } else {
                response.status(500);
                return "ERROR";
            }
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Logger.getLogger(API.class.getName()).log(Level.SEVERE, null, ex);
            response.status(500);
            return "ERROR";
        }
    }
}
