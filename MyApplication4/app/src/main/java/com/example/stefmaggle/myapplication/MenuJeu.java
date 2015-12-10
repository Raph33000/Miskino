package com.example.stefmaggle.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Stephane on 01/12/2015.
 */
public class MenuJeu extends Activity{

    ImageButton jouer = null;
    ImageView logo = null;
    ImageView header = null;
    ImageView swipeit;
    Animation arrive;
    Animation zoom;
    Animation rotationlogo;
    Animation zoomtexte;
    Animation transitionlogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jeu);
        jouer = (ImageButton)findViewById(R.id.jouer_button);
        arrive = AnimationUtils.loadAnimation(this, R.anim.anim_arrivejouer);
        logo = (ImageView) findViewById(R.id.logo);
        swipeit = (ImageView) findViewById(R.id.imageView6);
        header = (ImageView) findViewById(R.id.imageView4);
        rotationlogo = AnimationUtils.loadAnimation(this, R.anim.anim_rotationlogo);
        zoomtexte = AnimationUtils.loadAnimation(this, R.anim.anim_effettextelogo);
        transitionlogo = AnimationUtils.loadAnimation(this, R.anim.anim_zoomlogoturfu);
        zoom = AnimationUtils.loadAnimation(this, R.anim.anim_zoom);
        swipeit.startAnimation(zoomtexte);
        logo.startAnimation(rotationlogo);

        jouer.startAnimation(arrive);
        jouer.setOnClickListener(Jouer);

        transitionlogo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // your code here
                        jouer.setVisibility(View.INVISIBLE);
                        swipeit.clearAnimation();
                        swipeit.setVisibility(View.INVISIBLE);

                    }
                }, 200/* 1sec delay */);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // your code here

                        header.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(MenuJeu.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    }
                }, 420/* 1sec delay */);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
                //ceci est un test

        private View.OnClickListener Jouer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            play_sound("play.mp3");
            jouer.startAnimation(zoom);

            logo.startAnimation(transitionlogo);



        }
    };

    public void play_sound(String file_name){
        final MediaPlayer mp = new MediaPlayer();
        if(mp.isPlaying())
        {
            mp.stop();
        }
        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = getAssets().openFd(file_name);
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
