package fr.ldnr.androtennis;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;


/**
 * Created by Unkof on 19/04/2017.
 */

public class HomeActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


setContentView(R.layout.home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
}

