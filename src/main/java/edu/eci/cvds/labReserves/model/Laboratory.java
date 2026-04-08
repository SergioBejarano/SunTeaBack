package edu.eci.cvds.labReserves.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Representa un laboratorio con sus recursos físicos, software y horarios.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Laboratory {

    /** Abreviatura del laboratorio. */
    private String abbreviation;

    /** Nombre del laboratorio. */
    private String name;

    /** Capacidad total del laboratorio. */
    private int totalCapacity;

    /** Ubicación del laboratorio. */
    private String location;

    /** Recurso físico asociado. */
    private Physical physicalResource;

    /** Recurso de software asociado. */
    private Software softwareResource;

    /** Lista de horarios de referencia. */
    private List<ScheduleReference> scheduleReferences;

    /**
     * Constructor con parámetros para inicializar un laboratorio.
     *
     * @param pName           Nombre del laboratorio.
     * @param pAbbreviation    Abreviatura del laboratorio.
     * @param pTotalCapacity   Capacidad total.
     * @param pLocation        Ubicación.
     * @param pSchedules       Lista de horarios de referencia.
     */
    public Laboratory(final String pName, final String pAbbreviation,
                      final int pTotalCapacity, final String pLocation,
                      final List<ScheduleReference> pSchedules) {
        this.name = pName;
        this.abbreviation = pAbbreviation;
        this.totalCapacity = pTotalCapacity;
        this.location = pLocation;
        this.scheduleReferences = pSchedules;
    }

    /**
     * Establece los horarios a partir de un mapa.
     *
     * @param daySchedules Mapa de días y horarios.
     */
    public final void setReferenceSchedules(
            final Map<DayOfWeek, ScheduleReference> daySchedules) {
        this.scheduleReferences = new ArrayList<>(daySchedules.values());
    }

    /**
     * Agrega o actualiza un horario de referencia.
     *
     * @param scheduleReference Horario a agregar o actualizar.
     */
    public final void addScheduleReference(
            final ScheduleReference scheduleReference) {
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
     * Agrega un día disponible con un horario específico.
     *
     * @param day         Día de la semana.
     * @param openingTime Hora de apertura.
     * @param closingTime Hora de cierre.
     */
    public final void addAvailableDay(final DayOfWeek day,
                                      final LocalTime openingTime,
                                      final LocalTime closingTime) {
        ScheduleReference reference = new ScheduleReference(day,
                openingTime, closingTime);
        addScheduleReference(reference);
    }

    /**
     * Obtiene el horario de referencia para un día específico.
     *
     * @param day Día de la semana a consultar.
     * @return Horario de referencia o null.
     */
    public final ScheduleReference getScheduleReferenceForDay(
            final DayOfWeek day) {
        for (ScheduleReference ref : scheduleReferences) {
            if (ref.getDayOfWeek().equals(day)) {
                return ref;
            }
        }
        return null;
    }

    /**
     * Actualiza o añade una referencia de horario.
     *
     * @param scheduleReference La referencia a establecer.
     */
    public final void setScheduleReference(
            final ScheduleReference scheduleReference) {
        boolean updated = false;

        for (int i = 0; i < scheduleReferences.size(); i++) {
            DayOfWeek existingDay = scheduleReferences.get(i).getDayOfWeek();
            if (existingDay.equals(scheduleReference.getDayOfWeek())) {
                scheduleReferences.set(i, scheduleReference);
                updated = true;
                break;
            }
        }

        if (!updated) {
            scheduleReferences.add(scheduleReference);
        }
    }
}
