package entity;

import java.util.Date;

public class ParkingTcket {

    private String ticketId;
    private VehicleType vehicleType;
    private ParkingSpot parkingSpot;
    private Vehicle vehicle;
    private GateType gateType;
    private Date entryTime;

    public ParkingTcket(String ticketId, VehicleType vehicleType, ParkingSpot parkingSpot,
                       Vehicle vehicle, GateType gateType, Date entryTime) {
        this.ticketId = ticketId;
        this.vehicleType = vehicleType;
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.gateType = gateType;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public GateType getGateType() {
        return gateType;
    }

    public void setGateType(GateType gateType) {
        this.gateType = gateType;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
}



