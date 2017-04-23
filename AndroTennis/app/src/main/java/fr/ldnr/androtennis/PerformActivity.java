package fr.ldnr.androtennis;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import misc.MenuNavigation;

/**
 * Created by Philip on 20/04/2017.
 */

public class PerformActivity extends Activity {

    private int[] valeur=new int[10];
    private String[] lDate=new String[10];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new canvasView(this));

        int index=0;
        manageBdd data=new manageBdd(this);

        SQLiteDatabase db=data.getReadableDatabase();

        String pDate="";

        Cursor c = db.rawQuery("SELECT * from donnees ORDER BY dateTime DESC LIMIT 10", null);
        if(c.moveToFirst())
        {
            valeur[index]=Integer.parseInt(c.getString(c.getColumnIndex("result")));
            lDate[index]=c.getString(c.getColumnIndex("dateTime")).substring(0,10);
            Log.i("Info","index="+index);
            index++;
            while (c.moveToNext())
            {
                valeur[index]=Integer.parseInt(c.getString(c.getColumnIndex("result")));
                lDate[index]=c.getString(c.getColumnIndex("dateTime")).substring(0,10);
                Log.i("Info","index="+index);
                index++;
            }
        }

        if(index<10)
        {
            for(int i=index;i<10;i++)
            {
                valeur[index]=0;
                lDate[index]="";
            }
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

    private class canvasView extends View
    {
         public canvasView(Context context)
         {
             super(context);
         }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint=new Paint();

            paint.setARGB(255,0,255,0);

            float textSize=getHeight()*0.2f;
            paint.setTextSize(textSize);
            canvas.drawRGB(64,64,64);
            paint.setARGB(255,0,0,255);

            int cxLabel=10;
            //int cyLabel=80;

            for(int l=0;l<valeur.length;l++)
            {
                int larg=getWidth();
                int hauteur=getHeight();
                int sizey=getHeight()/4;
                int middley=getHeight()/2;
                float flarg=larg/10-10;
                int sizex=(larg-((int)(flarg)*10+90))/2;
                Log.i("sizeX","="+sizex);
                Log.i("Info largeur=","="+larg);
                Log.i("Info flarg","flarg="+flarg);
                if(valeur[l]==1)
                {
                    paint.setARGB(255,0,255,0);
                    canvas.drawRect(sizex+(flarg*l)+10,middley-sizey,(flarg*l)+flarg,middley,paint);
                    paint.setTextSize(larg/100);
                    cxLabel=(int) (flarg*l);
                    cxLabel+=10;
                    canvas.drawText(lDate[l],sizex+cxLabel, middley-sizey-20, paint);
                    Log.i("cxLabel","="+cxLabel);
                }
                else if(valeur[l]==-1)
                {
                    paint.setARGB(255,255,0,0);
                    canvas.drawRect(sizex+(flarg*l)+10,middley,(flarg*l)+flarg,middley+sizey,paint);
                    paint.setTextSize(larg/100);
                    cxLabel=(int) (flarg*l);
                    cxLabel+=10;
                    canvas.drawText(lDate[l],sizex+cxLabel, middley-sizey-20, paint);
                    Log.i("cxLabel","="+cxLabel);
                }
            }
            //canvas.drawRect(50.0f,50.0f,100.0f,100.0f,paint);
            Log.i("Info taille","="+getWidth()+"-"+getHeight());

        }
    }
}
