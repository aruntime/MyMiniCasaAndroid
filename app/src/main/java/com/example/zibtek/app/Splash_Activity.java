package com.example.zibtek.app;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.appolica.flubber.Flubber;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;

import java.util.Objects;

public class Splash_Activity extends BaseActivity{

    ImageView myImageView,miniImageView,casaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        KenBurnsView kenBurnsView=findViewById(R.id.sa_kenburns_view);

        myImageView=findViewById(R.id.sa_my_ic);
        miniImageView=findViewById(R.id.sa_mini_ic);
        casaImageView=findViewById(R.id.sa_casa_ic);

        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_DOWN)
                .duration(2000)
                .createFor(myImageView)
                .start();

        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_UP)
                .duration(2000)
                .createFor(miniImageView)
                .start();

        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_RIGHT)
                .duration(2200)
                .createFor(casaImageView)
                .start();

//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Flubber.with()
//                        .animation(Flubber.AnimationPreset.SLIDE_DOWN)
//                        .duration(2000)
//                        .createFor(myImageView)
//                        .start();
//
//                Flubber.with()
//                        .animation(Flubber.AnimationPreset.SLIDE_UP)
//                        .duration(2000)
//                        .createFor(miniImageView)
//                        .start();
//
//                Flubber.with()
//                        .animation(Flubber.AnimationPreset.SLIDE_RIGHT)
//                        .duration(2200)
//                        .createFor(casaImageView)
//                        .start();
//
//            }
//        },700);

        kenBurnsView.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }
        });

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(Splash_Activity.this,LoginActivity.class));
                finish();

            }
        },3000);

    }
}
