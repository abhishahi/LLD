package service;

import entity.GateType;
import entity.ParkingSpot;
import entity.ParkingStatus;
import entity.ParkingTcket;
import entity.Payment;
import entity.PaymentReceipt;
import entity.PaymentStatus;
import entity.PaymentType;
import entity.Vehicle;
import entity.VehicleType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLotService {

    private final List<ParkingSpot> spots;
    private final Map<String, ParkingTcket> activeTickets;

    public ParkingLotService(int totalSpots) {
        this.spots = new ArrayList<>();
        this.activeTickets = new HashMap<>();

        for (int i = 1; i <= totalSpots; i++) {
            if (i % 3 == 0) {
                spots.add(new ParkingSpot(i, VehicleType.BiCycle));
            } else if (i % 5 == 0) {
                spots.add(new ParkingSpot(i, VehicleType.MoterCycle));
            } else if (i % 7 == 0) {
                spots.add(new ParkingSpot(i, VehicleType.Car));
            } else {
                spots.add(new ParkingSpot(i, VehicleType.LargeVehicle));
            }
        }
    }

    public ParkingTcket parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : spots) {
            if (spot.getParkingStatus() == ParkingStatus.Available
                    && spot.getSpotType() == vehicle.getVehicleType()) {
                spot.setParkingStatus(ParkingStatus.Occupied);

                String ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                ParkingTcket ticket = new ParkingTcket(ticketId, vehicle.getVehicleType(), spot, vehicle,
                        GateType.ENTRY, new Date());
                activeTickets.put(ticketId, ticket);

                System.out.println("Success: " + vehicle.getVehicleType() + " parked at Spot #" + spot.getSpotNumber());
                return ticket;
            }
        }

        System.out.println("Failed: No available spots for vehicle type " + vehicle.getVehicleType());
        return null;
    }

    public double parkingCharge(String ticketId) {
        ParkingTcket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            return 0;
        }

        long elapsedMillis = new Date().getTime() - ticket.getEntryTime().getTime();
        double hours = Math.ceil(elapsedMillis / (60.0 * 60 * 1000));

        switch (ticket.getVehicleType()) {
            case BiCycle:
                return hours * 20;
            case MoterCycle:
                return hours * 40;
            case Car:
                return hours * 80;
            case LargeVehicle:
                return hours * 120;
            default:
                return hours * 50;
        }
    }

    public PaymentReceipt Payment(PaymentType paymentType, String ticketId) {
        ParkingTcket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            return null;
        }

        double amount = parkingCharge(ticketId);
        Payment payment = new Payment(
                "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                paymentType,
                amount,
                PaymentStatus.Success
        );

        return new PaymentReceipt(
                "RCPT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                ticket,
                payment
        );
    }

    public boolean unpark(String ticketId) {
        ParkingTcket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            return false;
        }

        ticket.getParkingSpot().setParkingStatus(ParkingStatus.Available);
        return true;
    }
}
