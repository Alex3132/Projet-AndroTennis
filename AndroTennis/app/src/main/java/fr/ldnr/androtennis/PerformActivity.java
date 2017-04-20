package fr.ldnr.androtennis;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import misc.MenuNavigation;

/**
 * Created by Philip on 20/04/2017.
 */

public class PerformActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perform);

        Log.i("Info SQL","Manage de la table");
        manageBdd databa=new manageBdd(this);
        if(databa==null)
        {
            Log.i("InfoSQL","Objet databa VIDE!");
        }
        else
        {
            Log.i("InfoSQL","Ok objet databa instancié");
        }
        Log.i("Info SQL","On va insérer des données");
        int nb=databa.insertPerform("Rambo",3,6,6,1,6,2,1,"Toulouse","2017-04-20 15:41:22");
        Log.i("Info SQLite","Données insérées");
        Toast.makeText(this,"On a inséré les données",Toast.LENGTH_LONG).show();
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
                MenuNavigation.goToActivity(PerformActivity.this, HistoricActivity.class);
                return true;

            case R.id.home_menu_perform:
                MenuNavigation.goToActivity(PerformActivity.this, PerformActivity.class);
                return true;

            case R.id.home_menu_export:
                /*TODO : Add export code */
                return true;

            case R.id.home_menu_welcome:
                MenuNavigation.goToActivity(PerformActivity.this, HomeActivity.class);
            default:
                return false;
        }
    }
}
