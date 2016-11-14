package com.androidbelieve.drawerwithswipetabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment {
    private ProfilePictureView profilePictureView;
    private String userID;
    private String pid,name,email,contact;
    private TextView info,info_id,info_mail;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        info = (TextView) view.findViewById(R.id.fb_name);
        profilePictureView = (ProfilePictureView) view.findViewById(R.id.fb_pic);
        //info_id=(TextView)findViewById(R.id.info_name);
        info_mail=(TextView) view.findViewById(R.id.fb_email);
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Toast.makeText(getActivity(), "Manny", Toast.LENGTH_SHORT).show();
                            info.setText(object.getString("name"));
                            //info_id.setText("Hi, "+object.getString("id"));
                            info_mail.setText(object.getString("email"));

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
        userID= AccessToken.getCurrentAccessToken().getUserId();
        pid=userID;
        profilePictureView.setProfileId(userID);
        return view;
    }
}