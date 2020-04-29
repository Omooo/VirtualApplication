package top.omooo.virtualapplication;

import android.app.Activity;
import android.content.Intent;
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
        // 传给自己，然后调用自己的 onCreate 方法
        Intent proxyIntent = new Intent(this, ProxyActivity.class);
        proxyIntent.putExtra(EXT_CLASS_NAME, className);
        super.startActivity(proxyIntent);
    }
}
