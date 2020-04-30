package top.omooo.plugin_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Author: Omooo
 * Date: 2020/4/30
 * Version: 
 * Desc: 静态广播
 */
public class TestStaticReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "TestStaticReceiver onReceive 回调完成", Toast.LENGTH_SHORT).show();
    }
}
