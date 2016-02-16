package com.ondaway.poc.rest;

import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.vehicle.command.ReportStatus;
import java.util.UUID;
import static spark.Spark.*;

/**
 *
 * @author jeroldan
 */
public class API {

    private final Bus bus;

    public API(Bus bus) {
        this.bus = bus;
    }

    public void listen() {

        get("/ondaway", (req, res) -> "OndaWay up and running...");

        put("/ondaway/vehicle/:id/status", (req, res) -> {
            bus.send(new ReportStatus(UUID.randomUUID(), true, 1f, 1f));
            return "vehicle [" + req.params(":id") + "]: new status received.";
        });
    }
}
