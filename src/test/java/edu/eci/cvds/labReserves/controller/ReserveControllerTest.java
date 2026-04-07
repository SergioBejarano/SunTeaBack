package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import edu.eci.cvds.labReserves.dto.ReserveRequest;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Schedule;
import edu.eci.cvds.labReserves.services.ReserveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveControllerTest {

    @Mock
    private ReserveService reserveService;

    @InjectMocks
    private ReserveController reserveController;

    private ReserveRequest reserveRequest;
    private ReserveMongodb reserveMongodb;
    private Schedule schedule;
    private ScheduleMongodb scheduleMongodb;

    @BeforeEach
    void setUp() {
        reserveRequest = new ReserveRequest();
        reserveMongodb = new ReserveMongodb();
        reserveMongodb.setId("res-1");
        reserveMongodb.setSchedule("sch-1");

        schedule = new Schedule();
        scheduleMongodb = new ScheduleMongodb();
        scheduleMongodb.setId("sch-1");
    }

    @Test
    void testCreateReserve() throws LabReserveException {
        when(reserveService.saveReserve(any(ReserveRequest.class))).thenReturn(reserveMongodb);

        ReserveMongodb result = reserveController.createReserve(reserveRequest);

        assertEquals(reserveMongodb, result);
        verify(reserveService, times(1)).saveReserve(reserveRequest);
    }

    @Test
    void testDeleteReserveBySchedule() throws LabReserveException {
        when(reserveService.getScheduleBySchedule(any(Schedule.class))).thenReturn(scheduleMongodb);

        reserveController.deleteReserveBySchedule(schedule);

        verify(reserveService, times(1)).getScheduleBySchedule(schedule);
        verify(reserveService, times(1)).deleteReserveByScheduleId("sch-1");
        verify(reserveService, times(1)).deleteById("sch-1");
    }

    @Test
    void testDeleteReserveByUser() throws LabReserveException {
        when(reserveService.getReserveByUserId(1)).thenReturn(Collections.singletonList(reserveMongodb));

        reserveController.deleteReserveByUser(1);

        verify(reserveService, times(1)).getReserveByUserId(1);
        verify(reserveService, times(1)).deleteReserveById("res-1");
        verify(reserveService, times(1)).deleteById("sch-1");
    }

    @Test
    void testGetAllReserves() throws LabReserveException {
        List<ReserveRequest> reqs = Collections.singletonList(reserveRequest);
        when(reserveService.getAllReserves()).thenReturn(reqs);

        List<ReserveRequest> result = reserveController.getAllReserves();

        assertEquals(1, result.size());
        verify(reserveService, times(1)).getAllReserves();
    }

    @Test
    void testGetReserveByLaboratory() throws LabReserveException {
        List<ReserveRequest> reqs = Collections.singletonList(reserveRequest);
        when(reserveService.getReserveByLaboratory("LAB-1")).thenReturn(reqs);

        List<ReserveRequest> result = reserveController.getReserveByLaboratory("LAB-1");

        assertEquals(1, result.size());
        verify(reserveService, times(1)).getReserveByLaboratory("LAB-1");
    }

    @Test
    void testGetReserveByUser() throws LabReserveException {
        List<ReserveRequest> reqs = Collections.singletonList(reserveRequest);
        when(reserveService.getReserveByUser(1)).thenReturn(reqs);

        List<ReserveRequest> result = reserveController.getReserveByUser(1);

        assertEquals(1, result.size());
        verify(reserveService, times(1)).getReserveByUser(1);
    }

    @Test
    void testGetReserveByDay() throws LabReserveException {
        List<ReserveRequest> reqs = Collections.singletonList(reserveRequest);
        when(reserveService.getReserveByDay(DayOfWeek.MONDAY)).thenReturn(reqs);

        List<ReserveRequest> result = reserveController.getReserveByDay(DayOfWeek.MONDAY);

        assertEquals(1, result.size());
        verify(reserveService, times(1)).getReserveByDay(DayOfWeek.MONDAY);
    }

    @Test
    void testGetReserveByMonth() throws LabReserveException {
        List<ReserveMongodb> reqs = Collections.singletonList(reserveMongodb);
        when(reserveService.getReserveByMonth(Month.JANUARY)).thenReturn(reqs);

        List<ReserveMongodb> result = reserveController.getReserveByMonth(Month.JANUARY);

        assertEquals(1, result.size());
        verify(reserveService, times(1)).getReserveByMonth(Month.JANUARY);
    }

    @Test
    void testGetReserveById() throws LabReserveException {
        when(reserveService.getReserveById("res-1")).thenReturn(reserveRequest);

        ReserveRequest result = reserveController.getReserveById("res-1");

        assertEquals(reserveRequest, result);
        verify(reserveService, times(1)).getReserveById("res-1");
    }

    @Test
    void testGetOnlyReserveById() throws LabReserveException {
        when(reserveService.getOnlyReserveById("res-1")).thenReturn(reserveMongodb);

        ReserveMongodb result = reserveController.getOnlyReserveById("res-1");

        assertEquals(reserveMongodb, result);
        verify(reserveService, times(1)).getOnlyReserveById("res-1");
    }

    @Test
    void testPostReservesRandom() throws LabReserveException {
        List<ReserveRequest> reqs = Collections.singletonList(reserveRequest);
        when(reserveService.generateRandomReserves()).thenReturn(reqs);

        List<ReserveRequest> result = reserveController.postReservesRandom();

        assertEquals(1, result.size());
        verify(reserveService, times(1)).generateRandomReserves();
    }
}
