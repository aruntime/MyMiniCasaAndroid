package com.example.zibtek.app;


import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.calling.Call;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlaceCallActivity extends BaseActivity {

    private Button mCallButton;
    private EditText mCallName;
//    private Button Collector_button,Mute,Speaker,logout,Unmute,Unspeaker;
    private Button logout;
    private ImageView onHOLD,Mic1,Mic2,MMC;
    private TextView Kick1,Kick2,cc;
    RelativeLayout Rl,Rl2;
    private int _clicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      onHOLD=(ImageView)findViewById(R.id.ivonhold);
      Rl=(RelativeLayout)findViewById(R.id.ll);
      Rl2=(RelativeLayout)findViewById(R.id.ll2);
      Mic1=(ImageView)findViewById(R.id.mic);
      MMC=(ImageView)findViewById(R.id.imageMmc);
      Mic2=(ImageView)findViewById(R.id.mic2);
      Kick1=(TextView)findViewById(R.id.Kick);
      Kick2=(TextView)findViewById(R.id.kick2);
//      Mute=(Button)findViewById(R.id.mute);
//      Speaker=(Button)findViewById(R.id.speaker);
      logout=(Button)findViewById(R.id.Logout);
      cc=(TextView)findViewById(R.id.tvCC);
   //   Unmute=(Button)findViewById(R.id.unmute);
   //   Unspeaker=(Button)findViewById(R.id.unspeaker);

        mCallName = (EditText) findViewById(R.id.callName);
        mCallButton = (Button) findViewById(R.id.callButton);
        mCallButton.setEnabled(false);
        mCallButton.setOnClickListener(buttonClickListener);

        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(buttonClickListener);



        logout.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
//              Intent i=new Intent(getApplicationContext(),LoginActivity.class);
//              startActivity(i);
                finish();
            }
        });



    }


    @Override
    protected void onServiceConnected() {
        TextView userName = (TextView) findViewById(R.id.loggedInName);
        userName.setText(getSinchServiceInterface().getUserName());
        mCallButton.setEnabled(true);

        }

    private void stopButtonClicked() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().stopClient();
        }
    //    Speaker.setVisibility(View.GONE);
   //     Mute.setVisibility(View.GONE);
   //     Collector_button.setVisibility(View.GONE);
        logout.setVisibility(View.VISIBLE);
        //Rl.setVisibility(View.VISIBLE);
        finish();

    }

    private void callButtonClicked() {
        String userName = mCallName.getText().toString();

//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location lastLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        Double longitude = lastLoc.getLongitude();
//        Double latitude = lastLoc.getLatitude();


        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }


        try {
            Call call = getSinchServiceInterface().callUser(userName);
            // Call call = getSinchServiceInterface().callPhoneNumber("+46000000000");
            if (call == null) {
                // Service failed for some reason, show a Toast and abort
                Toast.makeText(this, "Service is not started. Try stopping the service and starting it again before "
                        + "placing a call.", Toast.LENGTH_LONG).show();
                return;
            }
            String callId = call.getCallId();

            //      dfc969f9-571a-4ac9-8f07-822324576564

            System.out.println("9999999999999999999999     : " + callId);

          /*  Intent callScreen = new Intent(this, CallScreenActivity.class);
            callScreen.putExtra(SinchService.CALL_ID, callId);

            startActivity(callScreen);
*/
        } catch (MissingPermissionException e) {
            ActivityCompat.requestPermissions(this, new String[]{e.getRequiredPermission()}, 0);
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You may now place a call", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "This application needs permission to use your microphone to function properly.", Toast
                    .LENGTH_LONG).show();
        }
    }


//    private void POSTStringAndJSONRequest() {
//        Rl2.setVisibility(View.VISIBLE);
//        String url = "https://callingapi.sinch.com/v1/callouts";
//
////create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
//        //      JSONObject jsonBody = new JSONObject("{\"message\": \"Hello\"}");
//        //      String JSON_STRING ="{\"method\":\"conferenceCallout\",\"conferenceCallout\":{\"cli\":\"+919986351005\",\"destination\":{\"type\": \"number\",\"endpoint\": \"+919986351005\"},\"domain\": \"pstn\",\"custom\": \"customData\",\"locale\": \"en-US\",\"greeting\": \"Welcome to my conference\",\"conferenceId\": \"myConference\",\"enableAce\": false,\"enableDice\": false}}";
//
//        String JSON_STRING = "{\"method\":\"conferenceCallout\",\"conferenceCallout\":{\"destination\":{\"type\": \"username\",\"endpoint\": \"cardcollector\"},\"domain\": \"mxp\",\"custom\": \"customData\",\"locale\": \"en-US\",\"greeting\": \"Welcome to my conference\",\"conferenceId\": \"myConference\",\"enableAce\": true,\"enableDice\": false}}";
//
//
//        JSONObject jsonBody = null;
//        try {
//            jsonBody = new JSONObject(JSON_STRING);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
////request a json object response
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                //now handle the response
//                // Toast.makeText( response, Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                //handle the error
//                //  Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//
//            }
//        }) {    //this is the part, that adds the header to the request
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> params = new HashMap<String, String>();
////                params.put("x-vacationtoken", "secret_token");
////                params.put("content-type", "application/json");
//                params.put("Authorization", "basic YXBwbGljYXRpb25cZTE1ZjBkZTctYzc5OC00NzVhLThkYTUtZWFmMzkxYTA2MmU1OkllU3NZZVVqS2tLekFiZGxyaEZTVUE9PQ==");
//                params.put("Content-Type", "application/json");
//                return params;
//            }
//        };
//
//// Add the request to the queue
//        Volley.newRequestQueue(this).add(jsonRequest);
//    }


    private OnClickListener buttonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.callButton:
                    callButtonClicked();
                    break;

                case R.id.stopButton:
                    stopButtonClicked();
                    break;

//                case R.id.cardButton:
//                    try {
//                        POSTStringAndJSONRequest();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;

            }
        }
    };
}
