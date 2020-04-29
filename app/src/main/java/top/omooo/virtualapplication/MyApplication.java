package top.omooo.virtualapplication;

import android.app.Application;
import android.content.Context;

/**
 * Author: Omooo
 * Date: 2020/4/29
 * Version: 
 * Desc: 
 */
public class MyApplication extends Application {

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
    }

    public static Context getContext() {
        return sContext;
    }
}
