package com.androidbelieve.drawerwithswipetabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FirstActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Most easiest Fucking way to generate hashkey
         try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md;

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("hash key", something);
            }
        }
        catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            Log.e("name not found", e1.toString());
        }

        catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            Log.e("no such an algorithm", e.toString());
        }
        catch (Exception e){
            Log.e("exception", e.toString());
        }
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences=getApplicationContext().getSharedPreferences("LOG", Context.MODE_PRIVATE);
        String abc=sharedPreferences.getString("abcxyz",null);
        if(abc!=null) {
            abc = abc.trim();
        }
        setContentView(R.layout.activity_first);
        if(isLogin()&&abc!=null){
            String pid=AccessToken.getCurrentAccessToken().getUserId();

            EncryptDecrypt encryptDecrypt=null;
            try {
                encryptDecrypt=new EncryptDecrypt("kthiksramAndroidDevs");
                String temp=encryptDecrypt.encrypt(pid);
                temp=temp.trim();
                if(temp.equals(abc.trim()))
                {
                    startActivity(new Intent(FirstActivity.this,MainActivity.class));
                    finish();
                }
                else
                {
                    Log.v("ogging out","okay"+this.getClass().getSimpleName());
                    LoginManager.getInstance().logOut();
                    final SharedPreferences tempSp= getSharedPreferences("NOTI",MODE_PRIVATE);
                    tempSp.edit().clear().apply();
                }
            } catch (Exception e) {
                e.printStackTrace();
                AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
                alertbox.setTitle("Error");
                alertbox.setMessage("There was am error while decoding Login parameters\nPlease notify the developers of this");
                alertbox.show();
            }
        }
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String fromAd=getIntent().getStringExtra("AdActivity");
                Intent i=new Intent(FirstActivity.this,LoginActivity.class);
                i.putExtra("AdActivity",fromAd);
                startActivity(i);
                finish();
                }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public static boolean isLogin()
    {
        
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
