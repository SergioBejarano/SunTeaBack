package edu.eci.cvds.labReserves.collections;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Reserve;
import edu.eci.cvds.labReserves.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a MongoDB user entity that extends the base {@code User} class.
 * This class is annotated with {@code @Document} to specify the collection name in MongoDB.
 */
@Document(collection = "user")
public  class UserMongodb extends User {
    @Id
    private int id; // The unique identifier for the user.

    /**
     * Default constructor.
     */
    public UserMongodb() {}

    /**
     * Constructs a new {@code UserMongodb} object from an existing {@code User} instance.
     *
     * @param user The {@code User} object to be converted into a {@code UserMongodb} instance.
     * @throws LabReserveException If an error occurs during the conversion.
     */
    public UserMongodb(User user) throws LabReserveException {
        super(user.getId(), user.getName(), user.getMail(), user.getPassword(), user.getRol());
        this.id = user.getId();
    }

}
