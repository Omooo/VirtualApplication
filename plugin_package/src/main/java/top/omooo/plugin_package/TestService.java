package top.omooo.plugin_package;

import android.content.Intent;
import android.widget.Toast;

/**
 * @author Omooo
 * @version v1.0
 * @Date 2020/04/29
 * desc :
 */
public class TestService extends BaseService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(mAppService, "TestService 已经启动...", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
