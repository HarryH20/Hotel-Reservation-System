package org.bearluxury;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

//Parser for room from CSV file
class RoomBuilder {
    Set <Room> roomList;

    RoomBuilder(String csvName){
        roomList = new TreeSet<>(Comparator.comparing(Room::getRoomNumber));
        File file = new File(csvName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine();
            while ((line = reader.readLine() ) != null) {
                String[] parsedLine = line.split(",");

                Room room = new Room(
                        Integer.parseInt(parsedLine[0]),
                        Double.parseDouble(parsedLine[1]),
                        Boolean.parseBoolean(parsedLine[5]),
                        readAsRoomType(parsedLine[2]),
                        readAsBedType(parsedLine[6]) ,
                        readAsQualityLevel(parsedLine[3]),
                        Integer.parseInt(parsedLine[4]));

                roomList.add(room);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }
    Set<Room> getRoomList(){
        return roomList;
    }

    public boolean addRoom(int roomNumber, double price, boolean canSmoke,
                        ROOM_TYPE roomType, BED_TYPE bed,
                        QUALITY_LEVEL qualityLevel, int numberOfBeds){
        Room room = new Room(roomNumber,
                price,
                canSmoke,
                roomType,
                bed,
                qualityLevel,
                numberOfBeds);
       return roomList.add(room);

    }
    public void writeRoom(String csvName){
        File file = new File(csvName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("room number, price, room type, quality, number of beds, smoke, type of beds\n");

            //FORMAT: room number	price	room type	quality	number of beds	smoke	type of beds
           for(Room room: roomList){
                writer.write(room.getRoomNumber()+","
                        +room.getPrice()+","
                        +room.getRoomType().csvFormat()+","
                        +room.getQualityLevel().csvFormat()+","
                        +room.getNumberOfBeds()+","
                        +room.isCanSmoke() +","
                        +room.getBed()+'\n');
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

    ROOM_TYPE readAsRoomType(String str){
        if(str.equals("vintage")){
            return ROOM_TYPE.VINTAGE_CHARM;
        }
        else if(str.equals("urban")){
            return ROOM_TYPE.URBAN_ELEGANCE;
        }
        else if(str.equals("nature")){
            return ROOM_TYPE.NATURE_RETREAT;
        }

        return ROOM_TYPE.NATURE_RETREAT;

    }
    BED_TYPE readAsBedType(String str){
        if(str.equals("King")){
            return BED_TYPE.KING;
        }
        else if(str.equals("Queen")){
            return BED_TYPE.QUEEN;
        }
        else if(str.equals("Full")){
            return BED_TYPE.FULL;
        }
        else if(str.equals("Twin")){
            return BED_TYPE.TWIN;
        }
        return BED_TYPE.KING;
    }
    QUALITY_LEVEL readAsQualityLevel(String str){
        if(str.equals("business")){
            return QUALITY_LEVEL.BUSINESS;
        }
        else if(str.equals("comfort")){
            return QUALITY_LEVEL.COMFORT;
        }
        else if(str.equals("economy")){
            return QUALITY_LEVEL.ECONOMY;
        }
        else if(str.equals("executive")){
            return QUALITY_LEVEL.EXECUTIVE;
        }
        return  QUALITY_LEVEL.COMFORT;

    }
}
