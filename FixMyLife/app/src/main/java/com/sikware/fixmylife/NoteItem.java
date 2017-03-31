
package com.sikware.fixmylife;

import java.util.UUID;

/**
 * Created by Ken on 3/31/2017.
 */

class NoteItem {

    UUID id;
    UUID ownerID;
    String name;
    String details;

    public MediaItem( UUID ownerID, String name, String type, String details ) {

        this.id = new UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.details = details;

    }
}
