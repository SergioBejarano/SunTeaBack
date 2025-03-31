package edu.eci.cvds.labReserves.model;

import java.io.InputStream;

/**
 * Represents a resource that may be available in a laboratory.
 */
public class Resource {

    private String name;

    /**
     * Default constructor of the Resource class.
     */
    public Resource() {
    }

    /**
     * Constructor that initializes a resource with a specific name.
     *
     * @param name Name of the resource.
     */
    public Resource(String name) {
        this.name = name;
    }

    /**
     * Get the name of the resource.
     *
     * @return Resource name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the resource.
     *
     * @param name Resource name.
     */
    public void setName(String name) {
        this.name = name;
    }

}
