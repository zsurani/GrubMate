package com.usc.zsurani.grubmate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CreateGroupFragment extends Fragment {

    private EditText editName;
    private Button addGroupMembers;
    private Button back;

    public CreateGroupFragment() {
        // Required empty public constructor
    }

    public static CreateGroupFragment newInstance() {
        CreateGroupFragment fragment = new CreateGroupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_create_group, null);

        editName = (EditText) v.findViewById(R.id.edit_group_name);
        addGroupMembers = (Button) v.findViewById(R.id.button_add_members);
        back = (Button) v.findViewById(R.id.button_back_to_mygroups);


        addGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(CreateGroupActivity.this, AddGroupMembersActivity.class);
//                i.putExtra("groupName", editName.getText().toString());
//                startActivityForResult(i, 0);

                ((MainActivity) getActivity()).goToFragment(10, editName.getText().toString());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goToMainFragment(2);
            }
        });

        return v;
    }

}
