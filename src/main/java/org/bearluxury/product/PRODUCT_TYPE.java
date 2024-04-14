package org.bearluxury.product;

public enum PRODUCT_TYPE {
    CLOTHING,
    ACCESSORY,
    ARTESIAN_GOOD,
    TOILETRY,
    PHARMACEUTICAL;

    public String toString(){
        if(this == CLOTHING){
            return "clothing";
        }
        else if(this == ACCESSORY){
            return "accessory";
        }
        else if(this == ARTESIAN_GOOD){
            return "artesian good";
        }
        else if(this == TOILETRY) {
            return "toiletry";
        }
        return "pharmaceutical";
    }
}

