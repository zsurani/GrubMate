package com.usc.zsurani.grubmate.activity_and_fragment;


import android.content.Context;
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
import com.usc.zsurani.grubmate.MainActivity;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.base_classes.Notifications;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.Transaction;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.NotificationsRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.TransactionRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madison on 10/15/17.
 */

public class TransactionHistoryFragment extends Fragment {

    private ListView transactionList;

    private List<Transaction> transactionData;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_transaction_history, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        transactionList = (ListView) v.findViewById(R.id.list_transaction_hist) ;

        /* TODO once TransactionRepo etc. is set up (should be done)
          TransactionRepo repo = new TransactionRepo(getApplicationContext()); // not sure if this context is right
          // get list of transactions here and then populate the listview with those transactions
        */

        // TODO remove dummy list
//        List<Transaction> dummyList= new ArrayList<Transaction>();
//        Transaction t = new Transaction(0, 0, "King Hall", -1);
//        t.setStatus("Accepted");
//        dummyList.add(t);

        if (getTransactions().size() > 0) {
            ((TextView) v.findViewById(R.id.text_empty_trans)).setVisibility(View.GONE);
        }

        transactionList.setAdapter(new TransactionHistoryFragment.TransactionAdapter(getContext(), R.layout.layout_transaction_row, getTransactions()));

    }

    private List<Transaction> getTransactions(){
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getContext());
        final int userId = up.getId(fbId);

        TransactionRepo tr = new TransactionRepo(getContext());
        List<Transaction> transList = new ArrayList<>();

        List<String> transactions = tr.getTransactionsId(userId);
        for (String id : transactions) {
            transList.add(tr.getTransaction(Integer.valueOf(id)));
        }

        return transList;
    }


    private class TransactionAdapter extends ArrayAdapter<Transaction> {
        Integer postId;
        Integer requesterId;
        Integer providerId;
        TransactionRepo tr;


        public TransactionAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public TransactionAdapter(Context context, int textViewResourceId, List<Transaction> items) {
            super(context, textViewResourceId, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.layout_transaction_row, null);
            }

            final Transaction t = getItem(position);

            if (t != null) {
                tr = new TransactionRepo(getContext());
                TextView transactName = (TextView) v.findViewById(R.id.label_transaction_name);
                TextView transactStatus = (TextView) v.findViewById(R.id.label_transaction_status);
                final Button requestButton = (Button) v.findViewById(R.id.button_transaction);
                final Button rateButton = (Button) v.findViewById(R.id.button_rate);


                // Get data from transaction to put into row
                providerId = t.getProviderID();
                requesterId = t.getRequesterID();
                postId = t.getPostID();
                String status = t.getStatus();


                /*
                    gets provider, requester
                 */
                UserRepo ur = new UserRepo(getContext());
                final String provider = ur.getName(providerId);
                final String requester = ur.getName(requesterId);

                if (!Profile.getCurrentProfile().getName().equals(provider)) {
                    requestButton.setEnabled(false);
                }

                PostRepo pr = new PostRepo(getContext());
                final Post post = pr.getPost(postId);
                String food = post.getFood();

                String name = getResources().getString(R.string.text_transaction_name);
                transactName.setText(String.format(name, requester, food, provider));
                transactStatus.setText(String.format(getResources().getString(R.string.text_transaction_status), status));

                switch (status) {
                    case "OPEN":
                        requestButton.setEnabled(true);
                        rateButton.setEnabled(false);
                        break;
                    case "CLOSED":
                        requestButton.setEnabled(false);
                        rateButton.setEnabled(true);
                        break;
                }

                requestButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tr.updateStatus(t.getId(), "CLOSED");
                        requestButton.setEnabled(false);
                        Notifications n1 = new Notifications(postId, requesterId,
                                providerId, "REVIEW");
                        Notifications n2 = new Notifications(postId, providerId,
                                requesterId, "REVIEW");
                        NotificationsRepo nr = new NotificationsRepo(getContext());
                        nr.insertReviewRequest(n1);
                        nr.insertReviewRequest(n2);
                    }
                });

                rateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Profile.getCurrentProfile().getName().equals(provider)) {
                            ((MainActivity) getActivity()).goToFragment(0, requesterId, -1);
                        } else {
                            ((MainActivity) getActivity()).goToFragment(0, providerId, -1);
                        }
                    }
                });


            }
            return v;
        }
    }
}
