package fr.ldnr.androtennis;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
//to get all the fields off the record in db
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



            case R.id.home_menu_welcome:
                MenuNavigation.goToActivity(PerformShowActivity.this, HomeActivity.class);
            default:
                return false;
        }
    }

//when modify button is clicked
    public void onModifyButtonClicked(View view){

Intent intent = new Intent(PerformShowActivity.this, ModifyPerformActivity.class);

        intent.putExtra("id", id);

        startActivityForResult(intent, 0);






    }

    //when delete button is clicked a popup is showing
    public void onDeletePerformButtonClicked(View view){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(PerformShowActivity.this, R.style.myDialog);
        builder1.setMessage(R.string.delete_alert_message);
        builder1.setCancelable(true);

        //yes button
        builder1.setPositiveButton(
                R.string.delete_yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{


                            manageBdd manager = new manageBdd(PerformShowActivity.this);

                            manager.deletePerform(Integer.parseInt(PerformShowActivity.this.id));
                            manager.close();

                            Toast.makeText(PerformShowActivity.this,"Supprimé",Toast.LENGTH_LONG).show();

                        }catch(SQLiteException E){


                            Log.e("Erreur SQL", "Erreur : "+E.getMessage());
                        }
                        dialog.cancel();
                        finish();
                    }
                });
//no button
        builder1.setNegativeButton(
                R.string.delete_no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();

        alert11.show();


    }


}
