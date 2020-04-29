package top.omooo.stander;

import android.app.Service;
import android.content.Intent;

/**
 * @author Omooo
 * @version v1.0
 * @Date 2020/04/29
 * desc :
 */
public interface ServiceInterface {

    void insertAppContext(Service hostService);

    void onCreate();

    int onStartCommand(Intent intent, int flags, int startId);

    void onDestroy();
}
