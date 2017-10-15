package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;

import java.util.HashSet;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText editName;
    private Button addGroupMembers;
    private Button saveChanges;

    public static final int RESULT_SAVE = 111;

    String fbId = Profile.getCurrentProfile().getId();
    UserRepo up = new UserRepo(getApplicationContext());
    final int userId = up.getId(fbId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        editName = (EditText) findViewById(R.id.edit_group_name);
        addGroupMembers = (Button) findViewById(R.id.button_add_members);
        saveChanges = (Button) findViewById(R.id.button_save_group_changes);

        // TODO all the listeners
        addGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateGroupActivity.this, AddGroupMembersActivity.class);
                startActivityForResult(i, 0);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO create intent to return info back to view group activity
                Intent i = new Intent(CreateGroupActivity.this, ViewGroupActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_SAVE:
                //getting unchecked cast error
                //Group group = new Group((HashSet<Integer>) data.getSerializableExtra(AddGroupMembersActivity.MEMBERS_SET), userId);

                break;
        }

    }
}
