package com.usc.zsurani.grubmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AddGroupMembersActivity extends AppCompatActivity {

    private EditText memberName;
    private Button addingMembers;
    private Button savingChanges;
    private ListView groups;
    private List<String> members = new ArrayList<>();
    private GroupAdapter adapter;
    private String groupName;

    public static final String MEMBERS_SET = "Members.Set";
    private String friendname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_members);

        Bundle extras = getIntent().getExtras();
        groupName = extras.getString("groupName");




        memberName = (EditText) findViewById(R.id.member_name);
        addingMembers = (Button) findViewById(R.id.adding_members);
        savingChanges = (Button) findViewById(R.id.save_members);

        adapter = new GroupAdapter(getApplicationContext(), R.layout.layout_member_row, members);
        groups = (ListView) findViewById(R.id.group_list);
        groups.setAdapter(adapter);

        /*
            gets userID using the friend name inserted by the user
            add the userid to the friends set needed to create the group
         */
        addingMembers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                friendname = memberName.getText().toString();
                GroupRepo groupRepo = new GroupRepo(getApplicationContext());
                members.add(friendname);
                adapter.notifyDataSetChanged();
                memberName.setText("");
            }
        });

        /*
            send the set of friends that will be used to create the new group
         */
        savingChanges.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupRepo groupRepo = new GroupRepo(getApplicationContext());
                groupRepo.insert(groupName, members);
            }
        });
    }


    /*
         * The custom adapter for the Groups list view.
         */
    private class GroupAdapter extends ArrayAdapter<String> {

        public GroupAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public GroupAdapter(Context context, int textViewResourceId, List<String> friends) {
            super(context, textViewResourceId, friends);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            // If the View to convert doesn't exist, inflate a new one with the correct layout
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getApplicationContext());
                v = vi.inflate(R.layout.layout_member_row, null);
            }

            // Get the Notifications object, and if it isn't null, populate the layout with its data
            final String t = getItem(position);

            if (t != null) {
                final TextView textName = (TextView) v.findViewById(R.id.label_member_name);
                Button buttonEnd = (Button) v.findViewById(R.id.button_member);

                textName.setText(t);

                // on click listener for the "Delete Member" button
                buttonEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i=0; i<members.size(); i++) {
                            if (members.get(i).equals(t)) members.remove(i);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            return v;
        }
    }
}