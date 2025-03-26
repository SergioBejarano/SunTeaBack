package edu.eci.cvds.labReserves;

import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Physical;
import edu.eci.cvds.labReserves.model.Software;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SoftwareTest {
    private Software software;

    @BeforeEach
    void setUp() {
        software = new Software("cisco","windows",false);
    }

    /**
     * Verify if set and get operative system is correct
     */
    @Test
    void testSetAndGetOperativeSystem(){
        software.setOperativeSystem("linux");
        assertEquals("linux",software.getOperativeSystem());
    }
    /**
     * Verify if set and get partition is correct
     */
    @Test
    void testSetAndGetPartition() {
        software.setPartition(true);
        assertTrue(software.isPartition());
    }
}
