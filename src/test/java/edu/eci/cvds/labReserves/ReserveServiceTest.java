package edu.eci.cvds.labReserves;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import edu.eci.cvds.labReserves.dto.ReserveRequest;
import edu.eci.cvds.labReserves.model.LabReserveException;
import edu.eci.cvds.labReserves.model.Reserve;
import edu.eci.cvds.labReserves.model.Schedule;
import edu.eci.cvds.labReserves.repository.mongodb.ReserveMongoRepository;
import edu.eci.cvds.labReserves.repository.mongodb.ScheduleMongoRepository;
import edu.eci.cvds.labReserves.services.ReserveService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReserveServiceTest {

    @Mock
    private ReserveMongoRepository reserveMongoRepository;

    @Mock
    private ScheduleMongoRepository scheduleMongoRepository;


    @InjectMocks
    private ReserveService reserveService;

    private ReserveMongodb reserveMongodbA;
    private ScheduleMongodb scheduleMongodbA;
    private Reserve reserveA;
    private Schedule scheduleA;
    private ReserveRequest request1;



    @BeforeEach
    public void setUp() throws LabReserveException {
        String reserveId1 = "dsk123dcs";
        String scheduleId1 = "aasfj863g";
        MockitoAnnotations.openMocks(this);
        reserveA = new Reserve("lesson", "LCAT", 1000095482);
        reserveMongodbA = new ReserveMongodb(reserveA);
        reserveMongodbA.setId(reserveId1);
        scheduleA = new Schedule(LocalTime.of(7, 30), 1, DayOfWeek.WEDNESDAY, Month.DECEMBER, 2025, "LABISW");
        scheduleMongodbA = new ScheduleMongodb(scheduleA);
        scheduleMongodbA.setId(scheduleId1);
        request1 = new ReserveRequest(reserveMongodbA, scheduleMongodbA);
    }

    @AfterEach
    public void tearDown() throws LabReserveException {
        reserveService.deleteReserveById("dsk123dcs");
        reserveService.deleteById("aasfj863g");
    }

    @Test
    public void testConsultReserve() throws LabReserveException {
        reserveService.saveReserve(request1);
        when(reserveMongoRepository.findByReserveId("dsk123dcs")).thenReturn(reserveMongodbA); //obtener la reserva por reserveService
        when(scheduleMongoRepository.findByScheduleId("aasfj863g")).thenReturn(scheduleMongodbA);
        ReserveMongodb reserveMongodb = reserveService.getOnlyReserveById("dsk123dcs");
        ScheduleMongodb scheduleMongodb = reserveService.getScheduleById("aasfj863g");
        //asserts de la reserva general
        assertEquals("LCAT", reserveMongodb.getReason());
        assertEquals(1000095482, reserveMongodb.getUser());
        assertEquals("lesson", reserveMongodb.getType());
        //asserts del horario de la reserva
        assertEquals(1, scheduleMongodb.getNumberDay());
        assertEquals(Month.DECEMBER, scheduleMongodb.getMonth());
        assertEquals(2025, scheduleMongodb.getYear());
        assertEquals("LABISW", scheduleMongodb.getLaboratory());
        assertEquals(LocalTime.of(7, 30), scheduleMongodb.getStartHour());
        assertEquals(DayOfWeek.WEDNESDAY, scheduleMongodb.getDay());
    }

    @Test
    public void testfindAllReservesEmpty() throws LabReserveException {
        when(reserveMongoRepository.findAll()).thenReturn(new ArrayList<>());
        List<ReserveRequest> reserveMongodbs = reserveService.getAllReserves();
        verify(reserveMongoRepository, times(1)).findAll();
        assertEquals(0, reserveMongodbs.size());
    }

    @Test
    public void testCreateNewReserve() throws LabReserveException {
        //generar schedule y reserve para ReserveRequest
        String reserveId2 = "ddfs3456s";
        String scheduleId2 = "zpnt783m";
        Reserve reserveB = new Reserve("lesson", "POOB", 1000095444);
        ReserveMongodb reserveMongodbB = new ReserveMongodb(reserveB);
        reserveMongodbB.setId(reserveId2);
        Schedule scheduleB = new Schedule(LocalTime.of(14, 30), 1,
                DayOfWeek.MONDAY, Month.JANUARY, 2025, "LABISW");
        ScheduleMongodb scheduleMongodbB = new ScheduleMongodb(scheduleB);
        scheduleMongodbB.setId(scheduleId2);
        ReserveRequest request2 = new ReserveRequest(reserveMongodbB, scheduleMongodbB);
        //agregar la reserva a la base de datos
        //when(reserveService.saveReserve(request2)).thenReturn(reserveMongodbB);
        when(reserveMongoRepository.save(reserveMongodbB)).thenReturn(reserveMongodbB);
        when(scheduleMongoRepository.save(scheduleMongodbB)).thenReturn(scheduleMongodbB);
        ReserveMongodb reserveMongodb = reserveService.saveReserve(request2);
        //confirmar por el id que se agrego correctamente (prueba testConsultReserve demuestra que se agregan por Id correctamente)
        when(reserveMongoRepository.findByReserveId("ddfs3456s")).thenReturn(reserveMongodbB);
        ReserveMongodb request = reserveService.getOnlyReserveById("ddfs3456s");
        assertEquals("POOB", reserveMongodb.getReason());
        assertEquals("POOB", request.getReason());

        //verify(reserveMongoRepository, times(1)).save(reserveMongodbB);
        //verify(scheduleMongoRepository, times(1)).save(scheduleMongodbB);
    }

    @Test
    public void testDeleteReserve() throws LabReserveException {
        when(reserveMongoRepository.save(reserveMongodbA)).thenReturn(reserveMongodbA);
        reserveService.saveReserve(request1);
        when(reserveMongoRepository.findAll()).thenReturn(new ArrayList<>());
        List<ReserveRequest> reserveMongodbs = reserveService.getAllReserves();
        reserveMongodbs.add(request1);
        verify(reserveMongoRepository, times(1)).findAll();
        assertEquals(1, reserveMongodbs.size());
        doNothing().when(reserveMongoRepository).deleteById(reserveMongodbA.getId());
        reserveService.deleteReserveById(reserveMongodbA.getId());
        when(reserveMongoRepository.findAll()).thenReturn(new ArrayList<>());
        reserveMongodbs = reserveService.getAllReserves();
        assertEquals(0, reserveMongodbs.size());
    }

    @Test
    public void testConsultWhenDeleteReserve() throws LabReserveException {
        when(reserveMongoRepository.save(reserveMongodbA)).thenReturn(reserveMongodbA);
        reserveService.saveReserve(request1);
        doNothing().when(reserveMongoRepository).deleteById(reserveMongodbA.getId());
        reserveService.deleteReserveById(reserveMongodbA.getId());
        when(reserveMongoRepository.findByReserveId("dsk123dcs")).thenReturn(null);
        ReserveMongodb reserveMongodbs = reserveService.getOnlyReserveById("dsk123dcs");
        assertNull(reserveMongodbs);
    }

}
