DROP TABLE IF EXISTS reservations;

-- Create the reservations table
CREATE TABLE reservations (
                              room_number INT NOT NULL,
                              first_name VARCHAR(255) NOT NULL,
                              last_name VARCHAR(255) NOT NULL,
                              email VARCHAR(255) NOT NULL,
                              number_of_guests INT NOT NULL,
                              start_date DATE NOT NULL,
                              end_date DATE NOT NULL
);
