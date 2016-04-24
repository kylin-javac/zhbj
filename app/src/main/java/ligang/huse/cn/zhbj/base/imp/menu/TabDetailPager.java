package ligang.huse.cn.zhbj.base.imp.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import ligang.huse.cn.zhbj.NewsDetailActivity;
import ligang.huse.cn.zhbj.R;
import ligang.huse.cn.zhbj.base.baseMenuDetailPager;
import ligang.huse.cn.zhbj.domain.NewsMenu;
import ligang.huse.cn.zhbj.domain.NewsTabean;
import ligang.huse.cn.zhbj.global.globalConstant;
import ligang.huse.cn.zhbj.utils.CacheUtils;
import ligang.huse.cn.zhbj.utils.PreferenceUtils;
import ligang.huse.cn.zhbj.view.PullToRefreshListView;
import ligang.huse.cn.zhbj.view.TopNewsViewpager;

/**
 * 页签页面
 */
public class TabDetailPager extends baseMenuDetailPager {
    private NewsMenu.NewsMenuData.NewsTabeData mpages;
    private TopNewsViewpager mViewpager;
    private final String url;
    private List<NewsTabean.newsTable.Topnews> topnews;
    private TextView topNews_tv_title;
    private CirclePageIndicator topNews_indicator;
    private PullToRefreshListView lv_lisetView;
    private List<NewsTabean.newsTable.NewsData> mNewslist;
    private String mMoreUrl;//下一页的链接
    private NewsAdapter mNewsAdapter;
    private NewsTabean fromJson;
    private String read_ids;
    private android.os.Handler mhandler;

    //通过构造函数拿到table标签内容
    public TabDetailPager(Activity activity, NewsMenu.NewsMenuData.NewsTabeData tabDetailPager) {
        super(activity);
        mpages = tabDetailPager;
        url = globalConstant.SEVER_URL + "/zhbj" + mpages.getUrl();
        Log.i("-----> ", url);
    }

    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.pager_table_detail, null);
        View header = View.inflate(mactivity, R.layout.list_item_header, null);
        mViewpager = (TopNewsViewpager) header.findViewById(R.id.vp_top_news);
        topNews_tv_title = (TextView) header.findViewById(R.id.topNews_tv_title);
        topNews_indicator = (CirclePageIndicator) header.findViewById(R.id.topNews_indicator);
        lv_lisetView = (PullToRefreshListView) view.findViewById(R.id.lv_listView);
        //给listview添加item点击事件(将点击过后的文本设置为灰色)
        lv_lisetView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int count = lv_lisetView.getHeaderViewsCount();
                position = position - count;
                read_ids = PreferenceUtils.getString(mactivity, "read_ids", "");
                if (!read_ids.contains(fromJson.getData().getNews().get(position).getId() + "")) {
                    read_ids += fromJson.getData().getNews().get(position).getId() + ",";
                    PreferenceUtils.SetString(mactivity, "read_ids", read_ids);
                }
                TextView tv_title = (TextView) view.findViewById(R.id.tv_news_title);
                tv_title.setTextColor(Color.GRAY);
                //跳转到新闻详情页
                Intent intent = new Intent(mactivity, NewsDetailActivity.class);
                //将url地址通过intent传递给新闻详情页
                intent.putExtra("url", fromJson.getData().getNews().get(position).getUrl());
                mactivity.startActivity(intent);


            }
        });


        //设置监听事件
        lv_lisetView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListern() {
            @Override
            public void OnRefresh() {
                DataDoland();
            }

            @Override
            public void OnloadMore() {
                if (!TextUtils.isEmpty(mMoreUrl)) {
                    //表示下一页有数据
                    getMoreData();
                } else {
                    //表示下一页没有数据
                    Toast.makeText(mactivity, "没有更多了...", Toast.LENGTH_SHORT).show();
                    lv_lisetView.Competeled(true);
                }
            }

        });
        lv_lisetView.addHeaderView(header);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //读缓存
        String cache = CacheUtils.getCache(url, mactivity);
        if (!TextUtils.isEmpty(cache)) {
            processJsonData(cache, false);
        }
        DataDoland();
    }

    public void DataDoland() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                processJsonData(json, false);
                CacheUtils.setCache(url, json, mactivity);//写缓存
                Log.i("----->", "onSuccess: 获取成功了");
                lv_lisetView.Competeled(true);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("----->", "onSuccess: 获取失败了了");
                lv_lisetView.Competeled(false);
            }
        });
    }

    //解析json数据
    private void processJsonData(String json, boolean flage) {
        Gson gson = new Gson();
        fromJson = gson.fromJson(json, NewsTabean.class);
        String More = fromJson.getData().getMore();
        if (TextUtils.isEmpty(More)) {
            mMoreUrl = null;
        } else {
            mMoreUrl = globalConstant.SEVER_URL + "/zhbj/" + More;

        }
        if (!flage) {
            //获取新闻头条信息
            topnews = fromJson.getData().getTopnews();
            if (topnews != null) {//头条信息不等于空才会显示
                mViewpager.setAdapter(new topNewsAdapter());
                topNews_indicator.setViewPager(mViewpager);//将指示器设置到viewpager中
                topNews_indicator.setSnap(true);//以快照的方式进行页面切换
                //更新新闻头条信息
                //viewpager与indicator在一起时，必须设置indicator的监听事件才生效
                topNews_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        topNews_tv_title.setText(topnews.get(position).getTitle());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
                topNews_tv_title.setText(topnews.get(0).getTitle());//默认设置第一个
                topNews_indicator.onPageSelected(0);//默认第一个选中
                mNewslist = fromJson.getData().getNews();//获取新闻列表
                if (mNewslist != null) {
                    mNewsAdapter = new NewsAdapter();
                    lv_lisetView.setAdapter(mNewsAdapter);
                }


                //使用handle的方式实现图片轮播的效果
                if (mhandler == null) {
                    mhandler = new android.os.Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            int currentItem = mViewpager.getCurrentItem();
                            currentItem++;
                            if (currentItem > topnews.size() - 1) {
                                currentItem = 0;
                            }
                            mViewpager.setCurrentItem(currentItem);
                            mhandler.sendEmptyMessageDelayed(0, 2000);
                        }
                    };
                    mhandler.sendEmptyMessageDelayed(0, 2000);

                    //监听viewpager的触摸点击事件，是否调用handle发送消息
                    mViewpager.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    mhandler.removeCallbacksAndMessages(null);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    mhandler.sendEmptyMessageDelayed(0, 2000);
                                    break;
                                case MotionEvent.ACTION_CANCEL:
                                    mhandler.sendEmptyMessageDelayed(0, 2000);
                                    break;
                            }
                            return false;
                        }
                    });
                }


            }
        } else {
            //加载更多的数据
            List<NewsTabean.newsTable.NewsData> datas = fromJson.getData().getNews();
            mNewslist.addAll(datas);
            mNewsAdapter.notifyDataSetChanged();
        }


    }

    /**
     * 获取下一页数据
     */
    public void getMoreData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                processJsonData(json, true);
                lv_lisetView.Competeled(true);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("----->", "onSuccess: 获取失败了了");
                lv_lisetView.Competeled(true);
            }

        });
    }

    //listview适配器
    class NewsAdapter extends BaseAdapter {
        private BitmapUtils bitmapUtils;

        public NewsAdapter() {
            bitmapUtils = new BitmapUtils(mactivity);
            bitmapUtils.configDefaultLoadingImage(R.mipmap.news_pic_default);//设置默认图片
        }


        @Override
        public int getCount() {
            return mNewslist.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewslist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder Holder;
            if (convertView == null) {
                convertView = View.inflate(mactivity, R.layout.list_item_news, null);
                Holder = new ViewHolder();
                Holder.image_news_icon = (ImageView) convertView.findViewById(R.id.image_news_icon);
                Holder.tv_news_title = (TextView) convertView.findViewById(R.id.tv_news_title);
                Holder.tv_news_data = (TextView) convertView.findViewById(R.id.tv_news_data);
                convertView.setTag(Holder);
            } else {
                Holder = (ViewHolder) convertView.getTag();
            }
            NewsTabean.newsTable.NewsData newsData = (NewsTabean.newsTable.NewsData) getItem(position);
            Holder.tv_news_title.setText(newsData.getTitle());
            Holder.tv_news_data.setText(newsData.getPubdate());
            bitmapUtils.display(Holder.image_news_icon, newsData.getListimage());
            //判断文本是否被点击过
            String read_ids = PreferenceUtils.getString(mactivity, "read_ids", "");
            if (read_ids.contains(newsData.getId() + "")) {
                Holder.tv_news_title.setTextColor(Color.GRAY);
            } else {
                Holder.tv_news_title.setTextColor(Color.BLACK);
            }
            return convertView;
        }


    }

    static class ViewHolder {
        public ImageView image_news_icon;
        public TextView tv_news_title, tv_news_data;
    }

    //viewPager适配器
    class topNewsAdapter extends PagerAdapter {
        private BitmapUtils bitmapUtils;

        public topNewsAdapter() {
            bitmapUtils = new BitmapUtils(mactivity);
            bitmapUtils.configDefaultLoadingImage(R.mipmap.news_pic_default);//设置默认图片
        }

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mactivity);
            view.setScaleType(ImageView.ScaleType.FIT_XY);//设置宽高填充父窗体
            bitmapUtils.display(view, topnews.get(position).getTopimage());//下载图片并填充到imageView中
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
