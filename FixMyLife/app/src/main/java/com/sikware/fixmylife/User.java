package com.sikware.fixmylife;

import java.util.UUID;

/**
 * Created by Adam Pluth on 3/28/2017.
 */

class User {

    boolean superAdmin = false;
    boolean admin = false;
    boolean regUser = false;
    String name;
    String email;
    UUID userID;
    UUID groupID;

    public User (String name, String email, UUID groupID, int privLvl) {

        this.name = name;
        this.email = email;
        this.userID = new UUID.randomUUID();
        this.groupID = groupID;

        switch (privLvl) {
            case 0: superAdmin = true; break;
            case 1: admin = true; break;
            case 2: regUser = true; break;
        }
    }

    public void updatePrivelege(int privLvl) {
        superAdmin = false;
        admin = false;
        regUser = false;

        switch (privLvl) {
            case 0: superAdmin = true; break;
            case 1: admin = true; break;
            case 2: regUser = true; break;
        }
    }

}
