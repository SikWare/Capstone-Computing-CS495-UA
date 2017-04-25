
package com.sikware.FixMyLife;

import java.util.UUID;

/**
 * Created by Ken on 3/31/2017.
 */

class NotesItem {

    UUID id;
    UUID ownerID;
    UUID assignedTo;
    String name;
    String details;
    String dueDate;
    String pointVal;
    boolean completed;
    boolean approved;

    public NotesItem(UUID ownerID, String name, String dueDate, String pointVal, String details) {

        this.id = UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.details = details;
        this.dueDate = dueDate;
        this.pointVal = pointVal;
//        this.assignedTo = assignTo;
        this.completed = false;
        this.approved = false;
    }

    public String toString(){
        String s = name + ";" + details;
        return s;
    }

}
