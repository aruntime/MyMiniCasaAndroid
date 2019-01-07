package com.example.zibtek.app;

import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.net.*;




public class  CallScreenActivity extends BaseActivity {

    static final String TAG = CallScreenActivity.class.getSimpleName();

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;

    private String mCallId;

    private TextView mCallDuration;
    private TextView mCallState;
    private TextView mCallerName;



    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            CallScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callscreen);

        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = (TextView) findViewById(R.id.callDuration);
        mCallerName = (TextView) findViewById(R.id.remoteUser);
        mCallState = (TextView) findViewById(R.id.callState);
        Button endCallButton = (Button) findViewById(R.id.hangupButton);
        Button addCallButton = (Button) findViewById(R.id.addcallButton);



        addCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                POSTStringAndJSONRequest();

//                Intent callScreen = new Intent(this, CallScreenActivity.class);
//                callScreen.putExtra(SinchService.CALL_ID, callId);
//                startActivity(callScreen);


//                Intent intent = new Intent(view.getContext(), PlaceCallActivity.class);
//                view.getContext().startActivity(intent);}

            }
        });

        endCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endCall();
            }
        });


       // Button dropCallButton = (Button)findViewById(R.id.wmute);
      //  dropCallButton.setOnClickListener();


        //final boolean canMerge = activeCall.can(Capabilities.MERGE_CALLS);
    }


    private void POSTStringAndJSONRequest() {
       // Rl2.setVisibility(View.VISIBLE);
        String url = "https://callingapi.sinch.com/v1/callouts";

        //      create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        //      JSONObject jsonBody = new JSONObject("{\"message\": \"Hello\"}");
        //      String JSON_STRING ="{\"method\":\"conferenceCallout\",\"conferenceCallout\":{\"cli\":\"+919986351005\",\"destination\":{\"type\": \"number\",\"endpoint\": \"+919986351005\"},\"domain\": \"pstn\",\"custom\": \"customData\",\"locale\": \"en-US\",\"greeting\": \"Welcome to my conference\",\"conferenceId\": \"myConference\",\"enableAce\": false,\"enableDice\": false}}";

        String JSON_STRING = "{\"method\":\"conferenceCallout\",\"conferenceCallout\":{\"destination\":{\"type\": \"username\",\"endpoint\": \"cardcollector\"},\"domain\": \"mxp\",\"custom\": \"customData\",\"locale\": \"en-US\",\"greeting\": \"Welcome to my conference\",\"conferenceId\": \"myConference\",\"enableAce\": true,\"enableDice\": false}}";


        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject(JSON_STRING);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //now handle the response
                // Toast.makeText( response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                //  Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("x-vacationtoken", "secret_token");
//                params.put("content-type", "application/json");
                params.put("Authorization", "basic YXBwbGljYXRpb25cZTE1ZjBkZTctYzc5OC00NzVhLThkYTUtZWFmMzkxYTA2MmU1OkllU3NZZVVqS2tLekFiZGxyaEZTVUE9PQ==");
                params.put("Content-Type", "application/json");
                return params;
            }
        };

// Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);
    }




    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.addCallListener(new SinchCallListener());
            mCallerName.setText(call.getRemoteUserId());
            mCallState.setText(call.getState().toString());
        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mDurationTask.cancel();
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }

    private String formatTimespan(int totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            mCallDuration.setText(formatTimespan(call.getDetails().getDuration()));
        }
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended. Reason: " + cause.toString());
            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            String endMsg = "Call ended: " + call.getDetails().toString();
            Toast.makeText(CallScreenActivity.this, endMsg, Toast.LENGTH_LONG).show();
            endCall();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            mCallState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

    }
}
