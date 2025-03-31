package edu.eci.cvds.labReserves.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a laboratory with its respective physical and software resources and available schedules.
 */
public class Laboratory {

    private String abbreviation;
    private String name;
    private int totalCapacity;
    private String location;
    private Physical physicalResource;
    private Software softwareResource;
    private List<ScheduleReference> scheduleReferences;

    /**
     * Default builder. Initializes the lists of physical resources, software resources and schedule references.
     */
    public Laboratory() {
        this.scheduleReferences = new ArrayList<>();
    }

    /**
     * Constructor with parameters to initialize a laboratory with specific data.
     *
     * @param name Name of the laboratory.
     * @param abbreviation Abbreviation of the laboratory.
     * @param totalCapacity Total capacity of the laboratory.
     * @param location Location of the laboratory.
     * @param scheduleReferences List of laboratory reference schedules.
     */
    public Laboratory(String name, String abbreviation, int totalCapacity, String location, List<ScheduleReference> scheduleReferences) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.totalCapacity = totalCapacity;
        this.location = location;
        this.scheduleReferences = scheduleReferences;
    }

    /**
     * Establishes the laboratory's reference times from a map of days and times.
     *
     * @param daySchedules Map associating days of the week with reference times.
     */
    public void setReferenceSchedules(Map<DayOfWeek, ScheduleReference> daySchedules) {
        this.scheduleReferences = new ArrayList<>(daySchedules.values());
    }

    /**
     * Adds or updates a reference timetable according to the day of the week.
     * If a schedule already exists for that day, it updates it; otherwise, it adds it.
     *
     * @param scheduleReference Reference schedule to add or update.
     */
    public void addScheduleReference(ScheduleReference scheduleReference) {
        DayOfWeek day = scheduleReference.getDayOfWeek();
        for (int i = 0; i < scheduleReferences.size(); i++) {
            if (scheduleReferences.get(i).getDayOfWeek().equals(day)) {
                scheduleReferences.set(i, scheduleReference);
                return;
            }
        }
        scheduleReferences.add(scheduleReference);
    }

    /**
     * Add a day available with an opening and closing time.
     *
     * @param day Day of the week when the lab will be available.
     * @param openingTime Opening time.
     * @param closingTime Closing time.
     */
    public void addAvailableDay(DayOfWeek day, LocalTime openingTime, LocalTime closingTime) {
        ScheduleReference reference = new ScheduleReference(day, openingTime, closingTime);
        addScheduleReference(reference);
    }

    // Getters and setters

    /**
     * Get the name of the laboratory.
     *
     * @return Laboratory name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the laboratory.
     *
     * @param name New lab name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the abbreviation of the laboratory.
     *
     * @return Laboratory abbreviation.
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Set the abbreviation of the laboratory.
     *
     * @param abbreviation New laboratory abbreviation.
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Get the total capacity of the laboratory.
     *
     * @return Total capacity.
     */
    public int getTotalCapacity() {
        return totalCapacity;
    }

    /**
     * Set the total capacity of the laboratory.
     *
     * @param totalCapacity New total capacity of the lab.
     */
    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    /**
     * Get the location of the laboratory.
     *
     * @return Lab location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the location of the laboratory.
     *
     * @param location New lab location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the list of physical resources of the laboratory.
     *
     * @return List of physical resources.
     */
    public Physical getPhysicalResource() {
        return physicalResource;
    }

    /**
     * Get the list of software resources of the laboratory.
     *
     * @return List of software resources.
     */
    public Software getSoftwareResource() {
        return softwareResource;
    }

    /**
     * Set the physical resource to the laboratory.
     *
     * @param physicalResource Physical resource to add.
     */
    public void setPhysicalResource(Physical physicalResource) {
        this.physicalResource = physicalResource;
    }

    /**
     * Set the software resource to the laboratory.
     *
     * @param softwareResource software resource to add.
     */
    public void setSoftwareResource(Software softwareResource) {
        this.softwareResource = softwareResource;
    }

    /**
     * Get the list of reference times from the laboratory.
     *
     * @return List of reference schedules.
     */
    public List<ScheduleReference> getScheduleReferences() {
        return scheduleReferences;
    }

    /**
     * Set the list of laboratory reference times.
     *
     * @param scheduleReferences New reference schedule list.
     */
    public void setScheduleReferences(List<ScheduleReference> scheduleReferences) {
        this.scheduleReferences = scheduleReferences;
    }

    /**
     * Get the reference time for a specific day.
     *
     * @param day Day of the week to query.
     * @return Corresponding reference time, or null if it does not exist.
     */
    public ScheduleReference getScheduleReferenceForDay(DayOfWeek day) {
        for (ScheduleReference ref : scheduleReferences) {
            if (ref.getDayOfWeek().equals(day)) {
                return ref;
            }
        }
        return null;
    }
}
