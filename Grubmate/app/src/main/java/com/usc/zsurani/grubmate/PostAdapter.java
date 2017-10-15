package com.usc.zsurani.grubmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by caseyklecan on 10/14/17.
 */

public class PostAdapter extends ArrayAdapter<Post> {


    public PostAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PostAdapter(Context context, int textViewResourceId, List<Post> items) {
        super(context, textViewResourceId, items);
    }
    /*

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getApplicationContext());
            v = vi.inflate(R.layout.layout_transaction_row, null);
            // TODO delete this log line
            Log.d("TRANSACTION HISTORY", "inflated transaction row to view");
        }

        Post t = getItem(position);

        if (t != null) {

            TextView postName = (TextView) v.findViewById(R.id.label_post_name);
            TextView postDesc = (TextView) v.findViewById(R.id.label_post_description);
            Button requestButton = (Button) v.findViewById(R.id.button_request);

            // Get data from transaction to put into row

                /* TODO once repo classes are set up
                    get UserRepo
                    find provider name with ID
                    find requester name with ID

                    get PostRepo
                    find post info with ID
                 */

            // TODO delete dummy data
//            String name = getResources().getString(R.string.text_transaction_name);
    /*
            String name = getResources().getString(R.string.text_transaction_name);

//            postName.setText(String.format(name, "Casey", "Mexican Food", "Shivangi"));
//            postDesc.setText(String.format(getResources().getString(R.string.text_transaction_status), "status"));
            requestButton.setText("Request");

        }

        return v;

    }
    */
}
