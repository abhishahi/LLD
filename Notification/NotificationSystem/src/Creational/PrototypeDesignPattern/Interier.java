package Creational.PrototypeDesignPattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Interier {

    private String customerName;
    private String customerID;
    private LocalDateTime dueDate;
    private List<String> items;

    Interier(){}

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    /**
     * Clone method for deep copying an Interier object.
     */
    public Interier clone() {
        Interier copy = new Interier();
        copy.setCustomerID(this.customerID);
        copy.setCustomerName(this.customerName);
        copy.setDueDate(this.dueDate != null ? LocalDateTime.of(
            this.dueDate.getYear(),
            this.dueDate.getMonth(),
            this.dueDate.getDayOfMonth(),
            this.dueDate.getHour(),
            this.dueDate.getMinute(),
            this.dueDate.getSecond()
        ) : null);
        copy.setItems(this.items != null ? new ArrayList<>(this.items) : null);
        return copy;
    }

    @Override
    public String toString() {
        return "Interier{" +
                "customerName='" + customerName + '\'' +
                ", customerID='" + customerID + '\'' +
                ", dueDate=" + dueDate +
                ", items=" + items +
                '}';
    }
}
