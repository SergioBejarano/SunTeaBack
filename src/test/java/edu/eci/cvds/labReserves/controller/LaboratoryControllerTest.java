package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import edu.eci.cvds.labReserves.model.*;
import edu.eci.cvds.labReserves.services.LaboratoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LaboratoryControllerTest {

    @Mock
    private LaboratoryService laboratoryService;

    @InjectMocks
    private LaboratoryController laboratoryController;

    private Laboratory laboratory;
    private LaboratoryMongodb laboratoryMongodb;

    @BeforeEach
    void setUp() {
        laboratory = new Laboratory();
        laboratory.setAbbreviation("LAB-1");

        laboratoryMongodb = new LaboratoryMongodb();
        laboratoryMongodb.setAbbreviation("LAB-1");
    }

    @Test
    void testCreateLaboratory() {
        when(laboratoryService.createLaboratory(any(Laboratory.class))).thenReturn(laboratory);

        ResponseEntity<Laboratory> response = laboratoryController.createLaboratory(laboratory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(laboratory, response.getBody());
        verify(laboratoryService, times(1)).createLaboratory(laboratory);
    }

    @Test
    void testGetAllLaboratories() {
        List<LaboratoryMongodb> labs = Collections.singletonList(laboratoryMongodb);
        when(laboratoryService.getAllLaboratories()).thenReturn(labs);

        List<LaboratoryMongodb> result = laboratoryController.getAllLaboratories();

        assertEquals(1, result.size());
        assertEquals(laboratoryMongodb, result.get(0));
        verify(laboratoryService, times(1)).getAllLaboratories();
    }

    @Test
    void testGetLaboratoryByAbbreviation() {
        when(laboratoryService.getLaboratoryByAbbreviation("LAB-1")).thenReturn(laboratoryMongodb);

        LaboratoryMongodb result = laboratoryController.getLaboratoryByAbbreviation("LAB-1");

        assertEquals(laboratoryMongodb, result);
        verify(laboratoryService, times(1)).getLaboratoryByAbbreviation("LAB-1");
    }

    @Test
    void testUpdateLaboratoryCapacity() {
        laboratoryController.updateLaboratoryCapacity("LAB-1", Map.of("totalCapacity", 50));
        verify(laboratoryService, times(1)).updateLaboratoryTotalCapacity("LAB-1", 50);
    }

    @Test
    void testUpdateLaboratoryAbbreviation() {
        laboratoryController.updateLaboratoryAbbreviation("LAB-1", Map.of("abbreviation", "LAB-2"));
        verify(laboratoryService, times(1)).updateLaboratoryAbbreviation("LAB-1", "LAB-2");
    }

    @Test
    void testUpdateLaboratoryName() {
        laboratoryController.updateLaboratoryName("LAB-1", Map.of("name", "New Name"));
        verify(laboratoryService, times(1)).updateLaboratoryName("LAB-1", "New Name");
    }

    @Test
    void testUpdateLaboratoryLocation() {
        laboratoryController.updateLaboratoryLocation("LAB-1", Map.of("location", "New Location"));
        verify(laboratoryService, times(1)).updateLaboratoryLocation("LAB-1", "New Location");
    }

    @Test
    void testUpdateLaboratoryPhysicsResources() {
        Physical physical = new Physical();
        laboratoryController.updateLaboratoryPhysicsResources("LAB-1", physical);
        verify(laboratoryService, times(1)).updateLaboratoryPhysicsResources("LAB-1", physical);
    }

    @Test
    void testUpdateLaboratorySoftwareResources() {
        Software software = new Software();
        laboratoryController.updateLaboratorySoftwareResources("LAB-1", software);
        verify(laboratoryService, times(1)).updateLaboratorySoftwareResources("LAB-1", software);
    }

    @Test
    void testUpdateLaboratoryScheduleReference() {
        ScheduleReference scheduleRef = new ScheduleReference();
        laboratoryController.updateLaboratoryScheduleReference("LAB-1", scheduleRef);
        verify(laboratoryService, times(1)).updateLaboratoryScheduleReference("LAB-1", scheduleRef);
    }

    @Test
    void testDeleteLaboratory() {
        laboratoryController.deleteLaboratory("LAB-1");
        verify(laboratoryService, times(1)).deleteByAbbreviation("LAB-1");
    }

    @Test
    void testCheckAvailability() {
        Schedule schedule = new Schedule();
        when(laboratoryService.isLaboratoryAvailable("LAB-1", schedule)).thenReturn(true);

        ResponseEntity<Map<String, Boolean>> response = laboratoryController.checkAvailability("LAB-1", schedule);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("available"));
        verify(laboratoryService, times(1)).isLaboratoryAvailable("LAB-1", schedule);
    }
}