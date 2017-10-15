package com.usc.zsurani.grubmate;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

/**
 * Created by Madison on 10/15/17.
 */

public class MyGroupFragment extends Fragment {
    private String[] groupNames;

    private ListView groupList;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.layout_group_row, R.id.listText, groupNames);
        groupList.setAdapter(adapter);

    }


}
