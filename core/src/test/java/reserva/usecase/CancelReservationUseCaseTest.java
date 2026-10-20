package reserva.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reserva.exception.ReservationAlreadyCancelledException;
import reserva.modelo.EstadoReserva;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CancelReservationUseCaseTest {

    private ReservationRepository repository;
    private CancelReservationUseCase useCase;

    private static final UUID ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ReservationRepository.class);
        useCase = new CancelReservationUseCase(repository);
    }

    @Test
    void cancelaReservaExitosamente() {
        Reserva reserva = Reserva.instance(ID, "cliente@mail.com", LocalDate.of(2026, 12, 1), 2);
        when(repository.findById(ID)).thenReturn(Optional.of(reserva));

        useCase.execute(ID);

        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
        verify(repository, times(1)).save(reserva);
    }

    @Test
    void fallaSiLaReservaNoExiste() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(ID));
        verify(repository, never()).save(any());
    }

    @Test
    void fallaSiLaReservaYaEstabaCancelada() {
        Reserva reserva = Reserva.instance(ID, "cliente@mail.com", LocalDate.of(2026, 12, 1), 2);
        reserva.cancelar();
        when(repository.findById(ID)).thenReturn(Optional.of(reserva));

        assertThrows(ReservationAlreadyCancelledException.class, () -> useCase.execute(ID));
        verify(repository, never()).save(any());
    }
}
