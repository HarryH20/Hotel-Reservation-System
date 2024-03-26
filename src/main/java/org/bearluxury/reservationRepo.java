package org.bearluxury;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void save(Reservation reservations){
        String data = "insert into reservations (room_Number, guest_Name, start_Date, end_Date, discount) values (?,?,?,?,?)";
        int rows = template.update(data,
                reservations.getRoomNumber(),
                reservations.getGuestName(),
                reservations.getStartDate(),
                reservations.getEndDate(),
                reservations.getDiscount());
        System.out.println(rows + " rows affected");
    }



    public List<Reservation> findAll(){
        String data = "select * from reservations";
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

        return reservationList;

    }
}
