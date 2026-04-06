package edu.eci.cvds.labReserves.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReserveTest {

    private Reserve reseve;

    @BeforeEach
    void setUp() throws LabReserveException {
        reseve = new Reserve("lesson", "CVDS", 1);
    }

    /**
     * Verify a teacher can make a reserve
     */
    @Test
    void testMakeReserveOfTeachers() {
        try {
            Reserve reserveTest = new Reserve("lesson", "POOB", 2);
            assertEquals("POOB", reserveTest.getReason());
            assertEquals(2, reserveTest.getUser());
        }catch (LabReserveException e){
            fail(e.getMessage());
        }
    }

    /**
     * Verify an admin can make a reserve
     * @throws LabReserveException
     */
    @Test
    void testMakeReserveOfAdmin() throws LabReserveException {
        try {
            Reserve reserveTest = new Reserve("lesson", "MBDA", 3);
            assertEquals("MBDA", reserveTest.getReason());
            assertEquals(3, reserveTest.getUser());
        }catch (LabReserveException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Validate Can not create a reserve for invalid type
     */
    @Test
    void testShouldNotCreateReserveforInvalidType() {
        LabReserveException exception = assertThrows(LabReserveException.class, () -> {
            new Reserve("semillero", "MBDA", 3);
        });

        assertEquals("type reserve not found", exception.getMessage());
    }

    /**
     * Validate can not create a reserve for null reason
     */
    @Test
    void testShouldNotCreateReserveforInvalidReason() {
        LabReserveException exception = assertThrows(LabReserveException.class, () -> {
            new Reserve("lesson", "", 3);
        });

        assertEquals("reason reserve not found", exception.getMessage());
    }

    /**
     * Validate can not create a reserve for invalid user id
     */
    @Test
    void testShouldNotCreateReserveforInvalidUserId() {
        LabReserveException exception = assertThrows(LabReserveException.class, () -> {
            new Reserve("lesson", "gdgfe", -1);
        });
        assertEquals("user reserve not found", exception.getMessage());
    }

    /**
     * Validate can change reserve schedule
     */
    @Test
    void testReserveChangeSchedule() {
        reseve.setSchedule("3");
        assertEquals("3",reseve.getSchedule());
    }

    /**
     * Validate can change reserve reason
     */
    @Test
    void testReserveChangeReason() {
        reseve.setReason("test new reason");
        assertEquals("test new reason",reseve.getReason());
    }

    /**
     * Validate can change reserve user
     * @throws LabReserveException
     */
    @Test
    void testReserveChangeUser() throws LabReserveException{
        User irma = new User(2, "Irma", "irma@mail", "irma","teacher");
        reseve.setUser(irma.getId());
        assertEquals(2,reseve.getUser());
    }

    /**
     * validate can change reserve type
     */
    @Test
    void testShouldSetReserveType() {
        try {
            reseve.setType("available");
            assertEquals("available", reseve.getType());
        }catch (LabReserveException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Validate can not change reserve type
     */
    @Test
    void testShouldNotSetReserveType() {
        try {
            reseve.setType("not found");
        }catch (LabReserveException e) {
            assertEquals("type reserve not found", LabReserveException.TYPE_RESERVE_NOT_FOUND);
        }
    }

    /**
     * Validate can change reserve state
     * @throws LabReserveException
     */
    @Test
    void testShouldSetState() throws LabReserveException{
        reseve.setState("free");
        assertEquals("free",reseve.getState());
    }

    /**
     * validate can not change reserve state for invalid state
     */
    @Test
    void testShouldNotSetState() {
        try {
            reseve.setState("libre");
        }catch (LabReserveException e) {
            assertEquals("state reserve not found", LabReserveException.STATE_RESERVE_NOT_FOUND);
        }
    }
}
