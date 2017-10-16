package com.usc.zsurani.grubmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.facebook.Profile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import android.widget.ArrayAdapter;

public class NewsFeedActivity extends AppCompatActivity {

    private String[] posts;
    private ListView listFeed;

//    private NewsFeedAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        //listFeed = (ListView) findViewById(R.id.list_newsfeed);

        // TODO list & adapter
//        ArrayList<Post> posts = new ArrayList<Post>();
//        PostAdapter adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, posts);
//        listFeed.setAdapter(adapter);
//
        // TODO add Options menu to access other pages

    }
}

    /*
    private List<Post> getPostsList() {
        String fbId = Profile.getCurrentProfile().getId();
        PostRepo pr = new PostRepo(getApplicationContext());
        GroupRepo gr = new GroupRepo(getApplicationContext());
        UserRepo ur = new UserRepo(getApplicationContext());

        String userId = String.valueOf(ur.getId(fbId));
        ArrayList<Post> allPosts = pr.returnAllPosts();
        ArrayList<Post> postsUserCanView = new ArrayList<Post>();
        //Go through all posts
        for (int i = 0; i < allPosts.size(); i++){
            Post currPost = allPosts.get(i);
            Set<String> allGroupsInPost = currPost.getGroups();
            //Go through groups in post to see if user is in group
            for (String s : allGroupsInPost) {
                Group currGroup = gr.getGroup(s);
                if (currGroup.getOwnerId().equals(userId)){
                    postsUserCanView.add(currPost);
                    break;
                }
                else if(currGroup.getUsers().contains(userId)){
                    postsUserCanView.add(currPost);
                    break;
                }
            }

        }
        return postsUserCanView;
    }
    */

    /*
    * The custom adapter for the Notifications list view.
    private class NewsFeedAdapter extends ArrayAdapter<Post> {

        public NewsFeedAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public NewsFeedAdapter(Context context, int textViewResourceId, List<Post> items) {
            super(context, textViewResourceId, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            // If the View to convert doesn't exist, inflate a new one with the correct layout
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getApplicationContext());
                v = vi.inflate(R.layout.layout_notification_row, null);
            }

            // Get the Notifications object, and if it isn't null, populate the layout with its data
            final Post t = getItem(position);

            if (t != null) {
                TextView textName = (TextView) v.findViewById(R.id.label_post_name);
                TextView textInfo = (TextView) v.findViewById(R.id.label_post_description);
                Button buttonEnd = (Button) v.findViewById(R.id.button_more_information);

                String timeStart = t.getBeginTime();
                String timeEnd = t.getEndTime();
                String tags = t.getTags().toString();
                if (tags.length() > 0) {
                    tags = tags.substring(1, tags.length() - 1);
                } else {
                    tags = "none";
                }
                String categories = t.getCategory().toString();
                if (categories.length() > 0) {
                    categories = categories.substring(1, categories.length()-1);
                } else {
                    categories = "none";
                }

                textName.setText(t.getTitle());
                textInfo.setText(String.format(getResources().getString(R.string.text_food_name), t.getTitle()));
                textInfo.setText(String.format(getResources().getString(R.string.text_post_description), timeStart, timeEnd, tags, categories));

                //if time is passed, button is disabled; else it's enabled
                DateFormat df = new SimpleDateFormat("hh:mm a");
                Date end;
                try {
                    end = df.parse(timeEnd);
                } catch (Exception e) {
                    end = Calendar.getInstance().getTime();
                }
                Date now = Calendar.getInstance().getTime();

                if (now.before(end)) {
                    buttonEnd.setEnabled(false);
                } else {
                    buttonEnd.setEnabled(true);
                }

                // on click listener for the "next page" button
                buttonEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PostRepo repo = new PostRepo(getApplicationContext());
                        Intent intent = new Intent(NewsFeedActivity.this, ViewPostActivity.class);
                        startActivity(intent);
                        //intent.putExtra("currPost", t.getId());

                    }
                });
            }

            return v;
        }
    }
    */

