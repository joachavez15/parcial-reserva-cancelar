package reserva.usecase;

import reserva.exception.PersistenceException;
import reserva.exception.ReservationNotFoundException;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

import java.util.UUID;

public class CancelReservationUseCase {
    private final ReservationRepository reservationRepository;

    public CancelReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reserva execute(UUID id) {
        Reserva reserva = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("La reserva con ID " + id + " no fue encontrada"));

        reserva.cancelar();

        try {
            reservationRepository.save(reserva);
        } catch (Exception e) {
            throw new PersistenceException("Error al guardar la reserva cancelada", e);
        }

        return reserva;
    }
}