package fr.ldnr.androtennis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import misc.MenuNavigation;


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

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()){

            case R.id.home_menu_histo:
                MenuNavigation.goToActivity(HomeActivity.this, HistoricActivity.class);
            return true;

            case R.id.home_menu_perform:
                MenuNavigation.goToActivity(HomeActivity.this, PerformActivity.class);
                return true;

            case R.id.home_menu_export:
                /*TODO : Add export code */
                return true;

            case R.id.home_menu_welcome:
                MenuNavigation.goToActivity(HomeActivity.this, HomeActivity.class);
            default :
                return false;
        }

    }

    public void OnAddPerformButtonClicked(View view){

        Intent intent = new Intent(HomeActivity.this, AddPerformActivity.class);
        startActivity(intent);

    }

    public void onAboutButtonClicked(View view){

        Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
        startActivity(intent);

    }

    public void onSeePerformButtonClicked(View view){

        Intent intent = new Intent(HomeActivity.this, PerformActivity.class);
        startActivity(intent);
    }
}

