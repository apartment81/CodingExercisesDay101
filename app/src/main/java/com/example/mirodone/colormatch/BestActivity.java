package com.example.mirodone.colormatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BestActivity extends AppCompatActivity{

    TextView tv_best;
    int lastBest;
    int best1, best2, best3;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_best);

        tv_best = findViewById(R.id.tv_best);

        //load old score

        SharedPreferences preferences  = getSharedPreferences("PREF", 0);
        lastBest = preferences.getInt("lastBest",0);
        best1 = preferences.getInt("best1",0);
        best2 = preferences.getInt("best2",0);
        best3 = preferences.getInt("best3",0);


        //replace if there is a best score
        if(lastBest > best3) {
            best3 = lastBest;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3" , best3);
            editor.apply();
        }


        if(lastBest > best2) {
            int temp  = best2;
            best2 = lastBest;
            best3 = temp;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3" , best3);
            editor.putInt("best2" , best2);
            editor.apply();
        }

        if(lastBest > best1) {
            int temp  = best1;
            best1 = lastBest;
            best2 = temp;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2" , best2);
            editor.putInt("best1" , best1);
            editor.apply();
        }

        tv_best.setText("LAST SCORE: " + lastBest + "\n"+
        "First best: " + best1 + "\n" +
                "Second best: " + best2 + "\n" +
                "Third best: " + best3 );

        AlertDialog.Builder adb = new AlertDialog.Builder(BestActivity.this);
                        adb.setMessage("Game Over ! " )
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

}
