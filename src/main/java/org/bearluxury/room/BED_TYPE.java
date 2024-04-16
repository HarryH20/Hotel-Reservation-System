package org.bearluxury.room;

public enum BED_TYPE{
    QUEEN,
    KING,
    FULL,
    TWIN;
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
