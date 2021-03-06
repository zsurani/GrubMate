package com.usc.zsurani.grubmate.base_classes;

import android.util.Log;

import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.Time;

import java.util.HashSet;

import java.util.Set;

public class Post{

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

    public String description;
    public String owner_string;
    public String food;
    public String num_requests;
    public String categories;
    public String tag;
    public String beginTime;
    public String endTime;
    public String location;
    public String active_status;
    public String userRequested;
    public String userAccepted;
    public String homemade;
    public String user_rating;
    public String groupString;

    public Integer id;
    public String title;
    public User owner;
    public Set<String> image;
    public Set<String> category;
    public Set<String> tags;
    public Set<String> groups;
    public Boolean active;
    public Set<String> usersRequested;
    public Set<String> usersAccepted;
    public Boolean visibleToAll;
    public Integer maxAccepted;
    public Boolean isHomemade;

    byte[] photo_image;

    /*
     * Constructor which sets member variables of post when post is created
     */
    public Post(Set<String> tags, Set<String> category, User provider,
         Set<String> groupID, String title, String description,
         String beginTime, String endTime, String location, Set<String> imageLink,
         Boolean visibleToAllFriends, Integer maxAccepted, Boolean homemade) {
        //create variable tags? groupID add? title add? visible to all friends?
        owner = provider;
        this.description = description;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
        image = imageLink;
        this.tags = tags;
        this.groups = groupID;
        this.title = title;
        visibleToAll = visibleToAllFriends;
        this.maxAccepted = maxAccepted;
        isHomemade = homemade;
    }

    // images in between food and num_requests
    // groups in between active and usersRequested
    // allFriendsCanView at end
    public Post(String description, String owner, String food, byte[] images, String num_requests, String categories, String tags,
         String beginTime, String endTime, String location, String active,
         String usersRequested, String usersAccepted, String homemade_tag) {
        this.description = description;
        this.owner_string = owner;
        this.food = food;
        this.num_requests = num_requests;
        this.categories = categories;
        this.tag = tags;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
        this.active_status = active;
        this.userRequested = usersRequested;
        this.userAccepted = usersAccepted;
        this.homemade = homemade_tag;
        this.photo_image = images;
        Log.d("DEBUG", "in post");
    }

    public Post() {};

    /*
     * If request is accepted, update list of ids of accepted users to include new id
     */
    void updateAcceptedRequests(String userId) {
        usersAccepted.add(userId);
    }

    /*
     * When request is made, update list of ids of all users who requested the food to
     * include the new id
     */
    void updateAllRequests(String userId) {
        usersRequested.add(userId);
    }

    /*
     * If user updates description of post, update string containing description of post
     */
    void updateDescription(String description) {
        this.description = description;
    }

    /*
     * If user updates location of food, update string containing location
     */
    void updateLocation(String loc) {
        location = loc;
    }

    /*
     * If user updates the maximum number of requesters they want to
     * accept, then update the integer storing this information
     */
    void updateMaxRequesters(Integer maxNum) {
        maxAccepted = maxNum;
    }

    /*
     * Sets boolean allFriendsCanView to true if owner wants to make
     * the post visible to all friends on Facebook, sets boolean to
     * false if the post will only be visible to certain groups
     */
    void updateVisibleToAll(Boolean visibleToAll) {
        this.visibleToAll = visibleToAll;
    }

    /*
     * Returns true if post is on homemade food, returns false if post is on restaurant food
     */
    Boolean isHomemade() {
        return isHomemade;
    }

    /*
     * If post is active, set boolean to true, but if the post is
     * now inactive, set boolean to false
     */
    public void setStatus(Boolean active) {
        this.active = active;
    }

    /*
     * When editing a post, if user would like to add groups to
     * make the post visible to them, then add group ID to list
     * of IDs storing all groups who have access to the post
     */
    public void addGroup(String groupId) {
        if (groups == null) {
            groups = new HashSet<String>();
        }
        groups.add(groupId);
    }

    /*
     * When editing the post, if user would like to add more categories,
     * then add string to list of categories for post
     */
    void addCategory(String category) {
        this.category.add(category);
    }

    /*
     * When editing the post, if user would like to add more tags then
     * add string to list of tags for post
     */
    void addTag(String tag) {
        tags.add(tag);
    }

    /*
     * If user adds an image of food, add string containing link to food
     * to list containing all images
     */
    void addImage(String img) {
        image.add(img);
    }

    /*
     * When editing the post, if user would like to remove a tag, then
     * remove string from list of tags for post
     */
    void removeTag(String tag) {
        tags.remove(tag);
    }

    /*
     * When editing a post, if user would like to remove groups to make
     * the post no longer visible to them, then remove group ID from
     * list of IDs storing all groups who have access to the post
     */
    void removeGroup(Integer groupId) {
        groups.remove(groupId);
    }

    /*
     * When editing the post, if user would like to remove a category,
     * then remove string from list of categories for post
     */
    void removeCategory(String category) {
        this.category.remove(category);
    }

    /*
     * If user removes image of food, remove string containing link to
     * food from list containing all images
     */
    void removeImage(String img) {
        image.remove(img);
    }

    /*
     * Returns true if post is visible to all owner’s friends, false if post
     * is visible to only certain groups
     */
    Boolean getVisibility() {
        return visibleToAll;
    }

    /*
     * Returns maximum number of accepted requests owner sets
     */
    Integer getMaxRequesters() {
        return maxAccepted;
    }

    /*
     * Returns list of all images associated with post set
     * by owner of post
     */
    Set<String> getImages() {
        return image;
    }

    /*
     * Returns the User id of the provider.
     */
    public Integer getProviderID() {
        if(owner == null && owner_string == null)
            return -1;
        if(owner_string != null)
        {
            //UserRepo ur = new UserRepo(g)
            Log.d("DEBUG - owner_string", owner_string);
            return Integer.parseInt(owner_string);
        }
        return owner.getID();
    }

    /*
    Returns the user object associated with the owner.
     */
    public User getProvider() { return owner;}

    /*
	 * Returns list of User ids that have been accepted as receivers
	 */
    Set<String> getAcceptedRequesters() {
        return usersAccepted;
    }

    /*
	 * Returns a list of User ids that have requested on the post.
	 */
    Set<String> getAllRequesters() {
        return usersRequested;
    }

    /*
	 * Returns list of Group ids that this post is visible to.
	 */
    Set<String> getGroupID() {
        return groups;
    }

    /*
	 * Returns list of strings that represent the various categories
	 * that the post belongs to
	 */
    public Set<String> getCategory() {
        if (category == null && categories != null) {
            String[] split = categories.split(", ");
            Set<String> setToReturn = new HashSet<String>();
            for (String s : split) {
                setToReturn.add(s);
            }
            return setToReturn;
        }
        return category;
    }

    /*
	 * Returns list of strings that represent the various tags
	 * that the post belongs to.
	 */
    public Set<String> getTags() {
        if (tags == null && tag != null) {
            String[] split = tag.split(", ");
            Set<String> setToReturn = new HashSet<String>();
            for (String s : split) {
                setToReturn.add(s);
            }
            return setToReturn;
        }
        return tags;
    }

    /*
	 * Returns the beginning of the time when the delivery will be available
	 */
    public String getBeginTime() {
        return beginTime;
    }

    /*
	 * Returns the end of the time when the delivery will be available
	 */
    public String getEndTime() {
        return endTime;
    }

    /*
	 * Returns the description of the post.
	 */
    public String getDescription() {
        return description;
    }

    /*
	 * Sets the time when the owner wants to make the delivery available
	 */
    public void setBeginTime(String time) {
        beginTime = time;
    }

    /*
	 * Sets the time when the owner wants to set the latest delivery
	 */
    public void setEndTime(String time) {
        endTime = time;
    }

    public String getOwner_string() {
        return owner_string;
    }

    public String getFood() {
        return food;
    }

    public String getNum_requests() {
        return num_requests;
    }

    public String getCategories() {
        return categories;
    }

    public String getTag() {
        return tag;
    }

    public String getLocation() {
        return location;
    }

    public String getActive_status() {
        return active_status;
    }

    public Boolean getActive() {
        return active;
    }

    public byte[] getPhoto_image() {
        Log.d("here", "getting");
        return photo_image;
    }

    public String getTitle(){
        return title;
    }

    public Integer getId() {
        return id;
    }

    public String getUserRequested() {
        return userRequested;
    }

    public String getUserAccepted() {
        return userAccepted;
    }

    public String getHomemade() {
        return homemade;
    }

    public String getUserRating() {
        return user_rating;
    }

    public Integer getInt(){return id;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner_string(String owner_string) {
        this.owner_string = owner_string;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setNum_requests(String num_requests) {
        this.num_requests = num_requests;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }

    public void setUserRequested(String userRequested) {
        this.userRequested = userRequested;
    }

    public void setUserAccepted(String userAccepted) {
        this.userAccepted = userAccepted;
    }

    public void setHomemade(String homemade) {
        this.homemade = homemade;
    }

    public void setUser_rating(String rating) {
        this.user_rating = rating;
    }

    public void setPhoto_image(byte[] photo_image) {
        this.photo_image = photo_image;
    }

    public void setGroups(Set<String> groups){
        this.groups = groups;
        if (groups.size() > 0) {
            for (String group : groups) {
                groupString += group + ", ";
            }
            groupString.substring(0, groupString.length()-2);
        }
    }

    public void setGroupString(String grpString) {
        groupString = grpString;
    }

    public Set<String> getGroups(){
        if (groups == null && groupString != null) {
            String[] split = groupString.split(", ");
            Set<String> setToReturn = new HashSet<String>();
            for (String s : split) {
                setToReturn.add(s);
            }
            return setToReturn;
        }
        return groups;
    }

    public void setTitle(String t){
        title = t;
    }

    public void setId(Integer newId){id = newId;}

    public String getGroupString() {
        return groupString;
    }

    /*
     * Checks to see if this post matches the tags & categories on the given Notifications object.
     */
    public boolean matches(Notifications notif) {
        String notifStart = notif.getBeginTime();
        String notifEnd = notif.getEndTime();

        Log.d("weird", beginTime);
        Log.d("weird", endTime);
        Log.d("weird", "nada" + notifStart);
        Log.d("weird", "nada" + notifEnd);

        Time t = new Time();
        if (t.isInRange(beginTime, endTime, notifStart)) {
            return true;
        }

        if (t.isInRange(beginTime, endTime, notifEnd)) {
            return true;
        }

//        if (beginTime.charAt(0) != '0' && beginTime.charAt(1) == ':') {
//            beginTime = "0" + beginTime;
//        }
//        if (endTime.charAt(0) != '0' && endTime.charAt(1) == ':') {
//            endTime = "0" + endTime;
//        }

        /*
         logic for timing -- if the notification starts after the post ends, that's a no
         if the post starts after the notification ends, that's a no
         */

//        if (endTime != null || notifStart.compareTo(endTime) >= 0) {
//            return false;
//        } else if (beginTime.compareTo(notifEnd) >= 0) {
//            return false;
//        }

        tags = getTags();
        category = getCategory();

        Set<String> notifTags = notif.getTags();
        Set<String> notifCategories = notif.getCategory();


        // check tags
        if (notifTags != null && tags != null) {
            for (String nTag : notifTags) {
                if (tags.contains(nTag)) {
                    return true;
                }
            }
        }

        // check categories
        if (notifCategories != null && category != null) {
            for (String nCat : notifCategories) {
                if (category.contains(nCat)) {
                    return true;
                }
            }
        }

        return false;
    }

}