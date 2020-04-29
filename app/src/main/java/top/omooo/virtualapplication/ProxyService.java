package top.omooo.virtualapplication;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;

import androidx.annotation.Nullable;

import top.omooo.stander.ServiceInterface;

import static top.omooo.virtualapplication.ProxyActivity.EXT_CLASS_NAME;

/**
 * @author Omooo
 * @version v1.0
 * @Date 2020/04/29
 * desc :
 */
public class ProxyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取到真正要启动的插件 Service，然后执行 onStartCommand 方法
        String className = intent.getStringExtra(EXT_CLASS_NAME);
        try {
            Class clazz = getClassLoader().loadClass(className);
            ServiceInterface serviceInterface = (ServiceInterface) clazz.newInstance();
            serviceInterface.insertAppContext(this);
            serviceInterface.onStartCommand(intent, flags, startId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(this).getClassLoader();
    }
}
