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
 * Representa un laboratorio con sus respectivos recursos físicos, de software y horarios disponibles.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Laboratory {

    private String abbreviation;
    private String name;
    private int totalCapacity;
    private String location;
    private Physical physicalResource;
    private Software softwareResource;
    private List<ScheduleReference> scheduleReferences;

    /**
     * Constructor con parámetros para inicializar un laboratorio con datos específicos.
     *
     * @param name              Nombre del laboratorio.
     * @param abbreviation      Abreviatura del laboratorio.
     * @param totalCapacity     Capacidad total del laboratorio.
     * @param location          Ubicación del laboratorio.
     * @param scheduleReferences Lista de horarios de referencia del laboratorio.
     */
    public Laboratory(String name, String abbreviation, int totalCapacity, String location, List<ScheduleReference> scheduleReferences) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.totalCapacity = totalCapacity;
        this.location = location;
        this.scheduleReferences = scheduleReferences;
    }

    /**
     * Establece los horarios de referencia del laboratorio a partir de un mapa de días y horarios.
     *
     * @param daySchedules Mapa que asocia días de la semana con horarios de referencia.
     */
    public void setReferenceSchedules(Map<DayOfWeek, ScheduleReference> daySchedules) {
        this.scheduleReferences = new ArrayList<>(daySchedules.values());
    }

    /**
     * Agrega o actualiza un horario de referencia en función del día de la semana.
     * Si ya existe un horario para ese día, lo actualiza; de lo contrario, lo agrega.
     *
     * @param scheduleReference Horario de referencia a agregar o actualizar.
     */
    public void addScheduleReference(ScheduleReference scheduleReference) {
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
     * Agrega un día disponible con un horario de apertura y cierre.
     *
     * @param day          Día de la semana en el que el laboratorio estará disponible.
     * @param openingTime  Hora de apertura.
     * @param closingTime  Hora de cierre.
     */
    public void addAvailableDay(DayOfWeek day, LocalTime openingTime, LocalTime closingTime) {
        ScheduleReference reference = new ScheduleReference(day, openingTime, closingTime);
        addScheduleReference(reference);
    }

    // Getters y setters
    /**
     * Obtiene el horario de referencia para un día específico.
     *
     * @param day Día de la semana a consultar.
     * @return Horario de referencia correspondiente, o null si no existe.
     */
    public ScheduleReference getScheduleReferenceForDay(DayOfWeek day) {
        for (ScheduleReference ref : scheduleReferences) {
            if (ref.getDayOfWeek().equals(day)) {
                return ref;
            }
        }
        return null;
    }

    public void setScheduleReference(ScheduleReference scheduleReference){
        boolean updated = false;

        for (int i = 0; i < scheduleReferences.size(); i++) {
            if (scheduleReferences.get(i).getDayOfWeek().equals(scheduleReference.getDayOfWeek())) {
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
