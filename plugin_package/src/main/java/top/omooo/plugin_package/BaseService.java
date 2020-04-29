package top.omooo.plugin_package;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import top.omooo.stander.ServiceInterface;

/**
 * @author Omooo
 * @version v1.0
 * @Date 2020/04/29
 * desc :
 */
public class BaseService extends Service implements ServiceInterface {

    public Service mAppService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void insertAppContext(Service hostService) {
        mAppService = hostService;
    }

    @Override
    public void onCreate() {

    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    @Override
    public void onDestroy() {

    }
}
