package com.usc.zsurani.grubmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Profile;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by caseyklecan on 10/14/17.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    private Context context;

    public PostAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public PostAdapter(Context context, int textViewResourceId, List<Post> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getApplicationContext());
            v = vi.inflate(R.layout.layout_post_row, null);
        }

        final Post t = getItem(position);

        if (t != null) {
            TextView postName = (TextView) v.findViewById(R.id.label_post_name);
            TextView postDesc = (TextView) v.findViewById(R.id.label_post_description);
            Button fullButton = (Button) v.findViewById(R.id.button_request);

                /* TODO once repo classes are set up
                    if (already requested) disable request button
                 */

            postName.setText(t.getFood());
            postDesc.setText(t.getDescription());

            fullButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context, ViewPostActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("postID", t.getId());
                    context.startActivity(i);

                }
            });

        }

        return v;

    }

}
