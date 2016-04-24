package ligang.huse.cn.zhbj.base.imp.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import ligang.huse.cn.zhbj.Main0Activity;
import ligang.huse.cn.zhbj.R;
import ligang.huse.cn.zhbj.base.baseMenuDetailPager;
import ligang.huse.cn.zhbj.domain.NewsMenu;

/**
 * 新闻详情页
 */
public class NewsMenuDetailPager extends baseMenuDetailPager  implements ViewPager.OnPageChangeListener{
    private ViewPager mViewpager;
    private TabPageIndicator mindicator;

    private ArrayList<NewsMenu.NewsMenuData.NewsTabeData>mTabeData;//页签网络数据
    private ArrayList<TabDetailPager>mpagers;
    //通过构造函数拿到传递过来的标签数据集合
    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsMenuData.NewsTabeData> children) {
        super(activity);
        mTabeData= children;
    }


    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.pager_news_menu_detail, null);
        mViewpager= (ViewPager) view.findViewById(R.id.vp_news_menu_detail);
        mindicator= (TabPageIndicator) view.findViewById(R.id.indicator);
        return view;
    }

    //初始化页签
    @Override
    public void initData() {

        mpagers=new ArrayList<>();
        //通过循环拿到传递过来的table标签内容，并将它设置到table标签页中去
        for(int i=0;i<mTabeData.size();i++){
            TabDetailPager pager = new TabDetailPager(mactivity,mTabeData.get(i));
            mpagers.add(pager);
        }
        //设置Viewpager的适配器
        mViewpager.setAdapter(new NewsMenuDetailAdapter());
        mindicator.setViewPager(mViewpager);//将viewpager与指示绑定在一起
        mindicator.setOnPageChangeListener(this);//必须给indicator设置页面监听事件

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            //开启侧边栏
            setSlidmenuEnable(true);
        }else{
            //关闭侧边栏
            setSlidmenuEnable(false);
        }

    }
    //禁用侧边栏或开启侧边栏
    private void setSlidmenuEnable(boolean enable) {
        Main0Activity mainUI= (Main0Activity) mactivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();//得到slidingMenu
        if(enable) {
            slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_FULLSCREEN);//将viewpager设置为可用
        }else{
            slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_NONE);//将viewpager设置为禁用

        }

    }
    //给btn_next按钮添加点击事件
    @OnClick(R.id.btn_next)
    public void nextPager(View view){
        int item = mViewpager.getCurrentItem();
        item++;
        mViewpager.setCurrentItem(item);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class  NewsMenuDetailAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mpagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager detailPager = mpagers.get(position);
            View rootView = detailPager.rootView;
            container.addView(rootView);
            //更新数据
           // detailPager.initView();
            detailPager.initData();
            return rootView;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //指定指示器的标题
            String title = mTabeData.get(position).getTitle();
            return title;
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

}
