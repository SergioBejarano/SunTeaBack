package edu.eci.cvds.labReserves.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a resource that may be available in a laboratory.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resource {

    /**
     * -- GETTER --
     *  Get the name of the resource.
     *
     *
     * -- SETTER --
     *  Set the name of the resource.
     *
     @return Resource name.
      * @param name Resource name.
     */
    private String name;

}
