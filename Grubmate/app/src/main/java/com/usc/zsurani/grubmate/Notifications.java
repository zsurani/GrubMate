package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import java.util.List;

import java.util.List;
import java.util.Set;

public class Notifications {
    public static final String TABLE = "Notifs";
    public static final String KEY_id = "id";
    public static final String KEY_userID = "userID";
    public static final String KEY_requestorID = "requestorID";
    public static final String KEY_postID = "postID";
    public static final String KEY_location = "location";
    public static final String KEY_category = "category";
    public static final String KEY_tags = "tags";
    public static final String KEY_beginTime = "beginTime";
    public static final String KEY_endTime = "endTime";
    public static final String KEY_status = "status";
    public static final String KEY_name = "name";
    public static final String KEY_type = "type";

    String userID;


    Integer id;
    Integer postID;
    String requestID;
    Integer requesterID;
    Integer userId;

    Set<String> category;
    Set<String> tags;
    String beginTime;
    String endTime;
    Boolean status;
    String name;
    String type;
    String food;

    /*
     * Constructor (Type 1) for subscription type notification in
     * which user selects when to be notified of a post matching
     * inputted criteria
     */
    Notifications(String postName, Set<String> tags,
                  Set<String> category, String timeStart, String timeEnd, String type, String userId){
        name = postName;
        this.tags = tags;
        this.category = category;
        this.beginTime= timeStart;
        this.endTime= timeEnd;
        this.type = type;
        this.userID = userId;
        //idk if we want to add this or not
        this.requestID = "N/A";
    }

    /*
     * Constructor (Type 2) for notification in which provider gets
     * notification of someone sending a request to their post
     */

    Notifications(Integer postID, Integer requesterID, Integer providerID,
                  String locationOfRequester, String type){
        //PostID, requesterID, providerID,locationOfRequester need to create variable?
        this.postID = postID;
        this.requesterID = requesterID;
        this.userId = providerID;

        this.type = type;
        this.setActiveStatus(true);
        //idk if we want to add this or not
        this.beginTime = "N/A";
        this.endTime = "N/A";
        this.category = null;
        this.tags = null;


    }

    /*
     * Constructor (Type 3) for notification in which receiver
     * gets notification of whether provider accepted or rejected
     * their request
     */
    Notifications(Integer postID, Integer requesterID, Integer providerID,
                  Boolean accepted, String type){
        //PostID, requesterID, providerID,locationOfRequester need to create variable?
        status = accepted;
        this.type = type;
    }

    /*
     * Constructor (Type 4) for notification sent after transaction
     * is completed to all users involved, telling them to rate the
     * provider/receiver based on experience
     */
    Notifications(Integer postID, List<Integer> receiversID,
                  Integer providerID, String type){
        //PostID, requesterID, providerID need to create variable?
        this.type = type;
    }

    /*
     * Sets the ID for the current Notifications object.
     */
    void setId(int id) { this.id = id; }

    /*
     * Returns true if notification (Type 1) is still active,
     * returns false if inactive
     */
    Boolean isActive(){return status;}

    /*
     * If user unsubscribes from notification (Type 1), then set notification
     * to inactive and update database
     */
    void unsubscribe(){}

    /*
     * For notification (Type 1), update category
     */
    void updateCategory(String category){}

    /*
     * For notification (Type 1) if updating subscription to add tag,
     * add tag to list of string tags
     */
    void addTag(String tag){tags.add(tag);}

    /*
     * For notification (Type 1) if updating subscription to remove
     * tag, remove tag from list of string tags
     */
    void removeTag(String tag){tags.remove(tag);}

    /*
     * Returns the category that the notification was created with
     */
    Set<String> getCategory(){return category;}

    /*
     * Returns a list of tags associated with the notification
     * determined by the creator
     */
    Set<String> getTags(){return tags;}

    /*
     * Returns time set by creator of notification of beginning of
     * time period for notification
     */
    String getBeginTime(){return beginTime;}

    /*
    Returns the id of the notification that is being stored in database
     */
    Integer getId(){return id;}

    /*
    Returns the id of the user who owns the notification
     */
    String getUserId(){return userID;}

    /*
     * Returns time set by creator of notification of end
     * of time period for notification
     */
    String getEndTime(){return endTime;}

    /*
     * Changes notification (Type 1) to true if active, false if inactive
     */
    void setActiveStatus(Boolean active){ this.status = active; }

    /*
     * Sets or changes the beginning time set in the notification
     */
    void setBeginTime(String time){beginTime = time;}

    /*
     * Sets or changes the end time set in the notification
     */
    void setEndTime(String time){endTime = time;}

    /*
    Returns the name of the post
     */
    String getName(){return name;}

    /*
    Returns the type of the notification
     */
    String getType() {return type;}

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public Integer getRequestID() {
        return requesterID;
    }

    public void setRequestID(Integer requestID) {
        this.requesterID = requestID;
    }

    public Integer getProvider() {
        return userId;
    }

    public void setProvider(Integer userId) {
        this.userId = userId;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
