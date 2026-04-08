package edu.eci.cvds.labReserves.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicalTest {

    private Physical physical;

    @BeforeEach
    void setUp() {
        physical = new Physical("pantalla", false, true, 10);
    }

    /**
     * Validate if it has projector
     */
    @Test
    void testVerifyIfHasProjector() {
        assertFalse(physical.isProjector());
    }

    /**
     * validate if can change projector
     */
    @Test
    void testChangeProjector() {
        physical.setProjector(true);
        assertTrue(physical.isProjector());
    }

    /**
     * Validate if it has TV
     */
    @Test
    void testVerifyIfHasTV() {
        assertTrue(physical.isTV());
    }

    /**
     * Validate if can change TV
     */
    @Test
    void testChangeTV() {
        physical.setTV(false);
        assertFalse(physical.isTV());
    }

    /**
     * Validate the total computers
     */
    @Test
    void testVerifyTotalComputer() {
        assertEquals(10, physical.getTotalComputer());
    }

    /**
     * Validate if can change total computers
     */
    @Test
    void testChangeTotalComputer() {
        physical.setTotalComputer(20);
        assertEquals(20, physical.getTotalComputer());
    }
    /**
     * Validate if should have a TV
     */
    @Test
    void shouldSetAndGetTV() {
        Physical physical = new Physical();
        physical.setTV(true);
        assertTrue(physical.isTV());
        physical.setTV(false);
        assertFalse(physical.isTV());
    }

}
