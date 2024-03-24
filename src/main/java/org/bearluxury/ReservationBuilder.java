package org.bearluxury;

import java.io.*;
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
            while ((line = reader.readLine()) != null) {
                String[] parsedLine = line.split(",");

                Date startDate = new Date(parsedLine[2]);
                Date endDate = new Date(parsedLine[3]);

                Reservation reservation = new Reservation(
                        Integer.parseInt(parsedLine[0]),
                        parsedLine[1],
                        startDate,
                        endDate,
                        Double.parseDouble(parsedLine[0]));

                reservationList.add(reservation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addReservation(int roomNumber,
                               String guestName,
                               Date startDate,
                               Date endDate,
                               double discount){
        Reservation reservation = new Reservation(roomNumber,
                guestName,
                startDate,
                endDate,
                discount);
        reservationList.add(reservation);
    }
    public void writeReservation(String csvName){
        File file = new File(csvName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("room number,guest name,start date,end date,discount\n");

            for(int i = 0; i < reservationList.size(); i++){
                writer.write(reservationList.get(i).getRoomNumber()+","
                        +reservationList.get(i).getGuestName()+","
                        +reservationList.get(i).getStartDate()+","
                        +reservationList.get(i).getEndDate()+","
                        +reservationList.get(i).getDiscount()+'\n');
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