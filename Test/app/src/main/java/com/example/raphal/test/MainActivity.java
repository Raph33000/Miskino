package com.example.raphal.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button toto = null;
    TextView tutu = null;
    boolean caca = false;
    ImageView titi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toto = (Button) findViewById(R.id.button);
        toto.setOnClickListener(toto_listener);
        titi = (ImageView) findViewById(R.id.imageView);
        titi.setVisibility(View.INVISIBLE);
    }

    private View.OnClickListener toto_listener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            titi.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, menu.class);
            startActivity(intent);
            finish();
        }
    };
}