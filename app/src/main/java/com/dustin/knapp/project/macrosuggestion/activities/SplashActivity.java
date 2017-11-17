package com.dustin.knapp.project.macrosuggestion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dustin.knapp.project.macrosuggestion.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dknapp on 4/26/17
 */
//todo load current days nutritional data and populate the pending nutritional object
public class SplashActivity extends BaseActivity {

  @BindView(R.id.loader_bad_foods) ImageView badFoodsImage;

  @BindView(R.id.loader_good_foods) ImageView goodFoodsImage;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_screen);

    unbinder = ButterKnife.bind(this);

    badFoodsImage.setImageResource(R.drawable.splash_bad_foods);

    goodFoodsImage.setImageResource(R.drawable.splash_good_foods);

    final Animation rotateCounterClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_counter_clockwise);
    final Animation rotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);

    badFoodsImage.startAnimation(rotateCounterClockwise);
    goodFoodsImage.startAnimation(rotateClockwise);

    rotateCounterClockwise.setFillAfter(true);
    rotateClockwise.setFillAfter(true);

    new Timer().schedule(new TimerTask() {
      @Override public void run() {
        rotateClockwise.setRepeatCount(0);
        rotateCounterClockwise.setRepeatCount(0);

        runOnUiThread(new Runnable() {
          @Override public void run() {
            //todo iff client needs to login -> goto login
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
          }
        });
      }
    }, 10000);

    //todo load data here
    //if data is not saved in cloud sync at this time
    //psuedo if user logged in load data, if exists. else go to login
    //        new Handler().postDelayed(new Runnable() {
    //            @Override
    //            public void run() {
    //                //Simulating a long running task
    //
    //                System.out.println("Going to Profile Data");
    //      /* Create an Intent that will start the ProfileData-Activity. */
    //                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
    //                startActivity(mainIntent);
    //                finish();
    //
    //            }
    //        }, 5000);
  }
}
