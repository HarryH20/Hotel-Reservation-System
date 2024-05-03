package org.bearluxury.room;

/**
 * The BED_TYPE enum is used clarify the bed type of Room
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public enum BED_TYPE{
    QUEEN,
    KING,
    FULL,
    TWIN;

    /**
     * Converts the enum type to string
     *
     * @return the bed type in string form
     */
    public String toString(){
        if(this == QUEEN){
            return "Queen";
        }
        else if(this == KING){
            return "King";
        }
        else if(this == FULL){
            return "Full";
        }
        else if(this == TWIN){
            return "Twin";
        }
        return "EMPTY";
    }
}
