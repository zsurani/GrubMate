package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyGroupActivity extends AppCompatActivity {

    private String[] groupNames;

    private ListView groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygroups);

        groupList = (ListView) findViewById(R.id.list_groups);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_group_row, R.id.listText, groupNames);
        groupList.setAdapter(adapter);
    }
}
