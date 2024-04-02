package org.bearluxury;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class ReservationBuilder {

    ArrayList<Reservation> reservationList;

    ReservationBuilder(String csvName) {
        reservationList = new ArrayList<>();
        File file = new File(csvName);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            //ignore first line of garbage
            line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parsedLine = line.split(",");

                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(parsedLine[5]);
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(parsedLine[6]);

                // room number,first name,last name,email,number of guests,start date,end date
                Reservation reservation = new Reservation(
                        Integer.parseInt(parsedLine[0]),
                        parsedLine[1],
                        parsedLine[2],
                        parsedLine[3],
                        Integer.parseInt(parsedLine[4]),
                        startDate,
                        endDate);
                reservationList.add(reservation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public void addReservation(int roomNumber,
                               String firstName,
                               String lastName,
                               String email,
                               int numberOfGuests,
                               Date startDate,
                               Date endDate){
        Reservation reservation = new Reservation(roomNumber,
                firstName,
                lastName,
                email,
                numberOfGuests,
                startDate,
                endDate);
        reservationList.add(reservation);
    }
    public void writeReservation(String csvName){
        File file = new File(csvName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("room number,first name,last name,email,number of guests,start date,end date\n");

            for(int i = 0; i < reservationList.size(); i++){
                writer.write(reservationList.get(i).toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }

    
    ArrayList<Reservation> getReservationList(){
        return reservationList;
    }

}