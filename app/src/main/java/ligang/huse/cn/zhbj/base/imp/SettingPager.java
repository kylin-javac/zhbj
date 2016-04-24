package ligang.huse.cn.zhbj.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import ligang.huse.cn.zhbj.base.basePager;

/**
 * 首页
 */

public class SettingPager extends basePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDate() {
        TextView text=new TextView(mactivity);
        text.setText("设置");
        text.setTextSize(22);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);
        fl_content.addView(text);
        tv_title.setText("设置详情");
        btn_menu.setVisibility(View.GONE);
    }
}
