package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import edu.eci.cvds.labReserves.dto.ReserveRequest;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Reserve;
import edu.eci.cvds.labReserves.model.Schedule;
import edu.eci.cvds.labReserves.model.User;
import edu.eci.cvds.labReserves.services.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The ReserveController class handles HTTP requests for managing reserves.
 * It provides endpoints to create, retrieve, update, and delete reserves.
 */
@RestController
@RequestMapping("/api/reserve")
public class ReserveController {

    @Autowired
    private ReserveService reserveService; //Service of Reserve (logic code)

    /**
     * Creates a new reservation.
     *
     * @param reserveRequest The request body containing reservation details.
     * @return The created ReserveMongodb object.
     * @throws LabReserveException If an error occurs during reservation creation.
     */
    @PostMapping("")
    public ReserveMongodb createReserve(@RequestBody ReserveRequest reserveRequest) throws LabReserveException {
        return reserveService.saveReserve(reserveRequest);
    }

    /**
     * Deletes all reserves associated with a specific schedule.
     *
     * @param schedule The schedule associated with the reserves to be deleted.
     * @throws LabReserveException If an error occurs during deletion.
     */
    @DeleteMapping("/schedules")
    public void deleteReserveBySchedule(@RequestBody Schedule schedule) throws LabReserveException {
        ScheduleMongodb scheduleMongodb = reserveService.getScheduleBySchedule(schedule);
        reserveService.deleteReserveByScheduleId(scheduleMongodb.getId());
        reserveService.deleteById(scheduleMongodb.getId());
    }

    /**
     * Deletes all reserves made by a specific user.
     *
     * @param userId The ID of the user whose reserves should be deleted.
     * @throws LabReserveException If an error occurs during deletion.
     */
    @DeleteMapping("/users/{userId}")
    public void deleteReserveByUser(@PathVariable int userId) throws LabReserveException {
        List<ReserveMongodb> reserveMongodbs = reserveService.getReserveByUserId(userId);
        for (ReserveMongodb reserveMongodb : reserveMongodbs) {
            reserveService.deleteReserveById(reserveMongodb.getId());
            reserveService.deleteById(reserveMongodb.getSchedule());
        }
    }

    /**
     * Retrieves all reservations.
     *
     * @return A list of all reservation requests.
     * @throws LabReserveException If an error occurs during retrieval.
     */
    @GetMapping("/reserves")
    public List<ReserveRequest> getAllReserves() throws LabReserveException {
        return reserveService.getAllReserves();
    }

    /**
     * Retrieves reservations by laboratory abbreviation.
     *
     * @param labAbbreviation The abbreviation of the laboratory.
     * @return A list of reservations associated with the laboratory.
     * @throws LabReserveException If an error occurs during retrieval.
     */
    @GetMapping("/lab/{labAbbreviation}")
    public List<ReserveRequest> getReserveByLaboratory(@PathVariable String labAbbreviation) throws LabReserveException {
        return reserveService.getReserveByLaboratory(labAbbreviation);
    }

    /**
     * Retrieves reservations by user ID.
     *
     * @param userId The ID of the user.
     * @return A list of reservations made by the user.
     * @throws LabReserveException If an error occurs during retrieval.
     */
    @GetMapping("/users/{userId}")
    public List<ReserveRequest> getReserveByUser(@PathVariable int userId) throws LabReserveException {
        return reserveService.getReserveByUser(userId);
    }

    /**
     * Retrieves reservations by day of the week.
     *
     * @param day The day of the week.
     * @return A list of reservations for the specified day.
     * @throws LabReserveException If an error occurs during retrieval.
     */
    @GetMapping("/day/{day}")
    public List<ReserveRequest> getReserveByDay(@PathVariable DayOfWeek day) throws LabReserveException {
        return reserveService.getReserveByDay(day);
    }

    /**
     * Retrieves reservations by month.
     *
     * @param month The month of the reservation.
     * @return A list of reservations for the specified month.
     * @throws LabReserveException If an error occurs during retrieval.
     */
    @GetMapping("/month/{month}")
    public List<ReserveMongodb> getReserveByMonth(@PathVariable Month month) throws LabReserveException {
        return reserveService.getReserveByMonth(month);
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param id The ID of the reservation.
     * @return The reservation with the specified ID.
     * @throws LabReserveException If an error occurs during retrieval.
     */
    @GetMapping("/{id}")
    public ReserveRequest getReserveById(@PathVariable String id) throws LabReserveException {
        return reserveService.getReserveById(id);
    }

    @GetMapping("/reserves/{id}")
    public ReserveMongodb getOnlyReserveById(@PathVariable String id) throws LabReserveException {
        return reserveService.getOnlyReserveById(id);
    }
}
