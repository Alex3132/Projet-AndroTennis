package misc;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Philip on 20/04/2017.
 */

public class MenuNavigation {



    public static void goToActivity(Activity currentActivity, Class newActivity){

        Intent intent = new Intent(currentActivity, newActivity);
        currentActivity.startActivity(intent);

    }
}
