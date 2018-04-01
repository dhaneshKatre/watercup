package com.dk.watercup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2000);
        animation.setStartOffset(10);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        TextView data = findViewById(R.id.data);
        data.setAnimation(animation);
    }
}
