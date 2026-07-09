package Creational.PrototypeDesignPattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manages a collection of Interier objects and provides functionality to load and manage them.
 */
public class InterierManager {

    private String interierId;
    private List<Interier> interierList = new ArrayList<>();

    /**
     * Gets the ID of the InterierManager.
     *
     * @return the interier ID
     */
    public String getInterierId() {
        return interierId;
    }

    /**
     * Sets the ID of the InterierManager.
     *
     * @param interierId the interier ID to set
     */
    public void setInterierId(String interierId) {
        if (interierId == null || interierId.isEmpty()) {
            throw new IllegalArgumentException("Interier ID cannot be null or empty.");
        }
        this.interierId = interierId;
    }

    /**
     * Gets the list of Interier objects.
     *
     * @return the list of Interier objects
     */
    public List<Interier> getInterierList() {
        return interierList;
    }

    /**
     * Sets the list of Interier objects.
     *
     * @param interierList the list of Interier objects to set
     */
    public void setInterierList(List<Interier> interierList) {
        if (interierList == null) {
            throw new IllegalArgumentException("Interier list cannot be null.");
        }
        this.interierList = interierList;
    }

    /**
     * Loads Interier objects dynamically into the manager.
     *
     * @param interierId the ID to assign to the manager
     * @param count      the number of Interier objects to create
     */
    public void loadEnterier(String interierId, int count) {
        setInterierId(interierId);
        this.interierList.clear();

        for (int i = 0; i < count; i++) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Interier ir = new Interier();
            ir.setCustomerID(String.valueOf(i));
            ir.setDueDate(localDateTime);
            ir.setCustomerName("A" + i);
            ir.setItems(Arrays.asList("Bedroom", "Kitchen", "Balcony", "Hall", "Allmirah"));
            this.interierList.add(ir);
        }
    }

    @Override
    public String toString() {
        return "InterierManager{" +
                "interierList=" + interierList +
                ", interierId='" + interierId + '\'' +
                '}';
    }
}
