package fr.ldnr.androtennis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Unkof on 20/04/2017.
 */

public class manageBdd extends SQLiteOpenHelper
{
    public manageBdd(Context context)
    {
        super(context,"alert",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE donnees(id INTEGER PRIMARY KEY, noma VARCHAR(30), set1j TINYINT, set1a TINYINT, set2j TINYINT, set2a TINYINT, set3j TINYINT, set3a TINYINT, resultat TINYINT, lieu VARCHAR(30), date DATETIME");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public int insertPerform(String noma, int set1j, int set1a, int set2j, int set2ja, int set3j, int set3a, boolean result, String lieu, String dateTime)
    {
        int nb=0;
        return nb;
    }

}
