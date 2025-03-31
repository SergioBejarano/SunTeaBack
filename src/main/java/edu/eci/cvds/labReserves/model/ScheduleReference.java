package edu.eci.cvds.labReserves.model;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents a reference timetable for a laboratory, including the day of the week,
 * opening time and closing time. It also provides methods to verify
 * if a specific schedule is within the allowed range or if it is available.
 */
public class ScheduleReference {

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime openingTime;
    
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime closingTime;
    
    @JsonSerialize(using = DayOfWeekSerializer.class)
    @JsonDeserialize(using = DayOfWeekDeserializer.class)
    private DayOfWeek dayOfWeek;

    /**
     * Default constructor that initializes the schedule without defined values.
     */
    public ScheduleReference() {
        this.openingTime = null;
        this.closingTime = null;
        this.dayOfWeek = null;
    }

    /**
     * Constructor that allows to define a reference timetable.
     *
     * @param dayOfWeek Day of the week in which the timetable applies.
     * @param openingTime Opening time.
     * @param closingTime Closing time.
     */
    public ScheduleReference(DayOfWeek dayOfWeek, LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Checks if a given schedule is in the set time range.
     *
     * @param schedule Schedule to check.
     * @return true if the schedule is available, false otherwise.
     */
    public boolean isWithinSchedule(Schedule schedule) {
        DayOfWeek scheduleDay = schedule.getDay();
        if (!dayOfWeek.equals(scheduleDay)) {
            return false;
        }
        return !schedule.getStartHour().isBefore(openingTime) && !schedule.getEndHour().isAfter(closingTime);
    }

    /**
     * Checks if a given time slot is available at this reservation time.
     *
     * @param schedule Schedule to check.
     * @return true if the schedule is available, false otherwise.
     */
    public boolean isAvailable(Schedule schedule) {
        DayOfWeek scheduleDay = schedule.getDay();
        LocalTime scheduleStartTime = schedule.getStartHour();
        LocalTime scheduleEndTime = schedule.getEndHour();

        boolean isDayAvailable = dayOfWeek.equals(scheduleDay);
        boolean isTimeWithinRange = !scheduleStartTime.isBefore(this.openingTime) && !scheduleEndTime.isAfter(this.closingTime);

        return isDayAvailable && isTimeWithinRange;
    }

    // Getters y setters

    /**
     * Get the opening time of the lab.
     *
     * @return Opening time.
     */
    public LocalTime getOpeningTime() {
        return openingTime;
    }

    /**
     * Set the opening time of the laboratory.
     *
     * @param openingTime New opening time.
     */
    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    /**
     * Get the closing time of the laboratory.
     *
     * @return Closing time.
     */
    public LocalTime getClosingTime() {
        return closingTime;
    }

    /**
     * Set the closing time of the laboratory.
     *
     * @param closingTime New closing time.
     */
    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    /**
     * Get the day of the week on which this schedule applies.
     *
     * @return Day of the week.
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Set the day of the week on which this schedule applies.
     *
     * @param dayOfWeek New day of the week.
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    // Clases internas para la serialización y deserialización de LocalTime y DayOfWeek

    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value != null) {
                gen.writeString(value.toString());
            }
        }
    }

    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String timeString = p.getValueAsString();
            return timeString != null ? LocalTime.parse(timeString) : null;
        }
    }
    
    public static class DayOfWeekSerializer extends JsonSerializer<DayOfWeek> {
        @Override
        public void serialize(DayOfWeek value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value != null) {
                gen.writeString(value.name());
            }
        }
    }
    
    public static class DayOfWeekDeserializer extends JsonDeserializer<DayOfWeek> {
        @Override
        public DayOfWeek deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String dayString = p.getValueAsString();
            return dayString != null ? DayOfWeek.valueOf(dayString) : null;
        }
    }
}