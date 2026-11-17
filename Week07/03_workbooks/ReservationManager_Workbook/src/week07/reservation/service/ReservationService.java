package week07.reservation.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import week07.reservation.dao.ReservationDAO;
import week07.reservation.dto.ReservationDTO;

public class ReservationService {
    private final ReservationDAO dao = new ReservationDAO();

    public List<ReservationDTO> getReservations() throws SQLException {
        return dao.findAll();
    }

    public void addReservation(ReservationDTO r) throws SQLException {
        validate(r);
        dao.insert(r);
    }

    public void updateReservation(ReservationDTO r) throws SQLException {
        validate(r);
        dao.update(r);
    }

    public void deleteReservation(String id) throws SQLException {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("삭제할 예약을 선택하세요.");
        }
        dao.delete(id);
    }

    private void validate(ReservationDTO r) {
        if (r.reservationId() == null || r.reservationId().isBlank()) {
            throw new IllegalArgumentException("예약번호를 입력하세요.");
        }
        if (r.customerName() == null || r.customerName().isBlank()) {
            throw new IllegalArgumentException("고객명을 입력하세요.");
        }
        try {
            LocalDate.parse(r.reservationDate());
        } catch (Exception e) {
            throw new IllegalArgumentException("날짜는 yyyy-MM-dd 형식입니다.");
        }
        try {
            LocalTime.parse(r.reservationTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("시간은 HH:mm 형식입니다.");
        }
        if (r.status() == null || r.status().isBlank()) {
            throw new IllegalArgumentException("상태를 선택하세요.");
        }
    }
}
