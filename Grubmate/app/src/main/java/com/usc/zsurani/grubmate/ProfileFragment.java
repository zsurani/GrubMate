package com.usc.zsurani.grubmate;

import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    private TextView textRating;
    private TextView textNumRatings;
    private TextView textName;
    private ImageView profilePic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        postList = (ListView) v.findViewById(R.id.list_posts);
        textRating = (TextView) v.findViewById(R.id.label_ratings);
        textNumRatings = (TextView) v.findViewById(R.id.label_num_ratings);
        textName = (TextView) v.findViewById(R.id.label_name);

        profilePic = (ImageView) v.findViewById(R.id.image_profile_pic);

        //just for testing purposes
        Profile current_user = Profile.getCurrentProfile();
        textName.setText(current_user.getName());
        Uri pic = current_user.getProfilePictureUri(100, 100);
        //textRating.setText(pic.toString());
        profilePic.setImageURI(pic);
        //profilePic.setVisibility(View.VISIBLE);

        // TODO create custom adapter to put post info into rows on list view
        List<Post> posts = new ArrayList<Post>();
        postList.setAdapter(new PostAdapter(getContext(), R.layout.layout_post_row, posts));

        // getting all the info to populate the profile page TODO this assumes it's the current user
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getContext());
        final int userId = up.getId(fbId);

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

    //somehow need to get something that gets what profile we are looking at
    void Profile(User u)
    {
        profilePic.setImageURI(u.getImg());
        textName.setText(u.getName());
        textRating.setText(u.getRating());
        //textNumRatings.setText(u.getNumRating()); //need to make the getNumRating method if we think this is needed

    }

}
