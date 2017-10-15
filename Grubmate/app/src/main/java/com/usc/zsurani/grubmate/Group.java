package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import java.util.List;
import java.util.Set;

public class Group {
    public static final String TABLE = "Groups";
    public static final String KEY_id = "id";
    public static final String KEY_ownerid = "owner";
    public static final String KEY_user = "users";

    Integer id;
    //change to set for easier remove/add
    Set<Integer> users;

    /*
     * Constructor for when new group is created
     * including list of user IDs in group, and group creator’s id
     */
    Group(Set<Integer> users, Integer userId){
        id = userId;
        this.users = users;
    }

    /*
     * When editing a group, if user wants to add a user to
     * group, add new user’s id to list of ids of users in group
     */
    void addUser(Integer userId){users.add(userId);}

    /*
     * When editing a group, if user wants to remove a user to group,
     * remove new user’s id from list of ids of users in group
     */
    void removeUser(Integer userId){users.remove(userId);}

    //from db
	/*
	 * Returns list of the User ids that are part of this group.
	 */
    Set<Integer> getUsers(){return users;}
}

