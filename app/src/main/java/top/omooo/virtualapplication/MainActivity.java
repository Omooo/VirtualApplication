package top.omooo.virtualapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        PluginManager.getInstance(this).loadPlugin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(v -> startPluginActivity());
        // 注册静态广播
        findViewById(R.id.btn_register).setOnClickListener(v -> PluginManager.getInstance(this).parserApkAction());
    }

    private void startPluginActivity() {
        String dexPath = getFileStreamPath("plugin.apk").getPath();
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(dexPath, PackageManager.GET_ACTIVITIES);
        // 数组里面的第一个，其实就是 PluginActivity
        ActivityInfo activityInfo = packageInfo.activities[0];

        for (ActivityInfo info : packageInfo.activities) {
            Log.i(TAG, "ActivityInfo: " + info.name);
        }

        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra(ProxyActivity.EXT_CLASS_NAME, activityInfo.name);
        startActivity(intent);
    }
}
