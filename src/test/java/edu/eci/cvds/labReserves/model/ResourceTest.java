package edu.eci.cvds.labReserves.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {
    private Resource resource;

    @BeforeEach
    void setUp() {
        resource = new Resource("monitor");
    }

    /**
     * Verify if set name is valid
     */
    @Test
    void testSetName() {
        resource.setName("proyector");
        assertEquals("proyector",resource.getName());
    }


}
