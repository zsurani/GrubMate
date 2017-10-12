package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePostActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editDesc;
    private EditText editNumAvailable;
    private EditText editBeginTime;
    private EditText editEndTime;
    private EditText editLocation;
    private EditText editTags;
    private Button buttonSave;

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

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
    }
}
