package edu.eci.cvds.labReserves.repository.mongodb;

import edu.eci.cvds.labReserves.collections.ReserveMongodb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveMongoRepositoryTest {

    @Mock
    private ReserveMongoRepository reserveMongoRepository;

    @Test
    void shouldFindReservesByUserId() {
        ReserveMongodb reserve = new ReserveMongodb();
        when(reserveMongoRepository.findByUserId(1)).thenReturn(List.of(reserve));

        List<ReserveMongodb> result = reserveMongoRepository.findByUserId(1);

        assertFalse(result.isEmpty());
        verify(reserveMongoRepository, times(1)).findByUserId(1);
    }

    @Test
    void shouldFindReserveByReserveId() {
        ReserveMongodb reserve = new ReserveMongodb();
        when(reserveMongoRepository.findByReserveId("res-1")).thenReturn(reserve);

        ReserveMongodb result = reserveMongoRepository.findByReserveId("res-1");

        assertNotNull(result);
        verify(reserveMongoRepository, times(1)).findByReserveId("res-1");
    }

    @Test
    void shouldFindByScheduleId() {
        ReserveMongodb reserve = new ReserveMongodb();
        when(reserveMongoRepository.findByScheduleId("sch-1")).thenReturn(reserve);

        ReserveMongodb result = reserveMongoRepository.findByScheduleId("sch-1");

        assertNotNull(result);
        verify(reserveMongoRepository, times(1)).findByScheduleId("sch-1");
    }

    @Test
    void shouldGetAllByUserId() {
        ReserveMongodb reserve = new ReserveMongodb();
        when(reserveMongoRepository.getAllByUserId(1)).thenReturn(List.of(reserve));

        List<ReserveMongodb> result = reserveMongoRepository.getAllByUserId(1);

        assertFalse(result.isEmpty());
        verify(reserveMongoRepository, times(1)).getAllByUserId(1);
    }

    @Test
    void shouldDeleteReserveById() {
        doNothing().when(reserveMongoRepository).deleteById("res-1");

        assertDoesNotThrow(() -> reserveMongoRepository.deleteById("res-1"));
        verify(reserveMongoRepository, times(1)).deleteById("res-1");
    }
}