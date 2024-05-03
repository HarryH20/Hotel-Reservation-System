package org.bearluxury.room;

/**
 * The ROOM_TYPE enum is used to clarify the type of room
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public enum ROOM_TYPE{
    VINTAGE,
    URBAN,
    NATURE;

    /**
     * Converts a room type enum to a string
     * and overrides the toString method
     * @return room type enum
     */

    public String toString(){
        if(this == VINTAGE){
            return "Vintage Charm";
        }
        else if(this == URBAN){
            return "Urban Elegance";
        }
        else if(this == NATURE){
            return "Nature Retreat";
        }
        return "EMPTY";
    }

    /**
     * converts a room type enum to a string
     * @return string
     */
    public String csvFormat(){
        if(this == VINTAGE){
            return "vintage";
        }
        else if(this == URBAN){
            return "urban";
        }
        else if(this == NATURE){
            return "nature";
        }
        return "EMPTY";
    }


}
