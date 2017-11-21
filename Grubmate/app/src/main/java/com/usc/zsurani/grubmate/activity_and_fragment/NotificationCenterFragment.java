package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.Context;
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
import com.usc.zsurani.grubmate.MainActivity;
import com.usc.zsurani.grubmate.base_classes.Notifications;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.adapters.PostAdapter;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.Transaction;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.NotificationsRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.TransactionRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A fragment for the Notification Center.
 */
public class NotificationCenterFragment extends Fragment {

    private Button buttonSubscriptions;
    private Button buttonRequests;
    private ListView listSubscriptions;
    private ListView listRequests;
    private TextView emptyRequests;
    private TextView emptySubscriptions;

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
        emptyRequests = (TextView) v.findViewById(R.id.text_empty_requests);
        emptySubscriptions = (TextView) v.findViewById(R.id.text_empty_subscriptions);


        if (displaySubscriptions) {
            PostAdapter subAdapter = new PostAdapter(getActivity().getApplicationContext(), R.layout.layout_post_row, getMatchingPosts(), getActivity());
            listSubscriptions.setAdapter(subAdapter);
            listRequests.setVisibility(View.INVISIBLE);
            listSubscriptions.setVisibility(View.VISIBLE);
            emptyRequests.setVisibility(View.INVISIBLE);

            if (getMatchingPosts().size() > 0) {
                emptySubscriptions.setVisibility(View.INVISIBLE);
            } else {
                emptySubscriptions.setVisibility(View.VISIBLE);
            }
        } else {
            TransnotifAdapter transAdapter = new TransnotifAdapter(getActivity().getApplicationContext(), R.layout.layout_transnotif_row, getOtherNotifs());
            listRequests.setAdapter(transAdapter);
            listRequests.setVisibility(View.VISIBLE);
            listSubscriptions.setVisibility(View.INVISIBLE);
            emptySubscriptions.setVisibility(View.INVISIBLE);

            if (getOtherNotifs().size() > 0) {
                emptyRequests.setVisibility(View.INVISIBLE);
            } else {
                emptyRequests.setVisibility(View.VISIBLE);
            }
        }

        buttonSubscriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostAdapter subAdapter = new PostAdapter(getActivity().getApplicationContext(), R.layout.layout_post_row, getMatchingPosts(), getActivity());
                listSubscriptions.setAdapter(subAdapter);
                listRequests.setVisibility(View.INVISIBLE);
                listSubscriptions.setVisibility(View.VISIBLE);
                emptyRequests.setVisibility(View.INVISIBLE);

                if (getMatchingPosts().size() > 0) {
                    emptySubscriptions.setVisibility(View.INVISIBLE);
                } else {
                    emptySubscriptions.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransnotifAdapter transnotifAdapter = new TransnotifAdapter(getActivity().getApplicationContext(), R.layout.layout_post_row, getOtherNotifs());
                listSubscriptions.setAdapter(transnotifAdapter);
                listRequests.setVisibility(View.VISIBLE);
                listSubscriptions.setVisibility(View.INVISIBLE);
                emptySubscriptions.setVisibility(View.INVISIBLE);

                if (getOtherNotifs().size() > 0) {
                    emptyRequests.setVisibility(View.INVISIBLE);
                } else {
                    emptyRequests.setVisibility(View.VISIBLE);
                }
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

        PostRepo pr = new PostRepo(getActivity().getApplicationContext());
        List<Post> allPosts = pr.getPosts();

        for (Post post : allPosts) {
            if (post.getTags() == null) Log.d("NOTIF CENTER" , "POST TAGS NULL");
            if (post.getCategory() == null) Log.d("NOTIF CENTER", "POST CAT NULL");
        }

        List<Post> matchingPosts = new ArrayList<>();

        for (Notifications notif : getNotificationList()) {
            if (notif.isActive()) {
                Log.d("notif", "here");
                for (Post post : allPosts) {
                    Log.d("notif", "see");
                    if (post.matches(notif)) {
                        matchingPosts.add(post);
                    }
                }
            }
        }

        return matchingPosts;
    }

    private List<Notifications> getOtherNotifs() {
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getActivity().getApplicationContext());
        final int userId = up.getId(fbId);

        NotificationsRepo notificationsRepo = new NotificationsRepo(getActivity().getApplicationContext());
        return notificationsRepo.getTransNotif(userId);
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
        List<String> notifStrings = repo.getSubscriptions(userId);
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
                v = vi.inflate(R.layout.layout_transnotif_row, null);
            }

            // Get the Notifications object, and if it isn't null, populate the layout with its data
            final Notifications t = getItem(position);

            if (t != null) {
                TextView textInfo = (TextView) v.findViewById(R.id.label_description);
                final Button button_one = (Button) v.findViewById(R.id.button_notification1);
                final Button button_two = (Button) v.findViewById(R.id.button_notification2);

                UserRepo userRepo = new UserRepo(v.getContext());

                if (t.getType().equals("REQUEST")) { // and gets status
                    textInfo.setText(userRepo.getName(t.getRequestID()) + " requests food");
                    button_one.setText("Accept");
                    button_two.setText("Reject");
                    if (!t.isActive()) {
                        button_one.setEnabled(false);
                        button_two.setEnabled(false);
                    }
                } else if (t.getType().equals("ACCEPT")) {
                    UserRepo up = new UserRepo(getActivity().getApplicationContext());
                    textInfo.setText(up.getName(t.getProvider()) + " has accepted your request");
                    button_one.setVisibility(View.INVISIBLE);
                    button_two.setVisibility(View.INVISIBLE);
                } else if (t.getType().equals("REVIEW")) {
                    textInfo.setText("A rating and review has been requested");
                    button_one.setText("Review");
                    button_two.setVisibility(View.INVISIBLE);
                    if (!t.isActive()) {
                        button_one.setEnabled(false);
                    }
                }

                // on click listener for the "End Notification" button
                button_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (t.getType().equals("REQUEST")) {
                            String fbId = Profile.getCurrentProfile().getId();
                            UserRepo up = new UserRepo(getActivity().getApplicationContext());
                            final int userId = up.getId(fbId);

                            PostRepo postRepo = new PostRepo(getActivity().getApplicationContext());
                            postRepo.addNewAccepted(Integer.toString(t.getPostID()), Integer.toString(userId));

                            button_one.setEnabled(false);
                            button_two.setEnabled(false);

                            Transaction transaction = new Transaction(postRepo.getProviderId(t.getPostID()),
                                    t.getRequestID(), postRepo.getLocation(t.getPostID()), t.getPostID());
                            TransactionRepo tr = new TransactionRepo(getApplicationContext());
                            transaction.setStatus("OPEN");
                            tr.insert(transaction);

                            NotificationsRepo notificationsRepo = new NotificationsRepo(getActivity().getApplicationContext());
                            notificationsRepo.insertAccepted(t.getRequestID(), t.getPostID(), userId);
                            notificationsRepo.setInactive(Integer.toString(t.getId()));

                        } else {
//                            Intent i = new Intent(getApplicationContext(), RatingReviewActivity.class);
//                            i.putExtra("UserRatingId", t.getRequestID());
//                            startActivity(i);
                            Log.d("figure", Integer.toString(t.getRequestID()));
                            NotificationsRepo notificationsRepo = new NotificationsRepo(getActivity().getApplicationContext());
                            notificationsRepo.setInactive(Integer.toString(t.getId()));
                            button_one.setEnabled(false);

                            ((MainActivity) getActivity()).goToFragment(0, t.getProvider(), -1);

                        }
                    }
                });

                button_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        NotificationsRepo notificationsRepo = new NotificationsRepo(getActivity().getApplicationContext());
                        notificationsRepo.setInactive(Integer.toString(t.getId()));
                        button_one.setEnabled(false);
                        button_two.setEnabled(false);
                        // make notification inactive
                        // turns off buttons
                        // changes status
                    }
                });

            }

            return v;
        }
    }

}
