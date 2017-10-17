package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

/**
 * Created by Madison on 10/16/17.
 */

public class RatingReviewActivity extends AppCompatActivity {

    private RatingBar rating;
    private EditText review;
    private Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_review);
        rating = (RatingBar) findViewById(R.id.user_rating);
        review = (EditText) findViewById(R.id.edit_review);
        saveChanges = (Button) findViewById(R.id.button_save_rating);



        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DUMMY VALUE
                String DUMMY_userId = "1";
                UserRepo ur = new UserRepo(getApplicationContext());
                ur.addRating(DUMMY_userId, rating.getRating());
                ur.addReview(DUMMY_userId, review.getText().toString());
            }
        });
    }

}
