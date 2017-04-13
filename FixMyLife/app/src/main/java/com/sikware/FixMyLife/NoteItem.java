
package com.sikware.FixMyLife;

import java.util.UUID;

/**
 * Created by Ken on 3/31/2017.
 */

class NoteItem {

    UUID id;
    UUID ownerID;
    String name;
    String details;

    public NoteItem( UUID ownerID, String name, String type, String details ) {

        this.id = UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.details = details;
    }

    public String toString(){
        String s = name + ";" + details;
        return s;
    }

}
