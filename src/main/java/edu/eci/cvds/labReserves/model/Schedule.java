package edu.eci.cvds.labReserves.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.*;

/**
 * The Schedule class represents a time schedule with a specific time of reserve
 * It includes information about hours, days, month and year
 * It includes methods to check if two schedules overlap.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {

    /**
     * -- GETTER --
     *  Get the start time of the schedule.
     *
     *
     * -- SETTER --
     *  Set the start time of the schedule.
     *
     @return The start time
      * @param startHour The start time
      *
     */
    private LocalTime startHour; //start of resource
    /**
     * -- GETTER --
     *  Get the end time of the schedule.
     *
     *
     * -- SETTER --
     *  Set the end time of the schedule.
     *
     @return The end time
      * @param endHour The end time
     */
    private LocalTime endHour; //end of resource

    /**
     * -- GETTER --
     *  Get the day of the month.
     *
     *
     * -- SETTER --
     *  Set the day of the month.
     *
     @return The day of the month
      * @param numberDay The day of the month
      *
     */
    private int numberDay; //number of day that generate resource
    /**
     * -- GETTER --
     *  Get the day of the week.
     *
     *
     * -- SETTER --
     *  Set the day of the week.
     *
     @return The day of the week
      * @param day The day of the week
      *
     */
    private DayOfWeek day; //day that generate resource
    /**
     * -- GETTER --
     *  Get the month of the schedule.
     *
     *
     * -- SETTER --
     *  Set the month of the schedule.
     *
     @return The month of the schedule
      * @param month The month of the schedule
      *
     */
    private Month month; //month that generate resource
    /**
     * -- GETTER --
     *  Get the year of the schedule.
     *
     *
     * -- SETTER --
     *  Set the year of the schedule.
     *
     @return The year of the schedule
      * @param year The year of the schedule
      *
     */
    private int year; //year that generate resource

    /**
     * -- GETTER --
     *  Get the Laboratory of the schedule.
     *
     *
     * -- SETTER --
     *  Set the Laboratory of the schedule.
     *
     @return The Laboratory
      * @param laboratory The schedule ID
     */
    private String laboratory; //laboratory that resolve some reserves

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
        LocalDateTime referenceNow = LocalDateTime.now();
        boolean correctSchedule = validateCorrectDateTime(numberDay,month,year,startHour,referenceNow);
        if(correctSchedule){
            setStartHour(startHour);
            setNumberDay(numberDay);
            setDay(day);
            setMonth(month);
            setYear(year);
            setLaboratory(laboratory);
        }else{
            throw new LabReserveException(LabReserveException.INVALID_SCHEDULE_TIME);
        }
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

    /**
     * Validates that a given date and time are not earlier than the provided reference date and time.
     *
     * @param numberDay     Day of the month to validate.
     * @param month         Month corresponding to the provided day.
     * @param year          Year corresponding to the provided date.
     * @param givenHour     Specific time to validate.
     * @param referenceNow  Reference date and time for validation.
     * @return {@code true} if the provided date and time are not earlier than the reference, {@code false} otherwise.
     * @throws LabReserveException If the provided date is invalid (e.g., February 30th).
     */
    public static boolean validateCorrectDateTime(int numberDay, Month month, int year,
                                                  LocalTime givenHour, LocalDateTime referenceNow) throws LabReserveException {
        try {
            LocalDateTime inputDateTime = LocalDateTime.of(year, month, numberDay, givenHour.getHour(), givenHour.getMinute());
            return !inputDateTime.isBefore(referenceNow);
        } catch (DateTimeException e) {
            throw new LabReserveException("Invalid date provided: " + e.getMessage());
        }
    }
}
