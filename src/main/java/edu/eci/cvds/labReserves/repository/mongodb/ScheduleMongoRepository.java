package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

/**
 * Repository interface for managing ScheduleMongodb entities in MongoDB.
 * Extends MongoRepository to provide basic CRUD operations.
 */
@Repository
public interface ScheduleMongoRepository extends MongoRepository<ScheduleMongodb, String> {

    /**
     * Finds a ScheduleMongodb entity by its time attributes.
     *
     * @param startHour  The start hour of the schedule.
     * @param numberDay  The numeric representation of the day in the month.
     * @param day        The day of the week.
     * @param month      The month of the year.
     * @param year       The year.
     * @return The matching ScheduleMongodb entity or null if not found.
     */
    @Query("{ 'startHour' : ?0, 'numberDay' : ?1, 'day' : ?2, 'month' : ?3, 'year' : ?4, 'laboratory' : ?5  }")
    ScheduleMongodb findByTime(LocalTime startHour, int numberDay, DayOfWeek day, Month month, int year, String laboratory);

    /**
     * Deletes a ScheduleMongodb entity by its ID.
     *
     * @param id The ID of the schedule to delete.
     */
    void deleteById(String id);

    /**
     * Finds a ScheduleMongodb entity by its ID.
     *
     * @param id The ID of the schedule to find.
     * @return The matching ScheduleMongodb entity or null if not found.
     */
    @Query("{ '_id' : ?0 }")
    ScheduleMongodb findByScheduleId(String id);
}
