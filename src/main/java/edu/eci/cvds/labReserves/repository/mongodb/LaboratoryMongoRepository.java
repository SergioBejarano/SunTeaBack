package edu.eci.cvds.labReserves.repository.mongodb;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface for Laboratories entities data access.
 * It extends MongoRepository for basic CRUD operations.
 */
@Repository
public interface LaboratoryMongoRepository
        extends MongoRepository<LaboratoryMongodb, String> {

    /**
     * Busca un laboratorio por su abreviatura.
     * @param abbreviation Abreviatura del laboratorio.
     * @return El laboratorio encontrado o null si no existe.
     */
    @Query("{ 'abbreviation' : ?0 }")
    LaboratoryMongodb findByAbbreviation(String abbreviation);

    /**
     * Verifica si existe un laboratorio con la abreviatura dada.
     * @param abbreviation Abreviatura del laboratorio.
     * @return true si existe, false en caso contrario.
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
