package edu.eci.cvds.labReserves.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * Represents a time schedule for a laboratory reservation.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {

    /** Start time of the schedule. */
    private LocalTime startHour;

    /** End time of the schedule. */
    private LocalTime endHour;

    /** Day of the month. */
    private int numberDay;

    /** Day of the week. */
    private DayOfWeek day;

    /** Month of the schedule. */
    private Month month;

    /** Year of the schedule. */
    private int year;

    /** Laboratory associated with this schedule. */
    private String laboratory;

    /**
     * Constructs a Schedule object with specific details.
     *
     * @param pStartHour  The start time.
     * @param pNumberDay  The day of the month.
     * @param pDay        The day of the week.
     * @param pMonth      The month.
     * @param pYear       The year.
     * @param pLaboratory The laboratory name.
     * @throws LabReserveException If the values are invalid.
     */
    public Schedule(final LocalTime pStartHour, final int pNumberDay,
                    final DayOfWeek pDay, final Month pMonth,
                    final int pYear, final String pLaboratory)
            throws LabReserveException {
        LocalDateTime referenceNow = LocalDateTime.now();
        boolean correctSchedule = validateCorrectDateTime(pNumberDay, pMonth,
                pYear, pStartHour, referenceNow);
        if (correctSchedule) {
            this.startHour = pStartHour;
            this.numberDay = pNumberDay;
            this.day = pDay;
            this.month = pMonth;
            this.year = pYear;
            this.laboratory = pLaboratory;
        } else {
            throw new LabReserveException(
                LabReserveException.INVALID_SCHEDULE_TIME);
        }
    }

    /**
     * Checks if this schedule overlaps with another.
     *
     * @param other The other schedule to compare.
     * @return true if they overlap.
     */
    public final boolean overlaps(final Schedule other) {
        if (!this.day.equals(other.day)) {
            return false;
        }
        return (startHour.isBefore(other.endHour)
                && endHour.isAfter(other.startHour));
    }

    /**
     * Validates that a given date and time are not in the past.
     *
     * @param pNumDay      Day of the month.
     * @param pMonth       Month.
     * @param pYear        Year.
     * @param pGivenHour   Specific time.
     * @param pRefNow      Reference date and time.
     * @return true if valid.
     * @throws LabReserveException If date is invalid.
     */
    public static boolean validateCorrectDateTime(final int pNumDay,
                                                   final Month pMonth,
                                                   final int pYear,
                                                   final LocalTime pGivenHour,
                                                   final LocalDateTime pRefNow)
            throws LabReserveException {
        try {
            LocalDateTime inputDateTime = LocalDateTime.of(pYear, pMonth,
                    pNumDay, pGivenHour.getHour(), pGivenHour.getMinute());
            return !inputDateTime.isBefore(pRefNow);
        } catch (DateTimeException e) {
            throw new LabReserveException("Invalid date provided: "
                    + e.getMessage());
        }
    }
}
