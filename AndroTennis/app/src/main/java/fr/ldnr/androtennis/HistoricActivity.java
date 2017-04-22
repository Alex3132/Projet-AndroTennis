package fr.ldnr.androtennis;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import misc.MenuNavigation;

import static java.lang.String.valueOf;

/**
 * Created by Philip on 20/04/2017.
 */

public class HistoricActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historic);

        LayoutInflater inflater=getLayoutInflater();
        TableLayout table=(TableLayout) findViewById(R.id.tablehisto);

        manageBdd data=new manageBdd(this);

        SQLiteDatabase db=data.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * from donnees ORDER BY dateTime DESC", null);
        if(c.moveToFirst())
        {
            while (c.moveToNext())
            {
                TableRow ligne=(TableRow) inflater.inflate(R.layout.table_ligne,null);
                Log.i("info","="+c.getString(c.getColumnIndex("result")));
                if(c.getString(c.getColumnIndex("result")).equals("1"))
                {
                    ligne.setBackgroundColor(Color.GREEN);
                }
                else if(c.getString(c.getColumnIndex("result"))=="-1")
                {
                    ligne.setBackgroundColor(Color.RED);
                }
                else
                {
                    ligne.setBackgroundColor(Color.BLACK);
                }

                ((TextView) ligne.findViewById(R.id.cDate)).setText(c.getString(c.getColumnIndex("dateTime")));
                ((TextView) ligne.findViewById(R.id.cNom)).setText(c.getString(c.getColumnIndex("noma")));
                ((TextView) ligne.findViewById(R.id.cSet1)).setText(c.getString(c.getColumnIndex("set1j")));
                ((TextView) ligne.findViewById(R.id.cSet1A)).setText(c.getString(c.getColumnIndex("set1a")));
                ((TextView) ligne.findViewById(R.id.cSet2)).setText(c.getString(c.getColumnIndex("set2j")));
                ((TextView) ligne.findViewById(R.id.cSet2A)).setText(c.getString(c.getColumnIndex("set2a")));
                ((TextView) ligne.findViewById(R.id.cSet3)).setText(c.getString(c.getColumnIndex("set3j")));
                ((TextView) ligne.findViewById(R.id.cSet3A)).setText(c.getString(c.getColumnIndex("set3a")));
                ((TextView) ligne.findViewById(R.id.cLieu)).setText(c.getString(c.getColumnIndex("lieu")));
                table.addView(ligne);
            }
        }
        c.close();
        db.close();

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
                MenuNavigation.goToActivity(HistoricActivity.this, HistoricActivity.class);
                return true;

            case R.id.home_menu_perform:
                MenuNavigation.goToActivity(HistoricActivity.this, PerformActivity.class);
                return true;

            case R.id.home_menu_export:
                /*TODO : Add export code */
                return true;

            case R.id.home_menu_welcome:
                MenuNavigation.goToActivity(HistoricActivity.this, HomeActivity.class);
            default:
                return false;
        }
    }
}
