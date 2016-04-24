package ligang.huse.cn.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager去掉滑动事件
 */
public class LgViewPager  extends ViewPager{
    public LgViewPager(Context context) {
        super(context);
    }

    public LgViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;//不拦截子控件的事件
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
