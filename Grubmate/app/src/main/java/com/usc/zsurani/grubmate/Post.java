package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import java.util.List;

public class Post {
    Integer id;
    String description;
    User owner;
    String food;
    List<String> image;
    Integer num_requests;
    List<String> category;
    Integer beginTime;
    Integer endTime;
    String location;
    Boolean active;
    List<Group> groups;
    List<Integer> usersRequested;
    List<Integer> usersAccepted;

    /*
     * Constructor which sets member variables of post when post is created
     */
    Post(List<String> tags, List<String> category, User provider,
         List<Integer> groupID, String title, String description,
         String beginTime, String endTime, String location, String imageLink,
         Boolean visibleToAllFriends){}
    /*
     * If request is accepted, update list of ids of accepted users to include new id
     */
    void updateAcceptedRequests(Integer userId){}

    /*
     * When request is made, update list of ids of all users who requested the food to
     * include the new id
     */
    void updateAllRequests(Integer userId){}

    /*
     * If user updates description of post, update string containing description of post
     */
    void updateDescription(String description){}

    /*
     * If user updates location of food, update string containing location
     */
    void updateLocation(String loc){}

    /*
     * If user updates the maximum number of requesters they want to
     * accept, then update the integer storing this information
     */
    void updateMaxRequesters(Integer maxNum){}

    /*
     * Sets boolean allFriendsCanView to true if owner wants to make
     * the post visible to all friends on Facebook, sets boolean to
     * false if the post will only be visible to certain groups
     */
    void updateVisibleToAll(Boolean visibleToAll){}

    /*
     * Returns true if post is on homemade food, returns false if post is on restaurant food
     */
    Boolean isHomemade(){}

    /*
     * If post is active, set boolean to true, but if the post is
     * now inactive, set boolean to false
     */
    void setStatus(Boolean active){}

    /*
     * When editing a post, if user would like to add groups to
     * make the post visible to them, then add group ID to list
     * of IDs storing all groups who have access to the post
     */
    void addGroup(Integer groupId){}

    /*
     * When editing the post, if user would like to add more categories,
     * then add string to list of categories for post
     */
    void addCategory(String category){}

    /*
     * When editing the post, if user would like to add more tags then
     * add string to list of tags for post
     */
    void addTag(String tag){}

    /*
     * If user adds an image of food, add string containing link to food
     * to list containing all images
     */
    void addImage(String img){}

    /*
     * When editing the post, if user would like to remove a tag, then
     * remove string from list of tags for post
     */
    void removeTag(String tag){}

    /*
     * When editing a post, if user would like to remove groups to make
     * the post no longer visible to them, then remove group ID from
     * list of IDs storing all groups who have access to the post
     */
    void removeGroup(Integer groupId){}

    /*
     * When editing the post, if user would like to remove a category,
     * then remove string from list of categories for post
     */
    void removeCategory(String category){}

    /*
     * If user removes image of food, remove string containing link to
     * food from list containing all images
     */
    void removeImage(String img){}

    /*
     * Returns true if post is visible to all owner’s friends, false if post
     * is visible to only certain groups
     */
    Boolean getVisibility(){}

    /*
     * Returns maximum number of accepted requests owner sets
     */
    Integer getMaxRequesters(){}

    /*
     * Returns list of all images associated with post set
     * by owner of post
     */
    List<String> getImages(){}

    /*
     * Returns the User id of the provider.
     */
    Integer getProviderID(){}

    /*
     * Returns list of User ids that have been accepted as receivers
     */
    List<Integer> getAcceptedRequesters(){}

    /*
     * Returns a list of User ids that have requested on the post.
     */
    List<Integer> getAllRequesters(){}

    /*
     * Returns list of Group ids that this post is visible to.
     */
    List<Integer> getGroupID(){}

    /*
     * Returns list of strings that represent the various categories
     * that the post belongs to
     */
    List<String> getCategory(){}

    /*
     * Returns list of strings that represent the various tags
     * that the post belongs to.
     */
    List<String> getTags(){}

    /*
     * Returns the beginning of the time when the delivery will be available
     */
    Integer getBeginTime(){}

    /*
     * Returns the end of the time when the delivery will be available
     */
    Integer getEndTime(){}

    /*
     * Returns the description of the post.
     */
    String getDescription(){}

    /*
     * Sets the time when the owner wants to make the delivery available
     */
    void setBeginTime(Integer time){}

    /*
     * Sets the time when the owner wants to set the latest delivery
     */
    void setEndTIme(Integer time){}
}

