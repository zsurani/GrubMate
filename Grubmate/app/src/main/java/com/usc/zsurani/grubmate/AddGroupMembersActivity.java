package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;

import java.util.HashSet;
import java.util.Set;


public class AddGroupMembersActivity extends AppCompatActivity {

    private EditText memberName;
    private Button addingMembers;
    private Button savingChanges;
    private HashSet<Integer> friends = new HashSet<Integer>();

    public static final String MEMBERS_SET = "Members.Set";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_members);

//        memberName = (EditText) findViewById(R.id.member_name);
//        addingMembers = (Button) findViewById(R.id.adding_members);
//        savingChanges = (Button) findViewById(R.id.save_members);
//
//        final String friendname = memberName.getText().toString();
//
//        /*
//            gets userID using the friend name inserted by the user
//            add the userid to the friends set needed to create the group
//         */
//        addingMembers.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserRepo ur = new UserRepo(getApplicationContext());
//                int userID = ur.getUserId(friendname);
//                friends.add(userID);
//            }
//        });
//
//        /*
//            send the set of friends that will be used to create the new group
//         */
//        savingChanges.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent dummy = new Intent();
//                dummy.putExtra(MEMBERS_SET, friends);
//                setResult(CreateGroupActivity.RESULT_SAVE, dummy);
//                finish();
//            }
//        });
    }
}