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

public class GovPager extends basePager {
    public GovPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDate() {
        TextView text=new TextView(mactivity);
        text.setText("政府服务");
        text.setTextSize(22);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);
        fl_content.addView(text);

        btn_menu.setVisibility(View.VISIBLE);
    }
}
