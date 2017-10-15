package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NewsFeedActivity extends AppCompatActivity {

    private String [] posts;
    private ListView listFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        listFeed = (ListView) findViewById(R.id.list_newsfeed);

        // TODO list & adapter
        ArrayList<Post> posts = new ArrayList<Post>();
        PostAdapter adapter = new PostAdapter(getApplicationContext(), R.layout.layout_post_row, posts);
        listFeed.setAdapter(adapter);

        // TODO add Options menu to access other pages

    }

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




}
