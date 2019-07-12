package com.qooplite.alpay.cryptolab;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Launch extends AppCompatActivity {


    ImageView Key;
    ImageView text;

    Animation launchtop;
    Animation fromside;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Key = (ImageView) findViewById(R.id.schluesselLogo);
        text = (ImageView) findViewById(R.id.textlLogo);

        hideSystemUI();


        launchtop = AnimationUtils.loadAnimation(this, R.anim.bounce);
        fromside = AnimationUtils.loadAnimation(this, R.anim.fadein);


        Key.setAnimation(launchtop);
        text.setAnimation(fromside);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent intent = new Intent(Launch.this, EncryptionWahl.class);
                startActivity(intent);

                finish();


            }
        }, 3500);
    }

    public void hideSystemUI(){


        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}