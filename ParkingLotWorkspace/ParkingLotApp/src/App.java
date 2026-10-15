import entity.PaymentReceipt;
import entity.PaymentType;
import entity.Vehicle;
import entity.VehicleType;
import service.ParkingLotService;

public class App {
    public static void main(String[] args) {
        ParkingLotService parkingLotService = new ParkingLotService(10);

        Vehicle car = new Vehicle("KA-01-AB-1234", VehicleType.Car);
        var ticket = parkingLotService.parkVehicle(car);

        if (ticket != null) {
            System.out.println("Ticket created: " + ticket.getTicketId());
            System.out.println("Parked at spot: " + ticket.getParkingSpot().getSpotNumber());

            PaymentReceipt receipt = parkingLotService.Payment(PaymentType.CARD, ticket.getTicketId());
            if (receipt != null) {
                System.out.println("Payment amount: " + receipt.getPayment().getAmount());
            }

            System.out.println("Unparked: " + parkingLotService.unpark(ticket.getTicketId()));
        } else {
            System.out.println("No parking spot available for the vehicle.");
        }
    }
}
