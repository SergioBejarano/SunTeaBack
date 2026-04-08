package edu.eci.cvds.labReserves.collections;

import edu.eci.cvds.labReserves.model.Laboratory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

/**
 * LaboratoryMongodb class specifically for MongoDB persistence.
 * This class maps to the "laboratories" collection in MongoDB.
 */
@Getter
@Setter
@Document(collection = "laboratory")
public class LaboratoryMongodb extends Laboratory {

    /**
     * -- GETTER --
     *  Gets the id time of the laboratory.
     *
     * @return The id
     */
    @Id
    private String id;

    /**
     * Default constructor for LaboratoryMongodb.
     */
    public LaboratoryMongodb() {
        super();
    }

    /**
     * Constructs a LaboratoryMongodb instance based on a Schedule object.
     * @param laboratory
     */
    public LaboratoryMongodb(final Laboratory laboratory) {
        super(laboratory.getName(), laboratory.getAbbreviation(),
        laboratory.getTotalCapacity(), laboratory.getLocation(),
        laboratory.getScheduleReferences());
        this.id = laboratory.getAbbreviation();
    }

}
