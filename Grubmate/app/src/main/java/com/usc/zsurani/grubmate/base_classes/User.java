package com.usc.zsurani.grubmate.base_classes;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import com.facebook.Profile;

import java.util.List;
import java.util.Set;
import com.facebook.Profile;

public class User {
    public static final String TABLE = "User";
    public static final String P_TABLE = "Profiles";
    public static final String F_TABLE = "Friends";

    public static final String KEY_ID = "id";
    public static final String KEY_ID3 = "id2";
    public static final String KEY_name = "name";
    public static final String KEY_image = "image";
    public static final String KEY_fbUniqueIdentifier = "facebookUniqueIdentifier";
    public static final String KEY_FRIENDS = "friends";
    public static final String KEY_rating = "rating";
    public static final String KEY_numRatings = "numRatings";

    public static final String TABLE2 = "UserToReview";
    public static final String KEY_ID2 = "id";
    public static final String KEY_userId = "userId";
    public static final String KEY_review = "review";


    public static int id;
    private Context context;


    public String name;
    public String facebookUniqueIdentifier;
    public Integer rating;
    public Integer numRatings;
    public List<Integer> transactions;
    public Set<Integer> posts;
    public List<Integer> groups;
    public Set<Integer> notifications;
    public List<String> reviews;
    public Uri image;
    public Profile current_user;

	/*
	 * Constructor which takes in user’s unique Facebook
	 * identifier, gets user information from Facebook Profile,
	 * and inserts the user into the database
	 */
    public User (String name, String fbID){
        this.name = name;
        this.facebookUniqueIdentifier = fbID;
    }

    public User() {} // for when methods just need to be used

    public User (String fbLink){
        current_user = Profile.getCurrentProfile();
        name = current_user.getName();
        facebookUniqueIdentifier = current_user.getId();
        image = current_user.getProfilePictureUri(10, 10); //would need to add the dimensions that we want the picture
        //get the rating from the database
        //get the numRating from the database
    }

    public String getFBid() {
        return facebookUniqueIdentifier;
    }


    /*
     * Add a post to the list storing ids of posts visible to the user
     */
    void addPostToFeed(Integer id){
        posts.add(id);
    }

    /*
     * When user creates a group, add new group id to the list
     * storing all ids for groups created by user
     */
    void addToCreatedGroups(Integer groupId){
        groups.add(groupId);
    }

    /*
     * When user creates a post, add new post id to the list
     * storing all ids for posts created by user
     */
    void addPost(Integer postId){
        posts.add(postId);
    }

    /*
     * When user either accepts a request or sends a request on a
     * post, add new transaction id to the list storing all of
     * that user’s transactions
     */
    void addTransaction(Integer transId){
        transactions.add(transId);
    }

    /*
     * When user creates a subscription notification or receives
     * a notification, add new notification id to the list storing
     * all ids for notifications the user creates/receives
     */
    void addNotification(Integer notifId){
        notifications.add(notifId);
    }

    /*
     * After transaction is complete and user rates the other
     * participant in the transaction, add rating score to
     * rated user to update rating
     * Updates number of ratings for user by one
     */
    void addRating(Integer rating){
        /*

            Would we need to multiple the current rating by the number of ratings,
            add the input, and then divide by the number of ratings + 1 to get the new rating?

            Integer oldRating = this.rating*numRatings;
            numRatings++;
            this.rating = (oldRating+rating)/numRatings;

         */
        /*
            The reason I added num ratings is so we can get the average to display on the UI
         */
        this.rating +=rating;
        numRatings++;
    }

    /*
     * After transaction is complete and user writes review
     * of the other participant in the transaction, add review
     * to list storing all reviews
     */
    void addReview(String review){
        reviews.add(review);
    }



    /*
     * Remove a post from the list storing ids of
     *  posts visible to the user
     */
    void removePostFromFeed(Integer postId){
    }

    /*
     * When user deletes a post, remove post id from the list
     * storing all ids for posts created by user
     */
    void removePost(Integer postId){
        posts.remove(postId);
    }

    /*
     * If a user unsubscribes from a notification they created,
     * remove notification id from the list storing all ids for
     * notifications the user creates/receives
     */
    void removeNotification(Integer notifId){
        notifications.remove(notifId);
    }

    /*
     * If a user wants to remove their request from a post,
     * remove the notification from the provider’s notifications
     *  and update the post
     */
    void removeRequest(Integer postId){

    }

    /*
     * Returns the id of the User
     */
    public Integer getID(){ return id;}

    /*
     * Returns the calculated rating of the user
     */
    public Integer getRating(){return rating;}

    /*
     * Returns a list of all reviews written for user
     */
    List<String> getReviews(){return reviews;}

    /*
     * Returns the Facebook ID of the user
     */
    public String getFacebookID(){return facebookUniqueIdentifier;}

    /*
     * Returns the name of the user
     */
    public String getName(){return name;}

    //from facebook api
	/*
	 * Returns the image of the user
	 */
    public Uri getImg(){return image;}

    //from db
	/*
	 * Returns list of the transactions that the
	 * user is involved in
	 */
    List<Integer> getTransactions(){return transactions;}

    //from db
	/*
	 * Returns list of post ids that have been posted
	 * to a group the user is apart of
	 */
   // List<Integer> getVisablePosts(){}

    //from db
	/*
	 * Returns list of ids of the notifications that
	 * the user has received
	 */
    public Set<Integer> getNotification(){return notifications;}

    //from db
	/*
	 * Returns list of the ids of the groups that the user has created
	 */
    List<Integer> getGroupList(){return groups;}

}
