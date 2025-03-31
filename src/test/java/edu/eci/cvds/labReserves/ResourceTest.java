package edu.eci.cvds.labReserves;

import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Resource;
import edu.eci.cvds.labReserves.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {
    private Resource resource;

    @BeforeEach
    void setUp() throws LabReserveException {
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
