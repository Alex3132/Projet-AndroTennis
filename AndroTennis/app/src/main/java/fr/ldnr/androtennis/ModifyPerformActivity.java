package fr.ldnr.androtennis;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import misc.MenuNavigation;

/**
 * Created by Philip on 22/04/2017.
 */

public class ModifyPerformActivity extends Activity {





    private String id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.modifyperform);
        id = getIntent().getStringExtra("id");

        try {
            manageBdd manager = new manageBdd(this);
            SQLiteDatabase db = manager.getWritableDatabase();

            EditText viewnom = (EditText) findViewById(R.id.modify_perform_edit_noma);
            EditText viewset1j = (EditText) findViewById(R.id.modify_perform_edit_set1j);
            EditText viewset2j = (EditText) findViewById(R.id.modify_perform_edit_set2j);
            EditText viewset3j = (EditText) findViewById(R.id.modify_perform_edit_set3j);
            EditText viewset1a = (EditText) findViewById(R.id.modify_perform_edit_set1a);
            EditText viewset2a = (EditText) findViewById(R.id.modify_perform_edit_set2a);
            EditText viewset3a = (EditText) findViewById(R.id.modify_perform_edit_set3a);
            CheckBox viewvictory = (CheckBox) findViewById(R.id.modify_perform_edit_result);
            EditText viewdate = (EditText) findViewById(R.id.modify_perform_edit_date);
            EditText viewlocation = (EditText) findViewById(R.id.modify_perform_edit_location);


            Cursor c = db.rawQuery("Select * from donnees where id = " + id, null);

            if (c.moveToFirst()) {

                viewnom.setText(c.getString(c.getColumnIndex("noma")));
                viewset1j.setText(c.getString(c.getColumnIndex("set1j")));
                viewset1a.setText(c.getString(c.getColumnIndex("set1a")));
                viewset2j.setText(c.getString(c.getColumnIndex("set2j")));
                viewset2a.setText(c.getString(c.getColumnIndex("set2a")));
                viewset3j.setText(c.getString(c.getColumnIndex("set3j")));
                viewset3a.setText(c.getString(c.getColumnIndex("set3a")));

                if (c.getString(c.getColumnIndex("result")).equals("1")) {

                    viewvictory.setChecked(true);
                } else {

                    viewvictory.setChecked(false);
                }

                viewlocation.setText(c.getString(c.getColumnIndex("lieu")));
                viewdate.setText(getFrenchDate(c.getString(c.getColumnIndex("dateTime"))));
Log.i("date : " ,c.getString(c.getColumnIndex("dateTime")));
db.close();c.close();
            }

        } catch (SQLiteException E) {

            Log.i("Erreur SQL", "Erreur : " + E.getMessage());

        }
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
                MenuNavigation.goToActivity(ModifyPerformActivity.this, HistoricActivity.class);
                return true;

            case R.id.home_menu_perform:
                MenuNavigation.goToActivity(ModifyPerformActivity.this, PerformActivity.class);
                return true;

            case R.id.home_menu_export:

                return true;

            case R.id.home_menu_welcome:
                MenuNavigation.goToActivity(ModifyPerformActivity.this, HomeActivity.class);
            default:
                return false;
        }
    }

    //modify button clicked a popup will be shown

    public void onModifyButtonConfirmClicked(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ModifyPerformActivity.this, R.style.myDialog);
        builder1.setMessage(R.string.modify_dialog_message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.delete_yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        EditText viewnom = (EditText) findViewById(R.id.modify_perform_edit_noma);
                        EditText viewset1j = (EditText) findViewById(R.id.modify_perform_edit_set1j);
                        EditText viewset2j = (EditText) findViewById(R.id.modify_perform_edit_set2j);
                        EditText viewset3j = (EditText) findViewById(R.id.modify_perform_edit_set3j);
                        EditText viewset1a = (EditText) findViewById(R.id.modify_perform_edit_set1a);
                        EditText viewset2a = (EditText) findViewById(R.id.modify_perform_edit_set2a);
                        EditText viewset3a = (EditText) findViewById(R.id.modify_perform_edit_set3a);
                        CheckBox viewvictory = (CheckBox) findViewById(R.id.modify_perform_edit_result);
                        EditText viewdate = (EditText) findViewById(R.id.modify_perform_edit_date);
                        EditText viewlocation = (EditText) findViewById(R.id.modify_perform_edit_location);

                        if (!viewnom.getText().toString().isEmpty()) {

                            if (viewdate.getText().toString().matches("^\\d{2}[ :\\.\\-\\/]{1}\\d{2}[ :\\.\\-\\/]{1}\\d{4}$")) {


                                try {



                                    try {

                                        manageBdd manager = new manageBdd(ModifyPerformActivity.this);

                                        manager.majPerform(Integer.parseInt(ModifyPerformActivity.this.id), viewnom.getText().toString(), Integer.parseInt(viewset1j.getText().toString()), Integer.parseInt(viewset1a.getText().toString()), Integer.parseInt(viewset2j.getText().toString()), Integer.parseInt(viewset2a.getText().toString()), Integer.parseInt(viewset3j.getText().toString()), Integer.parseInt(viewset3a.getText().toString()), getVictory(viewvictory), viewlocation.getText().toString(), getUSDate(viewdate.getText().toString()));

                                        Toast.makeText(ModifyPerformActivity.this, getResources().getString(R.string.modify_done), Toast.LENGTH_LONG).show();
                                    } catch (SQLiteException E) {

                                        Log.i("Erreur SQL", "Erreur : " + E.getMessage());

                                    }

                                } catch (SQLiteException E) {

                                    Log.i("Erreur SQL", "Erreur : " + E.getMessage());

                                }

                            } else {

                                Toast.makeText(ModifyPerformActivity.this, getResources().getString(R.string.need_good_date), Toast.LENGTH_LONG).show();
                            }


                        } else {

                            Toast.makeText(ModifyPerformActivity.this, getResources().getString(R.string.need_name), Toast.LENGTH_LONG).show();
                        }

                        dialog.cancel();
                    }
                });

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
//press cancel end the activity
    public void onCancelButtonClicked(View view){

        finish();
    }



    public String getVictory(CheckBox check) {


        if (check.isChecked()) {

            return "1";
        } else {

            return "-1";
        }


    }

    //to make dateTime conversion easily
    public String getUSDate(String date) {


            if(!date.isEmpty()&& date.matches("^\\d{2}[ :\\.\\-\\/]{1}\\d{2}[ :\\.\\-\\/]{1}\\d{4}$")){

                Log.i("Chainecarac", date);

                String day= date.substring(0,2);

                Log.i("jour", day);

                String month = date.substring(3,5);
                Log.i("moi", month);

                String year = date.substring(6,10);


                String dateFormated =  year+"-"+month+"-"+day+" 00:00:00";

                return dateFormated;
            }else{

                return "";
            }

    }

    public String getFrenchDate(String date){

        String dateDump= date.substring(0,10);
Log.i(" date N", dateDump);
        String year= dateDump.substring(0,4);

        String month = dateDump.substring(5,7);

        String day = dateDump.substring(8,10);

        String dateFormated= day+" "+month+" "+year;

return dateFormated;
    }

}
