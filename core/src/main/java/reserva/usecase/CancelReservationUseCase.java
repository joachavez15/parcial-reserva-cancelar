package reserva.usecase;

import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

import java.util.UUID;

public class CancelReservationUseCase {
    private final ReservationRepository reservationRepository;

    public CancelReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void execute(UUID id) {
        Reserva reserva = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La reserva no existe"));

        reserva.cancelar();
        reservationRepository.save(reserva);
    }
}