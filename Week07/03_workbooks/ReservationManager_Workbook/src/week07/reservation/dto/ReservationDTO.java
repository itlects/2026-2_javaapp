package week07.reservation.dto;

public record ReservationDTO(
        String reservationId,
        String customerName,
        String reservationDate,
        String reservationTime,
        String status) {
}
