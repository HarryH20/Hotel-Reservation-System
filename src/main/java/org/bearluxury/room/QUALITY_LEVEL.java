package org.bearluxury.room;

public enum QUALITY_LEVEL{
    EXECUTIVE,
    BUSINESS,
    COMFORT,
    ECONOMY;
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
