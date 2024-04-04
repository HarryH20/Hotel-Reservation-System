package org.bearluxury;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class reservationJDBCDAO implements DAO<Reservation> {
    private static final Logger log = LoggerFactory.getLogger(reservationJDBCDAO.class);
    private final JdbcTemplate jdbcTemplate;

    public reservationJDBCDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = ((rs, rowNum) -> {
        Reservation reservation = new Reservation();
        reservation.setRoomNumber(rs.getInt("room_number"));
        reservation.setFirstName(rs.getString("first_name"));
        reservation.setLastName(rs.getString("last_name"));
        reservation.setEmail(rs.getString("email"));
        reservation.setNumberOfGuests(rs.getInt("number_of_guests"));
        reservation.setStartDate(rs.getDate("start_date"));
        reservation.setEndDate(rs.getDate("end_date"));
        return reservation;
    });

    @Override
    public List<Reservation> list() {
        String sql = "SELECT room_number, first_name, last_name, email, number_of_guests, start_date, end_date FROM reservations";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Reservation reservation) {
        String sql = "INSERT INTO reservations (room_number, first_name, last_name, email, number_of_guests, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getRoomNumber(), reservation.getFirstName(), reservation.getLastName(), reservation.getEmail(), reservation.getNumberOfGuests(), reservation.getStartDate(), reservation.getEndDate());
    }

    @Override
    public Optional<Reservation> get(int roomNumber) {
        String sql = "SELECT room_number, first_name, last_name, email, number_of_guests, start_date, end_date FROM reservations WHERE room_number = ?";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, new Object[]{roomNumber}, rowMapper);
            return Optional.ofNullable(reservation);
        } catch (DataAccessException e) {
            log.info("Reservation not found for room number: " + roomNumber);
            return Optional.empty();
        }
    }

    @Override
    public void update(Reservation reservation, int roomNumber) {
        String sql = "UPDATE reservations SET first_name = ?, last_name = ?, email = ?, number_of_guests = ?, start_date = ?, end_date = ? WHERE room_number = ?";
        jdbcTemplate.update(sql, reservation.getFirstName(), reservation.getLastName(), reservation.getEmail(), reservation.getNumberOfGuests(), reservation.getStartDate(), reservation.getEndDate(), roomNumber);
    }

    @Override
    public void delete(int roomNumber) {
        jdbcTemplate.update("DELETE FROM reservations WHERE room_number = ?", roomNumber);
    }
}

