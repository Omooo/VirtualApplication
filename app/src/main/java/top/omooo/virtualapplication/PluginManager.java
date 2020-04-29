package top.omooo.virtualapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.widget.Toast;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Method;

/**
 * Author: Omooo
 * Date: 2020/4/29
 * Version: 
 * Desc: 
 */
public class PluginManager {

    private volatile static PluginManager sPluginManager;
    private Context mContext;

    public String mApkName = "plugin.apk";
    private DexClassLoader mPluginClassLoader;
    private Resources mPluginResources;

    public static PluginManager getInstance(Context context) {
        if (sPluginManager == null) {
            synchronized (PluginManager.class) {
                if (sPluginManager == null) {
                    sPluginManager = new PluginManager(context);
                }
            }
        }
        return sPluginManager;
    }

    public PluginManager(Context context) {
        mContext = context;
    }

    public void loadPlugin() {
        Utils.extractAssets(mContext, mApkName);
        File extractFile = mContext.getFileStreamPath(mApkName);
        String dexPath = extractFile.getPath();
        File fileRelease = mContext.getDir("dex", Context.MODE_PRIVATE);

        mPluginClassLoader = new DexClassLoader(dexPath,
                fileRelease.getAbsolutePath(), null, mContext.getClassLoader());
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.invoke(assetManager, dexPath);
            mPluginResources = new Resources(assetManager, mContext.getResources().getDisplayMetrics(),
                    mContext.getResources().getConfiguration());
        } catch (Exception e) {
            Toast.makeText(mContext, "加载 Plugin 失败", Toast.LENGTH_SHORT).show();
        }
    }

    public DexClassLoader getClassLoader() {
        return mPluginClassLoader;
    }

    public Resources getResources() {
        return mPluginResources;
    }
}
