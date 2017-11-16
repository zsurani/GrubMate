package com.usc.zsurani.grubmate.activity_and_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.usc.zsurani.grubmate.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewGroupFragment extends Fragment {

    private String[] groupMembers;

    private ListView memberList;

    public ViewGroupFragment() {
        // Required empty public constructor
    }


    public static ViewGroupFragment newInstance() {
        ViewGroupFragment fragment = new ViewGroupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_view_group, null);

        memberList = (ListView) v.findViewById(R.id.list_members);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.layout_group_row, R.id.listText, groupMembers);
        memberList.setAdapter(adapter);

        return v;
    }

}
