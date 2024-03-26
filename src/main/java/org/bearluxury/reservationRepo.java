package org.bearluxury;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class reservationRepo {
    private JdbcTemplate template;

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public void save(Reservation reservation){
        String data = "insert into RESERVATIONDB.PUBLIC.RESERVATIONS (room_Number, guest_Name, start_Date, end_Date, discount) values (?,?,?,?,?)";
        int rows = template.update(data,
                reservation.getRoomNumber(),
                reservation.getGuestName(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getDiscount());
        System.out.println(rows + " rows affected");
    }



    public List<Reservation> findAll(){
        String data = "select * from RESERVATIONS";
        RowMapper<Reservation> mapper = new RowMapper<Reservation>() {
            @Override
            public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
                Reservation res = new Reservation();
                res.setRoomNumber(rs.getInt(1));
                res.setGuestName(rs.getString(2));
                res.setStartDate(rs.getDate(3));
                res.setEndDate(rs.getDate(4));
                res.setDiscount(rs.getDouble(5));
                return res;
            }
        };



        List<Reservation> reservationList = template.query(data,mapper);

        // Print reservations in tabular format
        System.out.println("Room Number | Guest Name | Start Date | End Date | Discount");
        System.out.println("--------------------------------------------------------------");
        for (Reservation reservation : reservationList) {
            System.out.println(reservation.toString());
        }
        return reservationList;

    }

}
