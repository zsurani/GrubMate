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

/**
 * Created by caseyklecan on 10/14/17.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    private Context context;

    public PostAdapter(Context context, int textViewResourceId, Context current) {
        super(context, textViewResourceId);
        context = current;
    }

    public PostAdapter(Context context, int textViewResourceId, Context current, List<Post> items) {
        super(context, textViewResourceId, items);
        context = current;
    }
    /*

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context.getApplicationContext());
            v = vi.inflate(R.layout.layout_transaction_row, null);
        }

        Post t = getItem(position);

        if (t != null) {

            TextView postName = (TextView) v.findViewById(R.id.label_post_name);
            TextView postDesc = (TextView) v.findViewById(R.id.label_post_description);
            Button requestButton = (Button) v.findViewById(R.id.button_request);

            // Get data from transaction to put into row

                /* TODO once repo classes are set up
                    get PostRepo
                    find post info (name, description, times, tags, categories) with ID
                    if (already requested) disable request button
                 */
    
            // TODO delete dummy data
    /*
            String name = getResources().getString(R.string.text_transaction_name);

            postName.setText(String.format(name, "Casey", "Mexican Food", "Shivangi"));
            postDesc.setText(String.format(getResources().getString(R.string.text_transaction_status), status));
            requestButton.setText("Request");
>>>>>>> b958d746077b2390f56373b5900b76ae9c4467b7

        }

        return v;

    }
    */
}
