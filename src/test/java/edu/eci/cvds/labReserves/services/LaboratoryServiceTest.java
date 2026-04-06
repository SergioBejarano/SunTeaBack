package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import edu.eci.cvds.labReserves.model.*;
import edu.eci.cvds.labReserves.repository.mongodb.LaboratoryMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaboratoryServiceTest {

    @Mock
    private LaboratoryMongoRepository laboratoryRepository;

    @InjectMocks
    private LaboratoryService laboratoryService;

    private Laboratory laboratory;
    private LaboratoryMongodb laboratoryMongodb;

    @BeforeEach
    void setUp() {
        laboratory = new Laboratory("LAB1", "Laboratorio 1", 30, "Edificio A", null, null, new ArrayList<>());
        laboratoryMongodb = new LaboratoryMongodb(laboratory);
    }

    @Test
    void createLaboratory() {
        when(laboratoryRepository.save(any(LaboratoryMongodb.class))).thenReturn(laboratoryMongodb);

        Laboratory result = laboratoryService.createLaboratory(laboratory);

        assertNotNull(result);
        assertEquals(laboratory.getAbbreviation(), result.getAbbreviation());
        verify(laboratoryRepository, times(1)).save(any(LaboratoryMongodb.class));
    }

    @Test
    void getAllLaboratories() {
        List<LaboratoryMongodb> labs = new ArrayList<>();
        labs.add(laboratoryMongodb);
        when(laboratoryRepository.findAll()).thenReturn(labs);

        List<LaboratoryMongodb> result = laboratoryService.getAllLaboratories();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(laboratoryRepository, times(1)).findAll();
    }

    @Test
    void getLaboratoryByAbbreviation() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);

        LaboratoryMongodb result = laboratoryService.getLaboratoryByAbbreviation("LAB1");

        assertNotNull(result);
        assertEquals("LAB1", result.getAbbreviation());
        verify(laboratoryRepository, times(1)).findByAbbreviation("LAB1");
    }

    @Test
    void updateLaboratoryTotalCapacity() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);

        laboratoryService.updateLaboratoryTotalCapacity("LAB1", 50);

        assertEquals(50, laboratoryMongodb.getTotalCapacity());
        verify(laboratoryRepository, times(1)).save(laboratoryMongodb);
    }

    @Test
    void updateLaboratoryAbbreviation() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);

        laboratoryService.updateLaboratoryAbbreviation("LAB1", "LAB2");

        assertEquals("LAB2", laboratoryMongodb.getAbbreviation());
        verify(laboratoryRepository, times(1)).save(laboratoryMongodb);
    }

    @Test
    void updateLaboratoryName() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);

        laboratoryService.updateLaboratoryName("LAB1", "Nuevo Nombre");

        assertEquals("Nuevo Nombre", laboratoryMongodb.getName());
        verify(laboratoryRepository, times(1)).save(laboratoryMongodb);
    }

    @Test
    void updateLaboratoryLocation() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);

        laboratoryService.updateLaboratoryLocation("LAB1", "Nueva Locacion");

        assertEquals("Nueva Locacion", laboratoryMongodb.getLocation());
        verify(laboratoryRepository, times(1)).save(laboratoryMongodb);
    }

    @Test
    void updateLaboratoryPhysicsResources() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);
        Physical physical = new Physical(true, true, 1);

        laboratoryService.updateLaboratoryPhysicsResources("LAB1", physical);

        assertEquals(physical, laboratoryMongodb.getPhysicalResource());
        verify(laboratoryRepository, times(1)).save(laboratoryMongodb);
    }

    @Test
    void updateLaboratorySoftwareResources() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);
        Software software = new Software("S1", "Description", false);

        laboratoryService.updateLaboratorySoftwareResources("LAB1", software);

        assertEquals(software, laboratoryMongodb.getSoftwareResource());
        verify(laboratoryRepository, times(1)).save(laboratoryMongodb);
    }

    @Test
    void updateLaboratoryScheduleReference() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);
        ScheduleReference ref = new ScheduleReference(DayOfWeek.MONDAY, LocalTime.of(8,0), LocalTime.of(12,0));

        laboratoryService.updateLaboratoryScheduleReference("LAB1", ref);

        assertTrue(laboratoryMongodb.getScheduleReferences().contains(ref));
        verify(laboratoryRepository, times(1)).save(laboratoryMongodb);
    }

    @Test
    void deleteLaboratory_Exists() {
        when(laboratoryRepository.existsByAbbreviation("LAB1")).thenReturn(true);

        boolean result = laboratoryService.deleteLaboratory(laboratory);

        assertTrue(result);
        verify(laboratoryRepository, times(1)).deleteByAbbreviation("LAB1");
    }

    @Test
    void deleteLaboratory_NotExists() {
        when(laboratoryRepository.existsByAbbreviation("LAB1")).thenReturn(false);

        boolean result = laboratoryService.deleteLaboratory(laboratory);

        assertFalse(result);
        verify(laboratoryRepository, never()).deleteByAbbreviation(anyString());
    }

    @Test
    void deleteByAbbreviation() {
        laboratoryService.deleteByAbbreviation("LAB1");
        verify(laboratoryRepository, times(1)).deleteByAbbreviation("LAB1");
    }

    @Test
    void addAvailableDay() {
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);
        laboratoryMongodb.setScheduleReferences(new ArrayList<>());

        laboratoryService.addAvailableDay("LAB1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0));

        assertEquals(1, laboratoryMongodb.getScheduleReferences().size());
        verify(laboratoryRepository, times(1)).save(laboratoryMongodb);
    }

    @Test
    void isLaboratoryAvailable() {
        ScheduleReference ref = mock(ScheduleReference.class);
        laboratoryMongodb.setScheduleReferences(new ArrayList<>());
        laboratoryMongodb.getScheduleReferences().add(ref);
        when(laboratoryRepository.findByAbbreviation("LAB1")).thenReturn(laboratoryMongodb);
        Schedule schedule = new Schedule(LocalTime.of(8,0), LocalTime.of(10,0), 6, DayOfWeek.MONDAY, java.time.Month.APRIL, 2026, "type");
        when(ref.getDayOfWeek()).thenReturn(DayOfWeek.MONDAY);
        when(ref.isAvailable(any(), any(), any())).thenReturn(true);

        boolean result = laboratoryService.isLaboratoryAvailable("LAB1", schedule);

        assertTrue(result);
    }
}