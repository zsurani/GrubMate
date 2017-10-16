package com.usc.zsurani.grubmate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A fragment for the Notification Center.
 */
public class NotificationCenterFragment extends Fragment {

    private Button buttonSubscriptions;
    private Button buttonRequests;
    private ListView listSubscriptions;
    private ListView listRequests;

    private boolean displaySubscriptions = false;
    private static final String KEY_DISPLAY_SUB = "grubmate.notification_center.display_subscriptions";

    public NotificationCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification_center, container, false);

        if (savedInstanceState != null) {
            displaySubscriptions = savedInstanceState.getBoolean(KEY_DISPLAY_SUB);
        }

        buttonRequests = (Button) v.findViewById(R.id.button_notification_center_see_requests);
        buttonSubscriptions = (Button) v.findViewById(R.id.button_notification_center_see_subscriptions);
        listRequests = (ListView) v.findViewById(R.id.list_notification_center_requests);
        listSubscriptions = (ListView) v.findViewById(R.id.list_notification_center_subscriptions);

        if (displaySubscriptions) {
            PostAdapter subAdapter = new PostAdapter(getActivity().getApplicationContext(), R.layout.layout_post_row, getMatchingPosts());
            listSubscriptions.setAdapter(subAdapter);
            listRequests.setVisibility(View.INVISIBLE);
            listSubscriptions.setVisibility(View.VISIBLE);
        } else {
            // TODO request list adapter

            listRequests.setVisibility(View.VISIBLE);
            listSubscriptions.setVisibility(View.INVISIBLE);
        }


        buttonSubscriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostAdapter subAdapter = new PostAdapter(getActivity().getApplicationContext(), R.layout.layout_post_row, getMatchingPosts());
                listSubscriptions.setAdapter(subAdapter);
                listRequests.setVisibility(View.INVISIBLE);
                listSubscriptions.setVisibility(View.VISIBLE);
            }
        });

        buttonRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO request list adapter

                listRequests.setVisibility(View.VISIBLE);
                listSubscriptions.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_DISPLAY_SUB, displaySubscriptions);
    }

    private List<Post> getMatchingPosts() {
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getActivity().getApplicationContext());
        final int userId = up.getId(fbId);

        PostRepo pr = new PostRepo(getActivity().getApplicationContext());
        List<Post> allPosts = pr.returnAllPosts();
        List<Post> matchingPosts = new ArrayList<>();

        for (Post post : allPosts) {
            for (Notifications notif : getNotificationList()) {
                if (post.matches(notif)) {
                    matchingPosts.add(post);
                }
            }
        }

        return matchingPosts;
    }

    private List<Transaction> getNewTransactions() {
        return null;
    }

    private List<Notifications> getNotificationList() {
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getActivity().getApplicationContext());
        final int userId = up.getId(fbId);

        List<Notifications> notifList = new ArrayList<>();

        NotificationsRepo repo = new NotificationsRepo(getActivity().getApplicationContext());
        List<String> notifStrings = repo.getNotifications(userId);
        for (String id : notifStrings) {
            notifList.add(repo.getNotification(Integer.valueOf(id)));
        }

        return notifList;
    }

}
