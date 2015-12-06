package com.example.stefmaggle.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Stephane on 01/12/2015.
 */
public class MenuJeu extends Activity{

    ImageButton jouer = null;
    Animation arrive;
    Animation zoom;
    TextView score;
    int value;
    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jeu);
        jouer = (ImageButton)findViewById(R.id.button);
        arrive = AnimationUtils.loadAnimation(this, R.anim.anim_arrivejouer);
        zoom = AnimationUtils.loadAnimation(this, R.anim.anim_zoom);
        jouer.startAnimation(arrive);
        jouer.setOnClickListener(Jouer);
        //prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);//recupere les valeurs enregistrées
        //value = prefs.getInt("high_score", 0); //0 is the default value

        //score = (TextView)findViewById(R.id.textView_value);
        //score.setText(value); //change la valeur de la textviex score
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("high_score", 5);
        editor.commit();
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
