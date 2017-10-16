package com.usc.zsurani.grubmate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private int userId;

    private String name;
    private float rating;
    private int numRatings;
    private String[] reviews;
    // pic
    // private Post[] posts;

    private ListView postList;
    private TextView textRating;
    private TextView textNumRatings;
    private TextView textName;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        postList = (ListView) findViewById(R.id.list_posts);
        textRating = (TextView) findViewById(R.id.label_ratings);
        textNumRatings = (TextView) findViewById(R.id.label_num_ratings);
        textName = (TextView) findViewById(R.id.label_name);

        profilePic = (ImageView) findViewById(R.id.image_profile_pic);

        //just for testing purposes
        Profile current_user = Profile.getCurrentProfile();
        textName.setText(current_user.getName());
        Uri pic = current_user.getProfilePictureUri(100, 100);
        //textRating.setText(pic.toString());
        profilePic.setImageURI(pic);
        //profilePic.setVisibility(View.VISIBLE);

        // TODO create custom adapter to put post info into rows on list view
        List<Post> posts = new ArrayList<Post>();
        postList.setAdapter(new PostAdapter(getApplicationContext(), R.layout.layout_post_row, posts));

        // getting all the info to populate the profile page TODO this assumes it's the current user
        //TO FIX: should send an extra with the user id that we want to get the profile of
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getApplicationContext());
        final int userId = up.getId(fbId); // this is what we would need to change if it we want a different user

        String stringNumRatings = up.getNumRatings(String.valueOf(userId));
        String stringRating = up.getRating(String.valueOf(userId));
        String stringName = Profile.getCurrentProfile().getName();
        Uri uri = Profile.getCurrentProfile().getProfilePictureUri(profilePic.getMaxWidth(), profilePic.getMaxHeight());

        if (stringNumRatings.isEmpty()) stringNumRatings = "0";
        if (stringRating.isEmpty()) stringRating = "N/A";

        textNumRatings.setText(String.format(getResources().getString(R.string.text_profile_num_ratings), stringNumRatings));
        textRating.setText(String.format(getResources().getString(R.string.text_profile_rating),stringRating));
        textName.setText(stringName);
        Picasso.with(this).load(uri).into(profilePic);


        //need to get all of the posts that the user owns
        PostRepo pr = new PostRepo(getApplicationContext());
        Log.d("DEBUG", "creating the postRepo");
        List<Integer> postIds = pr.getPosts(userId);
        Log.d("DEBUG", "gotten all the post Ids");
        Log.d("DEBUG", Integer.toString(postIds.size()));
        List<Post> userPosts = new ArrayList<Post>();
        for(int i = 0; i < postIds.size(); i++) {
            Log.d("I = ", Integer.toString(i));
            Log.d("DEBUG", Integer.toString(postIds.get(i)));
            Integer postID = postIds.get(i);
            Post post = pr.getPost(postID);

            userPosts.add(post);
            // Log.d("DEBUG", Integer.toString(p.getId()));
        }


        PostAdapter adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, userPosts);
        postList.setAdapter(adapter);



    }

    //somehow need to get something that gets what profile we are looking at
    void Profile(User u)
    {
        profilePic.setImageURI(u.getImg());
        textName.setText(u.getName());
        textRating.setText(u.getRating());
        //textNumRatings.setText(u.getNumRating()); //need to make the getNumRating method if we think this is needed

    }

}
