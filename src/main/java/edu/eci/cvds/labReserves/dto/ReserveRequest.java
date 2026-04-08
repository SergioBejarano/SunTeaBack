package edu.eci.cvds.labReserves.dto;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;

/**
 * The ReserveRequest class represents a request for a reserve and its schedule.
 * It contains details about the reservation type, reason, state,
 * user information, and schedule details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRequest {

    /** Type of this reserve. */
    private String type;

    /** Reason why this reserve was created. */
    private String reason;

    /** State of this reserve at the moment. */
    private String state;

    /** ID of the user that made the reservation. */
    private int userId;

    /** Start hour of the resource. */
    private LocalTime startHour;

    /** Day number of the resource. */
    private int numberDay;

    /** Day of the week of the resource. */
    private DayOfWeek day;

    /** Month of the resource. */
    private Month month;

    /** Year of the resource. */
    private int year;

    /** Laboratory associated with the reserve. */
    private String laboratoryName;

    /**
     * Constructs a ReserveRequest based on MongoDB objects.
     *
     * @param reserveMongodb  The reservation data.
     * @param scheduleMongodb The schedule data.
     */
    public ReserveRequest(final ReserveMongodb reserveMongodb,
                          final ScheduleMongodb scheduleMongodb) {
        this.type = reserveMongodb.getType();
        this.reason = reserveMongodb.getReason();
        this.state = reserveMongodb.getState();
        this.userId = reserveMongodb.getUser();

        this.startHour = scheduleMongodb.getStartHour();
        this.numberDay = scheduleMongodb.getNumberDay();
        this.day = scheduleMongodb.getDay();
        this.month = scheduleMongodb.getMonth();
        this.year = scheduleMongodb.getYear();
        this.laboratoryName = scheduleMongodb.getLaboratory();
    }
}
