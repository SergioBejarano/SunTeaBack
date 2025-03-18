package edu.eci.cvds.labReserves.collections;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Reserve;
import edu.eci.cvds.labReserves.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
public  class UserMongodb extends User {
    @Id
    private int id;

    public UserMongodb() {}

    public UserMongodb(User user) throws LabReserveException {
        super(user.getId(), user.getName(), user.getMail(), user.getPassword(), user.getRol());
        this.id = user.getId();
    }

}
