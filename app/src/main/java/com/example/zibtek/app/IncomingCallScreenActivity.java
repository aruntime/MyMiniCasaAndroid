package com.example.zibtek.app;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomingCallScreenActivity extends BaseActivity   {

    static final String TAG = IncomingCallScreenActivity.class.getSimpleName();
    private static final String TAG9 = "Mylogs";
    private EditText mLoginName;
    private String mCallId;
    private AudioPlayer mAudioPlayer;
    private Button Speaker,Answer,Decline,Unspeaker;
    private Button Collector_button,Kick_button, Gray_Button, mute_Button, unmute_Button, Kick2_button;
    private TextView loggedInName;
    public static final String EXTRA_IS_CORRECT = "extra_is_correct";
    public AudioManager mAudioManager;
    private RelativeLayout ll_agent;
    private RelativeLayout ll2_card;
    private String client;
    private TextView userName;

//   String strParsedValue=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming);

        Button answer = (Button) findViewById(R.id.answerButton);
        answer.setOnClickListener(mClickListener);
        Button decline = (Button) findViewById(R.id.declineButton);
        decline.setOnClickListener(mClickListener);

        //loggedInName=(TextView)findViewById(R.id.loggedInName);
        Collector_button=(Button)findViewById(R.id.cardButton);
        Collector_button.setOnClickListener(mClickListener);
        Kick_button=(Button) findViewById(R.id.Kick);
        Kick_button.setOnClickListener(mClickListener);

        Kick2_button=(Button) findViewById(R.id.Kick2);
        Kick2_button.setOnClickListener(mClickListener);

        Gray_Button=(Button) findViewById(R.id.cardButtongray);
        Gray_Button.setVisibility(View.GONE);

        mute_Button=(Button)findViewById(R.id.mute);

        unmute_Button=(Button)findViewById(R.id.unmute);


        mLoginName = (EditText) findViewById(R.id.loginName);

        Answer=(Button)findViewById(R.id.answerButton);
        Decline=(Button)findViewById(R.id.declineButton);
        mAudioPlayer = new AudioPlayer(this);
        mAudioPlayer.playRingtone();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);

        ll_agent=(RelativeLayout)findViewById(R.id.ll);

       // ll_agent.setOnClickListener(mClickListener);
        ll2_card=(RelativeLayout)findViewById(R.id.ll2);
       // ll2_card.setOnClickListener(mClickListener);

     /*   Intent in = getIntent();
        String tv1= in.getExtras().getString("location");
        loggedInName.setText(tv1);*/
     /*   if(tv1.contains("colton")){
            Kick_button.setVisibility(View.GONE);
        }
        else if(tv1.contains("cardcollector")){
            Kick_button.setVisibility(View.VISIBLE);
        }*/

       /* if(LoginActivity.userName.equals("colton")){
            Kick_button.setVisibility(View.GONE);
        }*/

//        String location = getIntent().getStringExtra(SinchService.LOCATION);

        Speaker=(Button)findViewById(R.id.speaker);
        Unspeaker=(Button)findViewById(R.id.unspeaker);



    }

 /*   public void speakerOn(){

        Speaker.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Speaker.setVisibility(View.GONE);
                Unspeaker.setVisibility(View.VISIBLE);
                mAudioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                mAudioManager.setMode(AudioManager.MODE_IN_CALL);
                if (!mAudioManager.isSpeakerphoneOn()) {
                    mAudioManager.setSpeakerphoneOn(true);
                    mAudioManager.setMode(AudioManager.MODE_NORMAL);

                }
            }
        });



    }

    public void speakerOff(){

        Unspeaker.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
            Speaker.setVisibility(View.VISIBLE);
            Unspeaker.setVisibility(View.GONE);
           mAudioManager =  (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
           mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            mAudioManager.setSpeakerphoneOn(false);
        }
    });

}*/
    public void updateTextView(String c) {
        userName = (TextView)findViewById(R.id.loggedInName);
        userName.setText(c);
        Log.d("C==","++>"+c);

    }
    @Override
    protected void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);

        if (call != null) {
            call.addCallListener(new SinchCallListener());
            TextView remoteUser = (TextView) findViewById(R.id.remoteUser);
            remoteUser.setText(call.getRemoteUserId());
        } else {
           Log.e(TAG, "Started with invalid callId, aborting");
           finish();
        }
       // updateTextView("1757676878");
       /* userName.setText("");*/
        //String r="sunny";

        if(getSinchServiceInterface().getUserName().equals("jared")||getSinchServiceInterface().getUserName().equals("runtime")){
            //userName.setText("agent");
            ll_agent.setVisibility(View.VISIBLE);
            ll2_card.setVisibility(View.VISIBLE);
        }
        else{

          //  userName.setText(getSinchServiceInterface().getUserName());
          //  ll2_card.setVisibility(View.GONE);
        }
    }


//    private void answerClicked() {
//        mAudioPlayer.stopRingtone();
//        Call call = getSinchServiceInterface().getCall(mCallId);
//        if (call != null) {
//            try {
//
////                Intent intent = new Intent(this, CallScreenActivity.class);
////                intent.putExtra(SinchService.CALL_ID, mCallId);
////                startActivity(intent);
//
//                call.answer();
//
//            } catch (MissingPermissionException e) {
//                ActivityCompat.requestPermissions(this, new String[]{e.getRequiredPermission()}, 0);
//            }
//        } else {
//            finish();
//        }
//    }

    private void answerClicked() {
        getClientNumber(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("C==","+++>"+result);

                updateTextView(result);

                System.out.println("number:"+result);
            }
        });

        Speaker.setOnClickListener(mClickListener);
        Unspeaker.setOnClickListener(mClickListener);
        mute_Button.setOnClickListener(mClickListener);
        unmute_Button.setOnClickListener(mClickListener);
        mAudioPlayer.stopRingtone();
        Decline.setVisibility(View.VISIBLE);
        Answer.setVisibility(View.GONE);
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            try {
                //              Intent callScreen = new Intent(this, CallScreenActivity.class);
//                callScreen.putExtra(SinchService.CALL_ID, callId);
//                startActivity(callScreen);

//                  Intent mainActivity = new Intent(this, PlaceCallActivity.class);
//                   mainActivity.putExtra(EXTRA_IS_CORRECT, true);
//                  startActivity(mainActivity);


//                Log.e(TAG, "" + "answer button clicked");
//                Intent intent = new Intent(this, CallScreenActivity.class);
//                intent.putExtra(SinchService.CALL_ID, mCallId);
//                startActivity(intent);

//                setContentView(R.layout.callscreen);


                call.answer();//kumar cm


            } catch (MissingPermissionException e) {
                ActivityCompat.requestPermissions(this, new String[]{e.getRequiredPermission()}, 0);
            }
        } else {
            finish();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You may now answer the call", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "This application needs permission to use your microphone to function properly.", Toast
                    .LENGTH_LONG).show();
        }
    }

    private void declineClicked() {
        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
           Log.d(TAG, "Call ended, cause: " + cause.toString());
            mAudioPlayer.stopRingtone();
            finish();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

    }

    private void AddCardCollectorPostMethod() {

      //  Rl2.setVisibility(View.VISIBLE);
        String url = "https://callingapi.sinch.com/v1/callouts";

     //   System.out.println("ZZZZZZZZZZZZZZZZZZZZ    :   "+url);

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        //      JSONObject jsonBody = new JSONObject("{\"message\": \"Hello\"}");
        //      String JSON_STRING ="{\"method\":\"conferenceCallout\",\"conferenceCallout\":{\"cli\":\"+919986351005\",\"destination\":{\"type\": \"number\",\"endpoint\": \"+919986351005\"},\"domain\": \"pstn\",\"custom\": \"customData\",\"locale\": \"en-US\",\"greeting\": \"Welcome to my conference\",\"conferenceId\": \"myConference\",\"enableAce\": false,\"enableDice\": false}}";

        String JSON_STRING = "{\"method\":\"conferenceCallout\",\"conferenceCallout\":{\"destination\":{\"type\": \"username\",\"endpoint\": \"jared\"},\"domain\": \"mxp\",\"custom\": \"customData\",\"locale\": \"en-US\",\"greeting\": \"Welcome to my conference\",\"conferenceId\": \"myConference\",\"enableAce\": true,\"enableDice\": false}}";

        String JSON_STRING2 = "{\"method\":\"conferenceCallout\",\"conferenceCallout\":{\"destination\":{\"type\": \"username\",\"endpoint\": \"runtime\"},\"domain\": \"mxp\",\"custom\": \"customData\",\"locale\": \"en-US\",\"greeting\": \"Welcome to my conference\",\"conferenceId\": \"myConference\",\"enableAce\": true,\"enableDice\": false}}";


        JSONObject jsonBody = null;
        try {
            if(getSinchServiceInterface().getUserName().equals("colton")){
                jsonBody = new JSONObject(JSON_STRING);

            }
            else{
                jsonBody = new JSONObject(JSON_STRING2);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //now handle the response
                // Toast.makeText( response, Toast.LENGTH_SHORT).show();


            //    System.out.println("RRRRRRRRRRRR  :   " + response.toString());

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
                params.put("x-vacationtoken", "secret_token");
                params.put("content-type", "application/json");
                params.put("Authorization", "Basic MWIzMDRiMGYtMGNjMS00ZjUyLTg1MTgtY2YwOTk2MDY5ZjA0OlI4Nm5wdFFSVVVPbk9RLzNXRHlwMGc9PQ==");
                params.put("Content-Type", "application/json");
                return params;
            }
        };

// Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);


    }


    private void kickAgent(String kick) {
        //  Rl2.setVisibility(View.VISIBLE);

        String url = "https://callingapi.sinch.com/v1/conferences/id/myConference/"+kick+"";
     //   String dmo=url+""+kick;
     //   System.out.println("ZZZZ: "+url);

//request a json object response
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                     //   Toast.makeText($this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-vacationtoken", "secret_token");
                params.put("content-type", "application/json");
                params.put("Authorization", "Basic MWIzMDRiMGYtMGNjMS00ZjUyLTg1MTgtY2YwOTk2MDY5ZjA0OlI4Nm5wdFFSVVVPbk9RLzNXRHlwMGc9PQ==");
                params.put("Content-Type", "application/json");
                return params;
            }

        };

// Add the request to the queue


        Volley.newRequestQueue(this).add(dr);


    }

    private void getKickAgent(){
        String url = "https://callingapi.sinch.com/v1/conferences/id/myConference";

//request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //now handle the response
              //  Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
          //      Log.e(TAG9, "RRRRRRRRRRRR  :   ");


                // Retrieve the body of the Response
                try {
                    JSONObject jsonObject = new JSONObject(response);
                  //  JSONObject object = jsonObject.getJSONObject("participants");
                    JSONArray subArray = jsonObject.getJSONArray("participants");

              //  String strParsedValue=null;

                    for (int i = 1; i < subArray.length(); i++) {
                    if(i==1) {
                      String strParsedValue =subArray.getJSONObject(i).getString("callId").toString();

                    //    System.out.println("RRRRRRRRRRRR  :   "+strParsedValue);

                        kickAgent(strParsedValue);
                    }
                    }
               //   String strParsedValue =subArray.getJSONObject(2).getString("callId").toString();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
             //   Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        })
            {    //this is the part, that adds the header to the request
                @Override
                public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-vacationtoken", "secret_token");
                params.put("content-type", "application/json");
                params.put("Authorization", "Basic MWIzMDRiMGYtMGNjMS00ZjUyLTg1MTgtY2YwOTk2MDY5ZjA0OlI4Nm5wdFFSVVVPbk9RLzNXRHlwMGc9PQ==");
                params.put("Content-Type", "application/json");
                return params;
            }

            };
// Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void getKickCardCollector(){


        String url = "https://callingapi.sinch.com/v1/conferences/id/myConference";

//request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //now handle the response
                //  Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                Log.e(TAG9, "CCCCCCCC  :   ");


                // Retrieve the body of the Response
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //  JSONObject object = jsonObject.getJSONObject("participants");
                    JSONArray subArray = jsonObject.getJSONArray("participants");

                    //  String strParsedValue=null;

                    for (int i = 2; i < subArray.length(); i++) {
                        if(i==2) {
                            String strValue =subArray.getJSONObject(i).getString("callId").toString();

                            System.out.println("CCCCCCCCCC  :   "+strValue);

                            kickCardCollector(strValue);
                        }
                    }
                    //   String strParsedValue =subArray.getJSONObject(2).getString("callId").toString();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                //   Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        })
        {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-vacationtoken", "secret_token");
                params.put("content-type", "application/json");
                params.put("Authorization", "Basic MWIzMDRiMGYtMGNjMS00ZjUyLTg1MTgtY2YwOTk2MDY5ZjA0OlI4Nm5wdFFSVVVPbk9RLzNXRHlwMGc9PQ==");
                params.put("Content-Type", "application/json");
                return params;
            }

        };
// Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void kickCardCollector(String kick) {
        //  Rl2.setVisibility(View.VISIBLE);
        ll2_card.setVisibility(View.GONE);
        Gray_Button.setVisibility(View.GONE);
        Collector_button.setVisibility(View.VISIBLE);

        String url = "https://callingapi.sinch.com/v1/conferences/id/myConference/"+kick+"";
        //   String dmo=url+""+kick;
        System.out.println("ZZZZ: "+url);

//request a json object response
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //   Toast.makeText($this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-vacationtoken", "secret_token");
                params.put("content-type", "application/json");
                params.put("Authorization", "Basic MWIzMDRiMGYtMGNjMS00ZjUyLTg1MTgtY2YwOTk2MDY5ZjA0OlI4Nm5wdFFSVVVPbk9RLzNXRHlwMGc9PQ==");
                params.put("Content-Type", "application/json");
                return params;
            }

        };

// Add the request to the queue


        Volley.newRequestQueue(this).add(dr);


    }

    public interface VolleyCallback{
        void onSuccess(String result);


    }
    public void getClientNumber(final VolleyCallback volleyCallback){
        String url = "https://callingapi.sinch.com/v1/conferences/id/myConference";

//request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //now handle the response
                //  Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                //      Log.e(TAG9, "RRRRRRRRRRRR  :   ");


                // Retrieve the body of the Response
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //  JSONObject object = jsonObject.getJSONObject("participants");
                    JSONArray subArray = jsonObject.getJSONArray("participants");

                    //  String strParsedValue=null;

                    for (int i = 0; i < subArray.length(); i++) {

                        //Log.d("log","-->"+ subArray.getJSONArray(0));
                            String strParsedValue2 = subArray.getJSONObject(0).getString("cli").toString();
                        Log.d("log","-->"+ strParsedValue2);
                            System.out.println("RRRRRRRRRRRR  :   " + strParsedValue2);
                            volleyCallback.onSuccess(strParsedValue2);




                    }
                    //   String strParsedValue =subArray.getJSONObject(2).getString("callId").toString();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                //   Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        })
        {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-vacationtoken", "secret_token");
                params.put("content-type", "application/json");
                params.put("Authorization", "Basic MWIzMDRiMGYtMGNjMS00ZjUyLTg1MTgtY2YwOTk2MDY5ZjA0OlI4Nm5wdFFSVVVPbk9RLzNXRHlwMGc9PQ==");
                params.put("Content-Type", "application/json");
                return params;
            }

        };
// Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.answerButton:

                    if(getSinchServiceInterface().getUserName().equals("cardcollector")){
                        Collector_button.setVisibility(View.GONE);
                      //  ll2_card.setVisibility(View.VISIBLE);
                         ll_agent.setVisibility(View.VISIBLE);
                    }
                    else{
                        Collector_button.setVisibility(View.VISIBLE);
                    }

                    answerClicked();


                    break;
                case R.id.declineButton:
                    declineClicked();
                    break;
                case R.id.cardButton:
                    try {

                        AddCardCollectorPostMethod();
                        Collector_button.setVisibility(View.GONE);
                        Gray_Button.setVisibility(View.VISIBLE);
                        ll2_card.setVisibility(View.VISIBLE);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.Kick:
                    try {

                        getKickAgent();
                        ll_agent.setVisibility(View.GONE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.Kick2:
                    try {
                        getKickCardCollector();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.mute:
                    try{
                        mAudioManager = (AudioManager)getApplicationContext().getSystemService(AUDIO_SERVICE);

                        //  mAudioManager.setMicrophoneMute(true);
                        mAudioManager.setMicrophoneMute(true);


                        mute_Button.setVisibility(View.GONE);
                        unmute_Button.setVisibility(View.VISIBLE);

                    }catch(Exception e){}
                    break;

                case R.id.unmute:
                    try{
                        mAudioManager = (AudioManager)getApplicationContext().getSystemService(AUDIO_SERVICE);

                        mAudioManager.setMicrophoneMute(false);
                        mute_Button.setVisibility(View.VISIBLE);
                        unmute_Button.setVisibility(View.GONE);
                        //  getMuteTest();

                    }catch(Exception e){}
                    break;

                case R.id.speaker:
                    try {
                        Speaker.setVisibility(View.GONE);
                        Unspeaker.setVisibility(View.VISIBLE);
                        mAudioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                        mAudioManager.setMode(AudioManager.MODE_IN_CALL);
                        if (!mAudioManager.isSpeakerphoneOn()) {
                            mAudioManager.setSpeakerphoneOn(true);
                            mAudioManager.setMode(AudioManager.MODE_NORMAL);

                        }
                    }catch(Exception e){}
                    break;
                case R.id.unspeaker:
                    try {
                        Speaker.setVisibility(View.VISIBLE);
                        Unspeaker.setVisibility(View.GONE);
                        mAudioManager =  (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                        mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                        mAudioManager.setSpeakerphoneOn(false);

                    }catch(Exception e){}
                    break;
            }
        }
    };
}
