package ligang.huse.cn.zhbj;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
