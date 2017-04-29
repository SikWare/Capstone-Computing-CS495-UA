package com.sikware.FixMyLife;

import java.util.UUID;
import java.util.Date;

/**
 * Created by Ken on 3/31/2017.
 */

public class TaskItem {

    UUID id;
    UUID ownerID;
    String name;
    String details;
    String dueDate;
    String assignedTo; //Will probably need to change this to "User assignedTo;" once User class is implemented.
    Boolean completed = false;
    Boolean approved = false;

    public TaskItem(UUID id, String name, String details, String dueDate) {

        this.id = UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.details = details;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;

    }

    public String toString(){
        String s = name + ";" + details + ";" + dueDate;
        return s;
    }


}
