package com.usc.zsurani.grubmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zsurani on 10/13/17.
 */

public class PostRepo {
    private DatabaseHandler dbHelper;

    public PostRepo(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public int insert(Post post) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + User.KEY_ID + " FROM " + User.TABLE
                + " WHERE " + User.KEY_fbUniqueIdentifier + " = " + post.getOwner_string(), null);

        String userId = "";
        if (c.moveToFirst()) {
            userId = c.getString(c.getColumnIndex(User.KEY_ID));
        }

        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_description, post.getDescription());
        values.put(Post.KEY_owner, userId);
        values.put(Post.KEY_food, post.getFood());
        values.put(Post.KEY_num_requests, post.getNum_requests());
        values.put(Post.KEY_categories, post.getCategories());
        values.put(Post.KEY_tags, post.getTag());
        values.put(Post.KEY_beginTime, post.getBeginTime());
        values.put(Post.KEY_endTime, post.getEndTime());
        values.put(Post.KEY_location, post.getLocation());
        values.put(Post.KEY_active, post.getActive_status());
        values.put(Post.KEY_usersAccepted, post.getUserAccepted());
        values.put(Post.KEY_usersRequested, post.getUserRequested());
        values.put(Post.KEY_homemadeNotRestaurant, post.getHomemade());
        values.put(Post.KEY_images, post.getPhoto_image());


        // Inserting Row
        long post_id = db.insert(Post.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) post_id;
    }


//    public void delete(int student_Id) {
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        // It's a good practice to use parameter ?, instead of concatenate string
//        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
//        db.close(); // Closing database connection
//    }

//    public void update(Student student) {
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(Student.KEY_age, student.age);
//        values.put(Student.KEY_email,student.email);
//        values.put(Student.KEY_name, student.name);
//
//        // It's a good practice to use parameter ?, instead of concatenate string
//        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
//        db.close(); // Closing database connection
//    }

    public Boolean newUser(String Id) {
        //Open connection to read only

        int count = 0;

        Log.d("SQL", "SELECT " + User.KEY_ID + " FROM " + User.TABLE
                + " WHERE " + User.KEY_fbUniqueIdentifier + " = " + Id);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + User.KEY_ID + " FROM " + User.TABLE
                + " WHERE " + User.KEY_fbUniqueIdentifier + " = " + Id, null);
        if(c.moveToFirst()){
            do{
                count++;

            }while(c.moveToNext());
        }
        c.close();
        db.close();

        if (count > 0) {
            return false;
        }
        return true;
    }

    public Post getPost(int postid) {
        String postId = Integer.toString(postid);
        Post post = new Post();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Post.TABLE
                + " WHERE " + Post.KEY_id + " = " + postId, null);

        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                post.setFood(c.getString(c.getColumnIndex(Post.KEY_food)));
                post.setOwner_string(c.getString(c.getColumnIndex(Post.KEY_owner)));
                post.setDescription(c.getString(c.getColumnIndex(Post.KEY_description)));
                post.setHomemade(c.getString(c.getColumnIndex(Post.KEY_homemadeNotRestaurant)));
                post.setNum_requests(c.getString(c.getColumnIndex(Post.KEY_num_requests)));
                post.setBeginTime(c.getString(c.getColumnIndex(Post.KEY_beginTime)));
                post.setEndTime(c.getString(c.getColumnIndex(Post.KEY_endTime)));
                post.setLocation(c.getString(c.getColumnIndex(Post.KEY_location)));
                post.setCategories(c.getString(c.getColumnIndex(Post.KEY_categories)));
                post.setTag(c.getString(c.getColumnIndex(Post.KEY_tags)));
                post.setPhoto_image(c.getBlob(c.getColumnIndex(Post.KEY_images)));
                post.setId(postid);
            } while (c.moveToNext());
        }

        c = db.rawQuery("SELECT * FROM " + User.TABLE
                + " WHERE " + User.KEY_ID + " = " + post.getOwner_string(), null);


        if (c.moveToFirst()) {
            post.setOwner_string(c.getString(c.getColumnIndex(User.KEY_name)));
            post.setUser_rating(c.getString(c.getColumnIndex(User.KEY_rating)));
        }
        c.close();
        db.close();

        return post;
    }

//    public int insert(Post post) {
//        Set<String> imageSet = new HashSet<String>(post.getImages());
//        List<String> listImage = new ArrayList<String>(imageSet);
//        String images = TextUtils.join(", ", listImage);
//
//        Set<String> categorySet = new HashSet<String>(post.getCategory());
//        List<String> listCategory = new ArrayList<String>(categorySet);
//        String category = TextUtils.join(", ", listCategory);
//
//        Set<String> tagSet = new HashSet<String>(post.getTags());
//        List<String> listTags = new ArrayList<String>(tagSet);
//        String tag = TextUtils.join(", ", listTags);
//
//        Set<String> groupSet = new HashSet<String>(post.getGroupID());
//        List<String> listGroup = new ArrayList<String>(groupSet);
//        String group = TextUtils.join(", ", listGroup);
//
//        Set<String> requestSet = new HashSet<String>(post.getAllRequesters());
//        List<String> listRequest = new ArrayList<String>(requestSet);
//        String allrequests = TextUtils.join(", ", listRequest);
//
//        Set<String> acceptSet = new HashSet<String>(post.getAcceptedRequesters());
//        List<String> listAccept = new ArrayList<String>(acceptSet);
//        String acceptedRequests = TextUtils.join(", ", listAccept);
//
//        //Open connection to write data
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(Post.KEY_description, post.getDescription());
//        values.put(Post.KEY_owner, post.getOwner_string());
//        values.put(Post.KEY_food, post.getFood());
//        values.put(Post.KEY_images, images);
//        values.put(Post.KEY_num_requests, post.getNum_requests());
//        values.put(Post.KEY_categories, category);
//        values.put(Post.KEY_tags, tag);
//        values.put(Post.KEY_beginTime, post.getBeginTime());
//        values.put(Post.KEY_endTime, post.getEndTime());
//        values.put(Post.KEY_location, post.getLocation());
//        values.put(Post.KEY_active, post.getActive());
//        values.put(Post.KEY_groups, group);
//        values.put(Post.KEY_usersRequested, allrequests);
//        values.put(Post.KEY_usersAccepted, acceptedRequests);
//        values.put(Post.KEY_homemadeNotRestaurant, post.getHomemade());
//        values.put(Post.KEY_allFriendsCanView, post.getVisibility());
//        values.put(Post.KEY_maxRequesters, post.getMaxRequesters());
//
//        // Inserting Row
//        long postID = db.insert(Post.TABLE, null, values);
//        db.close(); // Closing database connection
//        return (int) postID;
//    }

    public void updateDescription(String postId, String newDescription) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_description, newDescription);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "= " + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void updateFood(String postId, String newFood) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_food, newFood);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "= " + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void updateBeginTime(String postId, String newBeginTime) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_beginTime, newBeginTime);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "= " + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void updateEndTime(String postId, String newEndTime) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_endTime, newEndTime);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "= " + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void updateLocation(String postId, String newLocation ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_location, newLocation);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "= " + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void updateActiveStatus(String postId, String active) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_active, active);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "= " + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void updateHomemade(String postId, String homemade ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_homemadeNotRestaurant,homemade );

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "=" + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void updateAllFriendsCanView(String postId, String allView) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_allFriendsCanView, allView);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "= " + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void updateMaxRequestors(String postId, String maxNum) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_maxRequesters, maxNum);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Post.TABLE, values, Post.KEY_id + "= " + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public void addNewImage(String postId, String image){
        String oldImageList = getImages(postId);
        String newImageList = oldImageList + "," + image;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_images, newImageList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public void deleteImage(String postId, String image){
        String oldImageList = getImages(postId);
        List<String> imagesList = Arrays.asList(oldImageList.split(","));
        Set<String> imagesSet = new HashSet<String>(imagesList);
        imagesSet.remove(image);
        List<String> list = new ArrayList<String>(imagesSet);
        String newImageList = TextUtils.join(", ", list);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_images, newImageList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public String getImages(String postId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_images +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Integer.parseInt(postId);// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_images));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public void addNewCategory(String postId, String category){
        String oldCategoryList = getCategories(postId);
        String newCategoryList = oldCategoryList + "," + category;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_categories, newCategoryList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public void deleteCategory(String postId, String category){
        String oldCategoryList = getCategories(postId);
        List<String> categoryList = Arrays.asList(oldCategoryList.split(","));
        Set<String> categorySet = new HashSet<String>(categoryList);
        categorySet.remove(category);
        List<String> list = new ArrayList<String>(categorySet);
        String newCategoryList = TextUtils.join(", ", list);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_categories, newCategoryList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public String getCategories(String postId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_categories +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Integer.parseInt(postId);// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, null);
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_categories));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public void addNewTag(String postId, String tag){
        String oldTagList = getTags(postId);
        String newTagList = oldTagList + "," + tag;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_tags, newTagList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public void deleteTag(String postId, String tag){
        String oldTagList = getTags(postId);
        List<String> tagList = Arrays.asList(oldTagList.split(","));
        Set<String> tagSet = new HashSet<String>(tagList);
        tagSet.remove(tag);
        List<String> list = new ArrayList<String>(tagSet);
        String newTagList = TextUtils.join(", ", list);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_tags, newTagList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public String getTags(String postId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_tags +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Integer.parseInt(postId);// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_tags));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public void addNewRequestor(String postId, String userId){
        String oldReqList = getRequestors(postId);
        String newReqList = oldReqList + "," + userId;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_usersRequested, newReqList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public void deleteRequestor(String postId, String userId){
        String oldReqList = getRequestors(postId);
        List<String> reqList = Arrays.asList(oldReqList.split(","));
        Set<String> reqSet = new HashSet<String>(reqList);
        reqSet.remove(userId);
        List<String> list = new ArrayList<String>(reqSet);
        String newReqList = TextUtils.join(", ", list);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_usersRequested, newReqList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public String getRequestors(String postId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_usersRequested +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Integer.parseInt(postId);// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_usersRequested));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public void addNewAccepted(String postId, String userId){
        String oldAcceptedList = getAccepted(postId);
        String newAcceptedList = oldAcceptedList + "," + postId;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_usersAccepted, newAcceptedList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public void deleteAccepted(String postId, String userId){
        String oldAcceptedList = getAccepted(postId);
        List<String> acceptedList = Arrays.asList(oldAcceptedList.split(","));
        Set<String> acceptedSet = new HashSet<String>(acceptedList);
        acceptedSet.remove(userId);
        List<String> list = new ArrayList<String>(acceptedSet);
        String newAcceptedList = TextUtils.join(", ", list);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Post.KEY_usersAccepted, newAcceptedList);

        db.update(Post.TABLE, values, Post.KEY_id + "= " + postId, null);
        db.close(); // Closing database connection
    }

    public String getAccepted(String postId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_usersAccepted +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Integer.parseInt(postId);// It's a good practice to use parameter ?, instead of concatenate string

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_usersAccepted));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public void deletePost(String postId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Post.TABLE, Post.KEY_id + "=" + Integer.parseInt(postId), null);
        db.close(); // Closing database connection
    }

    public ArrayList<Post> returnAllPosts(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor  c = db.rawQuery("SELECT * FROM " + Post.TABLE,null);
        ArrayList<Post> toReturn = new ArrayList<Post>();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                Post post = new Post();
                post.setFood(c.getString(c.getColumnIndex(Post.KEY_food)));
                post.setOwner_string(c.getString(c.getColumnIndex(Post.KEY_owner)));
                post.setDescription(c.getString(c.getColumnIndex(Post.KEY_description)));
                post.setHomemade(c.getString(c.getColumnIndex(Post.KEY_homemadeNotRestaurant)));
                post.setNum_requests(c.getString(c.getColumnIndex(Post.KEY_num_requests)));
                post.setBeginTime(c.getString(c.getColumnIndex(Post.KEY_beginTime)));
                post.setEndTime(c.getString(c.getColumnIndex(Post.KEY_endTime)));
                String groupString = c.getString(c.getColumnIndex(Post.KEY_groups));
                List<String> groupList = Arrays.asList(groupString.split(","));
                Set<String> groupSet = new HashSet<String>(groupList);
                post.setGroups(groupSet);
                post.setLocation(c.getString(c.getColumnIndex(Post.KEY_location)));
                post.setCategories(c.getString(c.getColumnIndex(Post.KEY_categories)));
                post.setTag(c.getString(c.getColumnIndex(Post.KEY_tags)));
                post.setPhoto_image(c.getBlob(c.getColumnIndex(Post.KEY_images)));
                toReturn.add(post);
                c.moveToNext();
            }
        }
        return toReturn;
    }

    public List<Integer> getPosts(int userId) {
        List<Integer> posts = new ArrayList<Integer>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " + Post.KEY_id + " FROM " + Post.TABLE + " WHERE " +
                Post.KEY_owner + "= " + Integer.toString(userId);
        Cursor c = db.rawQuery(selectQuery, null);
        Log.d("DEBUG", "preformed the query in getPosts");
        if(c.moveToFirst()) {
            do {
                posts.add(Integer.parseInt(c.getString(c.getColumnIndex(Post.KEY_id))));
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return posts;
    }

    public String getFood(String postId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_food +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Post.KEY_id;

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_food));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }
    public String getDescription(String postId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_description +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Post.KEY_id;

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_description));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public String getBeginTime(String postId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_beginTime +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Post.KEY_id;

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_beginTime));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public String getEndTime(String postId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_endTime +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Post.KEY_id;

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_endTime));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public String getNumReq(String postId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Post.KEY_num_requests +
                " FROM " + Post.TABLE
                + " WHERE " +
                Post.KEY_id + "=" + Post.KEY_id;

        Cursor cursor = db.rawQuery(selectQuery, null );
        String toReturn = "";
        if (cursor.moveToFirst()) {
            do {
                toReturn = cursor.getString(cursor.getColumnIndex(Post.KEY_num_requests));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toReturn;
    }

    public List<Post> getPosts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * FROM " + Post.TABLE;

        Cursor cursor = db.rawQuery(selectQuery, null );
        List<Post> postList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Post post = new Post();
                post.setFood(cursor.getString(cursor.getColumnIndex(Post.KEY_food)));
                post.setDescription(cursor.getString(cursor.getColumnIndex(Post.KEY_description)));
                post.setId(cursor.getInt(cursor.getColumnIndex(Post.KEY_id)));
                postList.add(post);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return postList;
    }

}