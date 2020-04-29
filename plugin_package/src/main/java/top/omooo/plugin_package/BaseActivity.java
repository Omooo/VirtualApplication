package top.omooo.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import top.omooo.stander.ActivityInterface;

/**
 * Author: Omooo
 * Date: 2020/4/29
 * Version: 
 * Desc: 
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity implements ActivityInterface {

    public Activity mHostActivity;

    @Override
    public void insertAppContext(Activity hostActivity) {
        mHostActivity = hostActivity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }

    public void setContentView(int resId) {
        mHostActivity.setContentView(resId);
    }
}
