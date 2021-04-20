package com.example.jtorch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int SPLASH_TIME_OUT = 2500;
    Animation topanimation,bottomanimation,middleanimation;
    View first,second,third,fourth,fifth,six;
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topanimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middleanimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);

        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        fifth = findViewById(R.id.fifth);
        six = findViewById(R.id.six);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        first.setAnimation(topanimation);
        second.setAnimation(topanimation);
        third.setAnimation(topanimation);
        fourth.setAnimation(topanimation);
        fifth.setAnimation(topanimation);
        six.setAnimation(topanimation);

        tv1.setAnimation(middleanimation);
        tv2.setAnimation(bottomanimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,secondActivity.class);
                        startActivity(intent);
                        finish();
            }
        },SPLASH_TIME_OUT);
    }
}
