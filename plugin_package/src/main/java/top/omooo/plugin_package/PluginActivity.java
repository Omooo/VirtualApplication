package top.omooo.plugin_package;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(mHostActivity, "Plugin Activity", Toast.LENGTH_SHORT).show();

        findViewById(R.id.btn_start).setOnClickListener(
                v -> startActivity(new Intent(mHostActivity, TestActivity.class))
        );
        findViewById(R.id.btn_start_service).setOnClickListener(
                v -> startService(new Intent(mHostActivity, TestService.class))
        );
    }
}
