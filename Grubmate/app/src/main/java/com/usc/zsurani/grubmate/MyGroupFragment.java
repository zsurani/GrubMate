package com.usc.zsurani.grubmate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Madison on 10/15/17.
 */

public class MyGroupFragment extends Fragment {
    private String[] groupNames;

    private ListView groupList;
    private Button createGroup;
    private GroupAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_mygroups, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        groupList = (ListView) v.findViewById(R.id.list_groups);

        adapter = new GroupAdapter(getApplicationContext(), R.layout.layout_group_row, getGroupList());
        groupList = (ListView) v.findViewById(R.id.list_groups);
        groupList.setAdapter(adapter);

        createGroup = (Button) v.findViewById(R.id.create_group);
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
//                        NotificationsRepo repo = new NotificationsRepo(getApplicationContext());
//                        repo.deleteNotification(String.valueOf(t.getId()));
//
//                        // have to update adapter?

                    }
                });
            }

            return v;
        }
    }

}
