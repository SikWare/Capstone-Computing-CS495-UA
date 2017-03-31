
package com.sikware.fixmylife;

import java.util.UUID;

/**
 * Created by Ken on 3/31/2017.
 */

class MediaItem {

    UUID id;
    UUID ownerID;
    String name;
    String type;
    String platform;
    String genre;

    package MediaItem( UUID ownerID, String name, String type, String platform, String genre) {

        this.id = new UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.type = type;
        this.platform = platform;
        this.genre = genre;

    }
}