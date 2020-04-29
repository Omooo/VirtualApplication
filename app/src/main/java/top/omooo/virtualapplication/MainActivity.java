package top.omooo.virtualapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_load).setOnClickListener(v -> loadPlugin());
        findViewById(R.id.btn_start).setOnClickListener( v->startPluginActivity());
    }

    private void loadPlugin() {
        PluginManager.getInstance(this).loadPlugin();
    }

    private void startPluginActivity() {
        String dexPath = getFileStreamPath("plugin.apk").getPath();
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(dexPath, PackageManager.GET_ACTIVITIES);
        ActivityInfo activityInfo = packageInfo.activities[0];

        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra(ProxyActivity.EXT_CLASS_NAME, activityInfo.name);
        startActivity(intent);
    }
}
