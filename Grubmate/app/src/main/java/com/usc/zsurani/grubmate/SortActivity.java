package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.HashSet;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SortActivity extends AppCompatActivity {
    private Button saveSort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        HashSet<String> sorts = new HashSet<String>();
        saveSort = (Button) findViewById(R.id.button_savesort);
        saveSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SortResultActivity.class);
                HashSet<String> sorts = new HashSet<String>();
                String sortsString = "";
                CheckBox c = (CheckBox) findViewById(R.id.rating_sort);
            if (c.isChecked()) {
                sorts.add("Rating");
                sortsString+="Rating";
            }
            c = (CheckBox) findViewById(R.id.popularity_sort);
            if (c.isChecked())
            {
                sorts.add("Popularity");
                sortsString+="Popularity";
            }
            c = (CheckBox) findViewById(R.id.time_sort);
            if (c.isChecked())
            {
                sorts.add("Time");
                sortsString+="Time";
            }
            c = (CheckBox) findViewById(R.id.distance_sort);
            if (c.isChecked())
            {
                sorts.add("Distance");
                sortsString+="Distance";
            }
                i.putExtra ( "sortChecks", sortsString );
                startActivity(i);
            }
        });
    }
}
