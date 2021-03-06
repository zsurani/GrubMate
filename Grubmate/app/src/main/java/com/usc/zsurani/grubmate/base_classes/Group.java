package com.usc.zsurani.grubmate.base_classes;

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
    public static final String KEY_name = "name";

    public String id;
    //change to set for easier remove/add
    public Set<String> users;
    public String ownerId;

    public String name;

    /*
     * Constructor for when new group is created
     * including list of user IDs in group, and group creator’s id
     */
    public Group(Set<String> users, String userId, String ownerId){
        id = userId;
        this.users = users;
        this.ownerId = ownerId;
    }

    public Group(Set<String> users, String userId){
        id = userId;
        this.users = users;
    }


    public Group(){}

    /*
     * When editing a group, if user wants to add a user to
     * group, add new user’s id to list of ids of users in group
     */
    void addUser(String userId){users.add(userId);}

    /*
     * When editing a group, if user wants to remove a user to group,
     * remove new user’s id from list of ids of users in group
     */
    void removeUser(Integer userId){users.remove(userId);}

    //from db
	/*
	 * Returns list of the User ids that are part of this group.
	 */
    Set<String> getUsers(){return users;}
    public String getId(){
        return id;
    }

    String getOwnerId(){return ownerId;}
    public void setUsers(Set<String> u){users = u;}
    public void setOwner(String o){
        ownerId = o;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

