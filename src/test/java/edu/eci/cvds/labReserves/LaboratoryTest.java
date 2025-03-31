package edu.eci.cvds.labReserves;

import edu.eci.cvds.labReserves.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTest {

    private Laboratory laboratory;

    @BeforeEach
    void setUp() {
        List<ScheduleReference> scheduleReferences = new ArrayList<>();
        scheduleReferences.add(new ScheduleReference(DayOfWeek.FRIDAY,LocalTime.of(7,0),LocalTime.of(5,30)));
        laboratory = new Laboratory("redes","RECO",60,"B",scheduleReferences);
    }

    /**
     * Verify if can set reference schedule reference for a laboratory
     */
    @Test
    void testSetReferenceSchedules() {
        Map<DayOfWeek, ScheduleReference> daySchedules = new HashMap<>();
        daySchedules.put(DayOfWeek.MONDAY, new ScheduleReference(DayOfWeek.MONDAY,LocalTime.of(7,0),LocalTime.of(5,30)));
        daySchedules.put(DayOfWeek.WEDNESDAY, new ScheduleReference(DayOfWeek.WEDNESDAY,LocalTime.of(7,0),LocalTime.of(5,30)));
        ArrayList arraySchedules = new ArrayList<>(daySchedules.values());
        laboratory.setReferenceSchedules(daySchedules);
        assertEquals(arraySchedules,laboratory.getScheduleReferences());
    }

    /**
     * Can add schedule reference for a laboratory
     */
    @Test
    void testShouldAddScheduleReference() {
        ScheduleReference testSchedule = new ScheduleReference(DayOfWeek.MONDAY,LocalTime.of(7,0),LocalTime.of(8,30));
        laboratory.addScheduleReference(testSchedule);
        List<ScheduleReference> scheduleReferences = laboratory.getScheduleReferences();
        assertEquals(testSchedule,scheduleReferences.get(1));
    }

    /**
     * Verify that can save a schedule reference
     */
    @Test
    void testShouldSaveScheduleReference() {
        ScheduleReference testSchedule = new ScheduleReference(DayOfWeek.FRIDAY,LocalTime.of(7,0),LocalTime.of(8,30));
        laboratory.addScheduleReference(testSchedule);
        List<ScheduleReference> scheduleReferences = laboratory.getScheduleReferences();
        assertEquals(testSchedule,scheduleReferences.get(0));
    }

    /**
     * Validate can add a day with an specific start and end hours
     */
    @Test
    void testShouldAddAvailableDay() {
        laboratory.addAvailableDay(DayOfWeek.SUNDAY,LocalTime.of(7,0),LocalTime.of(10,0));
        List<ScheduleReference> scheduleReferences = laboratory.getScheduleReferences();
        assertEquals(DayOfWeek.SUNDAY,scheduleReferences.get(1).getDayOfWeek());
        assertEquals(LocalTime.of(7,0),scheduleReferences.get(1).getOpeningTime());
        assertEquals(LocalTime.of(10,0),scheduleReferences.get(1).getClosingTime());
    }

    /**
     * Verify if can get and set the physical resources for a lab
     */
    @Test
    void testShouldSetAndGetPhysicalResource() {
        Physical physicaltest = new Physical("TV",false,true,10);
        laboratory.setPhysicalResource(physicaltest);
        assertEquals(physicaltest,laboratory.getPhysicalResource());
    }

    /**
     * Verify if can get and set the Software resources for a lab
     */
    @Test
    void testShouldSetAndGetSoftwareResource() {
        Software softwaretest = new Software("BIZAGI","Windows",false);
        laboratory.setSoftwareResource(softwaretest);
        assertEquals(softwaretest,laboratory.getSoftwareResource());
    }

    /**
     * Verify if can set and get name of a laboratory
     */
    @Test
    void testShouldSetAndGetName() {
        laboratory.setName("software");
        assertEquals("software",laboratory.getName());
    }

    /**
     * verify if can get and set the abbreviation of a laboratory
     */
    @Test
    void testShouldSetAndGetAbbreviation() {
        laboratory.setAbbreviation("SFTW");
        assertEquals("SFTW",laboratory.getAbbreviation());
    }

    /**
     * Verify if can set and get the total capacity of a laboratory
     */
    @Test
    void testShouldSetAndGetTotalCapacity() {
        laboratory.setTotalCapacity(200);
        assertEquals(200,laboratory.getTotalCapacity());
    }

    /**
     * Verify if can set and get the location of a laboratory
     */
    @Test
    void testShouldSetAndGetLocation() {
            laboratory.setLocation("B0");
        assertEquals("B0",laboratory.getLocation());
    }

    /**
     * Verify can set a schedule reference for a laboratory
     */
    @Test
    void testShouldSetScheduleReference() {
        Map<DayOfWeek, ScheduleReference> daySchedules = new HashMap<>();
        daySchedules.put(DayOfWeek.MONDAY, new ScheduleReference(DayOfWeek.MONDAY,LocalTime.of(7,0),LocalTime.of(5,30)));
        daySchedules.put(DayOfWeek.WEDNESDAY, new ScheduleReference(DayOfWeek.WEDNESDAY,LocalTime.of(7,0),LocalTime.of(5,30)));
        ArrayList arraySchedules = new ArrayList<>(daySchedules.values());
        laboratory.setScheduleReferences(arraySchedules);
        assertEquals(arraySchedules,laboratory.getScheduleReferences());
    }

    /**
     * Verify that can get the schedule reference by a day
     */
    @Test
    void testShouldGetScheduleReferenceForDay() {
        assertEquals(laboratory.getScheduleReferences().get(0),laboratory.getScheduleReferenceForDay(DayOfWeek.FRIDAY));
    }

    /**
     * verify can not get schedule reference by a day
     */
    @Test
    void testShouldNotGetScheduleReferenceForDay() {
        assertEquals(null,laboratory.getScheduleReferenceForDay(DayOfWeek.MONDAY));
    }
}
