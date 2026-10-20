package reserva.modelo;

import org.junit.jupiter.api.Test;
import reserva.exception.InvalidReservationException;
import reserva.exception.ReservationAlreadyCancelledException;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String EMAIL = "cliente@mail.com";
    private static final LocalDate FECHA = LocalDate.of(2026, 11, 15);

    @Test
    void creaReservaValidaConSusDatos() {
        Reserva reserva = Reserva.instance(ID, EMAIL, FECHA, 4);

        assertEquals(ID, reserva.getId());
        assertEquals(EMAIL, reserva.getEmailContacto());
        assertEquals(FECHA, reserva.getFecha());
        assertEquals(4, reserva.getCantidadPersonas());
    }

    @Test
    void reservaNuevaComienzaActiva() {
        Reserva reserva = Reserva.instance(ID, EMAIL, FECHA, 2);

        assertEquals(EstadoReserva.ACTIVA, reserva.getEstado());
    }

    @Test
    void fallaSiIdEsNulo() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(null, EMAIL, FECHA, 2));
    }

    @Test
    void fallaSiFechaEsNula() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(ID, EMAIL, null, 2));
    }

    @Test
    void fallaSiEmailEsNulo() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(ID, null, FECHA, 2));
    }

    @Test
    void fallaSiEmailEstaVacio() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(ID, "", FECHA, 2));
    }

    @Test
    void fallaSiEmailSoloTieneEspacios() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(ID, "   ", FECHA, 2));
    }

    @Test
    void fallaSiCantidadDePersonasEsCero() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(ID, EMAIL, FECHA, 0));
    }

    @Test
    void fallaSiCantidadDePersonasEsNegativa() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(ID, EMAIL, FECHA, -1));
    }

    @Test
    void cancelarCambiaElEstadoACancelada() {
        Reserva reserva = Reserva.instance(ID, EMAIL, FECHA, 2);

        reserva.cancelar();

        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
    }

    @Test
    void noSePuedeCancelarUnaReservaYaCancelada() {
        Reserva reserva = Reserva.instance(ID, EMAIL, FECHA, 2);
        reserva.cancelar();

        assertThrows(ReservationAlreadyCancelledException.class, reserva::cancelar);
    }
}