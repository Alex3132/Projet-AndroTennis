package fr.ldnr.androtennis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by Philip on 22/04/2017.
 */

public class SplashscreenAndroTennis extends Activity {

    //disappear after 5 secs

    private static int SPLASH_TIME_OUT= 5000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splashscreen);
        //creation of the bdd
manageBdd manager= new manageBdd(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashscreenAndroTennis.this, HomeActivity.class);

                startActivity(i);

                finish();


            }
        }, SPLASH_TIME_OUT);
        }
    }

