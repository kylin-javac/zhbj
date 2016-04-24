package ligang.huse.cn.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义新闻头条的viewpager
 */
public class TopNewsViewpager extends ViewPager {

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public TopNewsViewpager(Context context) {
        super(context);
    }

    public TopNewsViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /**
         * true表示拦截父控件及其祖上控件的滑动事件
         * false表示不拦截父控件及其祖上的滑动事件
         *
         * 上下滑动要拦截
         * 向左滑动时当前是第一个页面时需要拦截
         * 向左滑动时当前是最后一个页面时需要拦截
         */

        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = (int) ev.getX();
                endY = (int) ev.getY();
                int dx = endX - startX;
                int dy = endY - startY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    //左右滑动
                    if (dx > 0) {
                        //向右滑
                        if (getCurrentItem() == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);//不拦截
                        }
                    } else {
                        //向左滑
                        if (getCurrentItem() == getAdapter().getCount() - 1) {
                            getParent().requestDisallowInterceptTouchEvent(false);//不拦截
                        }
                    }

                } else {

                    //上下滑动拦截父类的事件
                    getParent().requestDisallowInterceptTouchEvent(false);//不拦截

                }
                break;
        }


        return super.dispatchTouchEvent(ev);
    }
}
