package com.usc.zsurani.grubmate.activity_and_fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.usc.zsurani.grubmate.MainActivity;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

public class ViewPostFragment extends Fragment {

    private static final String ARG_PARAM1 = "viewpost.postid";

    private Button buttonRequestOnPost;
    private Button buttonEdit;
    private TextView postName;
    private TextView postUser;
    private TextView userRating;
    private TextView description;
    private TextView homemade;
    private TextView servings;
    private TextView begin;
    private TextView end;
    private TextView location;
    private TextView categories;
    private TextView tags;
    private ImageView image;
    private int postID;
    private Post post;
    private PostRepo postRepo;


    public ViewPostFragment() {
        // Required empty public constructor
    }

    public static ViewPostFragment newInstance(int param1) {
        ViewPostFragment fragment = new ViewPostFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
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
        View v = inflater.inflate(R.layout.activity_view_post, null);

        postID = getArguments().getInt(ARG_PARAM1);

        buttonRequestOnPost = (Button) v.findViewById(R.id.button_request_on_post_view);
        postName = (TextView) v.findViewById(R.id.view_post_name);
        postUser = (TextView) v.findViewById(R.id.view_post_user);
        userRating = (TextView) v.findViewById(R.id.view_post_rating);
        description = (TextView) v.findViewById(R.id.view_post_desc);
        homemade = (TextView) v.findViewById(R.id.view_post_homemade);
        servings = (TextView) v.findViewById(R.id.view_post_servings);
        begin = (TextView) v.findViewById(R.id.view_post_begin);
        end = (TextView) v.findViewById(R.id.view_post_end);
        location = (TextView) v.findViewById(R.id.view_post_location);
        categories = (TextView) v.findViewById(R.id.view_post_categories);
        tags = (TextView) v.findViewById(R.id.view_post_tags);
        image = (ImageView) v.findViewById(R.id.view_post_picture);
        buttonEdit = (Button) v.findViewById(R.id.edit_button);

        postRepo = new PostRepo(getActivity().getApplicationContext());
        Log.d("DEBUG - postID", Integer.toString(postID));
        post = postRepo.getPost(postID);
        postName.setText("Name: " + post.getFood());
        postUser.setText("User: " + post.getOwner_string());
        userRating.setText("Rating: " + post.getUserRating());
        description.setText("Description: " + post.getDescription());
        homemade.setText("Homemade: " + post.getHomemade());
        servings.setText("Servings: " + post.getNum_requests());
        begin.setText("Start Time: " + post.getBeginTime());
        end.setText("End Time: " + post.getEndTime());
        location.setText("Location: " + post.getLocation());
        categories.setText("Categories: " + post.getCategories());
        tags.setText("Tags: " + post.getTag());
        byte[] images = post.getPhoto_image();
        Bitmap images2 = BitmapFactory.decodeByteArray(images, 0, images.length);
        image.setImageBitmap(images2);

        if (post.getOwner_string().equals(Profile.getCurrentProfile().getName())) {
            buttonEdit.setVisibility(View.VISIBLE);
        } else {
            buttonEdit.setVisibility(View.INVISIBLE);
        }

        //Log.d("DEBUG userreq", "post = " + postRepo.getRequestors(postID).length);

        if(Integer.parseInt(post.getNum_requests()) <= (postRepo.getRequestors(postID).length - 1)) //because it starts wiht ","
        {
            buttonRequestOnPost.setEnabled(false);
        } else {
            buttonRequestOnPost.setEnabled(true);
        }

        if(postRepo.getAccepted(postID).length -1 > 0)
        {
            buttonEdit.setEnabled(false);
        } else {
            buttonEdit.setEnabled(true);
        }

        buttonRequestOnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //need to update the post with the user id of who is requesting
                UserRepo ur = new UserRepo(getActivity().getApplicationContext());
                Integer userId = ur.getId(Profile.getCurrentProfile().getId());

                if(Integer.parseInt(post.getNum_requests()) <= (postRepo.getAccepted(Integer.toString(postID))).length() - 1) //because it starts wiht ","
                {
                    buttonRequestOnPost.setEnabled(false);
                } else {
                    buttonRequestOnPost.setEnabled(true);
                }

//                Intent i = new Intent(getApplicationContext(), EnterLocationActivity.class);
//                i.putExtra("postID", postID);
//                i.putExtra("userID", userId);
//                startActivity(i);
                if (postRepo.isRequestor(Integer.toString(postID), Integer.toString(userId))) {
                    Toast.makeText(getActivity().getApplicationContext(), "You have already requested this food", Toast.LENGTH_LONG).show();
                } else {
                    postRepo.addNewRequestor(Integer.toString(postID), Integer.toString(userId));
                    ((MainActivity) getActivity()).goToFragment(1, userId, postID);
                }

            }
        });

        //goes to the user profile from the User label
        postUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Post p = new Post(postID);
                //ProfileActivity pa = new ProfileActivity(p.getProvider());
                // Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                //i.putExtra("frgToLoad", 0); //0 = profile
                // startActivity(i);
                //MAKE REQUEST
            }
        });

        // edit post button leads to create post page
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), CreatePostActivity.class);
                i.putExtra("postID", postID);
                startActivity(i);
            }
        });

        return v;
    }

}
