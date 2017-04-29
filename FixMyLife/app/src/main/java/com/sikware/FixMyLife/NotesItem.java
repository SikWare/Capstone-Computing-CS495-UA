
package com.sikware.FixMyLife;

import java.util.UUID;

/**
 * Created by Ken on 3/31/2017.
 */

class NotesItem {

    UUID id;
    UUID ownerID;
    String name;
    String details;


    public NotesItem(UUID id, String name, String details) {

        this.id = id;
        //this.ownerID = ownerID;
        this.name = name;
        this.details = details;

    }

    public String toString(){
        String s = name + ";" + details;
        return s;
    }

}
