package edu.eci.cvds.labReserves.model;

import java.io.InputStream;

/**
 * Representa un recurso que puede estar disponible en un laboratorio.
 */
public class Resource {

    private String name;

    /**
     * Constructor por defecto de la clase Resource.
     */
    public Resource() {
    }

    /**
     * Constructor que inicializa un recurso con un nombre específico.
     *
     * @param name Nombre del recurso.
     */
    public Resource(String name) {
        this.name = name;
    }

    /**
     * Obtiene el nombre del recurso.
     *
     * @return Nombre del recurso.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del recurso.
     *
     * @param name Nombre del recurso.
     */
    public void setName(String name) {
        this.name = name;
    }

}
