package edu.eci.cvds.labReserves.model;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.LocalTime;

/**
 * The Schedule class represents a time schedule with a specific time of reserve
 * It includes information about hours, days, month and year
 * It includes methods to check if two schedules overlap.
 */
public class Schedule {

    private LocalTime startHour; //start of resource
    private LocalTime endHour; //end of resource

    private int numberDay; //number of day that generate resource
    private DayOfWeek day; //day that generate resource
    private Month month; //month that generate resource
    private int year; //year that generate resource

    private String laboratory; //laboratory that resolve some reserves

    /**
     * Default constructor.
     */
    public Schedule() { }

    /**
     * Constructs a Schedule object with specified start time, day, month, and year.
     * @param startHour The start time of the schedule
     * @param numberDay The day of the month
     * @param day The day of the week
     * @param month The month of the year
     * @param year The year of the schedule
     * @throws LabReserveException If the provided values are invalid
     */
    public Schedule(LocalTime startHour, int numberDay, DayOfWeek day,
                    Month month, int year, String laboratory) throws LabReserveException {
        setStartHour(startHour);
        setNumberDay(numberDay);
        setDay(day);
        setMonth(month);
        setYear(year);
        setLaboratory(laboratory);
    }

    /**
     * Checks if this schedule overlaps with another schedule.
     * @param other The other schedule to compare with
     * @return true if the schedules overlap, false otherwise
     */
    public boolean overlaps(Schedule other) {
        if (!this.day.equals(other.day)) {
            return false;
        }
        return (startHour.isBefore(other.endHour) && endHour.isAfter(other.startHour));
    }

    // Getters y setters

    /**
     * Gets the start time of the schedule.
     * @return The start time
     */
    public LocalTime getStartHour() {
        return startHour;
    }

    /**
     * Sets the start time of the schedule.
     * @param startHour The start time
     * @throws LabReserveException If the start time is before the current time
     */
    public void setStartHour(LocalTime startHour) throws LabReserveException {
        this.startHour = startHour;
    }

    /**
     * Gets the end time of the schedule.
     * @return The end time
     */
    public LocalTime getEndHour() {
        return endHour;
    }

    /**
     * Sets the end time of the schedule.
     * @param endHour The end time
     */
    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    /**
     * Gets the day of the month.
     * @return The day of the month
     */
    public int getNumberDay() {
        return numberDay;
    }

    /**
     * Sets the day of the month.
     * @param numberDay The day of the month
     * @throws LabReserveException If the day is before the current day
     */
    public void setNumberDay(int numberDay) throws LabReserveException {
        this.numberDay = numberDay;
    }

    /**
     * Gets the day of the week.
     * @return The day of the week
     */
    public DayOfWeek getDay() {
        return day;
    }

    /**
     * Sets the day of the week.
     * @param day The day of the week
     * @throws LabReserveException If the day is before the current day
     */
    public void setDay(DayOfWeek day) throws LabReserveException {
        this.day = day;
    }

    /**
     * Gets the month of the schedule.
     * @return The month of the schedule
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Sets the month of the schedule.
     * @param month The month of the schedule
     * @throws LabReserveException If the month is before the current month
     */
    public void setMonth(Month month) throws LabReserveException {
        this.month = month;
    }

    /**
     * Gets the year of the schedule.
     * @return The year of the schedule
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the schedule.
     * @param year The year of the schedule
     * @throws LabReserveException If the year is before the current year
     */
    public void setYear(int year) throws LabReserveException {
        this.year = year;
    }

    /**
     * Sets the Laboratory of the schedule.
     * @param laboratory The schedule ID
     */
    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    /**
     * Gets the Laboratory of the schedule.
     *
     * @return The Laboratory
     */
    public String getLaboratory() {
        return laboratory;
    }
}