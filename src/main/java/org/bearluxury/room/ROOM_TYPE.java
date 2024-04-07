package org.bearluxury.room;

public enum ROOM_TYPE{
    VINTAGE,
    URBAN,
    NATURE;

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
