package ligang.huse.cn.zhbj.base;

import android.app.Activity;
import android.view.View;

/**
 * 菜单详情页基类
 */
public abstract class baseMenuDetailPager {
    public Activity mactivity;
   public View rootView;

    public baseMenuDetailPager(Activity activity){
        mactivity=activity;
        rootView = initView();

    }
    public abstract View initView();
    public void initData(){


    }
}
