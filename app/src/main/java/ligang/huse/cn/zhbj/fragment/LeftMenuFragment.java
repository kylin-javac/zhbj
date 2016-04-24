package ligang.huse.cn.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import ligang.huse.cn.zhbj.Main0Activity;
import ligang.huse.cn.zhbj.R;
import ligang.huse.cn.zhbj.base.imp.NewsPager;
import ligang.huse.cn.zhbj.domain.NewsMenu;

/**
 * 左侧Fragment
 */
public class LeftMenuFragment extends BaseFragment {

    private static ListView lv_leftMenu;
    private  ArrayList<NewsMenu.NewsMenuData> newsMenuDatas;
    private int  mCurrment;
    private LeftMenuFragment.leftMenuListview leftMenuListview;
    private static TextView tv_menu_title;

    @Override
    public View initView() {
        View view = View.inflate(activity, R.layout.left_fragment, null);
        lv_leftMenu = (ListView) view.findViewById(R.id.lv_leftMenu);

        return view;
    }

    @Override
    public void initDate() {
    }

    //填充左侧面板数据
    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> data){
        mCurrment=0;
        newsMenuDatas=data;
    //填充数据
        leftMenuListview = new leftMenuListview();
        lv_leftMenu.setAdapter(leftMenuListview);
        //添加lisview条目的单击事件
        lv_leftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //更新选中的id
                mCurrment=position;
                //调用关闭与弹出侧面板
                toggle();
                //更新listView
                leftMenuListview.notifyDataSetChanged();
                //获取当前选中的专题详情页
                setCurrentDetailPager(position);
            }


        });


    }

    private void setCurrentDetailPager(int position) {
        Main0Activity mainUI= (Main0Activity) activity;//获取mainActivity
        MainContentFragment mainMenuFragment = mainUI.getMainMenuFragment();//通过mainActivity获取主面板
        NewsPager newsCenterPager = mainMenuFragment.getNewsCenterPager();//通过主面板获取新闻中心页面
        newsCenterPager.setCurrentDetailPager(position);//调用新闻中心的当前页
    }

    //当点击了文本后，使得可以弹出与关闭侧面板的功能
    private void toggle() {
        Main0Activity mainUI= (Main0Activity) activity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();


    }

    //自定义listview填充数据
   class leftMenuListview extends BaseAdapter{

       @Override
       public int getCount() {
           return newsMenuDatas.size();
       }

       @Override
       public Object getItem(int position) {
           return newsMenuDatas.get(position);
       }

       @Override
       public long getItemId(int position) {
           return position;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           View view = View.inflate(activity, R.layout.left_menu, null);
           tv_menu_title = (TextView) view.findViewById(R.id.tv_menu_title);
           //如果id与mCurrent一样将侧边标题设为选中状态
           if(mCurrment==position){
               tv_menu_title.setEnabled(true);
           }else{
               tv_menu_title.setEnabled(false);
           }
           tv_menu_title.setText(newsMenuDatas.get(position).getTitle());
           return view;
       }
   }

}
