package org.bearluxury;

import java.io.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

//Parser for room from CSV file
class RoomBuilder {

    ArrayList<Room> roomList;

    RoomBuilder(String csvName){
        roomList = new ArrayList<>();
        File file = new File(csvName);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine() ) != null) {
                String[] parsedLine = line.split(",");

                Room room = new Room(
                        Integer.parseInt(parsedLine[0]),
                        Double.parseDouble(parsedLine[1]),
                        Boolean.parseBoolean(parsedLine[2]),
                        readAsRoomType(parsedLine[3]),
                        readAsBedType(parsedLine[4]) ,
                        readAsQualityLevel(parsedLine[5]),
                        Integer.parseInt(parsedLine[6]));

                roomList.add(room);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    ArrayList<Room> getRoomList(){
        return roomList;
    }

    ROOM_TYPE readAsRoomType(String str){
        return ROOM_TYPE.VINTAGE_CHARM;
    }
    BED_TYPE readAsBedType(String str){
        return BED_TYPE.TWIN;
    }
    QUALITY_LEVEL readAsQualityLevel(String str){
        return QUALITY_LEVEL.BUISNESS;
    }
}
