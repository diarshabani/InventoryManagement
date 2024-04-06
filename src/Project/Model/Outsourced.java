package Project.Model;

/**
 * Outsourced is a subclass of Part that represents a part outsourced from an external company
 * @author Diar Shabani
 */
public class Outsourced extends Part {
    private String companyName;
    private static int nextId = 0;
    /**
     * Constructor for the Outsourced class.
     * @param name  The name of the part
     * @param price  The price of the part
     * @param stock  The stock level of the part
     * @param min  The minimum stock level of the part
     * @param max  The maximum stock level of the part
     * @param companyName  The name of the company from which the part is outsourced
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Returns the next available ID for a new Part
     * @return The next available ID
     */
    public static int getNextId() {
        return nextId + 1;
    }

    /**
     * Provides the external provider's name for the part
     * @return The name associated with the external company
     */
    public String getCompanyName() {
            return companyName;
        }

    /**
     *  Specifies the name of the external provider for the part
     * @param companyName companyName The desired name to represent the company
     */
    public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
}
