package org.bearluxury.room;

import java.io.*;
import java.util.*;

/**
 * This class takes the rooms from the csv and puts it in a set
 * the set is then used to fill the room database.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */

public class RoomBuilder {
    Set <Room> roomSet;

    /**
     * Is a constructor for the RoomBuilder class.
     * Takes in a roomCSV and makes a tree set. It
     * pulls from the roomCSV and puts each room in
     * the tree set. The tree set is ordered by room
     * number.
     *
     * @param roomCSV
     */

    public RoomBuilder(String roomCSV){
        roomSet = new TreeSet<>(Comparator.comparing(Room::getRoomNumber));
        File file = new File(roomCSV);
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

                roomSet.add(room);
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


    /**
     * A setter for the set of rooms
     * @return a set of rooms
     */
    public Set<Room> getRoomSet(){
        return roomSet;
    }

    /**
     * Takes in a room type as a string
     * and converts it to an enum.
     * @param str the room type as a string
     * @return returns the enum
     */

    public static ROOM_TYPE readAsRoomType(String str){
        if(str.equals("vintage")){
            return ROOM_TYPE.VINTAGE;
        }
        else if(str.equals("urban")){
            return ROOM_TYPE.URBAN;
        }
        else if(str.equals("nature")){
            return ROOM_TYPE.NATURE;
        }

        return ROOM_TYPE.NATURE;

    }

    /**
     * Takes in a bed type as a string
     * and converts it to an enum.
     * @param str the bed type
     * @return the bed type as an enum
     */
    public static BED_TYPE readAsBedType(String str){
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

    /**
     * Takes in a quality level as a string
     * and converts it to an enum.
     * @param str the quality level
     * @return the quality level as an enum
     */
    public static QUALITY_LEVEL readAsQualityLevel(String str){
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
