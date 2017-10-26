package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Shivangi on 10/16/17.
 */

public class Search {
    private DatabaseHandler dbHelper;

    public Search(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public List<Post> searchForPost(String searchParameter){
        System.out.println(searchParameter);
        List<Post> toReturn = new ArrayList<Post>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Post.TABLE
                + " WHERE " + Post.KEY_tags + " like '%" + searchParameter + "%' ", null);

        /*
                Cursor c = db.rawQuery("SELECT * FROM " + Post.TABLE
                + " WHERE " + Post.KEY_food + " like '%" + searchParameter + "%' " +
<<<<<<< HEAD
                "AND " + Post.KEY_tags + " like '%" + searchParameter + "%' " +
                "AND " + Post.KEY_categories + " like '%" + searchParameter + "%' " +
                "AND " + Post.KEY_description + " like '%" + searchParameter + "%' " +
                        "AND " + Post.KEY_beginTime + " like '%" + searchParameter + "%' " +
                        "AND " + Post.KEY_endTime + " like '%" + searchParameter + "%' ", null);
         */

        KEY_BODY + " LIKE '"+search_text+"%' OR "
                + KEY_TITLE + " LIKE '"+search_text+"%'"
=======
                "OR " + Post.KEY_tags + " like '%" + searchParameter + "%' " +
                "OR " + Post.KEY_categories + " like '%" + searchParameter + "%' " +
                "OR " + Post.KEY_description + " like '%" + searchParameter + "%' " +
                        "OR " + Post.KEY_beginTime + " like '%" + searchParameter + "%' " +
                        "OR " + Post.KEY_endTime + " like '%" + searchParameter + "%' ", null);
>>>>>>> 34329aa1519e5c0cb145c9803aa9354e06de206a

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
}
