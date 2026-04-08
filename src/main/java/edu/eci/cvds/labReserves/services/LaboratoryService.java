package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.LaboratoryMongoRepository;
import edu.eci.cvds.labReserves.model.Laboratory;
import edu.eci.cvds.labReserves.model.Physical;
import edu.eci.cvds.labReserves.model.Software;
import edu.eci.cvds.labReserves.model.ScheduleReference;
import edu.eci.cvds.labReserves.model.Schedule;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * Servicio para la gestión de laboratorios en el sistema.
 */
@Service
public class LaboratoryService {

    /** Repositorio para el acceso a datos de laboratorios. */
    private final LaboratoryMongoRepository laboratoryRepository;

    /**
     * Constructor de LaboratoryService.
     * @param pLaboratoryRepository Repositorio de laboratorios.
     */
    public LaboratoryService(final LaboratoryMongoRepository
            pLaboratoryRepository) {
        this.laboratoryRepository = pLaboratoryRepository;
    }

    /**
     * Crea un nuevo laboratorio en la base de datos.
     * @param pLaboratory Objeto Laboratory con la información.
     * @return El laboratorio creado.
     */
    public final Laboratory createLaboratory(final Laboratory pLaboratory) {
        LaboratoryMongodb labMongo = new LaboratoryMongodb(pLaboratory);
        return laboratoryRepository.save(labMongo);
    }

    /**
     * Obtiene todos los laboratorios registrados en la base de datos.
     * @return Lista de todos los laboratorios.
     */
    public final List<LaboratoryMongodb> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    /**
     * Busca un laboratorio por su abreviatura.
     * @param pAbbreviation Abreviatura del laboratorio.
     * @return El laboratorio encontrado o null si no existe.
     */
    public final LaboratoryMongodb getLaboratoryByAbbreviation(
            final String pAbbreviation) {
        return laboratoryRepository.findByAbbreviation(pAbbreviation);
    }

    /**
     * Actualiza la capacidad total de un laboratorio existente.
     * @param pAbbreviation Abreviatura del laboratorio.
     * @param pCapacity nueva capacidad del laboratorio.
     */
    public final void updateLaboratoryTotalCapacity(
            final String pAbbreviation,
            final int pCapacity) {
        LaboratoryMongodb lab = laboratoryRepository
                .findByAbbreviation(pAbbreviation);
        lab.setTotalCapacity(pCapacity);
        laboratoryRepository.save(lab);
    }

    /**
     * Actualiza la abreviatura de un laboratorio existente.
     * @param pAbbreviation Abreviatura actual.
     * @param pNewAbbreviation nueva abreviatura.
     */
    public final void updateLaboratoryAbbreviation(
            final String pAbbreviation,
            final String pNewAbbreviation) {
        LaboratoryMongodb lab = laboratoryRepository
                .findByAbbreviation(pAbbreviation);
        lab.setAbbreviation(pNewAbbreviation);
        laboratoryRepository.save(lab);
    }

    /**
     * Actualiza el nombre de un laboratorio existente.
     * @param pAbbreviation Abreviatura del laboratorio.
     * @param pNewName nuevo nombre.
     */
    public final void updateLaboratoryName(
            final String pAbbreviation,
            final String pNewName) {
        LaboratoryMongodb lab = laboratoryRepository
                .findByAbbreviation(pAbbreviation);
        lab.setName(pNewName);
        laboratoryRepository.save(lab);
    }

    /**
     * Actualiza la ubicación de un laboratorio existente.
     * @param pAbbreviation Abreviatura del laboratorio.
     * @param pNewLocation nueva locación.
     */
    public final void updateLaboratoryLocation(
            final String pAbbreviation,
            final String pNewLocation) {
        LaboratoryMongodb lab = laboratoryRepository
                .findByAbbreviation(pAbbreviation);
        lab.setLocation(pNewLocation);
        laboratoryRepository.save(lab);
    }

    /**
     * Actualiza los recursos físicos de un laboratorio.
     * @param pAbbreviation Abreviatura del laboratorio.
     * @param pPhysical nuevo recurso físico.
     */
    public final void updateLaboratoryPhysicsResources(
            final String pAbbreviation,
            final Physical pPhysical) {
        LaboratoryMongodb lab = laboratoryRepository
                .findByAbbreviation(pAbbreviation);
        lab.setPhysicalResource(pPhysical);
        laboratoryRepository.save(lab);
    }

    /**
     * Actualiza los recursos de software de un laboratorio.
     * @param pAbbreviation Abreviatura del laboratorio.
     * @param pSoftware nuevo software.
     */
    public final void updateLaboratorySoftwareResources(
            final String pAbbreviation,
            final Software pSoftware) {
        LaboratoryMongodb lab = laboratoryRepository
                .findByAbbreviation(pAbbreviation);
        lab.setSoftwareResource(pSoftware);
        laboratoryRepository.save(lab);
    }

    /**
     * Actualiza la referencia de horario de un laboratorio.
     * @param pAbbreviation Abreviatura del laboratorio.
     * @param pScheduleRef nueva referencia de horario.
     */
    public final void updateLaboratoryScheduleReference(
            final String pAbbreviation,
            final ScheduleReference pScheduleRef) {
        LaboratoryMongodb lab = laboratoryRepository
                .findByAbbreviation(pAbbreviation);
        lab.setScheduleReference(pScheduleRef);
        laboratoryRepository.save(lab);
    }

    /**
     * Elimina un laboratorio por su objeto de referencia.
     * @param pLaboratory Objeto Laboratory a eliminar.
     * @return true si fue eliminado, false en caso contrario.
     */
    public final boolean deleteLaboratory(final Laboratory pLaboratory) {
        if (laboratoryRepository.existsByAbbreviation(
                pLaboratory.getAbbreviation())) {
            laboratoryRepository.deleteByAbbreviation(
                    pLaboratory.getAbbreviation());
            return true;
        }
        return false;
    }

    /**
     * Elimina un laboratorio por su abreviatura.
     * @param pAbbreviation Abreviatura del laboratorio a eliminar.
     */
    public final void deleteByAbbreviation(final String pAbbreviation) {
        laboratoryRepository.deleteByAbbreviation(pAbbreviation);
    }

    /**
     * Agrega un horario disponible a un laboratorio.
     * @param pAbbrev Abreviatura del laboratorio.
     * @param pDay Día de la semana.
     * @param pOpen Hora de apertura.
     * @param pClose Hora de cierre.
     */
    public final void addAvailableDay(
            final String pAbbrev,
            final DayOfWeek pDay,
            final LocalTime pOpen,
            final LocalTime pClose) {
        LaboratoryMongodb lab = laboratoryRepository
                .findByAbbreviation(pAbbrev);
        ScheduleReference newSchedule = new ScheduleReference(
                pDay, pOpen, pClose);
        lab.getScheduleReferences().add(newSchedule);
        laboratoryRepository.save(lab);
    }

    /**
     * Verifica si un laboratorio está disponible.
     * @param pAbbreviation Abreviatura del laboratorio.
     * @param pSchedule Objeto Schedule con el horario.
     * @return true si está disponible, false en caso contrario.
     */
    public final boolean isLaboratoryAvailable(
            final String pAbbreviation,
            final Schedule pSchedule) {
        Laboratory lab = getLaboratoryByAbbreviation(pAbbreviation);

        if (lab != null) {
            ScheduleReference scheduleRef = lab
                    .getScheduleReferenceForDay(pSchedule.getDay());
            DayOfWeek day = pSchedule.getDay();
            LocalTime start = pSchedule.getStartHour();
            LocalTime end = pSchedule.getEndHour();
            if (scheduleRef != null) {
                return scheduleRef.isAvailable(day, start, end);
            }
        }
        return false;
    }
}
