package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import java.util.List;

public class Notifications {
    Integer userID;
    Integer id;
    List<String> category;
    List<String> tags;
    Integer beginTime;
    Integer endTime;
    Boolean status;
    String name;
    Integer type;

    /*
     * Constructor (Type 1) for subscription type notification in
     * which user selects when to be notified of a post matching
     * inputted criteria
     */
    Notifications(String postName, List<String> tags,
                  List<String> category, String time, String type){}

    /*
     * Constructor (Type 2) for notification in which provider gets
     * notification of someone sending a request to their post
     */
    Notifications(Integer postID, Integer requesterID, Integer providerID,
                  String locationOfRequester, String type){}

    /*
     * Constructor (Type 3) for notification in which receiver
     * gets notification of whether provider accepted or rejected
     * their request
     */
    Notifications(Integer postID, Integer requesterID, Integer providerID,
                  Boolean accepted, String type){}

    /*
     * Constructor (Type 4) for notification sent after transaction
     * is completed to all users involved, telling them to rate the
     * provider/receiver based on experience
     */
    Notifications(Integer postID, List<Integer> receiversID,
                  Integer providerID, String type){}

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
    void addTag(String tag){}

    /*
     * For notification (Type 1) if updating subscription to remove
     * tag, remove tag from list of string tags
     */
    void removeTag(String tag){}

    /*
     * Returns the category that the notification was created with
     */
    List<String> getCategory(){return category;}

    /*
     * Returns a list of tags associated with the notification
     * determined by the creator
     */
    List<String> getTags(){return tags;}

    /*
     * Returns time set by creator of notification of beginning of
     * time period for notification
     */
    Integer getBeginTime(){return beginTime;}

    /*
     * Returns time set by creator of notification of end
     * of time period for notification
     */
    Integer getEndTime(){return endTime;}

    /*
     * Changes notification (Type 1) to true if active, false if inactive
     */
    void setActiveStatus(Boolean active){}

    /*
     * Sets or changes the beginning time set in the notification
     */
    void setBeginTime(Integer time){}

    /*
     * Sets or changes the end time set in the notification
     */
    void setEndTime(Integer time){}

}


