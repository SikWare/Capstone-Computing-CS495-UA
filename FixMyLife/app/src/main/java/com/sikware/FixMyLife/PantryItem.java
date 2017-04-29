package com.sikware.FixMyLife;

import java.util.UUID;

/**
 * Created by Ken on 3/31/2017.
 */

class PantryItem {

    UUID id;
    UUID ownerID;
    String name;
    String type;
    String unit;
    String quantity;
    String isBought;

    public PantryItem( UUID id, String name, String type, String unit, String quantity, String bought) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.quantity = quantity;
        this.isBought = bought;

    }

    public String toString(){
        String s = name + ";" + type + ";" + unit + ";" + quantity + ";" + isBought;
        return s;
    }
}