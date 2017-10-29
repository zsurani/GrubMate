package com.usc.zsurani.grubmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Madison on 10/26/17.
 */
@RunWith(AndroidJUnit4.class)
public class GroupRepoTest {
    //local global variables
    Context appContext;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    Group g;
    GroupRepo gr;
    String users = "";
    private ImageView profilePic;
    private Bundle args;
    private Integer userId;
    private TextView textRating;
    private TextView textNumRatings;
    private TextView textName;
    public static final String EXTRA_USER_ID = "grubmate.profile_fragment.user_id";

    @Before
    public void setup() {
        appContext = InstrumentationRegistry.getTargetContext();
        dbHandler = new DatabaseHandler(appContext);
        db = dbHandler.getReadableDatabase();

        //initalizing the NotificationsRepo
        gr = new GroupRepo(appContext);

        //initalizing the Noticition we will use to test
        String fbId = "";
        String stringName = "";
        Uri uri = null;
        UserRepo userRepo = new UserRepo(appContext);
        if (Profile.getCurrentProfile() == null) {
            fbId = userRepo.getProfile().getId();
            stringName = userRepo.getProfile().getName();
            uri = userRepo.getProfile().getUri();
        } else {
            fbId = Profile.getCurrentProfile().getId();
            stringName = Profile.getCurrentProfile().getName();
            uri = Profile.getCurrentProfile().getProfilePictureUri(profilePic.getMaxWidth(), profilePic.getMaxHeight());
        }
        UserRepo up = new UserRepo(appContext);
        //final int userId;
        if (args == null) {
            userId = up.getId(fbId);
            Log.d("TEST", "userId = " + userId);
        } else {
            userId = args.getInt(EXTRA_USER_ID);
        }

        String stringNumRatings = up.getNumRatings(String.valueOf(userId));
        String stringRating = up.getRating(String.valueOf(userId));
        Log.d("DEBUG - stringRating", stringNumRatings);

        Log.d("uri", uri.toString());

        if (stringNumRatings.isEmpty()) stringNumRatings = "0";
        if (stringRating.isEmpty()) stringRating = "N/A";

//        textNumRatings.setText(String.format(getResources().getString(R.string.text_profile_num_ratings), stringNumRatings));
//        textRating.setText(String.format(getResources().getString(R.string.text_profile_rating),stringRating));
        textName.setText(stringName);
        Picasso.with(appContext).load(uri).into(profilePic);
    }

    @Test
    public void testInsertNotification() throws Exception {
        List<String> userSet = new ArrayList<String>();
        userSet.add("Madison");
        gr.insert("310Squad", userSet);
        Cursor c = db.rawQuery("SELECT * FROM " + Group.TABLE
                + " WHERE " + Group.KEY_name + " like '%310Squad%'", null);

        if (c.moveToFirst()) {
            do {
                users = c.getString(c.getColumnIndex(Group.KEY_user));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        System.out.println("USERS" + users);
        assertEquals(users, "Madison");
    }
}
