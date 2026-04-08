package edu.eci.cvds.labReserves.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    private Schedule schedule;

    @BeforeEach
    void setUp() throws LabReserveException {
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.DECEMBER, 28, 10, 0);
        schedule = new Schedule(fixedNow.toLocalTime(), fixedNow.getDayOfMonth(), fixedNow.getDayOfWeek(), fixedNow.getMonth(), fixedNow.getYear(), "Redes");    }

    /**
     * Verify schedule has a valid DateTime
     * @throws LabReserveException
     */
    @Test
    void testShouldBeValidSchedule() throws LabReserveException {
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.DECEMBER, 27, 12, 0);
        boolean answer = Schedule.validateCorrectDateTime(31, Month.MARCH, 2031, LocalTime.of(14, 0), fixedNow);
        assertTrue(answer);
    }
    /**
     * Verify schedule has not a valid DateTime
     * @throws LabReserveException
     */
    @Test
    void testShouldNotBeValidSchedule() throws LabReserveException {
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.MARCH, 20, 12, 0);
        boolean answer = Schedule.validateCorrectDateTime(19, Month.MARCH, 2030, LocalTime.of(14, 30), fixedNow);
        assertFalse(answer);
    }
    /**
     * Verify schedule throw exception for unexist date
     */
    @Test
    void testShouldThrowExceptionForInvalidDate() {
        assertThrows(LabReserveException.class, () -> {
            Schedule.validateCorrectDateTime(30, Month.FEBRUARY, 2030, LocalTime.of(14, 0), LocalDateTime.now());
        });
    }
    /**
     * Verify schedule is not created for invalid datetime because is expired
     */
    @Test
    void testShouldNotCreateSchedule()  {
        try{
            LocalDateTime fixedNow = LocalDateTime.of(2030, Month.FEBRUARY, 28, 10, 0);
            schedule = new Schedule(fixedNow.toLocalTime(), fixedNow.getDayOfMonth(), fixedNow.getDayOfWeek(), fixedNow.getMonth(), fixedNow.getYear(), "Redes");
        }catch (LabReserveException e){
            assertEquals("schedule time is invalid",e.getMessage());
        }
    }
    /**
     * Verify reserve dont overlap
     * @throws LabReserveException
     */
    @Test
    void testShouldReservesNotOverlap() throws LabReserveException{
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.DECEMBER, 31, 10, 0);
        Schedule schedule2 = new Schedule(fixedNow.toLocalTime(), fixedNow.getDayOfMonth(), fixedNow.getDayOfWeek(), fixedNow.getMonth(), fixedNow.getYear(), "Redes");
        assertFalse(schedule.overlaps(schedule2));
    }
    /**
     * Verify reserve overlap
     * @throws LabReserveException
     */
    @Test
    void testShouldReservesOverlap() throws LabReserveException{
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.DECEMBER, 28, 10, 0);
        Schedule schedule2 = new Schedule(fixedNow.toLocalTime(), fixedNow.getDayOfMonth(), fixedNow.getDayOfWeek(), fixedNow.getMonth(), fixedNow.getYear(), "Redes");
        schedule.setStartHour(LocalTime.of(10, 0));
        schedule2.setStartHour(LocalTime.of(10, 30));
        schedule.setEndHour(LocalTime.of(11,0 ));
        schedule2.setEndHour(LocalTime.of(12, 0));
        assertTrue(schedule.overlaps(schedule2));
    }
    /**
     * Verify get start hour of a Schedule
     */
    @Test
    void testShouldGetStartHour() {
        schedule.setStartHour(LocalTime.of(10, 0));
        assertEquals(LocalTime.of(10, 0),schedule.getStartHour());
    }
    /**
     * Verify get end hour of schedule
     */
    @Test
    void testShouldGetEndtHour() {
        schedule.setEndHour(LocalTime.of(10, 0));
        assertEquals(LocalTime.of(10, 0),schedule.getEndHour());
    }
    /**
     * Verify get number day of a schedule
     */
    @Test
    void testShouldGetNumberDay() {
        assertEquals(28, schedule.getNumberDay());
    }
    /**
     * Verify get day of a schedule
     */
    @Test
    void testShouldGetDay() {
        assertEquals(DayOfWeek.SATURDAY, schedule.getDay());
    }
    /**
     * Verify get number month of a schedule
     */
    @Test
    void testShouldGetNumberMonth() {
        assertEquals(Month.DECEMBER, schedule.getMonth());
    }
    /**
     * Verify get year of a schedule
     */
    @Test
    void testShouldGetYear() {
        assertEquals(2030, schedule.getYear());
    }
    /**
     * Verify get the schedule of a laboratory
     */
    @Test
    void testShouldGetLaboratory() {
        assertEquals("Redes", schedule.getLaboratory());
    }
}
