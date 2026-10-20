package reserva.modelo;

import reserva.exception.InvalidReservationException;
import reserva.exception.ReservationAlreadyCancelledException;

import java.time.LocalDate;
import java.util.UUID;

public class Reserva {
    private final UUID id;
    private final String emailContacto;
    private final LocalDate fecha;
    private final int cantidadPersonas;
    private EstadoReserva estado;

    private Reserva(UUID id, String emailContacto, LocalDate fecha, int cantidadPersonas) {
        validar(id, emailContacto, fecha, cantidadPersonas);
        this.id = id;
        this.emailContacto = emailContacto;
        this.fecha = fecha;
        this.cantidadPersonas = cantidadPersonas;
        this.estado = EstadoReserva.ACTIVA;
    }

    public static Reserva instance(UUID id, String emailContacto, LocalDate fecha, int cantidadPersonas) {
        return new Reserva(id, emailContacto, fecha, cantidadPersonas);
    }

    private void validar(UUID id, String emailContacto, LocalDate fecha, int cantidadPersonas) {
        if (id == null) {
            throw new InvalidReservationException("El ID no puede ser nulo");
        }
        if (fecha == null) {
            throw new InvalidReservationException("La fecha no puede ser nula");
        }
        if (emailContacto == null || emailContacto.trim().isEmpty()) {
            throw new InvalidReservationException("El email de contacto no puede ser nulo o vacío");
        }
        if (cantidadPersonas <= 0) {
            throw new InvalidReservationException("La cantidad de personas debe ser mayor a cero");
        }
    }

    public void cancelar() {
        if (this.estado == EstadoReserva.CANCELADA) {
            throw new ReservationAlreadyCancelledException("La reserva ya se encuentra cancelada");
        }
        this.estado = EstadoReserva.CANCELADA;
    }

    public UUID getId() {
        return id;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public EstadoReserva getEstado() {
        return estado;
    }
}