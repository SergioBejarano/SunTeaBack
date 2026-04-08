package edu.eci.cvds.labReserves.collections;

import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ScheduleMongodb class specifically for MongoDB persistence.
 * This class maps to the "schedules" collection in MongoDB.
 */
@Document(collection = "schedule")
@NoArgsConstructor
@Getter
@Setter
public class ScheduleMongodb extends Schedule {

    /**
     * -- GETTER --
     * Get the schedule ID.
     *
     *
     * -- SETTER --
     * Set the schedule ID.
     *
     * @return The schedule ID.
     * @param id The schedule ID.
     */
    @Id
    private String id = new ObjectId().toString();
    // identifier for the scheduleMongodb (auto-generated).

    /**
     * Constructs a ScheduleMongodb instance based on a Schedule object.
     *
     * @param schedule the Schedule object to copy properties from
     */
    public ScheduleMongodb(final Schedule schedule) throws LabReserveException {
        super(schedule.getStartHour(), schedule.getNumberDay(),
                schedule.getDay(), schedule.getMonth(),
                schedule.getYear(), schedule.getLaboratory());
    }

}
