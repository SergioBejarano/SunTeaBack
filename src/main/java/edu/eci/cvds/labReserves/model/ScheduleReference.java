package edu.eci.cvds.labReserves.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un horario de referencia para un laboratorio.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleReference {

    /** Hora de apertura del laboratorio. */
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime openingTime;

    /** Hora de cierre del laboratorio. */
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime closingTime;

    /** Día de la semana en el que aplica este horario. */
    @JsonSerialize(using = DayOfWeekSerializer.class)
    @JsonDeserialize(using = DayOfWeekDeserializer.class)
    private DayOfWeek dayOfWeek;

    /**
     * Constructor que permite definir un horario de referencia.
     *
     * @param pDayOfWeek   Día de la semana.
     * @param pOpeningTime Hora de apertura.
     * @param pClosingTime Hora de cierre.
     */
    public ScheduleReference(final DayOfWeek pDayOfWeek,
                             final LocalTime pOpeningTime,
                             final LocalTime pClosingTime) {
        this.openingTime = pOpeningTime;
        this.closingTime = pClosingTime;
        this.dayOfWeek = pDayOfWeek;
    }

    /**
     * Verifica si un objeto Schedule está dentro de este horario.
     *
     * @param pSchedule El horario a validar.
     * @return true si está dentro del rango.
     */
    public final boolean isWithinSchedule(final Schedule pSchedule) {
        DayOfWeek scheduleDay = pSchedule.getDay();
        if (!dayOfWeek.equals(scheduleDay)) {
            return false;
        }
        boolean afterStart = !pSchedule.getStartHour().isBefore(openingTime);
        boolean beforeEnd = !pSchedule.getEndHour().isAfter(closingTime);
        return afterStart && beforeEnd;
    }

    /**
     * Verifica si un horario dado está disponible.
     *
     * @param pDay       Día a verificar.
     * @param pStart     Hora de inicio.
     * @param pEnd       Hora de fin.
     * @return true si el horario está disponible.
     */
    public final boolean isAvailable(final DayOfWeek pDay,
                                     final LocalTime pStart,
                                     final LocalTime pEnd) {
        boolean isDayAvailable = dayOfWeek.equals(pDay);
        boolean isTimeInRange = !pStart.isBefore(this.openingTime)
                && !pEnd.isAfter(this.closingTime);

        return isDayAvailable && isTimeInRange;
    }

    /** Serializador para LocalTime. */
    public static final class LocalTimeSerializer
            extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(final LocalTime value, final JsonGenerator gen,
                              final SerializerProvider provider)
                throws IOException {
            if (value != null) {
                gen.writeString(value.toString());
            }
        }
    }

    /** Deserializador para LocalTime. */
    public static final class LocalTimeDeserializer
            extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(final JsonParser p,
                                     final DeserializationContext ctxt)
                throws IOException {
            String timeString = p.getValueAsString();
            return timeString != null ? LocalTime.parse(timeString) : null;
        }
    }

    /** Serializador para DayOfWeek. */
    public static final class DayOfWeekSerializer
            extends JsonSerializer<DayOfWeek> {
        @Override
        public void serialize(final DayOfWeek value, final JsonGenerator gen,
                              final SerializerProvider provider)
                throws IOException {
            if (value != null) {
                gen.writeString(value.name());
            }
        }
    }

    /** Deserializador para DayOfWeek. */
    public static final class DayOfWeekDeserializer
            extends JsonDeserializer<DayOfWeek> {
        @Override
        public DayOfWeek deserialize(final JsonParser p,
                                     final DeserializationContext ctxt)
                throws IOException {
            String dayString = p.getValueAsString();
            return dayString != null ? DayOfWeek.valueOf(dayString) : null;
        }
    }
}
