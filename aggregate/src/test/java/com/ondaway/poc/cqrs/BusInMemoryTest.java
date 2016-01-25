package com.ondaway.poc.cqrs;

import com.ondaway.poc.cqrs.inmemory.InMemorySynchroBus;
import com.ondaway.poc.vehicle.VehicleCommandHandlers;
import com.ondaway.poc.vehicle.command.Activate;
import com.ondaway.poc.vehicle.command.ReportStatus;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ernesto
 */
public class BusInMemoryTest {

    Bus bus = new InMemorySynchroBus();
    VehicleCommandHandlers vehicleCommandHandlers = new VehicleCommandHandlers((null));

    @Test
    public void registerCommandHandler() {
        bus.registerHandler(Activate.class.getName(), vehicleCommandHandlers::handleActivate);
        bus.registerHandler(ReportStatus.class.getName(), vehicleCommandHandlers::handleReportStatus);
        Assert.assertTrue(true); //TODO
    }

}
