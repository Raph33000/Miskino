package com.example.stefmaggle.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Stephane on 01/12/2015.
 */
public class Rejouer extends Activity {


    TextView ScoreA2;
    TextView Partiefin;
    Button replay;
    TextView value;
    ImageView logo;
    ImageButton share;
    int final_score;
    Animation zoom;
    Animation animzoomlogo;

    SharedPreferences sharedpreferences;
    String high_score;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_replay);

        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ScoreA2 = (TextView) findViewById(R.id.textView4);
        Partiefin = (TextView) findViewById(R.id.partiefin);
        replay = (Button) findViewById(R.id.button2);
        logo = (ImageView) findViewById(R.id.logo);

        animzoomlogo = AnimationUtils.loadAnimation(this, R.anim.anim_effettextelogo);
        logo.startAnimation(animzoomlogo);
        ScoreA2.startAnimation(animzoomlogo);


        share = (ImageButton) findViewById(R.id.Button);
        share.setOnClickListener(ShareListener);
        replay.setOnClickListener(RejListener);
        Typeface font = Typeface.createFromAsset(getAssets(),"dimbo.ttf");
        ScoreA2.setTypeface(font);
        Partiefin.setTypeface(font);



        zoom = AnimationUtils.loadAnimation(this, R.anim.anim_zoom);
        Intent intent = getIntent();
        if (intent != null) {
            String Scored = "Score : " + String.valueOf(intent.getIntExtra("ScoreAff", 0));
            final_score = intent.getIntExtra("ScoreAff", 0);
            ScoreA2.setText(Scored);
            if (final_score > sharedpreferences.getInt("high", 0))
            {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("high", final_score);
                editor.commit();
            }
            value = (TextView) findViewById(R.id.text_high);
            value.setTypeface(font);
            high_score = value.getText() + String.valueOf(sharedpreferences.getInt("high", 0));
            value.setText(high_score);
        }
    }

    private View.OnClickListener ShareListener = new View.OnClickListener(){

        public void onClick(View v){

            share.startAnimation(zoom);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "J'ai fait le score de " + String.valueOf(final_score) + " sur l'application Swipe it !! Viens essayer de me battre ! http://tsu.olympe.in/Swipeit.apk");
            startActivity(Intent.createChooser(intent, "Share"));
        }
    };

    private View.OnClickListener RejListener = new View.OnClickListener(){
        public void onClick(View v) {

            play_sound("retry.mp3");
            Intent intent = new Intent(Rejouer.this, MainActivity.class);
            startActivity(intent);
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
