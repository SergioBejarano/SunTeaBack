package edu.eci.cvds.labReserves.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

class ScheduleReferenceTest {
    private ScheduleReference scheduleReference;

    @BeforeEach
    void setUp() {
        scheduleReference = new ScheduleReference(DayOfWeek.MONDAY, LocalTime.of(7, 0), LocalTime.of(11, 30));
    }

    /**
     * Create undefined schedule reference
     */
    @Test
    void testUndefinedScheduleReference() {
        ScheduleReference scheduleReference1 = new ScheduleReference();
        assertNull(scheduleReference1.getOpeningTime());
        assertNull(scheduleReference1.getDayOfWeek());
    }

    /**
     * Verify if schedule reference has a schedule by the day
     * @throws LabReserveException
     */
    @Test
    void testVerifyScheduleIsWithScheduleReferenceByDay() throws LabReserveException {
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.DECEMBER, 28, 10, 0);
        Schedule scheduletest = new Schedule(fixedNow.toLocalTime(), fixedNow.getDayOfMonth(), fixedNow.getDayOfWeek(), fixedNow.getMonth(), fixedNow.getYear(), "Redes");
        scheduletest.setDay(DayOfWeek.THURSDAY);
        assertFalse(scheduleReference.isWithinSchedule(scheduletest));

    }

    /**
     *verify if schedule reference has a schedule by the start and end hour
     * @throws LabReserveException
     */
    @Test
    void testVerifyScheduleIsWithScheduleReferenceByStartAndEndHour() throws LabReserveException {
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.DECEMBER, 28, 10, 0);
        Schedule scheduletest = new Schedule(fixedNow.toLocalTime(), fixedNow.getDayOfMonth(), fixedNow.getDayOfWeek(), fixedNow.getMonth(), fixedNow.getYear(), "Redes");
        scheduletest.setDay(DayOfWeek.MONDAY);
        scheduletest.setStartHour(LocalTime.of(8, 30));
        scheduletest.setEndHour(LocalTime.of(11, 20));
        assertTrue(scheduleReference.isWithinSchedule(scheduletest));
    }

    /**
     * Verify that the schedule reference has not a schedule for the start hour
     * @throws LabReserveException
     */
    @Test
    void testVerifyScheduleisWithinScheduleReferenceByStartHour() throws LabReserveException {
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.DECEMBER, 28, 10, 0);
        Schedule scheduletest = new Schedule(fixedNow.toLocalTime(), fixedNow.getDayOfMonth(), fixedNow.getDayOfWeek(), fixedNow.getMonth(), fixedNow.getYear(), "Redes");
        scheduletest.setDay(DayOfWeek.MONDAY);
        scheduletest.setStartHour(LocalTime.of(6, 30));
        scheduletest.setEndHour(LocalTime.of(11, 20));
        assertFalse(scheduleReference.isWithinSchedule(scheduletest));
    }

    /**
     * Verify that schedule reference has not a schedule for the end hour
     * @throws LabReserveException
     */
    @Test
    void testVerifyScheduleisWithinScheduleReferenceByEndHour() throws LabReserveException {
        LocalDateTime fixedNow = LocalDateTime.of(2030, Month.DECEMBER, 28, 10, 0);
        Schedule scheduletest = new Schedule(fixedNow.toLocalTime(), fixedNow.getDayOfMonth(), fixedNow.getDayOfWeek(), fixedNow.getMonth(), fixedNow.getYear(), "Redes");
        scheduletest.setDay(DayOfWeek.MONDAY);
        scheduletest.setStartHour(LocalTime.of(7,30));
        scheduletest.setEndHour(LocalTime.of(12,20));
        assertFalse(scheduleReference.isWithinSchedule(scheduletest));
    }

    /**
     * Verify if set and get opening time
     */
    @Test
    void testShouldSetAndGetOpeningTime() {
        scheduleReference.setOpeningTime(LocalTime.of(8,0));
        assertEquals(LocalTime.of(8,0),scheduleReference.getOpeningTime());
    }
    /**
     * Verify if set and get Closing time
     */
    @Test
    void testShouldSetAndGetClosingTime() {
        scheduleReference.setClosingTime(LocalTime.of(1,0));
        assertEquals(LocalTime.of(1,0),scheduleReference.getClosingTime());
    }
}
