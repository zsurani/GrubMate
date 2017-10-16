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

    public NotificationCenterFragment() {
        // Required empty public constructor
    }

//    public static NotificationCenterFragment newInstance(String param1, String param2) {
//        NotificationCenterFragment fragment = new NotificationCenterFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification_center, container, false);

        buttonRequests = (Button) v.findViewById(R.id.button_notification_center_see_requests);
        buttonSubscriptions = (Button) v.findViewById(R.id.button_notification_center_see_subscriptions);
        listRequests = (ListView) v.findViewById(R.id.list_notification_center_requests);
        listSubscriptions = (ListView) v.findViewById(R.id.list_notification_center_subscriptions);

        return v;
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
