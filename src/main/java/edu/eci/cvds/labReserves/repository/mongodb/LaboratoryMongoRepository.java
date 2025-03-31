package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.model.Resource;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * The LaboratoryMongoRepository interface provides the data access layer for Laboratories entities.
 * It extends the MongoRepository interface, which provides basic CRUD operations.
 */
@Repository
public interface LaboratoryMongoRepository extends MongoRepository<LaboratoryMongodb, String> {

    /**
     * Busca todos los recursos asociados a un laboratorio.
     * @param resource Recurso a buscar.
     * @return Lista de recursos que coinciden con el criterio.
     */
    @Query("{ 'resource' : ?0 }")
    List<Resource> findAllByRecourses(Resource resource);

    /**
     * Busca un laboratorio por su abreviatura.
     * @param abbreviation Abreviatura del laboratorio.
     * @return El laboratorio encontrado o null si no existe.
     */
    @Query("{ 'abbreviation' : ?0 }")
    LaboratoryMongodb findByAbbreviation(String abbreviation);

    /**
     * Busca un laboratorio por su nombre.
     * @param name Nombre del laboratorio.
     * @return El laboratorio encontrado o null si no existe.
     */
    @Query("{ 'name' : ?0 }")
    LaboratoryMongodb findByName(String name);

    /**
     * Busca laboratorios en una ubicación específica.
     * @param location Ubicación del laboratorio.
     * @return Lista de laboratorios en la ubicación especificada.
     */
    @Query("{ 'location' : ?0 }")
    List<LaboratoryMongodb> findByLocation(String location);

    /**
     * Busca laboratorios con una capacidad total específica.
     * @param totalCapacity Capacidad total del laboratorio.
     * @return Lista de laboratorios con la capacidad especificada.
     */
    @Query("{ 'totalCapacity' : ?0 }")
    List<LaboratoryMongodb> findByTotalCapacity(int totalCapacity);

    /**
     * Busca laboratorios con una capacidad mayor o igual a la especificada.
     * @param capacity Capacidad mínima requerida.
     * @return Lista de laboratorios que cumplen con la capacidad especificada.
     */
    @Query("{ 'totalCapacity' : { $gte : ?0 } }")
    List<LaboratoryMongodb> findByTotalCapacityGreaterThanEqual(int capacity);

    /**
     * Verifica si existe un laboratorio con la abreviatura dada.
     * @param abbreviation Abreviatura del laboratorio.
     * @return true si existe un laboratorio con esa abreviatura, false en caso contrario.
     */
    @Query(value = "{ 'abbreviation' : ?0 }", exists = true)
    boolean existsByAbbreviation(String abbreviation);

    /**
     * Elimina un laboratorio por su abreviatura.
     * @param abbreviation Abreviatura del laboratorio a eliminar.
     */
    @Query(value = "{ 'abbreviation' : ?0 }", delete = true)
    void deleteByAbbreviation(String abbreviation);

}