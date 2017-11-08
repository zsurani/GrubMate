package com.usc.zsurani.grubmate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashSet;


public class SortFragment extends Fragment {

    private Button saveSort;

    public SortFragment() {
        // Required empty public constructor
    }

    public static SortFragment newInstance() {
        SortFragment fragment = new SortFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.activity_sort, null);

        HashSet<String> sorts = new HashSet<String>();
        saveSort = (Button) v.findViewById(R.id.button_savesort);
        saveSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SortResultActivity.class);
                HashSet<String> sorts = new HashSet<String>();
                String sortsString = "";
                CheckBox c = (CheckBox) v.findViewById(R.id.rating_sort);
                if (c.isChecked()) {
                    sorts.add("Rating");
                    sortsString+="Rating";
                }
                c = (CheckBox) v.findViewById(R.id.popularity_sort);
                if (c.isChecked())
                {
                    sorts.add("Popularity");
                    sortsString+="Popularity";
                }
                c = (CheckBox) v.findViewById(R.id.time_sort);
                if (c.isChecked())
                {
                    sorts.add("Time");
                    sortsString+="Time";
                }
                c = (CheckBox) v.findViewById(R.id.distance_sort);
                if (c.isChecked())
                {
                    sorts.add("Distance");
                    sortsString+="Distance";
                }
                i.putExtra ( "sortChecks", sortsString );
                startActivity(i);
            }
        });

        return v;
    }

}
