package Project.Model;

/**
 * InHouse is a subclass of Part that represents a part produced in-house.
 * @author Diar Shabani
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for the InHouse class.
     *
     * @param name  The specific name of this part
     * @param price  The price of the part
     * @param stock  Number of this part we currently have
     * @param min  The least amount we should have of this part
     * @param max  The most of this part we'd want to keep on hand
     * @param machineId  A unique identifier for the machine this part is associated with
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Fetches the machine ID linked with this part object
     * @return The associated machine ID
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Update or assign a machine ID to this part object
     * @param machineId The machine ID to be associated with
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}