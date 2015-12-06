package com.example.stefmaggle.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Stephane on 01/12/2015.
 */
public class MenuJeu extends Activity{

    ImageButton jouer = null;
    Animation arrive;
    Animation zoom;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jeu);
        jouer = (ImageButton)findViewById(R.id.button);
        arrive = AnimationUtils.loadAnimation(this, R.anim.anim_arrivejouer);
        zoom = AnimationUtils.loadAnimation(this, R.anim.anim_zoom);
        jouer.startAnimation(arrive);
        jouer.setOnClickListener(Jouer);
    }
    //ceci est un test

    private View.OnClickListener Jouer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            jouer.startAnimation(zoom);
            Intent intent = new Intent(MenuJeu.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
