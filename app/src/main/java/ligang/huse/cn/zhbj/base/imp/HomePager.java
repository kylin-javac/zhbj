package ligang.huse.cn.zhbj.base.imp;

import ligang.huse.cn.zhbj.base.basePager;

/**
 * 首页
 */
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import ligang.huse.cn.zhbj.base.basePager;

public class HomePager extends basePager {
    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDate() {
        TextView text=new TextView(mactivity);
        text.setText("首页");
        text.setTextSize(22);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);
        fl_content.addView(text);
        tv_title.setText("智慧北京");
        btn_menu.setVisibility(View.GONE);
    }
}
