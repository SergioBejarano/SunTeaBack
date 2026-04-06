package edu.eci.cvds.labReserves.dto;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import edu.eci.cvds.labReserves.model.LabReserveException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ReserveRequestTest {

    @Test
    void testReserveRequestConstructorsAndSetters() {
        ReserveRequest req = new ReserveRequest();
        req.setType("type");
        req.setReason("reason");
        req.setState("state");
        req.setUserId(1);
        req.setStartHour(LocalTime.of(8,0));
        req.setNumberDay(1);
        req.setDay(DayOfWeek.MONDAY);
        req.setMonth(Month.JANUARY);
        req.setYear(2023);
        req.setLaboratoryName("lab1");

        assertEquals("type", req.getType());
        assertEquals("reason", req.getReason());
        assertEquals("state", req.getState());
        assertEquals(1, req.getUserId());
        assertEquals(LocalTime.of(8,0), req.getStartHour());
        assertEquals(1, req.getNumberDay());
        assertEquals(DayOfWeek.MONDAY, req.getDay());
        assertEquals(Month.JANUARY, req.getMonth());
        assertEquals(2023, req.getYear());
        assertEquals("lab1", req.getLaboratoryName());

        ReserveRequest req2 = new ReserveRequest("type", "reason", "state", 1, LocalTime.of(8,0), 1, DayOfWeek.MONDAY, Month.JANUARY, 2023, "lab1");
        assertEquals("type", req2.getType());
    }

    @Test
    void testReserveRequestCustomConstructor() throws LabReserveException {
        ReserveMongodb reserve = new ReserveMongodb();
        reserve.setType("lesson");
        reserve.setReason("reason");
        reserve.setState("reserved");
        reserve.setUser(1);

        ScheduleMongodb schedule = new ScheduleMongodb();
        schedule.setStartHour(LocalTime.of(9,0));
        schedule.setNumberDay(2);
        schedule.setDay(DayOfWeek.TUESDAY);
        schedule.setMonth(Month.FEBRUARY);
        schedule.setYear(2024);
        schedule.setLaboratory("lab2");

        ReserveRequest req = new ReserveRequest(reserve, schedule);

        assertEquals("lesson", req.getType());
        assertEquals("reason", req.getReason());
        assertEquals("reserved", req.getState());
        assertEquals(1, req.getUserId());
        assertEquals(LocalTime.of(9,0), req.getStartHour());
        assertEquals(2, req.getNumberDay());
        assertEquals(DayOfWeek.TUESDAY, req.getDay());
        assertEquals(Month.FEBRUARY, req.getMonth());
        assertEquals(2024, req.getYear());
        assertEquals("lab2", req.getLaboratoryName());
    }
}