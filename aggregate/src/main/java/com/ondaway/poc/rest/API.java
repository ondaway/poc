package com.ondaway.poc.rest;

import com.google.gson.Gson;
import com.ondaway.poc.cqrs.Bus;
import com.ondaway.poc.cqrs.inmemory.BusInMemory;
import com.ondaway.poc.vehicle.command.ReportStatus;
import java.util.UUID;
import static spark.Spark.*;

/**
 *
 * @author jeroldan
 */
public class API {

    public static void main(String[] args) {

        Bus bus = new BusInMemory();

        Gson gson = new Gson();

        get("/ondaway", (req, res) -> "OndaWay up and running...");

        put("/ondaway/vehicle/:id/status", (req, res) -> {
            bus.send(new ReportStatus(UUID.randomUUID(), true, 1f, 1f));
            return "vehicle [" + req.params(":id") + "]: new status received.";
        });
    }
}
