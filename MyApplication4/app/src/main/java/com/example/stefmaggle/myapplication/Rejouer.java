package com.example.stefmaggle.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Stephane on 01/12/2015.
 */
public class Rejouer extends Activity {


    TextView ScoreA2;
    Button replay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_replay);


        ScoreA2 = (TextView) findViewById(R.id.textView4);
        replay = (Button) findViewById(R.id.button2);

        replay.setOnClickListener(RejListener);

        Intent intent = getIntent();
        if (intent != null) {
            String Scored = intent.getStringExtra("ScoreAff");
            ScoreA2.setText(Scored);
        }
    }

    private View.OnClickListener RejListener = new View.OnClickListener(){
        public void onClick(View v) {
            Intent intent = new Intent(Rejouer.this, MainActivity.class);
            startActivity(intent);
        }
    };

}
