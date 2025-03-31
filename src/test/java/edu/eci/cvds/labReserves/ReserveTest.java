package edu.eci.cvds.labReserves;

import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Reserve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReserveTest {

    private Reserve reseve;

    @BeforeEach
    void setUp() throws LabReserveException {
        User rodrigo = new User(1, "Rodrigo", "rodrigo@mail", "rodrigo","teacher");
        reseve = new Reserve("lesson", "CVDS", 1);
    }

    /**
     * Verify a teacher can make a reserve
     * @throws LabReserveException
     */
    @Test
    void testMakeReserveOfTeachers() throws LabReserveException {
        User irma = new User(2, "Irma", "irma@mail", "irma","teacher");
        try {
            Reserve reserveTest = new Reserve("lesson", "POOB", 2);
            assertEquals(reserveTest.getReason(), "POOB");
            assertEquals(reserveTest.getUser(), 2);
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
        User aurora = new User(3, "Aurora", "aurora@mail", "aurora","admin");
        try {
            Reserve reserveTest = new Reserve("lesson", "MBDA", 3);
            assertEquals(reserveTest.getReason(), "MBDA");
            assertEquals(reserveTest.getUser(), 3);
        }catch (LabReserveException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Validate Can not create a reserve for invalid type
     * @throws LabReserveException
     */
    @Test
    void testShouldNotCreateReserveforInvalidType() throws LabReserveException{
        User irma = new User(2, "Irma", "irma@mail", "irma","teacher");
        LabReserveException exception = assertThrows(LabReserveException.class, () -> {
            new Reserve("semillero", "MBDA", 3);
        });

        assertEquals("type reserve not found", exception.getMessage());
    }

    /**
     * Validate can not create a reserve for null reason
     * @throws LabReserveException
     */
    @Test
    void testShouldNotCreateReserveforInvalidReason() throws LabReserveException{
        User irma = new User(2, "Irma", "irma@mail", "irma","teacher");
        LabReserveException exception = assertThrows(LabReserveException.class, () -> {
            new Reserve("lesson", "", 3);
        });

        assertEquals("reason reserve not found", exception.getMessage());
    }

    /**
     * Validate can not create a reserve for invalid user id
     * @throws LabReserveException
     */
    @Test
    void testShouldNotCreateReserveforInvalidUserId() throws LabReserveException{
        User irma = new User(2, "Irma", "irma@mail", "irma","teacher");
        LabReserveException exception = assertThrows(LabReserveException.class, () -> {
            new Reserve("lesson", "gdgfe", -1);
        });
        assertEquals("user reserve not found", exception.getMessage());
    }

    /**
     * Validate can change reserve schedule
     * @throws LabReserveException
     */
    @Test
    void testReserveChangeSchedule() throws LabReserveException{
        reseve.setSchedule("3");
        assertEquals("3",reseve.getSchedule());
    }

    /**
     * Validate can change reserve reason
     * @throws LabReserveException
     */
    @Test
    void testReserveChangeReason() throws LabReserveException{
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
     * @throws LabReserveException
     */
    @Test
    void testShouldSetReserveType() throws LabReserveException{
        try {
            reseve.setType("available");
            assertEquals("available", reseve.getType());
        }catch (LabReserveException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Validate can not change reserve type
     * @throws LabReserveException
     */
    @Test
    void testShouldNotSetReserveType() throws LabReserveException{
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
     * @throws LabReserveException
     */
    @Test
    void testShouldNotSetState() throws LabReserveException{
        try {
            reseve.setState("libre");
        }catch (LabReserveException e) {
            assertEquals("state reserve not found", LabReserveException.STATE_RESERVE_NOT_FOUND);
        }
    }
}
