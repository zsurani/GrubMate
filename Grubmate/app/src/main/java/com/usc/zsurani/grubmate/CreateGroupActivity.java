package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText editName;
    private Button addGroupMembers;
    private Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        editName = (EditText) findViewById(R.id.edit_group_name);
        addGroupMembers = (Button) findViewById(R.id.button_add_members);
        saveChanges = (Button) findViewById(R.id.button_save_group_changes);

        // TODO all the listeners 
    }
}
