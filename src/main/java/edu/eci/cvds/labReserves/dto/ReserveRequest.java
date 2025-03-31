package edu.eci.cvds.labReserves.dto;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import edu.eci.cvds.labReserves.model.User;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;

/**
 * The ReserveRequest class represents a request for a reservae and its schedule
 * It contains details about the reservation type, reason, state,
 * user information, and schedule details.
 */
public class ReserveRequest {

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
     * Default constructor.
     */
    public ReserveRequest() {}

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

    /** Getters for the class fields. */

    public String getType() {
        return type;
    }
    public String getReason() {
        return reason;
    }
    public String getState() {
        return state;
    }
    public int getUserId() {
        return userId;
    }
    public LocalTime getStartHour() {
        return startHour;
    }
    public int getNumberDay() {
        return numberDay;
    }
    public DayOfWeek getDay() {
        return day;
    }
    public Month getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    public String getLaboratoryName() { return laboratoryName; }
}
