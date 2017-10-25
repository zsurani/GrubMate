package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

public class ViewPostActivity extends AppCompatActivity {

    private Button buttonRequestOnPost;
    private Button buttonEdit;
    private TextView postName;
    private TextView postUser;
    private TextView userRating;
    private TextView description;
    private TextView homemade;
    private TextView servings;
    private TextView begin;
    private TextView end;
    private TextView location;
    private TextView categories;
    private TextView tags;
    private ImageView image;
    private int postID;
    private Post post;
    private PostRepo postRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        //final int postID;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            postID = 0;
        } else {
            postID = extras.getInt("postID");
        }

        Log.d("hello", Integer.toString(postID));

        buttonRequestOnPost = (Button) findViewById(R.id.button_request_on_post_view);
        postName = (TextView) findViewById(R.id.view_post_name);
        postUser = (TextView) findViewById(R.id.view_post_user);
        userRating = (TextView) findViewById(R.id.view_post_rating);
        description = (TextView) findViewById(R.id.view_post_desc);
        homemade = (TextView) findViewById(R.id.view_post_homemade);
        servings = (TextView) findViewById(R.id.view_post_servings);
        begin = (TextView) findViewById(R.id.view_post_begin);
        end = (TextView) findViewById(R.id.view_post_end);
        location = (TextView) findViewById(R.id.view_post_location);
        categories = (TextView) findViewById(R.id.view_post_categories);
        tags = (TextView) findViewById(R.id.view_post_tags);
        image = (ImageView) findViewById(R.id.view_post_picture);
        buttonEdit = (Button) findViewById(R.id.edit_button);

         postRepo = new PostRepo(getApplicationContext());
        Log.d("DEBUG - postID", Integer.toString(postID));
        post = postRepo.getPost(postID);
        postName.setText("Name: " + post.getFood());
        postUser.setText("User: " + post.getOwner_string());
        userRating.setText("Rating: " + post.getUserRating());
        description.setText("Description: " + post.getDescription());
        homemade.setText("Homemade: " + post.getHomemade());
        servings.setText("Servings: " + post.getNum_requests());
        begin.setText("Start Time: " + post.getBeginTime());
        end.setText("End Time: " + post.getEndTime());
        location.setText("Location: " + post.getLocation());
        categories.setText("Categories: " + post.getCategories());
        tags.setText("Tags: " + post.getTag());
        byte[] images = post.getPhoto_image();
        Bitmap images2 = BitmapFactory.decodeByteArray(images, 0, images.length);
        image.setImageBitmap(images2);

        if (post.getOwner_string().equals(Profile.getCurrentProfile().getName())) {
            buttonEdit.setVisibility(View.VISIBLE);
        } else {
            buttonEdit.setVisibility(View.INVISIBLE);
        }

        //Log.d("DEBUG userreq", "post = " + postRepo.getRequestors(postID).length);

        if(Integer.parseInt(post.getNum_requests()) <= (postRepo.getRequestors(postID).length - 1)) //because it starts wiht ","
        {
            buttonRequestOnPost.setEnabled(false);
        } else {
            buttonRequestOnPost.setEnabled(true);
        }

        if(postRepo.getAccepted(postID).length -1 > 0)
        {
            buttonEdit.setEnabled(false);
        } else {
            buttonEdit.setEnabled(true);
        }
        
        buttonRequestOnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //need to update the post with the user id of who is requesting
                UserRepo ur = new UserRepo(getApplicationContext());
                Integer userId = ur.getId(Profile.getCurrentProfile().getId());
                postRepo.addNewRequestor(Integer.toString(postID), Integer.toString(userId));

                if(Integer.parseInt(post.getNum_requests()) <= (postRepo.getAccepted(Integer.toString(postID))).length() - 1) //because it starts wiht ","
                {
                    buttonRequestOnPost.setEnabled(false);
                } else {
                    buttonRequestOnPost.setEnabled(true);
                }

                Intent i = new Intent(getApplicationContext(), EnterLocationActivity.class);
                i.putExtra("postID", postID);
                i.putExtra("userID", userId);
                startActivity(i);
            }
        });

        //goes to the user profile from the User label
        postUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Post p = new Post(postID);
                //ProfileActivity pa = new ProfileActivity(p.getProvider());
                // Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                //i.putExtra("frgToLoad", 0); //0 = profile
                // startActivity(i);
                //MAKE REQUEST
            }
        });

        // edit post button leads to create post page
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreatePostActivity.class);
                i.putExtra("postID", postID);
                startActivity(i);
            }
        });


    }
}
