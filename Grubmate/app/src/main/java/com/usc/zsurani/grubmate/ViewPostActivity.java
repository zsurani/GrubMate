package com.usc.zsurani.grubmate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPostActivity extends AppCompatActivity {

    private Button buttonRequestOnPost;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        int postID;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            postID= 0;
        } else {
            postID= extras.getInt("postID");
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

        PostRepo postRepo = new PostRepo(getApplicationContext());
        final Post post = postRepo.getPost(postID);
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

        buttonRequestOnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
