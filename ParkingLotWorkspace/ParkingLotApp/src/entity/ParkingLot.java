package entity;

public class ParkingLot {

   private final String parkingLotName;
   private final int floorNo;
   private final String parkingLotId;

   public ParkingLot(String parkingLotId, String parkingLotName, int floorNo)
   {

      this.parkingLotId = parkingLotId;
      this.parkingLotName = parkingLotName;
      this.floorNo = floorNo;
   }
    
   public String getParkingLotName() {
    return parkingLotName;
}

   public int getFloorNo() {
    return floorNo;
   }

   public String getParkingLotId() {
    return parkingLotId;
   }

   
}
