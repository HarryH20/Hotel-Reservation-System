package org.bearluxury.product;


/**
 * Enumeration representing different types of products.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public enum PRODUCT_TYPE {
    CLOTHING,

    ACCESSORY,

    ARTESIAN_GOOD,

    TOILETRY,


    PHARMACEUTICAL;

    /**
     * Returns a string representation of the product type.
     *
     * @return a string representation of the product type
     */
    @Override
    public String toString() {
        switch (this) {
            case CLOTHING:
                return "clothing";
            case ACCESSORY:
                return "accessory";
            case ARTESIAN_GOOD:
                return "artesian good";
            case TOILETRY:
                return "toiletry";
            case PHARMACEUTICAL:
                return "pharmaceutical";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}

