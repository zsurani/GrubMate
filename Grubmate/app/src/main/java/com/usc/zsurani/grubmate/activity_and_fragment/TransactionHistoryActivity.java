package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.base_classes.Transaction;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.TransactionRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private ListView transactionList;

    private List<Transaction> transactionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        transactionList = (ListView) findViewById(R.id.active_transaction_hist) ;

        /* TODO once TransactionRepo etc. is set up
          TransactionRepo repo = new TransactionRepo(getApplicationContext()); // not sure if this context is right
          // get list of transactions here and then populate the listview with those transactions
        */
        TransactionRepo repo = new TransactionRepo(getApplicationContext());

        // TODO remove dummy list
        List<Transaction> dummyList= new ArrayList<Transaction>();
        Transaction t = new Transaction(0, 0, "King Hall", -1);
        t.setStatus("Accepted");
        dummyList.add(t);

        transactionList.setAdapter(new TransactionAdapter(getApplicationContext(), R.layout.layout_transaction_row, dummyList));

    }

    private class TransactionAdapter extends ArrayAdapter<Transaction> {

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
                vi = LayoutInflater.from(getApplicationContext());
                v = vi.inflate(R.layout.layout_transaction_row, null);
            }

            Transaction t = getItem(position);

            if (t != null) {
                TextView transactName = (TextView) v.findViewById(R.id.label_transaction_name);
                TextView transactStatus = (TextView) v.findViewById(R.id.label_transaction_status);
                Button requestButton = (Button) v.findViewById(R.id.button_transaction);

                // Get data from transaction to put into row
                Integer providerId = t.getProviderID();
                Integer requesterId = t.getRequesterID();
                int postId = t.getPostID();
                String status = t.getStatus();


                UserRepo ur = new UserRepo(getApplicationContext());
                String provider = ur.getName(providerId);
                String requester = ur.getName(requesterId);

                PostRepo pr = new PostRepo(getApplicationContext());
                final Post post = pr.getPost(postId);
                String food = pr.getFood(Integer.toString(postId));

                String name = getResources().getString(R.string.text_transaction_name);
                transactName.setText(String.format(name, provider, food, requester));
                transactStatus.setText(String.format(getResources().getString(R.string.text_transaction_status), status));

                // TODO delete dummy data


//                transactName.setText(String.format(name, "Casey", "Mexican Food", "Shivangi"));
//                transactStatus.setText(String.format(getResources().getString(R.string.text_transaction_status), status));

                switch (status) {
                    case "Accepted":
                        requestButton.setEnabled(false);
                        break;
                    case "Denied":
                        requestButton.setEnabled(false);
                        break;
                    case "Pending":
                        requestButton.setEnabled(true);
                        // change to cancel?
                        break;
                }

            }

            return v;
        }
    }
}
