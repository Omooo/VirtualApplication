package top.omooo.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

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

    public View findViewById(int layoutId) {
        return mHostActivity.findViewById(layoutId);
    }

    public void startActivity(Intent intent) {
        // 仍给宿主 Activity 去启动
        Intent newIntent = new Intent();
        newIntent.putExtra("ext_class_name", intent.getComponent().getClassName());
        mHostActivity.startActivity(newIntent);
    }

    @Override
    public Resources getResources() {
        return mHostActivity.getResources();
    }
}
