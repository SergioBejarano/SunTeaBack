package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.LaboratoryMongoRepository;
import edu.eci.cvds.labReserves.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * Service for managing laboratories within the system.
 */
@Service
public class LaboratoryService {

    @Autowired
    private LaboratoryMongoRepository laboratoryRepository;

    /**
     * Creates a new laboratory in the database.
     * @param laboratory Laboratory object containing the lab information.
     * @return The created laboratory.
     */
    public LaboratoryMongodb createLaboratory(Laboratory laboratory) {
        LaboratoryMongodb labMongo = new LaboratoryMongodb(laboratory);
        return laboratoryRepository.save(labMongo);
    }

    /**
     * Retrieves all registered laboratories from the database.
     * @return A list of all laboratories.
     */
    public List<LaboratoryMongodb> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    /**
     * Searches for a laboratory by its abbreviation.
     * @param abbreviation The abbreviation of the laboratory.
     * @return The found laboratory or null if it does not exist.
     */
    public LaboratoryMongodb getLaboratoryByAbbreviation(String abbreviation) {
        return laboratoryRepository.findByAbbreviation(abbreviation);
    }

    /**
     * Updates the schedule reference of an existing laboratory.
     * @param abbreviation The abbreviation of the laboratory to update.
     */
    public void updateLaboratoryScheduleReference(String abbreviation, int totalCapacity) {
        LaboratoryMongodb existingLab = laboratoryRepository.findByAbbreviation(abbreviation);
        // Esta linea se va mañana RECORDATORIOOOO (2 lineas)
        /*List<ScheduleReference> listschedule = existingLab.getScheduleReferences();
        for (ScheduleReference schedule : listschedule){
            if (schedule.getDayOfWeek().equals(schedulereference.getDayOfWeek())){
                schedule.setOpeningTime(schedulereference.getOpeningTime());
                schedule.setClosingTime(schedulereference.getClosingTime());
            }
        }
        existingLab.setScheduleReferences(listschedule);
        */
        existingLab.setTotalCapacity(totalCapacity);
        laboratoryRepository.save(existingLab);
    }

    /**
     * Deletes a laboratory by its reference object.
     * @param laboratory The Laboratory object to be deleted.
     * @return true if the laboratory was deleted, false if it does not exist.
     */
    public boolean deleteLaboratory(Laboratory laboratory) {
        if (laboratoryRepository.existsByAbbreviation(laboratory.getAbbreviation())) {
            LaboratoryMongodb lab = getLaboratoryByAbbreviation(laboratory.getAbbreviation());
            laboratoryRepository.deleteByAbbreviation(lab.getAbbreviation());
            return true;
        }
        return false;
    }

    /**
     * Deletes a laboratory by its abbreviation.
     * @param abbreviation The abbreviation of the laboratory to be deleted.
     */
    public void deleteByAbbreviation(String abbreviation) {
        laboratoryRepository.deleteByAbbreviation(abbreviation);
    }

    /**
     * Adds an available schedule to a laboratory.
     * @param abbreviation The abbreviation of the laboratory.
     * @param day The day of the week when the laboratory will be available.
     * @param openingTime The opening time.
     * @param closingTime The closing time.
     */
    public void addAvailableDay(String abbreviation, DayOfWeek day, LocalTime openingTime,
                                      LocalTime closingTime) {
        LaboratoryMongodb lab = laboratoryRepository.findByAbbreviation(abbreviation);
        ScheduleReference newSchedule = new ScheduleReference(day, openingTime, closingTime);
        lab.getScheduleReferences().add(newSchedule);
        laboratoryRepository.save(lab);
    }

    /**
     * Checks if a laboratory is available for a specific schedule.
     * @param abbreviation The abbreviation of the laboratory.
     * @param schedule The Schedule object with the schedule to verify.
     * @return true if the laboratory is available, false otherwise.
     */
    public boolean isLaboratoryAvailable(String abbreviation, Schedule schedule) {
        LaboratoryMongodb lab = getLaboratoryByAbbreviation(abbreviation);
        if (lab != null) {
            ScheduleReference scheduleRef = lab.getScheduleReferenceForDay(schedule.getDay());
            if (scheduleRef != null) {
                return scheduleRef.isAvailable(schedule);
            }
        }
        return false;
    }

}