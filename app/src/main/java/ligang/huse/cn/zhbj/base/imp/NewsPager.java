package ligang.huse.cn.zhbj.base.imp;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import ligang.huse.cn.zhbj.Main0Activity;
import ligang.huse.cn.zhbj.base.baseMenuDetailPager;
import ligang.huse.cn.zhbj.base.basePager;
import ligang.huse.cn.zhbj.base.imp.menu.InteractMenuDetailPager;
import ligang.huse.cn.zhbj.base.imp.menu.NewsMenuDetailPager;
import ligang.huse.cn.zhbj.base.imp.menu.TopicMenuDetailPager;
import ligang.huse.cn.zhbj.base.imp.menu.phtosMenuDetailPager;
import ligang.huse.cn.zhbj.domain.NewsMenu;
import ligang.huse.cn.zhbj.fragment.LeftMenuFragment;
import ligang.huse.cn.zhbj.global.globalConstant;
import ligang.huse.cn.zhbj.utils.CacheUtils;

/**
 * 新闻页
 */
public class NewsPager extends basePager {
    private  ArrayList<baseMenuDetailPager>mMenuDetailPagers;
    private NewsMenu newsMenu;

    public NewsPager(Activity activity) {
        super(activity);
    }
    @Override
    public void initDate() {
        //设置标题内容
        tv_title.setText("新闻");
        //设置菜单按钮是否可见
        btn_menu.setVisibility(View.VISIBLE);

        //在网络请求数据之前先去判断一下缓存中又没有数据
        String cache = CacheUtils.getCache(globalConstant.CATGORY_URL, mactivity);
        if(!TextUtils.isEmpty(cache)) {
            Log.i("---->", "我是从缓存里来的 ");
            processData(cache);
        }
        //从网络请求数据
        getDatefromSever();
    }

    public void getDatefromSever() {


        HttpUtils utils=new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, globalConstant.CATGORY_URL, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String josn =  responseInfo.result;
                processData(josn);
                //写入缓存
                CacheUtils.setCache(globalConstant.CATGORY_URL,josn,mactivity);
                //Log.i("---->", "onSuccess: "+josn);
            }



            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("---->", "onFailure: "+"失败 失败");

            }
        });

    }
    //处理从服务器过来的数据(解析json数据)
    private void processData(String josn) {
        Gson gson=new Gson();
        //解析json数据
        newsMenu = gson.fromJson(josn, NewsMenu.class);
        Log.i("---->", newsMenu.toString());
        //得到mainActivity
        Main0Activity mainUI= (Main0Activity) mactivity;
        //通过mainActivity拿到左侧的fragment
        LeftMenuFragment leftMenuFragment = mainUI.getLeftMenuFragment();
        //将新闻侧边栏的数据传递给左侧面板
        leftMenuFragment.setMenuData((ArrayList<NewsMenu.NewsMenuData>) newsMenu.getData());
        //初始化4个详情页
        mMenuDetailPagers=new ArrayList<baseMenuDetailPager>();
        //将获取到的table标签内容也传递过去
        mMenuDetailPagers.add(new NewsMenuDetailPager(mactivity, (ArrayList<NewsMenu.NewsMenuData.NewsTabeData>) newsMenu.getData().get(0).getChildren()));
        mMenuDetailPagers.add(new TopicMenuDetailPager(mactivity));
        mMenuDetailPagers.add(new phtosMenuDetailPager(mactivity,btn_photo));
        mMenuDetailPagers.add(new InteractMenuDetailPager(mactivity));

        //默认将第一个设为选中页面
        setCurrentDetailPager(0);
    }

    //设置选中专题后的新闻详情页
    public void setCurrentDetailPager(int postion){
        //得到指定详情页
        baseMenuDetailPager pager = mMenuDetailPagers.get(postion);
        //得到详情页的view
        View view = pager.rootView;
        //将之前的所有内容清除
        fl_content.removeAllViews();
        //将得到的view填充到当前页的fragment中
        fl_content.addView(view);
        //更新数据
        pager.initData();
        if(pager instanceof  phtosMenuDetailPager){
            btn_photo.setVisibility(View.VISIBLE);
        }else{
            btn_photo.setVisibility(View.INVISIBLE);
        }
        //从新设置一下标题
        tv_title.setText(newsMenu.getData().get(postion).getTitle());
    }

}
