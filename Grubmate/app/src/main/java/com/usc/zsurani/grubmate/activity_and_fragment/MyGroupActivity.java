package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.activity_and_fragment.AddGroupMembersActivity;
import com.usc.zsurani.grubmate.activity_and_fragment.CreateGroupActivity;
import com.usc.zsurani.grubmate.base_classes.Group;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.GroupRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;

public class MyGroupActivity extends AppCompatActivity {

    private String[] groupNames;

    private ListView groupList;
    private Button createGroup;
    private GroupAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygroups);
        Log.d("ACTIVITY", "entered mygroupactivity");

        adapter = new GroupAdapter(getApplicationContext(), R.layout.layout_group_row, getGroupList());
        groupList = (ListView) findViewById(android.R.id.list);
        groupList.setAdapter(adapter);

        if (getGroupList().size() > 0) {
            ((TextView) findViewById(android.R.id.empty)).setVisibility(View.GONE);
        }

        createGroup = (Button) findViewById(R.id.create_group);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start new page for creating a notification
                Intent i = new Intent(getApplicationContext(), CreateGroupActivity.class);
                startActivity(i);
            }
        });
    }

    private List<Group> getGroupList() {
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getApplicationContext());
        final int userId = up.getId(fbId);

        List<Group> groupList = new ArrayList<>();

        GroupRepo repo = new GroupRepo(getApplicationContext());
        List<String> groupStrings = repo.getGroupsFromOwner(Integer.toString(userId));
        for (String id : groupStrings) {
            groupList.add(repo.getGroup(id));
        }

        return groupList;
    }

    /*
     * The custom adapter for the Notifications list view.
     */
    private class GroupAdapter extends ArrayAdapter<Group> {

        public GroupAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public GroupAdapter(Context context, int textViewResourceId, List<Group> items) {
            super(context, textViewResourceId, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            // If the View to convert doesn't exist, inflate a new one with the correct layout
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getApplicationContext());
                v = vi.inflate(R.layout.layout_group_row, null);
            }

            // Get the Notifications object, and if it isn't null, populate the layout with its data
            final Group t = getItem(position);

            if (t != null) {
                TextView textName = (TextView) v.findViewById(R.id.listText);

                textName.setText(t.getName());

                // on click listener for the "End Notification" button
                textName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), AddGroupMembersActivity.class);
                        i.putExtra("groupName", t.getName());
                        startActivityForResult(i, 0);
                    }
                });
            }

            return v;
        }
    }
}
