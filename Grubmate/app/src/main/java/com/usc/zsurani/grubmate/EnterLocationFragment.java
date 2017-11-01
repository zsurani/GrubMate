package com.usc.zsurani.grubmate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EnterLocationFragment extends Fragment {
    private static final String ARG_PARAM1 = "enterlocation.userid";
    private static final String ARG_PARAM2 = "enterlocation.postid";

    private EditText location;
    private Button send_button;

    private int postId;
    private int userId;


    public EnterLocationFragment() {
        // Required empty public constructor
    }
    public static EnterLocationFragment newInstance(int param1, int param2) {
        EnterLocationFragment fragment = new EnterLocationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_enter_location, null);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        postId = getArguments().getInt(ARG_PARAM2);
        userId = getArguments().getInt(ARG_PARAM1);

        send_button = (Button) v.findViewById(R.id.send_button);
        location = (EditText) v.findViewById(R.id.location_text);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sends notification to provider about receiving a request
                PostRepo pr = new PostRepo(getActivity().getApplicationContext());
                Notifications n = new Notifications(postId, userId, pr.getProviderId(postId),
                        location.getText().toString(), "REQUEST");
                NotificationsRepo nr = new NotificationsRepo(getActivity().getApplicationContext());
                nr.insertRequest(n);

                ((MainActivity) getActivity()).goToFragment(2, postId, -1);
            }
        });

        return v;
    }

}
