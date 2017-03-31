package com.sikware.fixmylife;

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
    Date dueDate;
    String assignedTo; //Will probably need to change this to "User assignedTo;" once User class is implemented.
    Boolean completed = false;
    Boolean approved = false;

    public TaskItem(UUID ownerID, String name, String details, Date due, String assignedTo ) {

        this.id = new UUID.randomUUID();
        this.ownerID = ownerID;
        this.name = name;
        this.details = details;
        this.dueDate = due;
        this.assignedTo = assignedTo;

    }


}
