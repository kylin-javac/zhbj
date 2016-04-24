package ligang.huse.cn.zhbj.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import ligang.huse.cn.zhbj.Main0Activity;
import ligang.huse.cn.zhbj.R;
import ligang.huse.cn.zhbj.base.basePager;
import ligang.huse.cn.zhbj.base.imp.GovPager;
import ligang.huse.cn.zhbj.base.imp.HomePager;
import ligang.huse.cn.zhbj.base.imp.NewsPager;
import ligang.huse.cn.zhbj.base.imp.SettingPager;
import ligang.huse.cn.zhbj.base.imp.SmartPager;
import ligang.huse.cn.zhbj.view.LgViewPager;

/**
 * 主面板Fragment
 */
public class MainContentFragment extends  BaseFragment {

    private LgViewPager vp_main;
    private ArrayList<basePager>mpagers;
    private RadioGroup rg_group;


    @Override
    public View initView() {
        View view = View.inflate(activity, R.layout.main_fragment, null);
        vp_main = (LgViewPager) view.findViewById(R.id.vp_main);
        rg_group = (RadioGroup) view.findViewById(R.id.rg_group);


        return view;
    }
    //禁用侧边栏或开启侧边栏
    private void setSlidmenuEnable(boolean enable) {
        Main0Activity mainUI= (Main0Activity) activity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();//得到slidingMenu
        if(enable) {
            slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_FULLSCREEN);//将viewpager设置为可用
        }else{
            slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_NONE);//将viewpager设置为禁用

        }

    }


    @Override
    public void initDate() {
       mpagers=new ArrayList<>();
        mpagers.add(new HomePager(activity));
        mpagers.add(new NewsPager(activity));
        mpagers.add(new GovPager(activity));
        mpagers.add(new SmartPager(activity));
        mpagers.add(new SettingPager(activity));
        vp_main.setAdapter(new MyViewpager());
        //给radioGroup添加监听事件
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        vp_main.setCurrentItem(0,false);//设置当前显示的viewpager
                        mpagers.get(0).initDate();//现在才初始化数据区
                        setSlidmenuEnable(false);
                        break;
                    case R.id.rb_news:
                        vp_main.setCurrentItem(1,false);
                        mpagers.get(1).initDate();
                        setSlidmenuEnable(true);
                        break;
                    case R.id.rb_smart:
                        vp_main.setCurrentItem(2,false);
                        mpagers.get(2).initDate();
                        setSlidmenuEnable(true);
                        break;
                    case R.id.rb_gov:
                        vp_main.setCurrentItem(3,false);
                        mpagers.get(3).initDate();
                        setSlidmenuEnable(true);
                        break;
                    case R.id.rb_setting:
                        vp_main.setCurrentItem(4,false);
                        mpagers.get(4).initDate();
                        setSlidmenuEnable(false);
                        break;


                }
            }
        });
        mpagers.get(0).initDate();
        setSlidmenuEnable(false);
    }


    class MyViewpager extends PagerAdapter{

        @Override
        public int getCount() {
            return mpagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
                basePager pager=mpagers.get(position);
                View view =pager.Rootview;
                container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }

    public NewsPager getNewsCenterPager(){
        NewsPager pager = (NewsPager) mpagers.get(1);
        return  pager;


    }

}
