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
 * The ReserveRequest class represents a request for a reservae and its schedule
 * It contains details about the reservation type, reason, state,
 * user information, and schedule details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRequest {

    /**
     * -- GETTER --
     * Getters for the class fields.
     */
    private String type; //type of this reserve at moment
    private String reason; //reason why this reserve was created
    private String state; //state of this reserve at moment
    private int userId; //id of user that made it

    private LocalTime startHour; //start of resource
    private int numberDay; //number of day that generate resource
    private DayOfWeek day; //day that generate resource
    private Month month; //month that generate resource
    private int year; //year that generate resource
    private String laboratoryName; //laboratory of the reserve

    /**
     * Constructs a ReserveRequest based on a ReserveMongodb and ScheduleMongodb.
     *
     * @param reserveMongodb  The reservation data.
     * @param scheduleMongodb The schedule data.
     */
    public ReserveRequest(ReserveMongodb reserveMongodb, ScheduleMongodb scheduleMongodb) {
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
