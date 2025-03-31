package edu.eci.cvds.labReserves.model;

/**
 * Represents a physical resource available in a laboratory, such as projectors, televisions and computers.
 */
public class Physical extends Resource {

    private boolean projector;
    private boolean TV;
    private int totalComputer;

    /**
     * Default constructor of the Physical class.
     */
    public Physical() {
        super();
    }

    /**
     * Constructor that initializes the attributes of the Physical class.
     *
     * @param name Name of the physical resource.
     * @param projector Indicates if the resource has a projector.
     * @param TV Indicates if the resource has a TV set.
     * @param totalComputer Total number of available computers.
     */
    public Physical(String name, boolean projector, boolean TV, int totalComputer) {
        super(name);
        this.projector = projector;
        this.TV = TV;
        this.totalComputer = totalComputer;
    }
    
    // Getters y setters

    /**
     * Gets the resource has a projector available.
     *
     * @return true if it has a projector, false otherwise.
     */
    public boolean isProjector() {
        return projector;
    }

    /**
     * Set the resource has a projector available.
     *
     * @param projector true if it has a projector, false otherwise.
     */
    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    /**
     * Get the resource has a TV available.
     *
     * @return true if it has a TV, false otherwise.
     */
    public boolean isTV() {
        return TV;
    }

    /**
     * Set the resource has a TV available.
     *
     * @param TV true if it has a TV, false otherwise.
     */
    public void setTV(boolean TV) {
        this.TV = TV;
    }

    /**
     * Get the total number of computers available on the resource.
     *
     * @return Total number of computers.
     */
    public int getTotalComputer() {
        return totalComputer;
    }

    /**
     * Set the total number of computers available on the resource.
     *
     * @param totalComputer Total number of computers.
     */
    public void setTotalComputer(int totalComputer) {
        this.totalComputer = totalComputer;
    }
}
