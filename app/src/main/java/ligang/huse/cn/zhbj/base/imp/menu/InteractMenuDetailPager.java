package ligang.huse.cn.zhbj.base.imp.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import ligang.huse.cn.zhbj.base.baseMenuDetailPager;

/**
 * 互动详情页
 */
public class InteractMenuDetailPager extends baseMenuDetailPager {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {

        TextView textView=new TextView(mactivity);
        textView.setText("互动详情页");
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }
}
