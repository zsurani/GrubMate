package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.Context;
import android.content.Intent;
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
import com.usc.zsurani.grubmate.base_classes.Notifications;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.NotificationsRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Madison on 10/15/17.
 */

public class MyNotificationFragment extends Fragment {
    private ListView notificationList;
    private Button createNotification;
    private MyNotificationFragment.NotificationAdapter adapter;
    private String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_my_notification, container, false);

        adapter = new MyNotificationFragment.NotificationAdapter(getContext(), R.layout.layout_notification_row, getSubscriptionList());
        notificationList = v.findViewById(R.id.list_notifications);
        notificationList.setAdapter(adapter);

        if (getSubscriptionList().size() > 0) {
            ((TextView) v.findViewById(R.id.text_empty_notif)).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) v.findViewById(R.id.text_empty_notif)).setVisibility(View.VISIBLE);

        }

        createNotification =  v.findViewById(R.id.button_add_notification);

        createNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start new page for creating a notification
                Intent i = new Intent(getActivity(), CreateNotificationActivity.class);
                ((TextView) v.findViewById(R.id.text_empty_notif)).setVisibility(View.INVISIBLE);
                startActivityForResult(i, 0);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View v = getView();

        refresh();

        if (getSubscriptionList().size() > 0) {
            ((TextView) v.findViewById(R.id.text_empty_notif)).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) v.findViewById(R.id.text_empty_notif)).setVisibility(View.VISIBLE);

        }

        createNotification =  v.findViewById(R.id.button_add_notification);

        createNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start new page for creating a notification
                ((TextView) v.findViewById(R.id.text_empty_notif)).setVisibility(View.INVISIBLE);
                Intent i = new Intent(getActivity(), CreateNotificationActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }

    private List<Notifications> getSubscriptionList() {
        String fbId;
        UserRepo up = new UserRepo(getContext());
        if (Profile.getCurrentProfile() == null) {
            fbId = up.getProfile().getId();
        } else {
            fbId = Profile.getCurrentProfile().getId();
        }
        final int userId = up.getId(fbId);

        List<Notifications> notifList = new ArrayList<>();

        NotificationsRepo repo = new NotificationsRepo(getActivity().getApplicationContext());
        List<String> notifStrings = repo.getNotifications(userId);
        for (String id : notifStrings) {
            Notifications n = repo.getNotification(Integer.valueOf(id));
            if (n.getType() != null && n.getType().equals(Notifications.TYPE_SUBSCRIPTION)) {
                n.setId(Integer.valueOf(id));
                notifList.add(n);
            }
        }

        return notifList;
    }

    public void refresh() {
        if (getView() != null) {
            adapter = new MyNotificationFragment.NotificationAdapter(getContext(), R.layout.layout_notification_row, getSubscriptionList());
            notificationList = getView().findViewById(R.id.list_notifications);
            notificationList.setAdapter(adapter);
        }
    }

    /*
     * The custom adapter for the Notifications list view.
     */
    public class NotificationAdapter extends ArrayAdapter<Notifications> {
        private Context context;

        public NotificationAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.context = context;
        }

        public NotificationAdapter(Context context, int textViewResourceId, List<Notifications> items) {
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
                Button editNotif = (Button) v.findViewById(R.id.edit_notification);

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

                //button for editing a notification
                editNotif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int notifID = t.getId();
                        Intent i = new Intent(getActivity().getApplicationContext(), CreateNotificationActivity.class);
                        i.putExtra("notifID", notifID);
                        startActivity(i);
                    }
                });

                Date now = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("hh:mm a");

                if (!t.isActive()) {
                    buttonEnd.setEnabled(false);
                    buttonEnd.setText("Notification Cancelled");
                } else {
                    buttonEnd.setEnabled(true);
                    buttonEnd.setText("Cancel Notification");
                }
            }

            return v;
        }
    }

}
