
package com.sikware.fixmylife;

import android.support.annotation.NonNull;

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
    Boolean isBought;

    public MediaItem( UUID ownerID, String name, String type, String platform, String genre, Boolean bought) {

        this.id = UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.type = type;
        this.platform = platform;
        this.genre = genre;
        this.isBought = bought;


    }

    public String toString(){
        String s = name + ";" + type + ";" + platform + ";" + genre + (isBought?"x":"");
        return s;
    }


}