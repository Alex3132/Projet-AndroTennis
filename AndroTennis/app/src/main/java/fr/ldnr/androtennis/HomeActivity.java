package fr.ldnr.androtennis;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import misc.MenuNavigation;

import static android.view.View.GONE;


/**
 * Created by Unkof on 19/04/2017.
 */

public class HomeActivity extends Activity {

    //perform ids

    private ArrayList<String> ids = new ArrayList<String>();
    //bdd name to save
    public String bddFileName = "bddAndrotennis.xml";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.home);
        //for sdk>23
        if (shouldAskPermissions()) {
            askPermissions();
        }

        //for api 11 minimum withdraw the buttons
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



            case R.id.home_menu_welcome:
                MenuNavigation.goToActivity(HomeActivity.this, HomeActivity.class);
            default:
                return false;
        }

    }
    //for the buttons < api11

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

    //export button code

    public void onExportButtonClicked(View view) {

        String xmlToWrite = getDatabaseXml();

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


            try {

                if (Build.VERSION.SDK_INT < 19) {
//to get a new file, put the time before bdd name
                    Long tsLong = System.currentTimeMillis() / 1000;
                    String ts = tsLong.toString();
                    File dir = new File(Environment.getExternalStorageDirectory() + "/Documents");
                    Boolean isPresent = true;

                    if (!dir.exists()) {
                        isPresent = dir.mkdirs();
                    }

                    if (isPresent) {
                        File f = new File(dir.getAbsolutePath(), ts + bddFileName);
                        FileOutputStream fw = new FileOutputStream(f, true);

                        fw.write(xmlToWrite.getBytes());
                        fw.close();

                        Toast.makeText(HomeActivity.this, "Données exportées : "+dir.toString()+f.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    File dir2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    dir2.mkdirs();

                    File f = new File(dir2, currentDateandTime + bddFileName);
                    FileOutputStream fw = new FileOutputStream(f, true);

                    fw.write(getDatabaseXml().getBytes());
                    fw.close();

                    Toast.makeText(HomeActivity.this, "Données exportées : "+dir2.toString()+f.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ioexc) {

                Toast.makeText(this, "Physical Storage Error", Toast.LENGTH_LONG).show();
            }

        } else {

            Log.e("HomeActivity", "noSDcard");
    }}

// to get the sqlite database as a xml string
    public String getDatabaseXml() {


        XmlSerializer serializer = Xml.newSerializer();

        StringWriter writer = new StringWriter();

        try {

            manageBdd manager = new manageBdd(this);

            SQLiteDatabase db = manager.getReadableDatabase();

            Cursor c = db.rawQuery("SELECT * from donnees", null);

            int number = c.getColumnCount();

            if (c.moveToFirst()) {
                serializer.setOutput(writer);
                serializer.startDocument("UTF-8", true);
                serializer.startTag("", "Columns");
                serializer.attribute("", "number", Integer.toString(number));
                String[] columns = c.getColumnNames();

                for (String str : columns) {

                    serializer.text(str);

                }
                serializer.endTag("", "Columns");

                for (int i = 0; i < number; i++) {
                    serializer.startTag("", c.getColumnName(i));
                    serializer.text(c.getString(i));
                    serializer.endTag("", c.getColumnName(i));


                }

                while (c.moveToNext()) {

                    for (int i = 0; i < number; i++) {
                        serializer.startTag("", c.getColumnName(i));
                        serializer.text(c.getString(i));
                        serializer.endTag("", c.getColumnName(i));


                    }

                }


            }
            serializer.endDocument();
            c.close();
            db.close();

            return writer.toString();


        } catch (SQLiteException E) {

            Log.e("Erreur SQL", "Erreur : " + E.getMessage());
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }



    }

    //for the textviews at home screen with the 5 last performs
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


        try {

            manageBdd manager = new manageBdd(this);

            SQLiteDatabase db = manager.getReadableDatabase();

            Cursor curs = db.rawQuery("SELECT count(*) noma from donnees", null);

            if (curs.moveToFirst()) {
                nb = curs.getInt(0);
                curs.close();

            } else {

                nb = 0;
            }

            Cursor c = db.rawQuery("SELECT id, noma, dateTime from donnees ORDER BY dateTime DESC LIMIT 5", null);

            if (c.moveToFirst()) {

                ids.add(c.getString(c.getColumnIndex("id")));
                tabBack.add(getResources().getString(R.string.home_adversary) + c.getString(c.getColumnIndex("noma")) + getResources().getString(R.string.home_date) + c.getString(c.getColumnIndex("dateTime")).substring(0, 10));

                while (c.moveToNext()) {
                    ids.add(c.getString(c.getColumnIndex("id")));
                    tabBack.add(getResources().getString(R.string.home_adversary) + c.getString(c.getColumnIndex("noma")) + getResources().getString(R.string.home_date) + c.getString(c.getColumnIndex("dateTime")).substring(0, 10));

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
        } catch (SQLiteException E) {

            Log.e("Erreur SQL", "Erreur : " + E.getMessage());
        }
    }

    //callback on last perform clicked

    public void OnPerformClicked(View view) {

        Intent intent = new Intent(HomeActivity.this, PerformShowActivity.class);

        switch (view.getId()) {

            case R.id.last_add_1:

                if (!ids.get(0).isEmpty()) {
                    intent.putExtra("id", ids.get(0));
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.last_add_2:
                if (!ids.get(1).isEmpty()) {
                    intent.putExtra("id", ids.get(1));
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.last_add_3:
                if (!ids.get(2).isEmpty()) {
                    intent.putExtra("id", ids.get(2));
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.last_add_4:
                if (!ids.get(3).isEmpty()) {
                    intent.putExtra("id", ids.get(3));
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.last_add_5:
                if (!ids.get(4).isEmpty()) {
                    intent.putExtra("id", ids.get(4));
                    startActivityForResult(intent, 0);
                }
                break;

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setLastAddViews();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setLastAddViews();
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


}



