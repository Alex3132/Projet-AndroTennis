package fr.ldnr.androtennis;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Unkof on 20/04/2017.
 */

public class manageBdd extends SQLiteOpenHelper
{

    public manageBdd(Context context)
    {
        super(context,"manageBdd",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL("CREATE TABLE donnees(id INTEGER PRIMARY KEY, noma TEXT, set1j INTEGER, set1a INTEGER, set2j INTEGER, set2a INTEGER, set3j INTEGER, set3a INTEGER, result INTEGER, lieu TEXT, dateTime DATETIME)");
        }
        catch (SQLiteException ex)
        {
            Log.e("InfoSQL","Erreur sqLite -> "+ex);
        }
        finally
        {
            Log.i("InfoSQL","On a passé la creation de table");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public int insertPerform(String noma, int set1j, int set1a, int set2j, int set2a, int set3j, int set3a, int result, String lieu, String dateTime)
    {

        SQLiteDatabase db=getWritableDatabase();
        try
        {
            //db.execSQL("INSERT INTO donnees(noma, set1j, set1a, set2j, set2a, set3j, set3a, result, lieu, dateTime) VALUES (?,?,?,?,?,?,?,?,?,?)", new Object[] { noma, set1j, set1a, set2j, set2a, set3j, set3a, result, lieu, dateTime }); // Requete paramétrée
            db.execSQL("INSERT INTO donnees(noma, set1j, set1a, set2j, set2a, set3j, set3a, result, lieu, dateTime) VALUES (?,?,?,?,?,?,?,?,?,?)", new Object[] { noma, set1j, set1a, set2j, set2a, set3j, set3a, result, lieu, dateTime }); // Requete paramétrée

        }
        catch (SQLiteException ex)
        {
            Log.e("InfoSQL","Erreur sqLite"+ex);
        }
        Cursor c=db.rawQuery("SELECT COUNT(*) id,noma FROM donnees",null);
        if(c.moveToFirst())        // moveToNext pour une boucle
        {
            int nb=c.getInt(0);
            c.close();
            db.close();
            return nb;
        }
        else
        {
            c.close();
            db.close();
            return 0;
        }
    }

    public boolean majPerform(int nid, String noma, int set1j, int set1a, int set2j, int set2a, int set3j, int set3a, String result, String lieu, String dateTime)
    {
        SQLiteDatabase db=getWritableDatabase();
        try
        {
            //db.execSQL("INSERT INTO donnees(noma, set1j, set1a, set2j, set2a, set3j, set3a, result, lieu, dateTime) VALUES (?,?,?,?,?,?,?,?,?,?)", new Object[] { noma, set1j, set1a, set2j, set2a, set3j, set3a, result, lieu, dateTime }); // Requete paramétrée
            db.execSQL("UPDATE donnees SET noma=?, set1j=?, set1a=?, set2j=?, set2a=?, set3j=?, set3a=?, result=?, lieu=?, dateTime=? WHERE id=? ", new Object[] { noma, set1j, set1a, set2j, set2a, set3j, set3a, result, lieu, dateTime, nid }); // Requete paramétrée

            return true;
        }
        catch (SQLiteException ex)
        {
            Log.e("InfoSQL","Erreur sqLite"+ex);
            return false;
        }

    }

    public boolean deletePerform(int nid)
    {

        SQLiteDatabase db=getWritableDatabase();
        try
        {
            db.execSQL("DELETE FROM donnees WHERE id=? ",new Object[] { nid });
            return true;

        }
        catch (SQLiteException ex)
        {
            Log.e("InfoSQL","Erreur sqLite"+ex);
            return false;

        }
    }

}
