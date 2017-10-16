package com.usc.zsurani.grubmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Madison on 10/15/17.
 */

public class ProfileFragment extends Fragment {
    private String name;
    private float rating;
    private int numRatings;
    private String[] reviews;
    // pic
    // private Post[] posts;

    private ListView postList;
    private ListView reviewList;
    private TextView textRating;
    private TextView textNumRatings;
    private TextView textName;
    private ImageView profilePic;
    private Button postButton;
    private Button reviewButton;

    private Bundle args;
    private Integer userId;

    public static final String EXTRA_USER_ID = "grubmate.profile_fragment.user_id";

    /*
     * CALL THIS when you're on a post for a different user, trying to pass that user's ID info
     * into this class. TODO test the args work
     */
    public static ProfileFragment newInstance(int user_id) {
        ProfileFragment frag = new ProfileFragment();
        Bundle toAttach = new Bundle();
        toAttach.putInt(EXTRA_USER_ID, user_id);
        frag.setArguments(toAttach);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        args = getArguments();

        postList = (ListView) v.findViewById(R.id.list_posts);
        reviewList = (ListView) v.findViewById(R.id.list_reviews);
        textRating = (TextView) v.findViewById(R.id.label_ratings);
        textNumRatings = (TextView) v.findViewById(R.id.label_num_ratings);
        textName = (TextView) v.findViewById(R.id.label_name);
        profilePic = (ImageView) v.findViewById(R.id.image_profile_pic);
        postButton = (Button) v.findViewById(R.id.button_profile_see_posts);
        reviewButton = (Button) v.findViewById(R.id.button_profile_see_reviews);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postList.setVisibility(View.VISIBLE);
                reviewList.setVisibility(View.INVISIBLE);
            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewList.setVisibility(View.VISIBLE);
                postList.setVisibility(View.INVISIBLE);
            }
        });

        //// TODO connect to db
       // List<Post> posts = new ArrayList<Post>();
       // postList.setAdapter(new PostAdapter(getContext(), R.layout.layout_post_row, posts));

        // TODO connect to db
        List<String> reviews = new ArrayList<>();
        reviewList.setAdapter(new ReviewAdapter(getContext(), R.layout.layout_review_row, reviews));

        setupProfile();

        //need to get all of the posts that the user owns
        PostRepo pr = new PostRepo(getContext());
        Log.d("DEBUG", "creating the postRepo");
        List<Integer> postIds = pr.getPosts(userId);
        Log.d("DEBUG", "gotten all the post Ids");
        Log.d("DEBUG", Integer.toString(postIds.size()));
        List<Post> userPosts = new ArrayList<Post>();
        for(int i = 0; i < postIds.size(); i++) {
            Log.d("I = ", Integer.toString(i));
            Log.d("DEBUG", Integer.toString(postIds.get(i)));
            Integer postID = postIds.get(i);
            Post post = pr.getPost(postID);

            userPosts.add(post);
           // Log.d("DEBUG", Integer.toString(p.getId()));
        }


        PostAdapter adapter = new PostAdapter(getContext(), R.layout.layout_post_row, userPosts);
        postList.setAdapter(adapter);
    }

    private void setupProfile() {
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getContext());
        //final int userId;
        if (args == null) {
            userId = up.getId(fbId);
        } else {
            userId = args.getInt(EXTRA_USER_ID);
        }

        String stringNumRatings = up.getNumRatings(String.valueOf(userId));
        String stringRating = up.getRating(String.valueOf(userId));
        String stringName = Profile.getCurrentProfile().getName();
        Uri uri = Profile.getCurrentProfile().getProfilePictureUri(profilePic.getMaxWidth(), profilePic.getMaxHeight());

        if (stringNumRatings.isEmpty()) stringNumRatings = "0";
        if (stringRating.isEmpty()) stringRating = "N/A";

        textNumRatings.setText(String.format(getResources().getString(R.string.text_profile_num_ratings), stringNumRatings));
        textRating.setText(String.format(getResources().getString(R.string.text_profile_rating),stringRating));
        textName.setText(stringName);
        Picasso.with(getContext()).load(uri).into(profilePic);
    }

    private class ReviewAdapter extends ArrayAdapter<String> {

        private Context context;

        public ReviewAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.context = context;
        }

        public ReviewAdapter(Context context, int textViewResourceId, List<String> items) {
            super(context, textViewResourceId, items);
            this.context = context;
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(context.getApplicationContext());
                v = vi.inflate(R.layout.layout_review_row, null);
            }

            String review = getItem(position);
            if (review != null) {
                TextView description = (TextView) v.findViewById(R.id.text_profile_review);
                description.setText(review);
            }

            return v;

        }

    }

}

