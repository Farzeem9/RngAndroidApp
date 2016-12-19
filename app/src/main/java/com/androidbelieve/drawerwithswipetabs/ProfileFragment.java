package com.androidbelieve.drawerwithswipetabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment {
    private ProfilePictureView profilePictureView;
    private String userID;
    private String pid,name,email,contact;
    private TextView info,info_id,info_mail;
    private SharedPreferences sharedPreferences;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(getContext());
                alertbox.setTitle("Confirmation");
                alertbox.setMessage("Are you sure you want to delete your account?");
                alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertbox.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertbox.show();
            }
        });

        sharedPreferences=getActivity().getSharedPreferences("LOG", Context.MODE_PRIVATE);
        info = (TextView) view.findViewById(R.id.fb_name);
        profilePictureView = (ProfilePictureView) view.findViewById(R.id.fb_pic);
        info_id=(TextView) view.findViewById(R.id.fb_mobile);
        info_mail=(TextView) view.findViewById(R.id.fb_email);
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //Toast.makeText(getActivity(), "Manny", Toast.LENGTH_SHORT).show();
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
        Button logout=(Button)view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                sharedPreferences.edit().clear().commit();
                NotificationReceiver.cancelAlarm(getActivity().getApplicationContext());
                getActivity().finish();
            }
        });
        new GenericAsyncTask(getContext(), Config.link + "contact.php?pid=" + pid, "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                String num = (String) output;
                info_id.setText(num);
            }
        }).execute();
        //Log.v("contact",num);
        return view;
    }
}