package com.ondaway.poc.vehicle;

import com.ondaway.poc.ddd.Repository;
import com.ondaway.poc.vehicle.command.Activate;
import com.ondaway.poc.vehicle.command.ReportStatus;
import java.util.UUID;

/**
 *
 * @author jeroldan
 */
public class VehicleCommandHandlers {

    Repository<Vehicle> vehicles;

    public VehicleCommandHandlers(Repository<Vehicle> repository) {
        this.vehicles = repository;
    }

    public void handleActivate(Activate command) {
        Vehicle vehicle = _findVehicle(command.vehicle);
        vehicle.activate();
        vehicles.Save(vehicle);
    }

    public void handleReportStatus(ReportStatus command) {
        Vehicle vehicle = _findVehicle(command.vehicle);
        vehicle.move(command.x, command.y);
        if (!vehicle.active && command.active) {
            vehicle.activate();
        }
        vehicles.Save(vehicle);
    }

    private Vehicle _findVehicle(UUID id) {
        Vehicle vehicle = vehicles.GetById(id, Vehicle.class).get();
        return vehicle;
    }
}
