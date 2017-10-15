package com.usc.zsurani.grubmate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.Profile;

public class CreatePostActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editDesc;
    private EditText editNumAvailable;
    private EditText editBeginTime;
    private EditText editEndTime;
    private EditText editLocation;
    private EditText editTags;
    private Button buttonSave;
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private CheckBox checkbox4;
    private CheckBox checkbox5;
    private CheckBox checkbox6;
    private CheckBox checkbox7;
    private CheckBox checkbox8;
    private CheckBox checkbox9;
    private CheckBox checkbox10;
    private CheckBox checkbox11;
    private CheckBox checkbox12;
    private CheckBox checkbox13;
    private CheckBox checkbox14;
    private CheckBox checkbox15;
    private CheckBox checkbox16;
    private RadioButton homemade;
    private String num_requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        editName = (EditText) findViewById(R.id.edit_post_name);
        editDesc = (EditText) findViewById(R.id.edit_post_desc);
        editNumAvailable = (EditText) findViewById(R.id.edit_post_max_requests);
        editBeginTime = (EditText) findViewById(R.id.edit_post_begin_time);
        editEndTime = (EditText) findViewById(R.id.edit_post_end_time);
        editLocation = (EditText) findViewById(R.id.edit_post_location);
        editTags = (EditText) findViewById(R.id.edit_post_tags);
        buttonSave = (Button) findViewById(R.id.button_save_new_post);

        checkbox1 = (CheckBox) findViewById(R.id.american);
        checkbox2 = (CheckBox) findViewById(R.id.mexican);
        checkbox3 = (CheckBox) findViewById(R.id.fastFood);
        checkbox4 = (CheckBox) findViewById(R.id.burger);
        checkbox5 = (CheckBox) findViewById(R.id.pizza);
        checkbox6 = (CheckBox) findViewById(R.id.asian);
        checkbox7 = (CheckBox) findViewById(R.id.coffee);
        checkbox8 = (CheckBox) findViewById(R.id.italian);
        checkbox9 = (CheckBox) findViewById(R.id.sandwich);
        checkbox10 = (CheckBox) findViewById(R.id.sushi);
        checkbox11 = (CheckBox) findViewById(R.id.breakfast);
        checkbox12 = (CheckBox) findViewById(R.id.dessert);
        checkbox13 = (CheckBox) findViewById(R.id.bakery);
        checkbox14 = (CheckBox) findViewById(R.id.boba);
        checkbox15 = (CheckBox) findViewById(R.id.thai);
        checkbox16 = (CheckBox) findViewById(R.id.indian);

        homemade = (RadioButton) findViewById(R.id.radio_homemade);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String description = editDesc.getText().toString();
                final String owner = Profile.getCurrentProfile().getId();
                final String food = editName.getText().toString();
                // images
                num_requests = editNumAvailable.getText().toString(); // error check for words? TODO
                final String tags = editTags.getText().toString();
                final String beginTime = editBeginTime.getText().toString();
                final String endTime = editEndTime.getText().toString();
                final String location = editLocation.getText().toString();
                final String active = "true";
                // groups
                final String homemade_tag;
                if (homemade.isChecked()) homemade_tag = "homemade";
                else homemade_tag = "restaurant";
                // all friends can view

                String category = ""; // change to dynamic if time TODO

                if (checkbox1.isChecked()) {
                    category += checkbox1.getText();
                    category += ", ";
                }
                if (checkbox2.isChecked()) {
                    category += checkbox2.getText();
                    category += ", ";
                }
                if (checkbox3.isChecked()) {
                    category += checkbox3.getText();
                    category += ", ";
                }
                if (checkbox4.isChecked()) {
                    category += checkbox4.getText();
                    category += ", ";
                }
                if (checkbox5.isChecked()) {
                    category += checkbox5.getText();
                    category += ", ";
                }
                if (checkbox6.isChecked()) {
                    category += checkbox6.getText();
                    category += ", ";
                }
                if (checkbox7.isChecked()) {
                    category += checkbox7.getText();
                    category += ", ";
                }
                if (checkbox8.isChecked()) {
                    category += checkbox8.getText();
                    category += ", ";
                }
                if (checkbox9.isChecked()) {
                    category += checkbox9.getText();
                    category += ", ";
                }
                if (checkbox10.isChecked()) {
                    category += checkbox10.getText();
                    category += ", ";
                }
                if (checkbox11.isChecked()) {
                    category += checkbox11.getText();
                    category += ", ";
                }
                if (checkbox12.isChecked()) {
                    category += checkbox12.getText();
                    category += ", ";
                }
                if (checkbox13.isChecked()) {
                    category += checkbox13.getText();
                    category += ", ";
                }
                if (checkbox14.isChecked()) {
                    category += checkbox14.getText();
                    category += ", ";
                }
                if (checkbox15.isChecked()) {
                    category += checkbox15.getText();
                    category += ", ";
                }
                if (checkbox16.isChecked()) {
                    category += checkbox16.getText();
                }

                final String categories = category;
                final String users = "";

                // images in between food and num_requests
                // groups in between active and usersRequested
                // allFriendsCanView at end
                Post post = new Post(description, owner, food, num_requests, categories, tags,
                        beginTime, endTime, location, active, users, users, homemade_tag);

                PostRepo postRepo = new PostRepo(getApplicationContext());
                int postId = postRepo.insert(post);

                Intent intent = new Intent(CreatePostActivity.this, ViewPostActivity.class);
                intent.putExtra("postID", postId);
                startActivity(intent);
            }
        });

    }
}