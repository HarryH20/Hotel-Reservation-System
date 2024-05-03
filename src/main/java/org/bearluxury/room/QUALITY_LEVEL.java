package org.bearluxury.room;

/**
 * The QUALITY_LEVEL enum is used clarify the quality level of Room.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public enum QUALITY_LEVEL{
    EXECUTIVE,
    BUSINESS,
    COMFORT,
    ECONOMY;

    /**
     * Converts the enum type to string
     *
     * @return the enum type in string form
     */
    public String toString(){
        if(this == EXECUTIVE){
            return "Executive";
        }
        else if(this == BUSINESS){
            return "Business";
        }
        else if(this == COMFORT){
            return "Comfort";
        }
        else if(this == ECONOMY){
            return "Economy";
        }
        return "EMPTY";
    }

    /**
     * Converts the enum type to a string in csv format
     *
     * @return the enum type in string csv format
     */
    public String csvFormat() {
        if(this == EXECUTIVE){
            return "executive";
        }
        else if(this == BUSINESS){
            return "business";
        }
        else if(this == COMFORT){
            return "comfort";
        }
        else if(this == ECONOMY){
            return "economy";
        }
        return "EMPTY";
    }
}
