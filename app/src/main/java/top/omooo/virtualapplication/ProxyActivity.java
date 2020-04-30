package top.omooo.virtualapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;

import top.omooo.stander.ActivityInterface;

/**
 * Author: Omooo
 * Date: 2020/4/29
 * Desc: 插件代理 Activity
 */
public class ProxyActivity extends Activity {

    public static final String EXT_CLASS_NAME = "ext_class_name";
    public static final String EXT_ACTION = "ext_action";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取到真正要启动的插件 Activity，然后执行 onCreate 方法
        String className = getIntent().getStringExtra(EXT_CLASS_NAME);
        try {
            Class clazz = getClassLoader().loadClass(className);
            ActivityInterface activityInterface = (ActivityInterface) clazz.newInstance();
            activityInterface.insertAppContext(this);
            activityInterface.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(this).getClassLoader();
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra(EXT_CLASS_NAME);
        Intent proxyIntent = new Intent(this, ProxyActivity.class);
        proxyIntent.putExtra(EXT_CLASS_NAME, className);
        super.startActivity(proxyIntent);
    }

    @Override
    public ComponentName startService(Intent intent) {
        String className = intent.getStringExtra(EXT_CLASS_NAME);
        Intent proxyIntent = new Intent(this, ProxyService.class);
        proxyIntent.putExtra(EXT_CLASS_NAME, className);
        return super.startService(proxyIntent);
    }

    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        String receiverName = receiver.getClass().getName();
        return super.registerReceiver(new ProxyReceiver(receiverName), filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);
    }
}
