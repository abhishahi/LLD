package entity;

public class Payment {

    private String paymentId;
    private PaymentType paymentType;
    private double amount;
    private PaymentStatus status;

    public Payment(String paymentId, PaymentType paymentType, double amount, PaymentStatus status) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.amount = amount;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
