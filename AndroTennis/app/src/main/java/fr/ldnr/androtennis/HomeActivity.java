package fr.ldnr.androtennis;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import misc.MenuNavigation;

import static android.view.View.GONE;


/**
 * Created by Unkof on 19/04/2017.
 */

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.home);



        //for api 11 minimum
        View home = findViewById(R.id.home_buttons);
        if (Build.VERSION.SDK_INT >= 11) {

            home.setVisibility(GONE);


        }

        setLastAddViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {

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
            default:
                return false;
        }

    }

    public void OnAddPerformButtonClicked(View view) {

        MenuNavigation.goToActivity(HomeActivity.this, AddPerformActivity.class);

    }

    public void onAboutButtonClicked(View view) {

        MenuNavigation.goToActivity(HomeActivity.this, AboutActivity.class);
    }

    public void onSeePerformButtonClicked(View view) {
        MenuNavigation.goToActivity(HomeActivity.this, PerformActivity.class);
    }

    public void onWelcomeButtonClicked(View view) {


    }

    public void onHistoricButtonClicked(View view) {
        MenuNavigation.goToActivity(HomeActivity.this, HistoricActivity.class);

    }

    public void onExportButtonClicked(View view) {

        /*TODO: Add export code*/
    }

    public void setLastAddViews() {

        int nb = 0;
        ArrayList<String> tabBack = new ArrayList<String>();

        TextView lastAdd1 = (TextView) findViewById(R.id.last_add_1);
        TextView lastAdd2 = (TextView) findViewById(R.id.last_add_2);
        TextView lastAdd3 = (TextView) findViewById(R.id.last_add_3);
        TextView lastAdd4 = (TextView) findViewById(R.id.last_add_4);
        TextView lastAdd5 = (TextView) findViewById(R.id.last_add_5);

        lastAdd2.setVisibility(GONE);
        lastAdd3.setVisibility(GONE);
        lastAdd4.setVisibility(GONE);
        lastAdd5.setVisibility(GONE);


        manageBdd manager = new manageBdd(this);

        SQLiteDatabase db = manager.getReadableDatabase();

        Cursor curs = db.rawQuery("SELECT count(*) noma from donnees", null);

        if (curs.moveToFirst()) {
            nb = curs.getInt(0);
            Log.i("NOmbre : ", Integer.toString(nb));
            curs.close();

        } else {

            nb = 0;
        }

        Cursor c = db.rawQuery("SELECT noma, dateTime from donnees ORDER BY dateTime DESC LIMIT 5", null);

        if (c.moveToFirst()) {


            tabBack.add("Adversaire : " +c.getString(c.getColumnIndex("noma")) +"   Date : "+ c.getString(c.getColumnIndex("dateTime")).substring(0,10));

            while (c.moveToNext()) {

                tabBack.add("Adversaire : " +c.getString(c.getColumnIndex("noma")) +"   Date : "+ c.getString(c.getColumnIndex("dateTime")).substring(0,10));

            }
            c.close();

            switch (nb) {

                case 0:
                    break;
                case 1:
                    lastAdd1.setText(tabBack.get(0));

                    break;
                case 2:

                    lastAdd2.setVisibility(View.VISIBLE);
                    lastAdd1.setText(tabBack.get(0));
                    lastAdd2.setText(tabBack.get(1));
                    break;
                case 3:

                    lastAdd2.setVisibility(View.VISIBLE);
                    lastAdd3.setVisibility(View.VISIBLE);
                    lastAdd1.setText(tabBack.get(0));
                    lastAdd2.setText(tabBack.get(1));
                    lastAdd3.setText(tabBack.get(2));
                    break;
                case 4:

                    lastAdd2.setVisibility(View.VISIBLE);
                    lastAdd3.setVisibility(View.VISIBLE);
                    lastAdd4.setVisibility(View.VISIBLE);
                    lastAdd1.setText(tabBack.get(0));
                    lastAdd2.setText(tabBack.get(1));
                    lastAdd3.setText(tabBack.get(2));
                    lastAdd4.setText(tabBack.get(3));
                    break;

                default:
                    lastAdd2.setVisibility(View.VISIBLE);
                    lastAdd3.setVisibility(View.VISIBLE);
                    lastAdd4.setVisibility(View.VISIBLE);
                    lastAdd5.setVisibility(View.VISIBLE);
                    lastAdd1.setText(tabBack.get(0));
                    lastAdd2.setText(tabBack.get(1));
                    lastAdd3.setText(tabBack.get(2));
                    lastAdd4.setText(tabBack.get(3));
                    lastAdd5.setText(tabBack.get(4));
            }
        }
        db.close();
    }
}

