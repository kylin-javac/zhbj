package ligang.huse.cn.zhbj.base;


import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ligang.huse.cn.zhbj.Main0Activity;
import ligang.huse.cn.zhbj.R;

public class basePager {
    public Activity mactivity;
    public FrameLayout fl_content;
    public TextView tv_title;
    public ImageButton btn_menu;
    public View Rootview;
    public ImageView btn_photo;

    public basePager(Activity activity){
        mactivity=activity;
        Rootview = initView();
    }


    public View initView(){
        View view=View.inflate(mactivity, R.layout.base_pager,null);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        btn_menu = (ImageButton) view.findViewById(R.id.btn_menu);
        btn_photo= (ImageView) view.findViewById(R.id.btn_photo);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();//调用开关的功能
            }
        });
        return view;
    }


    public void initDate(){



    }

    //给btn_menu设置开关按钮功能
    public void toggle(){
        Main0Activity ManUI= (Main0Activity) mactivity;
        SlidingMenu slidingMenu = ManUI.getSlidingMenu();
        slidingMenu.toggle();//具有开关的功能


    }

}
