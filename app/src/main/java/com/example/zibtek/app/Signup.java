package com.example.zibtek.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {

    private Button SignUp;
    private EditText email,name,password,username;
    DatabaseHelper helper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignUp=(Button)findViewById(R.id.loginSignUp);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.loginSignUp) {
                    email = (EditText) findViewById(R.id.signupEmail);
                    name = (EditText) findViewById(R.id.signupName);
                    password = (EditText) findViewById(R.id.loginPassword);
                    username = (EditText) findViewById(R.id.signupUserName);

                    String namestr = name.getText().toString();
                    String eamilstr = email.getText().toString();
                    String unamestr = username.getText().toString();
                    String passstr = password.getText().toString();
                    //inserting details in database
                    Contacts c = new Contacts();
                    c.setEmail(eamilstr);
                    c.setName(namestr);
                    c.setPass(passstr);
                    c.setUname(unamestr);

                    helper.insertContacts(c);

                    if(namestr.length()>1){
                    Toast temp=Toast.makeText(Signup.this,"Account Created Sucessfully!",Toast.LENGTH_SHORT);
                    temp.show();}
                    Intent intent = new Intent ( getApplicationContext(), LoginActivity.class );
                    startActivity(intent);

                }



            }
        });
        }

/*   public void onSignUpClick(View v){

        if(v.getId()==R.id.loginSignUp){




        }


   }*/



}
