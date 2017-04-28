
package com.sikware.FixMyLife;

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
    String isBought;

    public MediaItem( UUID ownerID, String name, String type, String platform, String genre, String bought) {

        this.id = UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.type = type;
        this.platform = platform;
        this.genre = genre;
        this.isBought = bought;


    }
    @Override
    public String toString(){
        String s = name + ";" + type + ";" + platform + ";" + genre + ";" + isBought;
        return s;
    }


}