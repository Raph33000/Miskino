package com.example.stefmaggle.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.Random;


public class MainActivity extends Activity {

    ImageButton like = null;
    ImageButton dislike = null;
    ImageView photo = null;

    TextView match = null;
    Chronometer chrono = null;
    TextView ScoreA = null;

    Animation animScale;
    Animation animun;
    Animation animAlpha;
    Animation animArriv;

    boolean a = true;
    boolean b = true;
    boolean d = false;

    int i = 0;
    int Score = 0;
    String ScoreAff;


    //Array d'image pour le défilé de photos
    int imageIds[] = {
            R.drawable.borislike,
            R.drawable.sylvainlike,
            R.drawable.match2,
            R.drawable.damnlike,
            R.drawable.raphlike,
            R.drawable.tristanlike,
            R.drawable.match1};

    int messageCount = imageIds.length; //Nombre d'éléments dans l'array

    int currentIndex = 0;

    private ImageSwitcher imageSwitcher1;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        like = (ImageButton) findViewById(R.id.second);
        dislike = (ImageButton) findViewById(R.id.trois);



        chrono = (Chronometer) findViewById(R.id.chronometer);

        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_zoom);
        animun = AnimationUtils.loadAnimation(this, R.anim.anim_plusun);
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        animArriv = AnimationUtils.loadAnimation(this, R.anim.anim_arrivee);



        match = (TextView) findViewById(R.id.Perduview);
        ScoreA = (TextView) findViewById(R.id.Scoreview);

        photo = (ImageView) findViewById(R.id.plusun);

        like.setOnClickListener(likeListener);
        dislike.setOnClickListener(dislikeListener);

        like.startAnimation(animArriv);
        dislike.startAnimation(animArriv);
        photo.startAnimation(animArriv);

        imageSwitcher1 = (ImageSwitcher) findViewById(R.id.imageSwitcher);

        // On déclare les animations pour l'imageSwitcher
        //Slide vers la gauche et slide vers la droite
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        imageSwitcher1.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub

                // Create a new ImageView set it's properties
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.FILL_PARENT));
                return imageView;
            }
        });

        //On dit a l'image switcher qu'il doit contenir l'array d'images défini plus tôt
        imageSwitcher1.setImageResource(imageIds[currentIndex]);
        // On dit que l'animation "in" c'est in est celle "out" c'est out
        imageSwitcher1.setInAnimation(in);
        imageSwitcher1.setOutAnimation(out);



        //---------Listeners Animation-------------------------------
        animun.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                photo.setImageResource(R.drawable.plusun);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // your code here
                        photo.startAnimation(animAlpha);
                    }
                }, 350/* 1sec delay */);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                photo.setImageResource(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animAlpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                photo.setImageResource(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

        //Petite méthode pour dire d'éffacer "Its a match"

        if (animun.hasEnded()) {
            photo.setImageResource(0);
        }

        //-------------------Listener du chronometre -------------------------------------
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                // TODO Auto-generated method stub
                String currentTime = chrono.getText().toString();
                if (currentTime.equals("00:10")) //put time according to you
                {
                    chrono.stop();
                    Intent intent = new Intent(MainActivity.this, Rejouer.class);
                    intent.putExtra("ScoreAff", ScoreAff);
                    startActivity(intent);
                    finish();
                }
            }
        });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



    }


    //Listener du bouton de droite
    private OnClickListener likeListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            v.startAnimation(animScale); //Animation du bouton
            //On créer un boolean aleatoire pour l'inversion des boutons
            boolean inversion;
            inversion = getRandomBoolean();


            //Quand l'utilisateur appuie sur like, on met le chrono à 0 et on le lance
            if (i == 0) {
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                i++;
            }

            /* Si le boolean a est true, ca veut dire que le bouton de droite représente
            le like
            Et le bouton de gauche représente le dislike. Donc dans ce cas si a == true
            on met l'image du like sur le bouton de droite et l'image du dislike sur le
            bouton de gauche.
            Et inversement
             */

            if (a == true) {
                if (inversion == true) {
                    like.setBackgroundResource(R.drawable.like_button);
                    dislike.setBackgroundResource(R.drawable.dislike_button);
                    a = true;
                    b = false;

                } else if (inversion == false) {
                    //photo.setImageDrawable(getResources().getDrawable(R.drawable.tristan));
                    dislike.setBackgroundResource(R.drawable.like_button);
                    like.setBackgroundResource(R.drawable.dislike_button);
                    a = false;
                    b = true;

                }
                /*Si le joueur a choisi le bon bouton,
                on demarre l'animation et tout, on incrémente le
                 score et on l'affiche.
                 On incrémente currentIndex pour passer à la photo suivante
                 dans l'imageSwitcher */
                photo.startAnimation(animun);
                Score++;
                ScoreAff = "Score : " + Score;
                ScoreA.setText(ScoreAff);
                currentIndex++;
                // If index reaches maximum reset it
                if (currentIndex == messageCount) {
                    //Si on arrive a la fin de la liste de photos, on repasse à 0
                    currentIndex = 0;
                }
                //On change de photos
                imageSwitcher1.setImageResource(imageIds[currentIndex]);

            } else {
                //Changer de classe.
                //Envoyer Score

                Intent intent = new Intent(MainActivity.this, Rejouer.class);
                intent.putExtra("ScoreAff", ScoreAff);
                startActivity(intent);
                finish();
            }
        }


    };
    //listener bouton de gauche ( fonctionne comme celui de droite
    private OnClickListener dislikeListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(animScale);
            boolean inversion;
            inversion = getRandomBoolean();


            if (b == true) {
                if (inversion == true) {
                    like.setBackgroundResource(R.drawable.like_button);
                    dislike.setBackgroundResource(R.drawable.dislike_button);
                    b = false;
                    a = true;

                } else if (inversion == false) {
                    dislike.setBackgroundResource(R.drawable.like_button);
                    like.setBackgroundResource(R.drawable.dislike_button);
                    b = true;
                    a = false;

                }
                photo.startAnimation(animun);
                Score++;
                ScoreAff = "Score : " + Score;
                ScoreA.setText(ScoreAff);
                currentIndex++;
                // If index reaches maximum reset it
                if (currentIndex == messageCount) {
                    currentIndex = 0;
                }
                imageSwitcher1.setImageResource(imageIds[currentIndex]);

            } else {
                Intent intent = new Intent(MainActivity.this, Rejouer.class);
                intent.putExtra("ScoreAff", ScoreAff);
                startActivity(intent);
                finish();

            }
        }

    };

    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
    /*
    private void showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - chrono.getBase();

        Toast.makeText(MainActivity.this, "Secondes passées: " + (0.001) * elapsedMillis,
                Toast.LENGTH_SHORT).show();
    }   */





}

