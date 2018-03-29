package com.dk.watercup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        TextView data = findViewById(R.id.data);
        LinearLayout layout = findViewById(R.id.imageDisplay);
        data.setAnimation(animation);
        layout.setAnimation(animation);
    }
}
