package com.usc.zsurani.grubmate.activity_and_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.usc.zsurani.grubmate.MainActivity;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingReviewFragment extends Fragment {

    private RatingBar rating;
    private EditText review;
    private Button saveChanges;
    int id;

    private static final String KEY_ID = "ratingreview.id";


    public RatingReviewFragment() {
        // Required empty public constructor
    }

    public static RatingReviewFragment newInstance(int id) {
        RatingReviewFragment fragment = new RatingReviewFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_rating_review, null);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        rating = (RatingBar) v.findViewById(R.id.user_rating);
        review = (EditText) v.findViewById(R.id.edit_review);
        saveChanges = (Button) v.findViewById(R.id.button_save_rating);

        id = getArguments().getInt(KEY_ID, 0);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepo ur = new UserRepo(getActivity().getApplicationContext());
                Log.d("rating", Float.toString(rating.getRating()));
                ur.addRating(Integer.toString(id), rating.getRating());
                ur.addReview(Integer.toString(id), review.getText().toString());

                ((MainActivity) getActivity()).goToMainFragment(5);
            }
        });

        return v;
    }

}
