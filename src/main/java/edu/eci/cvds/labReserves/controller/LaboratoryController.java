package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import edu.eci.cvds.labReserves.model.Schedule;
import edu.eci.cvds.labReserves.model.Laboratory;
import edu.eci.cvds.labReserves.services.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar las operaciones de laboratorio.
 */
@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    /**
     * Crea un nuevo laboratorio.
     * @param laboratory Objeto Laboratory a crear.
     * @return ResponseEntity con el laboratorio creado y el estado HTTP.
     */
    @PostMapping("/")
    public ResponseEntity<LaboratoryMongodb> createLaboratory(@RequestBody Laboratory laboratory) {
        LaboratoryMongodb createdLab = laboratoryService.createLaboratory(laboratory);
        return new ResponseEntity<>(createdLab, HttpStatus.CREATED);
    }

    /**
     * Obtiene la lista de todos los laboratorios.
     * @return Lista de laboratorios almacenados.
     */
    @GetMapping("/laboratory")
    public List<LaboratoryMongodb> getAllLaboratories() {
        return laboratoryService.getAllLaboratories();
    }

    /**
     * Retrieves a reserve by its ID.
    @GetMapping("/{id}")
    public ReserveMongodb getReserveById(@PathVariable int id) throws LabReserveException {
        return reserveService.getReserveById(id);
    }*/

    /**
     * Obtiene un laboratorio por su abreviatura.
     * @param abbreviation Abreviatura del laboratorio.
     * @return Objeto LaboratoryMongodb encontrado.
     */
    @GetMapping("/abbreviation/{abbreviation}")
    public LaboratoryMongodb getLaboratoryByAbbreviation(@PathVariable String abbreviation) {
        return laboratoryService.getLaboratoryByAbbreviation(abbreviation);    
    }

    /**
     * Actualiza un laboratorio existente basado en su abreviatura.
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     */
    @PutMapping("/update/{abbreviation}")
    public void updateLaboratoryScheduleReference(@PathVariable String abbreviation,
                                                  @RequestBody Map<String, Integer> requestBody) {
        int totalCapacity = requestBody.get("totalCapacity");

        laboratoryService.updateLaboratoryScheduleReference(abbreviation, totalCapacity);
    }

    /**
     * Elimina un laboratorio basado en su abreviatura.
     * @param abbreviation Abreviatura del laboratorio a eliminar.
     */
    @DeleteMapping("/{abbreviation}/byelaboratory")
    public void deleteLaboratory(@PathVariable String abbreviation) {
        // boolean deleted = laboratoryService.deleteLaboratory(abbreviation);

        /*if (deleted) {
            return new ResponseEntity<>("Laboratorio eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Laboratorio no encontrado", HttpStatus.NOT_FOUND);
        }*/
        laboratoryService.deleteByAbbreviation(abbreviation);
    }

    // Agrega un horario disponible a un laboratorio
    /*@PutMapping("/{abbreviation}/schedule")
    public void addAvailableDay(
            @PathVariable String abbreviation,
                @RequestBody Map<String, String> requestBody) {

        // Convertir los valores del mapa a los tipos necesarios
        DayOfWeek day = DayOfWeek.valueOf(requestBody.get("day").toUpperCase());
        LocalTime openingTime = LocalTime.parse(requestBody.get("openingTime"));
        LocalTime closingTime = LocalTime.parse(requestBody.get("closingTime"));

        laboratoryService.addAvailableDay(abbreviation, day, openingTime, closingTime);

    }*/

    /**
     * Verifica la disponibilidad de un laboratorio para un horario específico.
     * @param abbreviation Abreviatura del laboratorio.
     * @param schedule Horario a verificar.
     * @return ResponseEntity con la disponibilidad del laboratorio.
     */
    @GetMapping("/{abbreviation}/availability")
    public ResponseEntity<Map<String, Boolean>> checkAvailability(
            @PathVariable String abbreviation,
            @RequestBody Schedule schedule) {

        boolean isAvailable = laboratoryService.isLaboratoryAvailable(abbreviation, schedule);
        Map<String, Boolean> response = Map.of("available", isAvailable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}