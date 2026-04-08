package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import edu.eci.cvds.labReserves.model.Laboratory;
import edu.eci.cvds.labReserves.model.Physical;
import edu.eci.cvds.labReserves.model.Schedule;
import edu.eci.cvds.labReserves.model.ScheduleReference;
import edu.eci.cvds.labReserves.model.Software;
import edu.eci.cvds.labReserves.services.LaboratoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar las operaciones de laboratorio.
 */
@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {

    /** Service for managing laboratory operations. */
    private final LaboratoryService laboratoryService;

    /**
     * Constructor to inject LaboratoryService dependency.
     *
     * @param labService Laboratory service instance.
     */
    public LaboratoryController(final LaboratoryService labService) {
        this.laboratoryService = labService;
    }

    /**
     * Crea un nuevo laboratorio.
     *
     * @param laboratory Objeto Laboratory a crear.
     * @return ResponseEntity con el laboratorio creado y el estado HTTP.
     */
    @PostMapping("/")
    public ResponseEntity<Laboratory> createLaboratory(
            @RequestBody final Laboratory laboratory) {
        final Laboratory createdLab =
                laboratoryService.createLaboratory(laboratory);
        return new ResponseEntity<>(createdLab, HttpStatus.CREATED);
    }

    /**
     * Obtiene la lista de todos los laboratorios.
     *
     * @return Lista de laboratorios almacenados.
     */
    @GetMapping("/laboratory")
    public List<LaboratoryMongodb> getAllLaboratories() {
        return laboratoryService.getAllLaboratories();
    }

    /**
     * Obtiene un laboratorio por su abreviatura.
     *
     * @param abbreviation Abreviatura del laboratorio.
     * @return Objeto LaboratoryMongodb encontrado.
     */
    @GetMapping("/abbreviation/{abbreviation}")
    public LaboratoryMongodb getLaboratoryByAbbreviation(
            @PathVariable final String abbreviation) {
        return laboratoryService.getLaboratoryByAbbreviation(abbreviation);
    }

    /**
     * Actualiza la capacidad de un laboratorio existente.
     *
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param requestbody nueva capacidad a actualizar.
     */
    @PutMapping("/totalCapacity/{abbreviation}")
    public void updateLaboratoryCapacity(
            @PathVariable final String abbreviation,
            @RequestBody final Map<String, Integer> requestbody) {
        final int totalcapacity = requestbody.get("totalCapacity");
        laboratoryService.updateLaboratoryTotalCapacity(
                abbreviation, totalcapacity);
    }

    /**
     * Actualiza la abreviatura de un laboratorio existente.
     *
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param requestbody nueva abreviatura a actualizar.
     */
    @PutMapping("/changeAbbreviation/{abbreviation}")
    public void updateLaboratoryAbbreviation(
            @PathVariable final String abbreviation,
            @RequestBody final Map<String, String> requestbody) {
        final String newAbbreviation = requestbody.get("abbreviation");
        laboratoryService.updateLaboratoryAbbreviation(
                abbreviation, newAbbreviation);
    }

    /**
     * Actualiza el nombre de un laboratorio existente.
     *
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param requestbody nuevo nombre a actualizar.
     */
    @PutMapping("/name/{abbreviation}")
    public void updateLaboratoryName(
            @PathVariable final String abbreviation,
            @RequestBody final Map<String, String> requestbody) {
        final String newName = requestbody.get("name");
        laboratoryService.updateLaboratoryName(abbreviation, newName);
    }

    /**
     * Actualiza la locacion de un laboratorio existente.
     *
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param requestbody nueva locacion a actualizar.
     */
    @PutMapping("/location/{abbreviation}")
    public void updateLaboratoryLocation(
            @PathVariable final String abbreviation,
            @RequestBody final Map<String, String> requestbody) {
        final String newLocation = requestbody.get("location");
        laboratoryService.updateLaboratoryLocation(
                abbreviation, newLocation);
    }

    /**
     * Actualiza los recursos fisicos de un laboratorio existente.
     *
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param physical nuevos recursos fisicos a actualizar.
     */
    @PutMapping("/physicalResources/{abbreviation}")
    public void updateLaboratoryPhysicsResources(
            @PathVariable final String abbreviation,
            @RequestBody final Physical physical) {
        laboratoryService.updateLaboratoryPhysicsResources(
                abbreviation, physical);
    }

    /**
     * Actualiza los recursos de software de un laboratorio existente.
     *
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param software nuevos recursos de software a actualizar.
     */
    @PutMapping("/softwareResources/{abbreviation}")
    public void updateLaboratorySoftwareResources(
            @PathVariable final String abbreviation,
            @RequestBody final Software software) {
        laboratoryService.updateLaboratorySoftwareResources(
                abbreviation, software);
    }

    /**
     * Actualiza la referencia de horario de un laboratorio existente.
     *
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param scheduleReference nueva referencia de horario a actualizar.
     */
    @PutMapping("/scheduleReference/{abbreviation}")
    public void updateLaboratoryScheduleReference(
            @PathVariable final String abbreviation,
            @RequestBody final ScheduleReference scheduleReference) {
        laboratoryService.updateLaboratoryScheduleReference(
                abbreviation, scheduleReference);
    }

    /**
     * Elimina un laboratorio basado en su abreviatura.
     *
     * @param abbreviation Abreviatura del laboratorio a eliminar.
     */
    @DeleteMapping("/{abbreviation}/byelaboratory")
    public void deleteLaboratory(
            @PathVariable final String abbreviation) {
        laboratoryService.deleteByAbbreviation(abbreviation);
    }

    /**
     * Verifica la disponibilidad de un laboratorio para un horario.
     *
     * @param abbreviation Abreviatura del laboratorio.
     * @param schedule Horario a verificar.
     * @return ResponseEntity con la disponibilidad del laboratorio.
     */
    @GetMapping("/{abbreviation}/availability")
    public ResponseEntity<Map<String, Boolean>> checkAvailability(
            @PathVariable final String abbreviation,
            @RequestBody final Schedule schedule) {
        final boolean isAvailable =
                laboratoryService.isLaboratoryAvailable(
                        abbreviation, schedule);
        final Map<String, Boolean> response =
                Map.of("available", isAvailable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
