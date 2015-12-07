package com.example.stefmaggle.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Stephane on 01/12/2015.
 */
public class Rejouer extends Activity {


    TextView ScoreA2;
    Button replay;
    TextView value;

    SharedPreferences sharedpreferences;
    String high_score;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_replay);

        sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ScoreA2 = (TextView) findViewById(R.id.textView4);
        replay = (Button) findViewById(R.id.button2);

        replay.setOnClickListener(RejListener);

        Intent intent = getIntent();
        if (intent != null) {
            String Scored = "Score : " + String.valueOf(intent.getIntExtra("ScoreAff", 0));
            int final_score = intent.getIntExtra("ScoreAff", 0);
            ScoreA2.setText(Scored);
            if (final_score > sharedpreferences.getInt("high", 0))
            {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("high", final_score);
                editor.commit();
            }
            value = (TextView) findViewById(R.id.text_high);
            high_score = value.getText() + String.valueOf(sharedpreferences.getInt("high", 0));
            value.setText(high_score);
        }
    }

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
