package com.usc.zsurani.grubmate.base_classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.facebook.Profile;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.GroupRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.Time;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;
import com.usc.zsurani.grubmate.databases.DatabaseHandler;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Shivangi on 10/16/17.
 */

public class Search {
    private Context context;
    private DatabaseHandler dbHelper;

    public Search(Context context) {
        dbHelper = new DatabaseHandler(context);
        this.context = context;
    }

    public List<Post> searchForPost(String searchParameter){
        if (!searchParameter.contains(":")) {
            Log.d("DEBUG", "searchParam = " + searchParameter);
            List<Post> toReturn = new ArrayList<Post>();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + Post.TABLE
                    + " WHERE " + Post.KEY_food + " like '%" + searchParameter + "%'" +
                    "OR " + Post.KEY_tags + " like '%" + searchParameter + "%' " +
                    "OR " + Post.KEY_categories + " like '%" + searchParameter + "%' " +
                    "OR " + Post.KEY_groups + " like '%" + searchParameter + "%' " +
                    "OR " + Post.KEY_description + " like '%" + searchParameter + "%' " +
                    "OR " + Post.KEY_beginTime + " like '%" + searchParameter + "%' " +
                    "OR " + Post.KEY_endTime + " like '%" + searchParameter + "%' ", null);


            // looping through all rows and adding to list

            if (c.moveToFirst()) {
                do {
                    Post post = new Post();
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
                    post.setId(c.getInt(c.getColumnIndex(Post.KEY_id)));
                    toReturn.add(post);
                } while (c.moveToNext());
            }
            c.close();
            db.close();

            return toReturn;
        }
        else{
            List<Post> allPosts = getPosts();
            List<Post> toReturn = new ArrayList<Post>();

            for (int i = 0; i < allPosts.size(); i++){
                String beginTime = allPosts.get(i).getBeginTime();
                String endTime = allPosts.get(i).getEndTime();

                Time t = new Time();
                boolean result = t.isInRange(beginTime, endTime, searchParameter);
                if (result == true){
                    toReturn.add(allPosts.get(i));
                }
            }
            return toReturn;
        }
    }
    public List<Post> getPosts() {

        List<Post> postList = new ArrayList<>();
        List<Post> modified_posts = new ArrayList<>();

        UserRepo u = new UserRepo(context);
        String personalId = Integer.toString(u.getId(Profile.getCurrentProfile().getId()));

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * FROM " + Post.TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Post post = new Post();
                post.setFood(cursor.getString(cursor.getColumnIndex(Post.KEY_food)));
                post.setDescription(cursor.getString(cursor.getColumnIndex(Post.KEY_description)));
                post.setId(cursor.getInt(cursor.getColumnIndex(Post.KEY_id)));
                post.setBeginTime(cursor.getString(cursor.getColumnIndex(Post.KEY_beginTime)));
                post.setEndTime(cursor.getString(cursor.getColumnIndex(Post.KEY_endTime)));
                postList.add(post);
            } while(cursor.moveToNext());
        }

        for (int i=0; i<postList.size(); i++) {
            selectQuery =  "SELECT  * FROM " + Post.TABLE + " WHERE " +
                    Post.KEY_id + "= " + postList.get(i).getId();
            cursor = db.rawQuery(selectQuery, null);
            String group = "";
            int owner = 0;
            if(cursor.moveToFirst()) {
                group = cursor.getString(cursor.getColumnIndex(Post.KEY_groups));
                owner = cursor.getInt(cursor.getColumnIndex(Post.KEY_owner));
            }

            if (group.equals("")) {
                GroupRepo groupRepo = new GroupRepo(context);
                if (groupRepo.checkIfFriend(owner, Profile.getCurrentProfile().getName())) {
                    modified_posts.add(postList.get(i));
                }
            } else {
                List<String> groupList = Arrays.asList(group.split(","));
                for (int j=0; j<groupList.size(); j++) {
                    selectQuery = "SELECT  " + Group.KEY_user + " FROM " + Group.TABLE + " WHERE " +
                            Group.KEY_id + "= " + groupList.get(j);
                    cursor = db.rawQuery(selectQuery, null);

                    if (cursor.moveToFirst()) {
                        String users = cursor.getString(cursor.getColumnIndex(Group.KEY_user));
                        List<String> usersInGroup = Arrays.asList(users.split(", "));
                        if (usersInGroup.contains(personalId)) {
                            modified_posts.add(postList.get(i));
                        }
                    }
                }
            }
        }

        cursor.close();
        db.close();
        return modified_posts;
    }
}
