package com.example.stefmaggle.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.Environment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
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

import java.io.IOException;
import java.util.Random;


public class MainActivity extends Activity {

    ImageButton like = null;
    ImageButton dislike = null;
    ImageButton superlike = null;
    ImageView photo = null;
    CountDownTimer mCountDownTimer;

    long timerdumoment;

    //variables utilisées pour le swipe
    private float x1,x2, y1;
    static final int MIN_DISTANCE = 150;

    TextView match = null;
    TextView timer = null;
    TextView ScoreA = null;

    Animation animScale;
    Animation animun;
    Animation animAlpha;
    Animation animArriv;
    Animation entreePhoto;
    Animation sortiePhoto;
    Animation animSuperlike;

    boolean a = true;
    boolean b = false;
    boolean d = false;

    int i = 0;
    int Score = 0;
    String ScoreAff;
    int temps = 15000;
    long temps1;

    Random rand = new Random();


    final MediaPlayer back_music = new MediaPlayer();
    final MediaPlayer mp = new MediaPlayer();

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
    superlike = (ImageButton) findViewById(R.id.superlike);

        //Mets par de baisser le son des médias
        setVolumeControlStream(AudioManager.STREAM_MUSIC);


        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_zoom);
        animun = AnimationUtils.loadAnimation(this, R.anim.anim_plusun);
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        animArriv = AnimationUtils.loadAnimation(this, R.anim.anim_arrivee);
        entreePhoto = AnimationUtils.loadAnimation(this, R.anim.anim_entreephoto);
        sortiePhoto = AnimationUtils.loadAnimation(this, R.anim.anim_sortie);
        animSuperlike = AnimationUtils.loadAnimation(this, R.anim.anim_superlike);



        match = (TextView) findViewById(R.id.Perduview);
        ScoreA = (TextView) findViewById(R.id.Scoreview);
        timer = (TextView) findViewById(R.id.timer);

        photo = (ImageView) findViewById(R.id.plusun);

        like.setOnClickListener(likeListener);
        dislike.setOnClickListener(dislikeListener);
        superlike.setOnClickListener(superlikeListener);

        like.startAnimation(animArriv);
        dislike.startAnimation(animArriv);
        superlike.setVisibility(View.INVISIBLE);


        imageSwitcher1 = (ImageSwitcher) findViewById(R.id.imageSwitcher);

        // On déclare les animations pour l'imageSwitcher
        //Slide vers la gauche et slide vers la droite
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);


        //pour la musique de fond

        back_music.setOnCompletionListener(restart_mus);
        play_sound("musiquefond.mp3", back_music);

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
        imageSwitcher1.setInAnimation(entreePhoto);
        imageSwitcher1.setOutAnimation(sortiePhoto);


        photo.setVisibility(View.INVISIBLE);
        //---------Listeners Animation-------------------------------
        animun.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                photo.setVisibility(View.VISIBLE);
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
                photo.setVisibility(View.INVISIBLE);
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
                photo.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

        animSuperlike.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                superlike.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });






        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                if (x1 >= imageSwitcher1.getLeft() - 200 && x1 <= imageSwitcher1.getLeft() + 200 + imageSwitcher1.getWidth() && y1 >= imageSwitcher1.getTop() - 200 && y1 <= imageSwitcher1.getTop()+ 200 + imageSwitcher1.getHeight())
                {
                    float deltaX = x2 - x1;
                    if (Math.abs(deltaX) > MIN_DISTANCE)
                    {
                        if (x2 > x1)
                        {
                            like.performClick();
                        }
                        else if (x2 < x1)
                        {
                            dislike.performClick();
                        }
                    }
                    break;

                }
        }
        return super.onTouchEvent(event);
    }

    private OnClickListener superlikeListener = new OnClickListener() {


        @Override
        public void onClick(View v) {
            temps = (int) (temps1 + 2000);
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
            createCountDownTimer();
            mCountDownTimer.start();



            superlike.startAnimation(animSuperlike);
            play_sound("bip.mp3", mp);
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

        }
    };

    private void affichesuperlike() {
        int nombre = rand.nextInt(6);
        if( nombre == 3) superlike.setVisibility(View.VISIBLE);
    }

    private void createCountDownTimer() {
        mCountDownTimer = new CountDownTimer(temps, 100) {

            public void onTick(long millisUntilFinished) {
                temps1 = millisUntilFinished;
                timer.setText("Temps restant : " + millisUntilFinished / 1000 );
            }

            public void onFinish() {
                play_sound("mort.mp3", mp);
                back_music.stop();
                Intent intent = new Intent(MainActivity.this, Rejouer.class);
                intent.putExtra("ScoreAff", Score);
                startActivity(intent);
                finish();
            }
        };
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
                createCountDownTimer();
                mCountDownTimer.start();

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
                play_sound("bip.mp3", mp);
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
                affichesuperlike();

            } else {
                //Changer de classe.
                //Envoyer Score
                mCountDownTimer.cancel();
                play_sound("mort.mp3", mp);
                back_music.stop();
                Intent intent = new Intent(MainActivity.this, Rejouer.class);
                intent.putExtra("ScoreAff", Score);
                startActivity(intent);
                finish();

            }
        }


    };
    //listener bouton de gauche ( fonctionne comme celui de droite
    public void play_sound(String file_name, MediaPlayer mp){
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

                play_sound("bip.mp3", mp);
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
                affichesuperlike();

            } else {
                mCountDownTimer.cancel();
                play_sound("mort.mp3", mp);
                back_music.stop();
                Intent intent = new Intent(MainActivity.this, Rejouer.class);
                intent.putExtra("ScoreAff", Score);
                startActivity(intent);
                finish();

            }
        }

    };

    private MediaPlayer.OnCompletionListener restart_mus = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            play_sound("musiquefond.mp3", back_music);
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


