package fr.ldnr.androtennis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

/**
 * Created by Philip on 23/04/2017.
 */

public class PerformTestActivity extends Activity {

    private int numbertoStore = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.performtest);


        ListView list = (ListView) findViewById(R.id.list_performs_items);

        ArrayList<String> arrayData = new ArrayList<String>();
        final ArrayList<String> idArray = new ArrayList<String>();
        try {
            manageBdd manager = new manageBdd(this);

            SQLiteDatabase db = manager.getReadableDatabase();

            Cursor c = db.rawQuery("Select * from donnees order by dateTime desc", null);

            int number = c.getCount();
            numbertoStore = c.getCount();

            if (c.moveToFirst()) ;

            for (int i = 0; i < number; i++) {
                String text = "";


                    text += getResources().getString(R.string.home_adversary)+c.getString(c.getColumnIndex("noma")) + "\n";
                    text += getResources().getString(R.string.score_set1j)+c.getString(c.getColumnIndex("set1j")) + "\n";
                text += getResources().getString(R.string.score_set1a)+c.getString(c.getColumnIndex("set1a")) + "\n";
                text += getResources().getString(R.string.score_set2j)+c.getString(c.getColumnIndex("set2j")) + "\n";
                text += getResources().getString(R.string.score_set2a)+c.getString(c.getColumnIndex("set2a")) + "\n";
                text += getResources().getString(R.string.score_set3j)+c.getString(c.getColumnIndex("set3j")) + "\n";
                text += getResources().getString(R.string.score_set3a)+c.getString(c.getColumnIndex("set3a")) + "\n";
            if(c.getString(c.getColumnIndex("result")).equals("1")){
                text += getResources().getString(R.string.result_victory) + "\n";
            }else{
                text += getResources().getString(R.string.result_loose) + "\n";
            }
            text += getResources().getString(R.string.home_date)+c.getString(c.getColumnIndex("dateTime")) + "\n";
                text += getResources().getString(R.string.location)+c.getString(c.getColumnIndex("lieu")) + "\n";

                arrayData.add(text);
                idArray.add(c.getString(c.getColumnIndex("id")));
                c.moveToNext();
            }

            c.close();
            db.close();

        } catch (SQLiteException E) {

            Log.i("Erreur SQL", E.getMessage());
        }

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayData);
        list.setAdapter(aa);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = parent.getItemAtPosition(position).toString();
                Log.i("donnée clic : ", value + " " + position);
                String idtosend = idArray.get(position);

                Intent intent = new Intent(PerformTestActivity.this, PerformShowActivity.class);
                intent.putExtra("id", idtosend);
                startActivityForResult(intent, 0);
            }
        });
    }


}
