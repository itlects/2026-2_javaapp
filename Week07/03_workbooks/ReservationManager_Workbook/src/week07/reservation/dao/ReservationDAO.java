package week07.reservation.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import week07.reservation.dto.ReservationDTO;
import week07.reservation.util.DBConnection;

public class ReservationDAO {
    public List<ReservationDTO> findAll() throws SQLException {
        List<ReservationDTO> list = new ArrayList<>();
        String sql = "SELECT reservation_id, customer_name, reservation_date, reservation_time, status "
                   + "FROM reservation ORDER BY reservation_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String date = rs.getDate("reservation_date").toLocalDate().toString();
                String time = rs.getTime("reservation_time").toLocalTime().toString();
                if (time.length() > 5) time = time.substring(0, 5);
                list.add(new ReservationDTO(
                        rs.getString("reservation_id"),
                        rs.getString("customer_name"),
                        date,
                        time,
                        rs.getString("status")));
            }
        }
        return list;
    }

    public int insert(ReservationDTO r) throws SQLException {
        String sql = "INSERT INTO reservation "
                   + "(reservation_id, customer_name, reservation_date, reservation_time, status) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.reservationId());
            ps.setString(2, r.customerName());
            ps.setDate(3, Date.valueOf(r.reservationDate()));
            ps.setTime(4, Time.valueOf(normalizeTime(r.reservationTime())));
            ps.setString(5, r.status());
            return ps.executeUpdate();
        }
    }

    public int update(ReservationDTO r) throws SQLException {
        String sql = "UPDATE reservation SET customer_name=?, reservation_date=?, reservation_time=?, status=? "
                   + "WHERE reservation_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.customerName());
            ps.setDate(2, Date.valueOf(r.reservationDate()));
            ps.setTime(3, Time.valueOf(normalizeTime(r.reservationTime())));
            ps.setString(4, r.status());
            ps.setString(5, r.reservationId());
            return ps.executeUpdate();
        }
    }

    public int delete(String reservationId) throws SQLException {
        String sql = "DELETE FROM reservation WHERE reservation_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, reservationId);
            return ps.executeUpdate();
        }
    }

    private String normalizeTime(String value) {
        return value.length() == 5 ? value + ":00" : value;
    }
}
