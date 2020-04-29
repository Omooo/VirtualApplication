package top.omooo.stander;

import android.app.Activity;
import android.os.Bundle;

/**
 * Author: Omooo
 * Date: 2020/4/29
 * Version: 
 * Desc: 
 */
public interface ActivityInterface {

    void insertAppContext(Activity hostActivity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
