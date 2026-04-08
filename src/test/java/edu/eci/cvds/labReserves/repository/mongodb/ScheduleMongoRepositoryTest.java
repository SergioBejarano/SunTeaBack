package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.collections.ScheduleMongodb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleMongoRepositoryTest {

    @Mock
    private ScheduleMongoRepository scheduleMongoRepository;

    @Test
    void shouldFindScheduleByTime() {
        ScheduleMongodb schedule = new ScheduleMongodb();
        LocalTime time = LocalTime.of(10, 0);
        when(scheduleMongoRepository.findByTime(time, 15, DayOfWeek.MONDAY, Month.JANUARY, 2024, "LAB1"))
                .thenReturn(schedule);

        ScheduleMongodb result = scheduleMongoRepository.findByTime(time, 15, DayOfWeek.MONDAY, Month.JANUARY, 2024, "LAB1");

        assertNotNull(result);
        verify(scheduleMongoRepository, times(1)).findByTime(time, 15, DayOfWeek.MONDAY, Month.JANUARY, 2024, "LAB1");
    }

    @Test
    void shouldFindScheduleById() {
        ScheduleMongodb schedule = new ScheduleMongodb();
        when(scheduleMongoRepository.findByScheduleId("sch-1")).thenReturn(schedule);

        ScheduleMongodb result = scheduleMongoRepository.findByScheduleId("sch-1");

        assertNotNull(result);
        verify(scheduleMongoRepository, times(1)).findByScheduleId("sch-1");
    }

    @Test
    void shouldDeleteScheduleById() {
        doNothing().when(scheduleMongoRepository).deleteById("sch-1");

        assertDoesNotThrow(() -> scheduleMongoRepository.deleteById("sch-1"));
        verify(scheduleMongoRepository, times(1)).deleteById("sch-1");
    }
}
