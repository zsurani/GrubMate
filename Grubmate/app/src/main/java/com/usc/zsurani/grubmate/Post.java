package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import java.util.List;

import java.util.List;
import java.util.Set;

public class Post {

    public static final String TABLE = "Post";
    public static final String KEY_id = "id";
    public static final String KEY_description = "description";
    public static final String KEY_owner = "owner";
    public static final String KEY_food = "food";
    public static final String KEY_images = "images";
    public static final String KEY_num_requests = "num_requests";
    public static final String KEY_categories = "categories";
    public static final String KEY_tags = "tags";
    public static final String KEY_beginTime = "beginTime";
    public static final String KEY_endTime = "endTime";
    public static final String KEY_location = "location";
    public static final String KEY_active = "active";
    public static final String KEY_groups = "groups";
    public static final String KEY_usersRequested = "usersRequested";
    public static final String KEY_usersAccepted = "usersAccepted";
    public static final String KEY_homemadeNotRestaurant = "homemadeNotRestaurant";
    public static final String KEY_allFriendsCanView = "allFriendsCanView";
    public static final String KEY_maxRequesters = "maxRequesters";

    Integer id;
    String description;
    User owner;
    String food;
    Set<String> image;
    Integer num_requests;
    Set<String> category;
    String beginTime;
    String endTime;
    String location;
    Boolean active;
    Set<Group> groups;
    List<Integer> usersRequested;
    List<Integer> usersAccepted;

    /*
     * Constructor which sets member variables of post when post is created
     */
    Post(List<String> tags, Set<String> category, User provider,
         List<Integer> groupID, String title, String description,
         String beginTime, String endTime, String location, Set<String> imageLink,
         Boolean visibleToAllFriends) {
        //create variable tags? groupID add? title add? visible to all friends?
        owner = provider;
        this.description = description;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
        image = imageLink;
    }
    /*
     * If request is accepted, update list of ids of accepted users to include new id
     */
    /*
    void updateAcceptedRequests(Integer userId){
        usersAccepted.add(userId);
    }
    */

    /*
     * When request is made, update list of ids of all users who requested the food to
     * include the new id
     */
    /*
    void updateAllRequests(Integer userId){
        usersRequested.add(userId);
    } */

    /*
     * If user updates description of post, update string containing description of post
     */
    /*
    void updateDescription(String description){
        this.description = description;
    }
*/
    /*
     * If user updates location of food, update string containing location
     */
    /*
    void updateLocation(String loc){
        location = loc;
    }
*/
    /*
     * If user updates the maximum number of requesters they want to
     * accept, then update the integer storing this information
     */
    /*
    void updateMaxRequesters(Integer maxNum){
        //create new variable for this?
    }

    /*
     * Sets boolean allFriendsCanView to true if owner wants to make
     * the post visible to all friends on Facebook, sets boolean to
     * false if the post will only be visible to certain groups
     */
    /*
    void updateVisibleToAll(Boolean visibleToAll){
        //create
    }

    /*
     * Returns true if post is on homemade food, returns false if post is on restaurant food
     */
    /*
    Boolean isHomemade(){
        //create
    }

    /*
     * If post is active, set boolean to true, but if the post is
     * now inactive, set boolean to false
     */
    /*
    void setStatus(Boolean active){
        //create
    }

    /*
     * When editing a post, if user would like to add groups to
     * make the post visible to them, then add group ID to list
     * of IDs storing all groups who have access to the post
     */
    /*
    void addGroup(Integer groupId){
        //groups.add( get group by id)
    }

    /*
     * When editing the post, if user would like to add more categories,
     * then add string to list of categories for post
     */
    /*
    void addCategory(String category){
        this.category.add(category);
    }

    /*
     * When editing the post, if user would like to add more tags then
     * add string to list of tags for post
     */
    /*
    void addTag(String tag){
        //CREATE TAGS? this.tag.add(tag);
    }

    /*
     * If user adds an image of food, add string containing link to food
     * to list containing all images
     */
    /*
    void addImage(String img){
        image.add(img);
    }

    /*
     * When editing the post, if user would like to remove a tag, then
     * remove string from list of tags for post
     */
    /*
    void removeTag(String tag){
        //CREATE TAG?
    }

    /*
     * When editing a post, if user would like to remove groups to make
     * the post no longer visible to them, then remove group ID from
     * list of IDs storing all groups who have access to the post
     */
    /*
    void removeGroup(Integer groupId){
        groups.remove(groupId);
    }

    /*
     * When editing the post, if user would like to remove a category,
     * then remove string from list of categories for post
     */
    /*
    void removeCategory(String category){
        this.category.remove(category);
    }

    /*
     * If user removes image of food, remove string containing link to
     * food from list containing all images
     */
    /*
    void removeImage(String img){
        image.remove(img);
    }

    /*
     * Returns true if post is visible to all owner’s friends, false if post
     * is visible to only certain groups
     */
    /*
    Boolean getVisibility(){
        // CREATE return
    }

    /*
     * Returns maximum number of accepted requests owner sets
     */
    /*
    Integer getMaxRequesters(){
        //CREATE
    }
    */

    /*
     * Returns list of all images associated with post set
     * by owner of post
     */
    /*Set<String> getImages(){
        return image;
    }*/

    /*
     * Returns the User id of the provider.
     */
    //Integer getProviderID(){//CREATE}

	/*
	 * Returns list of User ids that have been accepted as receivers
	 */
      //  List<Integer> getAcceptedRequesters(){ return usersAccepted;}

	/*
	 * Returns a list of User ids that have requested on the post.
	 */
       // List<Integer> getAllRequesters(){return usersRequested;}

	/*
	 * Returns list of Group ids that this post is visible to.
	 */
       // Set<Integer> getGroupID(){// get group by id return groups;}

	/*
	 * Returns list of strings that represent the various categories
	 * that the post belongs to
	 */
          //  Set<String> getCategory(){return category;}

	/*
	 * Returns list of strings that represent the various tags
	 * that the post belongs to.
	 */
          //  List<String> getTags(){//CREATE}

	/*
	 * Returns the beginning of the time when the delivery will be available
	 */
              //  String getBeginTime(){return beginTime;}

	/*
	 * Returns the end of the time when the delivery will be available
	 */
            //    String getEndTime(){return endTime;}

	/*
	 * Returns the description of the post.
	 */
             //   String getDescription(){return description;}

	/*
	 * Sets the time when the owner wants to make the delivery available
	 */
          //  void setBeginTime(String time){beginTime = time;}

	/*
	 * Sets the time when the owner wants to set the latest delivery
	 */
         //   void setEndTime(String time){endTime = time;}
        }
