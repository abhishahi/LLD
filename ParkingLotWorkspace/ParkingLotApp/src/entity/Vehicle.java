package entity;

public class Vehicle {

    private String VehicleNo;
	private	VehicleType vehicleType;
    
    public Vehicle(String vehicleNo, VehicleType vehicleType) {
        VehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }
    public VehicleType getVehicleType() {
        return vehicleType;
    }
    
    
}
