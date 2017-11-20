package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.GroupRepo;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText editName;
    private Button addGroupMembers;
    private Button back;

    public static final int RESULT_SAVE = 111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        editName = (EditText) findViewById(R.id.edit_group_name);
        addGroupMembers = (Button) findViewById(R.id.button_add_members);
        back = (Button) findViewById(R.id.button_back_to_mygroups);


        addGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupRepo groupRepo = new GroupRepo(getApplicationContext());
                if (groupRepo.checkIfGroup(editName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "You already have a group with the same name", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(CreateGroupActivity.this, AddGroupMembersActivity.class);
                    i.putExtra("groupName", editName.getText().toString());
                    startActivityForResult(i, 0);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_SAVE:
                //getting unchecked cast error
//                Group group = new Group((HashSet<Integer>) data.getSerializableExtra(AddGroupMembersActivity.MEMBERS_SET), userId);

                break;
        }

    }
}
