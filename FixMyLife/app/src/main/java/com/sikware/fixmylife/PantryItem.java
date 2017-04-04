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
    Boolean isBought;

    public PantryItem( UUID ownerID, String name, String type, String unit, String quantity, Boolean bought) {

        this.id = UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.quantity = quantity;
        this.isBought = bought;

    }

    public String toString(){
        String s = name + ";" + type + ";" + unit + ";" + quantity + (isBought?"x":"");
        return s;
    }
}