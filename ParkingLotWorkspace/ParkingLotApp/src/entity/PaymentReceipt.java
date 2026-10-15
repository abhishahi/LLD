package entity;

public class PaymentReceipt {

    private String receiptId;
    private ParkingTcket parkingTicket;
    private Payment payment;

    public PaymentReceipt(String receiptId, ParkingTcket parkingTicket, Payment payment) {
        this.receiptId = receiptId;
        this.parkingTicket = parkingTicket;
        this.payment = payment;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public ParkingTcket getParkingTicket() {
        return parkingTicket;
    }

    public void setParkingTicket(ParkingTcket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
