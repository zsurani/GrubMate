package com.usc.zsurani.grubmate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

        for (Post post : allPosts) {
            if (post.getTags() == null) Log.d("NOTIF CENTER" , "POST TAGS NULL");
            if (post.getCategory() == null) Log.d("NOTIF CENTER", "POST CAT NULL");
        }

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
            Notifications n = repo.getNotification(Integer.valueOf(id));
            n.setId(Integer.valueOf(id));
            notifList.add(n);
            if (n.getTags() == null) Log.d("NOTIF CENTER", "TAGS NULL");
            if (n.getCategory() == null) Log.d("NOTIF CENTER", "CATEGORIES NULL");
        }

        return notifList;
    }


    /*
     * The custom adapter for the Notifications list view.
     */
    private class TransnotifAdapter extends ArrayAdapter<Notifications> {
        private Context context;

        public TransnotifAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.context = context;
        }

        public TransnotifAdapter(Context context, int textViewResourceId, List<Notifications> items) {
            super(context, textViewResourceId, items);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            // If the View to convert doesn't exist, inflate a new one with the correct layout
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.layout_notification_row, null);
            }

            // Get the Notifications object, and if it isn't null, populate the layout with its data
            final Notifications t = getItem(position);

            if (t != null) {
                TextView textName = (TextView) v.findViewById(R.id.label_notification_name);
                TextView textInfo = (TextView) v.findViewById(R.id.label_notification_description);
                final Button buttonEnd = (Button) v.findViewById(R.id.button_notification);

                String timeStart = t.getBeginTime();
                String timeEnd = t.getEndTime();
                String tags = "";
                if (t.getTags() != null ) {
                    tags = t.getTags().toString();
                    if (tags.length() > 0) {
                        tags = tags.substring(1, tags.length() - 1);
                    } else {
                        tags = "none";
                    }
                }
                String categories = "";
                if (t.getCategory() != null) {
                    categories = t.getCategory().toString();
                    if (categories.length() > 0) {
                        categories = categories.substring(1, categories.length() - 1);
                    } else {
                        categories = "none";
                    }
                }


                textName.setText(t.getName());
                textInfo.setText(String.format(getResources().getString(R.string.text_notification_description), timeStart, timeEnd, tags, categories));

                //if time is passed, button is disabled; else it's enabled
                DateFormat df = new SimpleDateFormat("hh:mm a");
                Date end;
                try {
                    end = df.parse(timeEnd);
                } catch (Exception e) {
                    end = Calendar.getInstance().getTime();
                }
                Date now = Calendar.getInstance().getTime();

//                if (now.before(end)) {
//                    buttonEnd.setEnabled(false);
//                } else {
//                    buttonEnd.setEnabled(true);
//                }

                // on click listener for the "End Notification" button
                buttonEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NotificationsRepo repo = new NotificationsRepo(getActivity().getApplicationContext());
                        repo.updateStatus(String.valueOf(t.getId()), "0");
                        t.setActiveStatus(false);

                        // have to update adapter
                        notifyDataSetChanged();

                        buttonEnd.setEnabled(false);

                    }
                });

                if (!t.isActive()) {
                    buttonEnd.setEnabled(false);
                    buttonEnd.setText("Notification Cancelled");
                } else {
                    buttonEnd.setEnabled(true);
                    buttonEnd.setText("End Notification");
                }
            }

            return v;
        }
    }

}
