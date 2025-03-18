package edu.eci.cvds.labReserves.collections;

import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Reserve;
import edu.eci.cvds.labReserves.model.Schedule;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * ReserveMongodb class specifically for MongoDB persistence.
 * This class maps to the "reserves" collection in MongoDB.
 */
@Document(collection = "reserve")
public class ReserveMongodb extends Reserve {

    @Id
    private String id = new ObjectId().toString(); // id of reserve (autogenerate)

    /**
     * Constructs a ReserveMongodb instance based on a Reserve object.
     */
    public ReserveMongodb() {
        super();
    }

    /**
     * Constructs a ReserveMongodb instance based on a Reserve object.
     *
     * @param reserve the Reserve object to copy properties from
     */
    public ReserveMongodb(Reserve reserve) throws LabReserveException {
        super(reserve.getType(), reserve.getReason(), reserve.getUser());
        setSchedule(reserve.getSchedule());
    }

    /**
     * Gets the id time of the reserve.
     * @return The id
     */
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}