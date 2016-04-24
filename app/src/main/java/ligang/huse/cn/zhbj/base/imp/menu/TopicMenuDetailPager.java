package ligang.huse.cn.zhbj.base.imp.menu;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import ligang.huse.cn.zhbj.base.baseMenuDetailPager;

/**
 * 专题详情页.
 */
public class TopicMenuDetailPager extends baseMenuDetailPager {
    public TopicMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView textView=new TextView(mactivity);
        textView.setText("专题详情页");
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);

        textView.setTextColor(Color.RED);
        return textView;
    }
}
