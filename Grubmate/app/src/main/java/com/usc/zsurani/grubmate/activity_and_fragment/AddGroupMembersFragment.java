package com.usc.zsurani.grubmate.activity_and_fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.usc.zsurani.grubmate.MainActivity;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.GroupRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddGroupMembersFragment extends Fragment {

    private EditText memberName;
    private Button addingMembers;
    private Button savingChanges;
    private ListView groups;
    private List<String> members = new ArrayList<>();
    private AddGroupMembersFragment.GroupAdapter adapter;
    private String groupName;

    public static final String MEMBERS_SET = "Members.Set";
    private static final String GROUP_NAME = "Group.Name";
    private String friendname;


    public AddGroupMembersFragment() {
        // Required empty public constructor
    }

    public static AddGroupMembersFragment newInstance(String arg) {
        AddGroupMembersFragment fragment = new AddGroupMembersFragment();
        Bundle args = new Bundle();
        args.putString(GROUP_NAME, arg);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_group_members, null);

        groupName = getArguments().getString(GROUP_NAME);
        GroupRepo groupRepo = new GroupRepo(getContext());
        members = groupRepo.getUser(groupName);

        memberName = (EditText) v.findViewById(R.id.member_name);
        addingMembers = (Button) v.findViewById(R.id.adding_members);
        savingChanges = (Button) v.findViewById(R.id.save_members);

        adapter = new AddGroupMembersFragment.GroupAdapter(getContext(), R.layout.layout_member_row, members);
        groups = (ListView) v.findViewById(R.id.group_list);
        groups.setAdapter(adapter);

        /*
            gets userID using the friend name inserted by the user
            add the userid to the friends set needed to create the group
         */
        addingMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendname = memberName.getText().toString();
                members.add(friendname);
                adapter.notifyDataSetChanged();
                memberName.setText("");
            }
        });

        /*
            send the set of friends that will be used to create the new group
         */
        savingChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupRepo groupRepo = new GroupRepo(getContext());
                groupRepo.insert(groupName, members);
                ((MainActivity) getActivity()).goToMainFragment(2);
            }
        });

        return v;
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
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.layout_member_row, null);
            }

            // Get the Notifications object, and if it isn't null, populate the layout with its data
            final String t = getItem(position);

            if (t != null) {
                TextView textName = (TextView) v.findViewById(R.id.label_member_name);
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
