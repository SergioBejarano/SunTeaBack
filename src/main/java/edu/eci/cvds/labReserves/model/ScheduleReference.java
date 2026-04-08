package edu.eci.cvds.labReserves.model;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un horario de referencia para un laboratorio, incluyendo el día de la semana,
 * la hora de apertura y la hora de cierre. También proporciona métodos para verificar
 * si un horario específico está dentro del rango permitido o si está disponible.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleReference {

    /**
     * -- GETTER --
     *  Obtiene la hora de apertura del laboratorio.
     *
     *
     * -- SETTER --
     *  Establece la hora de apertura del laboratorio.
     *
     @return Hora de apertura.
      * @param openingTime Nueva hora de apertura.
     */
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime openingTime;

    /**
     * -- GETTER --
     *  Obtiene la hora de cierre del laboratorio.
     *
     *
     * -- SETTER --
     *  Establece la hora de cierre del laboratorio.
     *
     @return Hora de cierre.
      * @param closingTime Nueva hora de cierre.
     */
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime closingTime;

    /**
     * -- GETTER --
     *  Obtiene el día de la semana en el que aplica este horario.
     *
     *
     * -- SETTER --
     *  Establece el día de la semana en el que aplica este horario.
     *
     @return Día de la semana.
      * @param dayOfWeek Nuevo día de la semana.
     */
    @JsonSerialize(using = DayOfWeekSerializer.class)
    @JsonDeserialize(using = DayOfWeekDeserializer.class)
    private DayOfWeek dayOfWeek;

    /**
     * Constructor que permite definir un horario de referencia.
     *
     * @param dayOfWeek   Día de la semana en el que aplica el horario.
     * @param openingTime Hora de apertura.
     * @param closingTime Hora de cierre.
     */
    public ScheduleReference(DayOfWeek dayOfWeek, LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isWithinSchedule(Schedule schedule) {
        DayOfWeek scheduleDay = schedule.getDay();
        if (!dayOfWeek.equals(scheduleDay)) {
            return false;
        }
        return !schedule.getStartHour().isBefore(openingTime) && !schedule.getEndHour().isAfter(closingTime);
    }

    /**
     * Verifica si un horario dado está disponible en este horario de reserva.
     *
     * @param scheduleDay Horario a verificar.
     * @return true si el horario está disponible, false en caso contrario.
     */
    public boolean isAvailable(DayOfWeek scheduleDay, LocalTime scheduleStartTime, LocalTime scheduleEndTime) {

        boolean isDayAvailable = dayOfWeek.equals(scheduleDay);
        boolean isTimeWithinRange = !scheduleStartTime.isBefore(this.openingTime) && !scheduleEndTime.isAfter(this.closingTime);

        return isDayAvailable && isTimeWithinRange;
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
