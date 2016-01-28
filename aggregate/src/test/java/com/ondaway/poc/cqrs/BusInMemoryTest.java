package com.ondaway.poc.cqrs;

import com.ondaway.poc.cqrs.inmemory.BusInMemory;
import com.ondaway.poc.vehicle.VehicleCommandHandlers;
import com.ondaway.poc.vehicle.command.Activate;
import com.ondaway.poc.vehicle.command.ReportStatus;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ernesto
 */
public class BusInMemoryTest implements CommandSender {

    Bus bus = new BusInMemory();
    VehicleCommandHandlers vehicleCommandHandlers = new VehicleCommandHandlers((null));

    @Test
    public void registerCommandHandler() { 
        bus.registerHandler(Activate.class.getName(), vehicleCommandHandlers::handleActivate);
        bus.registerHandler(ReportStatus.class.getName(), vehicleCommandHandlers::handleReportStatus);
        Assert.assertTrue(true); //TODO
    }

    @Override
    public void send(Command command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
