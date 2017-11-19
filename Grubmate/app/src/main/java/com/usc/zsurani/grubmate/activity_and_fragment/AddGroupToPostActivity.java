package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.usc.zsurani.grubmate.R;

/**
 * Created by stephaniehernandez on 10/16/17.
 */

public class AddGroupToPostActivity extends AppCompatActivity {

    private EditText groupname;
    private Button viewGroup;
    private Button savePost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_groups_for_post);

        groupname = (EditText) findViewById(R.id.adding_group);
        viewGroup = (Button) findViewById(R.id.button_view_groups);
        savePost = (Button) findViewById(R.id.saving_post);

        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "view");
                Intent i = new Intent(AddGroupToPostActivity.this, MyGroupActivity.class);
                startActivity(i);
            }
        });

        savePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("groupname", groupname.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

}
