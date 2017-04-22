package fr.ldnr.androtennis;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import misc.MenuNavigation;

/**
 * Created by Philip on 20/04/2017.
 */

public class AddPerformActivity extends Activity {

    //views
    private int viewnom = R.id.add_perform_edit_noma;
    private int viewset1j = R.id.add_perform_edit_set1j;
    private int viewset2j = R.id.add_perform_edit_set2j;
    private int viewset3j = R.id.add_perform_edit_set3j;
    private int viewset1a = R.id.add_perform_edit_set1a;
    private int viewset2a = R.id.add_perform_edit_set2a;
    private int viewset3a = R.id.add_perform_edit_set3a;
    private int viewvictory = R.id.add_perform_edit_result;
    private int viewdate = R.id.add_perform_edit_date;
    private int viewlocation = R.id.add_perform_edit_location;

    //datas

    private String noma="";
    private int set1j=0;
    private int set2j=0;
    private int set3j=0;
    private int set1a=0;
    private int set2a=0;
    private int set3a=0;
    private String location="";
    private String date="";
    private int victory= -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addperform);
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
                MenuNavigation.goToActivity(AddPerformActivity.this, HistoricActivity.class);
                return true;

            case R.id.home_menu_perform:
                MenuNavigation.goToActivity(AddPerformActivity.this, PerformActivity.class);
                return true;

            case R.id.home_menu_export:
                /*TODO : Add export code */
                return true;

            case R.id.home_menu_welcome:
                MenuNavigation.goToActivity(AddPerformActivity.this, HomeActivity.class);
            default:
                return false;
        }
    }




    public void onAddPerformDataButtonClicked(View view) {

        this.noma=addNomA(viewnom);
        this.set1j=addset(viewset1j);
        this.set2j=addset(viewset2j);
        this.set3j=addset(viewset3j);
        this.set1a=addset(viewset1a);
        this.set2a=addset(viewset2a);
        this.set3a=addset(viewset3a);
        this.date=getDate(viewdate);
        this.location=addLocation(viewlocation);
        this.victory=addVictory(viewvictory);


        if(this.noma!=""){

            try {
                manageBdd manager = new manageBdd(this);
                SQLiteDatabase db= manager.getWritableDatabase();
                if(!date.equals("")){
                manager.insertPerform(noma, set1j, set1a, set2j, set2a, set3j, set3a, victory, location, date);
                    Log.i("InfoinsertPerform", "Données insérées");
                    Toast.makeText(this,getResources().getString(R.string.added_perform), Toast.LENGTH_LONG).show();
                   SQLiteDatabase dbm= manager.getReadableDatabase();

                    Cursor c=dbm.rawQuery("SELECT * FROM donnees",null);
                    if(c.moveToFirst()) {
                        while (c.moveToNext()) {
                            Log.i("Info", c.getString(c.getColumnIndex("dateTime")));
                        }
                        c.close();

                    }
            }else{

                    Toast.makeText(this, getResources().getString(R.string.need_good_date), Toast.LENGTH_LONG).show();
                }
            }catch (SQLiteException E){

                Log.i("Error InsertPerform", "Error : "+E);
            }
        }else {

            Toast.makeText(this, getResources().getString(R.string.need_name), Toast.LENGTH_LONG).show();
        }

    }


    private String addNomA(int view) {


        EditText noma = (EditText) findViewById(view);

        String nomAData = noma.getText().toString();

        if (!nomAData.isEmpty()) {

            return nomAData;
        }else{
            return "";
        }

    }

    private int addset(int view) {


        EditText set1j = (EditText) findViewById(view);

        if(! set1j.getText().toString().isEmpty()) {
            int set1JData = Integer.parseInt(set1j.getText().toString());

            return set1JData;
        }else{

            return 0;
        }

    }

    private int addVictory(int view) {

        CheckBox checkbox = (CheckBox) findViewById(view);

        if (checkbox.isChecked() == true) {

            return 1;
        } else {

            return -1;
        }

    }

    private String addLocation(int view) {


        EditText location = (EditText) findViewById(view);

        String locationData = location.getText().toString();

        if(!locationData.isEmpty()){
        return locationData;
        }else{

            return "";
        }
    }


    private String getDate(int view) {


        EditText date = (EditText) findViewById(view);

        String dateDump = date.getText().toString();
        if(!dateDump.isEmpty()&& dateDump.matches("^\\d{2}[ :\\.-/]{1}\\d{2}[ :\\.-/]{1}\\d{4}$")){

            Log.i("Chainecarac", dateDump);

           String day= dateDump.substring(0,2);

            Log.i("jour", day);

            String month = dateDump.substring(3,5);
            Log.i("moi", month);

            String year = dateDump.substring(6,10);


        String dateFormated =  year+"-"+month+"-"+day+" 00:00:00";

        return dateFormated;
        }else{

            return "";
        }

    }


}
