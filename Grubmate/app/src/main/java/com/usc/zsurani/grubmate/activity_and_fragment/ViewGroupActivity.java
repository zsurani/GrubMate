package com.usc.zsurani.grubmate.activity_and_fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.usc.zsurani.grubmate.R;

public class ViewGroupActivity extends AppCompatActivity {

    private String[] groupMembers;

    private ListView memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        memberList = (ListView) findViewById(R.id.list_members);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_group_row, R.id.listText, groupMembers);
        memberList.setAdapter(adapter);
    }
}
