package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaboratoryMongoRepositoryTest {

    @Mock
    private LaboratoryMongoRepository laboratoryMongoRepository;

    @Test
    void shouldFindLaboratoryByAbbreviation() {
        LaboratoryMongodb lab = new LaboratoryMongodb();
        when(laboratoryMongoRepository.findByAbbreviation("LAB1")).thenReturn(lab);

        LaboratoryMongodb result = laboratoryMongoRepository.findByAbbreviation("LAB1");

        assertNotNull(result);
        verify(laboratoryMongoRepository, times(1)).findByAbbreviation("LAB1");
    }

    @Test
    void shouldCheckIfLaboratoryExistsByAbbreviation() {
        when(laboratoryMongoRepository.existsByAbbreviation("LAB1")).thenReturn(true);

        boolean exists = laboratoryMongoRepository.existsByAbbreviation("LAB1");

        assertTrue(exists);
        verify(laboratoryMongoRepository, times(1)).existsByAbbreviation("LAB1");
    }

    @Test
    void shouldDeleteLaboratoryByAbbreviation() {
        doNothing().when(laboratoryMongoRepository).deleteByAbbreviation("LAB1");

        assertDoesNotThrow(() -> laboratoryMongoRepository.deleteByAbbreviation("LAB1"));
        verify(laboratoryMongoRepository, times(1)).deleteByAbbreviation("LAB1");
    }
}