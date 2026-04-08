package edu.eci.cvds.labReserves.collections;

import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Reserve;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ReserveMongodb class specifically for MongoDB persistence.
 * This class maps to the "reserves" collection in MongoDB.
 */
@Document(collection = "reserve")
@Getter
@Setter
@NoArgsConstructor
public class ReserveMongodb extends Reserve {

    /**
     * -- GETTER --
     *  Get the id of the reserve.
     * -- SETTER --
     *  Set the id of the reserve.
     *
     @return The id
      * @param id The id of reserveMongo
     */
    @Id
    private String id = new ObjectId()
            .toString();

    /**
     * Constructs a ReserveMongodb instance based on a Reserve object.
     *
     * @param reserve the Reserve object to copy properties from
     */
    public ReserveMongodb(final Reserve reserve) throws LabReserveException {
        super(reserve.getType(), reserve.getReason(), reserve.getUser());
        setSchedule(reserve.getSchedule());
    }

}
