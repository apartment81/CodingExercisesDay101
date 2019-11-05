package com.example.mirodone.colormatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private final static int STATE_BLUE = 1;
    private final static int STATE_RED = 2;
    private final static int STATE_YELLOW = 3;
    private final static int STATE_GREEN = 4;
    ImageView iv_button, iv_arrow;
    TextView tv_points;
    ProgressBar mProgressBar;
    Handler mHandler;
    Runnable mRunnable;
    Random random;
    int buttonState = STATE_BLUE;
    int arrowState = STATE_BLUE;

    int currentTime = 4000;
    int startTime = 4000;

    int currentPoints = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_button = findViewById(R.id.iv_button);
        iv_arrow = findViewById(R.id.iv_arrow);
        tv_points = findViewById(R.id.tv_points);
        mProgressBar = findViewById(R.id.progressBar);

        //set the initial progressbar time to4 seconds
        mProgressBar.setMax(startTime);
        mProgressBar.setProgress(startTime);

        // display the starting points
        tv_points.setText( String.valueOf(currentPoints));

        //generate random arrow color when game starts

        random = new Random();
        arrowState = random.nextInt(4) + 1;
        setArrowImage(arrowState);


        iv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the button with the colors
                setButtonImage(setButtonPosition(buttonState));

            }
        });

        //main game loop

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                //show progress
                currentTime = currentTime - 100;
                mProgressBar.setProgress(currentTime);

                //check if there is some time left in progressbar
                if (currentTime > 0) {
                    mHandler.postDelayed(mRunnable, 100);
                } else {
                    //check if colors are matching
                    if (buttonState == arrowState) {
                        currentPoints += 1;
                        tv_points.setText(String.valueOf(currentPoints));

                        //make the speed higher after every turn
                        startTime = startTime - 100;
                        //if the speed is 1 second, will be changed to 2 seconds
                        if (startTime < 1000) {
                            startTime = 2000;
                        }

                        mProgressBar.setMax(startTime);
                        currentTime = startTime;
                        mProgressBar.setProgress(currentTime);

                        //generate new color for arrow
                        arrowState = random.nextInt(4) + 1;
                        setArrowImage(arrowState);

                        mHandler.postDelayed(mRunnable, 100);
                    } else {
                        iv_button.setEnabled(false);
                      //  Toast.makeText(MainActivity.this, "Game Over !", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                        adb.setMessage("Game Over ! \nPoints: " + currentPoints)
                                .setCancelable(false)
                                .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog alertDialog = adb.create();
                        alertDialog.show();
                    }
                }
            }
        };

        //start the game loop
        mHandler.postDelayed(mRunnable, 100);

    }

    // display the arrow color according to the generated number
    private void setArrowImage(int state) {
        switch (state) {
            case STATE_BLUE:
                iv_arrow.setImageResource(R.drawable.ic_blue);
                arrowState = STATE_BLUE;
                break;
            case STATE_RED:
                iv_arrow.setImageResource(R.drawable.ic_red);
                arrowState = STATE_RED;
                break;
            case STATE_YELLOW:
                iv_arrow.setImageResource(R.drawable.ic_yellow);
                arrowState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                iv_arrow.setImageResource(R.drawable.ic_green);
                arrowState = STATE_GREEN;
                break;
        }
    }

    private void setRotation(final ImageView image, final int drawable) {

        //rotate 90 degrees
        RotateAnimation rotateAnimation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setImageResource(drawable);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image.startAnimation(rotateAnimation);
    }

    // set button colors position 1 >4
    private int setButtonPosition(int position) {
        position = position + 1;
        if (position == 5) {
            position = 1;
        }
        return position;
    }

    // display the button colors positions

    private void setButtonImage(int state) {
        switch (state) {
            case STATE_BLUE:
                setRotation(iv_button, R.drawable.ic_button_b);
                buttonState = STATE_BLUE;
                break;
            case STATE_RED:
                setRotation(iv_button, R.drawable.ic_button_r);
                buttonState = STATE_RED;
                break;
            case STATE_YELLOW:
                setRotation(iv_button, R.drawable.ic_button_y);
                buttonState = STATE_YELLOW;
                break;
            case STATE_GREEN:
                setRotation(iv_button, R.drawable.ic_button_g);
                buttonState = STATE_GREEN;
                break;
        }
    }

}
