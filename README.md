---
VirtualApplication

---

#### 项目结构

app: 宿主工程

plugin_package: 插件工程

stander: 通用接口，用于实现四大组件的生命周期管理

在每次修改插件工程时，只需要重新 build apk 生成一个 apk 文件，然后再重新运行 app 工程即可。

我会自动把插件的 apk 复制到 app 的 asset 目录下，在 app 启动的时候再把 apk 复制到 data 目录下，然后就可以用 DexClassLoader 加载了。

#### 静态代理式

目前并未实现 ContentProvider 的插件化。

核心实现原理就三点：

1. 使用 DexClassLoader 加载插件的 Apk
2. 通过代理的 Activity 去执行插件中的 Activity，加载对应的生命周期
3. 通过反射调用 AssetManager 的 addAssetPath 来加载插件中的资源

##### 源码分析

以加载插件 Activity 为例：

1. 获取 DexClassLoader

   ```java
   mPluginClassLoader = new DexClassLoader(dexPath,
                   fileRelease.getAbsolutePath(), null, mContext.getClassLoader());
   ```

   有了 ClassLoader，我们就可以加载插件中的所有的类了。

2. 启动插件的入口 Activity

   这一步主要做的就是给插件注册一个宿主的 Context。

   ```java
       // PorxyActivity
   	protected void onCreate(@Nullable Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           // 获取到真正要启动的插件 Activity，然后执行 onCreate 方法
           String className = getIntent().getStringExtra(EXT_CLASS_NAME);
           try {
               Class clazz = getClassLoader().loadClass(className);
               ActivityInterface activityInterface = (ActivityInterface) clazz.newInstance();
               // 注册宿主的 Context
               activityInterface.insertAppContext(this);
               activityInterface.onCreate(savedInstanceState);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   ```

这样其实就已经完成了 PluginActivity 的启动了，但是需要注意的是，在插件的 Activity 里面，我们不能再使用 this 了，因为插件并没有上下文环境，所以一些调用 Context 的方法都需要使用宿主的  Context 去执行，比如：

```java
        // PluginActivity
		findViewById(R.id.btn_send_static).setOnClickListener(
                v -> sendBroadcast(new Intent("static_receiver"))
        );

        // 动态广播的注册与发送
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_RECEIVER);
        registerReceiver(new TestReceiver(), intentFilter);
        Toast.makeText(mHostActivity, "TestReceiver 已经注册完成", Toast.LENGTH_SHORT).show();
        findViewById(R.id.btn_start_broadcast).setOnClickListener(
                v -> sendBroadcast(new Intent(ACTION_RECEIVER)));
```

实际上以上调用，都是扔给宿主去执行：

```java
    public View findViewById(int layoutId) {
        return mHostActivity.findViewById(layoutId);
    }

    public void startActivity(Intent intent) {
        // 扔给宿主 Activity 去启动
        Intent newIntent = new Intent();
        newIntent.putExtra("ext_class_name", intent.getComponent().getClassName());
        mHostActivity.startActivity(newIntent);
    }

    public ComponentName startService(Intent intent) {
        // 扔给宿主 Activity 去启动
        Intent newIntent = new Intent();
        newIntent.putExtra("ext_class_name", intent.getComponent().getClassName());
        return mHostActivity.startService(newIntent);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return mHostActivity.registerReceiver(receiver, filter);
    }

    public void sendBroadcast(Intent intent) {
        // 扔给宿主 Activity 去发送广播
        mHostActivity.sendBroadcast(intent);
    }
```

这个也是任玉刚的 Dynamic-Load-APK 的实现原理，它里面的 that 其实也就是指向宿主的 Context，DL 框架也被戏称为 that 框架。

启动 Service、注册动态广播其实和启动 Activity 一样，都是通过宿主的 Context 去启动，但是 DL 框架不支持静态广播。

静态广播是在应用安装的时候才会去解析并注册的，而我们插件的 Manifest 是没法注册的，所以里面的静态广播只能我们手动去解析注册，利用的是反射调用 PackageParser 的 parsePackage 方法，把静态广播都转变为动态广播，具体实现是在 PluginManager#parserApkAction 方法的实现。
