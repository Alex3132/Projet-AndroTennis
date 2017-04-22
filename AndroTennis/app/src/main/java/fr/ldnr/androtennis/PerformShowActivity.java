package fr.ldnr.androtennis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import misc.MenuNavigation;

/**
 * Created by Philip on 22/04/2017.
 */

public class PerformShowActivity extends Activity {

    private String id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> array = new ArrayList<String>();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");


        setContentView(R.layout.performshow);


        ListView lv = (ListView) findViewById(R.id.perform_show_list);

        manageBdd manager = new manageBdd(this);

        SQLiteDatabase db = manager.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM donnees WHERE id = " + id, null);

        if (c.moveToFirst()) {

            array.add(getResources().getString(R.string.adversary_name) + c.getString(c.getColumnIndex("noma")));
            array.add(getResources().getString(R.string.score_set1j) + c.getString(c.getColumnIndex("set1j")));
            array.add(getResources().getString(R.string.score_set1a) + c.getString(c.getColumnIndex("set1a")));
            array.add(getResources().getString(R.string.score_set2j) + c.getString(c.getColumnIndex("set2j")));
            array.add(getResources().getString(R.string.score_set2a) + c.getString(c.getColumnIndex("set2a")));
            array.add(getResources().getString(R.string.score_set3j) + c.getString(c.getColumnIndex("set3j")));
            array.add(getResources().getString(R.string.score_set3a) + c.getString(c.getColumnIndex("set3a")));
            if (c.getString(c.getColumnIndex("result")).equals("1")) {
                array.add(getResources().getString(R.string.result_victory));
            } else {
                array.add(getResources().getString(R.string.result_loose));

            }
            array.add(getResources().getString(R.string.location) + c.getString(c.getColumnIndex("lieu")));
            array.add(getResources().getString(R.string.home_date) + c.getString(c.getColumnIndex("dateTime")));

            c.close();
            db.close();
        } else {

            Log.i("Données", "Données non trouvées");
            c.close();
            db.close();
        }


        ArrayAdapter<String> aa = new ArrayAdapter<String>(PerformShowActivity.this, android.R.layout.simple_list_item_1, array);

        lv.setAdapter(aa);


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
                MenuNavigation.goToActivity(PerformShowActivity.this, HistoricActivity.class);
                return true;

            case R.id.home_menu_perform:
                MenuNavigation.goToActivity(PerformShowActivity.this, PerformActivity.class);
                return true;

            case R.id.home_menu_export:
                /*TODO : Add export code */
                return true;

            case R.id.home_menu_welcome:
                MenuNavigation.goToActivity(PerformShowActivity.this, HomeActivity.class);
            default:
                return false;
        }
    }


    public void onModifyButtonClicked(View view){

Intent intent = new Intent(PerformShowActivity.this, ModifyPerformActivity.class);

        intent.putExtra("id", id);

        startActivityForResult(intent, 0);






    }
    public void onDeletePerformButtonClicked(View view){

        try{


            manageBdd manager = new manageBdd(this);

            manager.deletePerform(Integer.parseInt(id));
            manager.close();

            Toast.makeText(this,"Supprimé",Toast.LENGTH_LONG).show();

        }catch(SQLiteException E){


            Log.i("Erreur SQL", "Erreur : "+E.getMessage());
        }

    }


}
