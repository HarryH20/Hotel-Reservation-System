DROP TABLE IF EXISTS reservations;

create table reservations (
                              room_number INT NOT NULL ,
                              guest_name VARCHAR(255) NOT NULL,
                              start_date DATE NOT NULL ,
                              end_date DATE NOT NULL ,
                              discount DOUBLE NOT NULL
);
