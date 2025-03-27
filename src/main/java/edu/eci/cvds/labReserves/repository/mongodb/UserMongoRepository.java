package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.collections.UserMongodb;
import edu.eci.cvds.labReserves.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

@Repository
public interface UserMongoRepository extends MongoRepository<UserMongodb, String>{

    User findById(int id);

    User findByMail(String mail);

    String findByRol(String rol);

    User findByName(String name);

    UserMongodb searchById(int id);

    void deleteById(int id);

    List<UserMongodb> findAll();


}