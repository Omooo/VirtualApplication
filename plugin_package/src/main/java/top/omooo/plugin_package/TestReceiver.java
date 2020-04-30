package top.omooo.plugin_package;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Author: Omooo
 * Date: 2020/4/30
 * Version: 
 * Desc: 
 */
public class TestReceiver extends BaseBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "TestReceiver onReceiver 回调完成", Toast.LENGTH_SHORT).show();
        super.onReceive(context, intent);
    }
}
