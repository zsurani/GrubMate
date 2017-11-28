package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.MessengerThreadParams;
import com.facebook.messenger.ShareToMessengerParams;
import com.facebook.messenger.ShareToMessengerParamsBuilder;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.usc.zsurani.grubmate.MainActivity;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.adapters.PostAdapter;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.Profiles;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

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
    private ListView reviewList;
    private TextView textRating;
    private TextView textNumRatings;
    private TextView textName;
    private ImageView profilePic;
    private Button postButton;
    private Button reviewButton;
    private Button createPost;
    private Button messageButton;

    private Bundle args;
    private Integer userId;
    private Integer extraId = null;

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
        if(args != null) {
            extraId = getArguments().getInt(EXTRA_USER_ID);
        }


        postList = (ListView) v.findViewById(R.id.list_posts);
        reviewList = (ListView) v.findViewById(R.id.list_reviews);
        textRating = (TextView) v.findViewById(R.id.label_ratings);
        textNumRatings = (TextView) v.findViewById(R.id.label_num_ratings);
        textName = (TextView) v.findViewById(R.id.label_name);
        profilePic = (ImageView) v.findViewById(R.id.image_profile_pic);
        postButton = (Button) v.findViewById(R.id.button_profile_see_posts);
        reviewButton = (Button) v.findViewById(R.id.button_profile_see_reviews);
        createPost = (Button) v.findViewById(R.id.create_post);
        messageButton = (Button) v.findViewById(R.id.button_fb_message);

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


        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), CreatePostActivity.class);
                startActivityForResult(i,0);
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMessenger();
            }
        });

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


        PostAdapter adapter = new PostAdapter(getContext(), R.layout.layout_post_row, userPosts, getActivity());
        postList.setAdapter(adapter);

        UserRepo ur = new UserRepo(getContext());
        List<String> reviews = ur.getReviews(Integer.toString(userId));
        reviewList.setAdapter(new ReviewAdapter(getContext(), R.layout.layout_review_row, reviews));

    }

    private void setupProfile() {
        String fbId = "";
        String stringName = "";
        Uri uri = null;
        UserRepo userRepo = new UserRepo(getContext());
        if (Profile.getCurrentProfile() == null) {
            fbId = userRepo.getProfile().getId();
            stringName = userRepo.getProfile().getName();
            uri = userRepo.getProfile().getUri();
        } else if (extraId != null) {
            userId = extraId;
            Log.d("DEBUG", "extra Id != null and userID = " + userId);
        }
        else {
            fbId = Profile.getCurrentProfile().getId();
            stringName = Profile.getCurrentProfile().getName();
            uri = Profile.getCurrentProfile().getProfilePictureUri(profilePic.getMaxWidth(), profilePic.getMaxHeight());
        }
        UserRepo up = new UserRepo(getContext());
        //final int userId;
        if (args == null) {
            userId = up.getId(fbId);
            Log.d("TEST", "userId = " + userId);
        } else {
            Log.d("TEST", "userId = " + userId);
            userId = extraId;
            stringName = up.getName(userId);
            String fbid = up.getFBId(userId);
            Log.d("TEST", "fbid: " + fbid);
            Profiles profile = up.getProfile(fbid);
            uri = profile.getUri();
            Log.d("TEST", "uri: " + uri);
        }

//        String stringNumRatings = up.getNumRatings(String.valueOf(userId));
//        String stringRating = up.getRating(String.valueOf(userId));
//        Log.d("DEBUG - stringRating", stringNumRatings);

//        Log.d("uri", uri.toString());

        String stringRating = Float.toString(userRepo.getRealRating(userId));
        String stringNumRatings = Integer.toString(userRepo.getRawNumRatings(Integer.toString(userId)));

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

    // opens facebook messenger (if installed) on the compose new message page
    public void goToMessenger() {
        boolean isFBInstalled = isPackageInstalled("com.facebook.orca", ((getActivity().getPackageManager())));

        if (!isFBInstalled) {
            Toast.makeText(getContext(),"Facebook messenger isn't installed. Please download the app first.", Toast.LENGTH_SHORT)
                    .show();
        }

        else {
            Uri uri = Uri.parse("fb-messenger://compose");

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            try {
                startActivity(intent);
                Log.d("FB INTENT", "intent created");
            }

            catch(Exception e) {
                Toast.makeText(getContext(), "Oops! Can't open Facebook messenger right now. Please try again later.", Toast.LENGTH_SHORT)
                        .show();

            }
        }

        return;
    }

    // Checks to see if the package we're looking for is installed on this phone
    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}

