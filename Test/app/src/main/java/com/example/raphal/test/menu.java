package com.example.raphal.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Raphaël on 05/12/2015.
 */
public class menu extends AppCompatActivity {

    ImageButton toto = null;
    Chronometer chronos = null;
    Animation animRotate;
    Animation animGodown;
    boolean isaction = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        toto = (ImageButton) findViewById(R.id.imageView2);
        animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        animRotate.setRepeatCount(Animation.INFINITE);
        animGodown = AnimationUtils.loadAnimation(this, R.anim.anim_godown);
        animGodown.setRepeatCount(Animation.INFINITE);
        chronos = (Chronometer) findViewById(R.id.chronometer);
        chronos.start();
        chronos.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                // TODO Auto-generated method stub
                String currentTime = chronos.getText().toString();
                if (currentTime.equals("00:02")) //put time according to you
                {
                    toto.startAnimation(animRotate);
                    //toto.startAnimation(animGodown);
                    chronos.stop();
                }
            }
        });
        toto.setOnClickListener(new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View v){
                if (isaction == false){
                    toto.startAnimation(animRotate);
                    //toto.startAnimation(animGodown);
                    isaction = true;
                }
                else{
                    isaction = false;
                    toto.clearAnimation();
                }
            }

        });
    }
}
