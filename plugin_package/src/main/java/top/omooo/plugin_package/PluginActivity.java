package top.omooo.plugin_package;

import android.os.Bundle;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(mHostActivity, "Plugin Activity", Toast.LENGTH_SHORT).show();
    }
}
