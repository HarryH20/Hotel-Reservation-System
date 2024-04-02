package org.bearluxury;


import org.bearluxury.DAO;
import org.bearluxury.Reservation;
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
    private JdbcTemplate jdbcTemplate;

    public reservationJDBCDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Reservation> rowMapper = ((rs, rowNum) -> {
        Reservation reservation = new Reservation();
        reservation.setRoomNumber(rs.getInt("room_number"));
        reservation.setGuestName(rs.getString("guest_name"));
        reservation.setStartDate(rs.getDate("start_date"));
        reservation.setEndDate(rs.getDate("end_date"));
        reservation.setDiscount(rs.getDouble("discount"));
        return reservation;
    } );



    @Override
    public List<Reservation> list() {
        String sql = "SELECT room_number, guest_name, start_date, end_date, discount, from reservations";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Reservation reservation) {
        String sql = "insert into reservations(room_number, guest_name, start_date, end_date, discount) values (?,?,?,?,?)";
        int insert = jdbcTemplate.update(sql, reservation.getRoomNumber(), reservation.getGuestName(), reservation.getStartDate(), reservation.getEndDate(), reservation.getDiscount());
        if(insert == 1){
            log.info("New reservation created: " + reservation.getGuestName());

        }


    }

    @Override
    public Optional<Reservation> get(int roomNumber) {
        String sql = "SELECT room_number, guest_name, start_date, end_date, discount, from reservations where room_number = ?";
        Reservation reservation = null;
        try{
            reservation = jdbcTemplate.queryForObject(sql, new Object[]{roomNumber}, rowMapper);

        }catch(DataAccessException ex){
            log.info("Reservation not found: " + roomNumber);

        }


        return Optional.ofNullable(reservation);
    }

    @Override
    public void update(Reservation reservation, int roomNumber) {
        String sql = "update reservations set guest_name = ?, start_date = ?, end_date = ?, discount = ? where room_number = ?";
        int update = jdbcTemplate.update(sql, reservation.getGuestName(), reservation.getStartDate(), reservation.getEndDate(), reservation.getDiscount(), roomNumber);
        if(update == 1){
            log.info("Reservation updated: " + reservation.getGuestName());
        }

    }

    @Override
    public void delete(int roomNumber) {
        jdbcTemplate.update("delete from reservations where room_number = ?", roomNumber);

    }
}
