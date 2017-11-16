package com.usc.zsurani.grubmate.activity_and_fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

/**
 * Created by Madison on 10/16/17.
 */

public class RatingReviewActivity extends AppCompatActivity {

    private RatingBar rating;
    private EditText review;
    private Button saveChanges;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_review);
        rating = (RatingBar) findViewById(R.id.user_rating);
        review = (EditText) findViewById(R.id.edit_review);
        saveChanges = (Button) findViewById(R.id.button_save_rating);

        Bundle extra = getIntent().getExtras();

        id = extra.getInt("UserRatingId");



        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepo ur = new UserRepo(getApplicationContext());
                ur.addRating(Integer.toString(id), rating.getRating());
                ur.addReview(Integer.toString(id), review.getText().toString());
            }
        });
    }

}
