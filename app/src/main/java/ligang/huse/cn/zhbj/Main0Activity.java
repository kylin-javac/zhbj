package ligang.huse.cn.zhbj;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.umeng.analytics.MobclickAgent;

import ligang.huse.cn.zhbj.fragment.LeftMenuFragment;
import ligang.huse.cn.zhbj.fragment.MainContentFragment;

public class Main0Activity extends SlidingFragmentActivity {
    public static  final String TAG_LEFT="TAG_LEFT";
    public static  final String TAG_MAIN="TAG_MAIN";


    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main0);
        setBehindContentView(R.layout.menu_layout);//设置左侧边栏
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_FULLSCREEN);//设置全屏都可以触摸
        int width = getWindowManager().getDefaultDisplay().getWidth();
        slidingMenu.setBehindOffset(width*400/728);//给屏幕预留宽度

        initFragment();

    }

    private void initFragment() {
       FragmentManager fm = getSupportFragmentManager();//得到Framgmenter
        FragmentTransaction transaction = fm.beginTransaction();//开启事务
        transaction.replace(R.id.fl_left_menu,new LeftMenuFragment(),TAG_LEFT);//替换原有的布局文件
        transaction.replace(R.id.fl_main,new MainContentFragment(),TAG_MAIN);
        transaction.commit();//提交事务
    }

    //获取左面板

     public   LeftMenuFragment  getLeftMenuFragment(){
            FragmentManager fm = getSupportFragmentManager();
        LeftMenuFragment leftMenu = (LeftMenuFragment) fm.findFragmentByTag(TAG_LEFT);
        return leftMenu;

    }
    //获取主面板

    public   MainContentFragment  getMainMenuFragment(){
        FragmentManager fm = getSupportFragmentManager();
        MainContentFragment mainMenu = (MainContentFragment) fm.findFragmentByTag(TAG_MAIN);
        return mainMenu;

    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
