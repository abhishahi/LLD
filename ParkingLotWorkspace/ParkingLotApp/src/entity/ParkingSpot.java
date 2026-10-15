package entity;

public class ParkingSpot {

    private final int spotNumber;
    private final VehicleType spotType;
    private ParkingStatus parkingStatus;

    public ParkingSpot(int spotNumber, VehicleType spotType) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.parkingStatus = ParkingStatus.Available;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public VehicleType getSpotType() {
        return spotType;
    }

    public ParkingStatus getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(ParkingStatus parkingStatus) {
        this.parkingStatus = parkingStatus;
    }
}
