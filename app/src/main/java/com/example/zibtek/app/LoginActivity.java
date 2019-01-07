package com.example.zibtek.app;

import com.appolica.flubber.Flubber;
import com.sinch.android.rtc.SinchError;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements SinchService.StartFailedListener {

    private Button mLoginButton,Kick_button,SignUp;
  //  private String mLoginName;
   private EditText mLoginName,mLoginPassword;
    private ProgressDialog mSpinner;
    public static String userName;
    DatabaseHelper helper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

     //  mLoginName="kumar";
     //   getSinchServiceInterface().startClient(mLoginName);
//        getSinchServiceInterface().startClient(mLoginName);

        mLoginName = (EditText) findViewById(R.id.loginName);
        userName=mLoginName.getText().toString();
        Kick_button=(Button) findViewById(R.id.Kick);
        mLoginButton = (Button) findViewById(R.id.loginButton);
//        mLoginButton.setEnabled(false);
        SignUp=(Button)findViewById(R.id.loginSignUp);
        mLoginPassword=(EditText)findViewById(R.id.loginPassword);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked();
            }
        });
       /* Intent intent = new Intent ( LoginActivity.this, IncomingCallScreenActivity.class );
        intent.putExtra ( "TextBox", mLoginName.getText().toString() );
        startActivity(intent);*/
       /*if(userName.contains("colton")){
           Kick_button.setVisibility(View.GONE);
       }
*/
       startAnimations();


       SignUp.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent ( getApplicationContext(), Signup.class );
               startActivity(intent);
           }
       });



    }

    private void startAnimations() {

        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_RIGHT)
                .duration(1000)
                .createFor(mLoginName)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_RIGHT)
                .duration(1200)
                .createFor(mLoginPassword)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_UP)
                .duration(1300)
                .createFor(mLoginButton)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_UP)
                .duration(1400)
                .createFor(SignUp)
                .start();

        ImageView mmc=findViewById(R.id.imageMmc);
        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_DOWN)
                .duration(1500)
                .createFor(mmc)
                .start();
    }


    @Override
    protected void onServiceConnected() {
        mLoginButton.setEnabled(true);
        getSinchServiceInterface().setStartListener(this);
    }

    @Override
    protected void onPause() {
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
    }

    @Override
    public void onStarted() {
     //  openPlaceCallActivity();
    }

    private void loginClicked() {
        String userName = mLoginName.getText().toString();
        String pass=mLoginPassword.getText().toString();

        String password=helper.searchPass(userName);
        if(pass.equals(password)){
            if (userName.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                return;
            }

            if (!userName.equals(getSinchServiceInterface().getUserName())) {
                getSinchServiceInterface().stopClient();
            }

            if (!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(userName);
                //showSpinner();
            } else {
                openPlaceCallActivity();
            }


        }
        else {
            Toast temp=Toast.makeText(LoginActivity.this,"Username and Password don't match",Toast.LENGTH_SHORT);
            temp.show();
        }



      // String userName = mLoginName;

    /*    if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }

        if (!userName.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userName);
            showSpinner();
        } else {
            openPlaceCallActivity();
        }*/
    }

    private void openPlaceCallActivity() {
        Intent mainActivity = new Intent(this, PlaceCallActivity.class);
        startActivity(mainActivity);
    }

    private void showSpinner() {
        mSpinner = new ProgressDialog(this);
        mSpinner.setTitle("Logging in");
        mSpinner.setMessage("Please wait...");
        mSpinner.show();
    }
}
