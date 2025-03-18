package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.model.Reserve;
import edu.eci.cvds.labReserves.model.Schedule;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;

/**
 * The ReserveMongoRepository interface provides the data access layer for Reserves entities.
 * It extends the MongoRepository interface, which provides basic CRUD operations.
 */
@Repository
public interface ReserveMongoRepository extends MongoRepository<ReserveMongodb, String>{

    /**
     * Deletes all reservations associated with a specific user ID.
     *
     * @param userId The ID of the user whose reservations should be deleted.
     */
    @Query("{ 'userId' : ?0 }")
    void deleteAllByUserId(int userId);

    /**
     * Finds all reservations made by a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of reservations associated with the user.
     */
    @Query("{ 'userId' : ?0 }")
    List<ReserveMongodb> findByUserId(int userId);

    /**
     * Finds a reservation by its ID.
     *
     * @param id The ID of the reservation.
     * @return The matching reservation or null if not found.
     */
    @Query("{ '_id' : ?0 }")
    ReserveMongodb findByReserveId(String id);

    /**
     * Deletes a reservation by its ID.
     *
     * @param id The ID of the reservation to be deleted.
     */
    void deleteById(String id);

    /**
     * Finds a reservation by its schedule ID.
     *
     * @param scheduleId The ID of the schedule associated with the reservation.
     * @return The matching reservation or null if not found.
     */
    @Query("{ 'scheduleId' : ?0 }")
    ReserveMongodb findByScheduleId(String scheduleId);

    /**
     * Retrieves all reservations associated with a specific user ID.
     *
     * @param userId The ID of the user.
     * @return A list of all reservations made by the user.
     */
    @Query("{ 'userId' : ?0 }")
    List<ReserveMongodb> getAllByUserId(int userId);
}
