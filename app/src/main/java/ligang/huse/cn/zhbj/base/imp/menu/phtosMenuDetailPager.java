package ligang.huse.cn.zhbj.base.imp.menu;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import ligang.huse.cn.zhbj.R;
import ligang.huse.cn.zhbj.base.baseMenuDetailPager;
import ligang.huse.cn.zhbj.domain.Photobean;
import ligang.huse.cn.zhbj.global.globalConstant;
import ligang.huse.cn.zhbj.utils.CacheUtils;
import ligang.huse.cn.zhbj.utils.MybitmapsUtils;

/**
 * 组图详情页
 */
public class phtosMenuDetailPager extends baseMenuDetailPager  implements View.OnClickListener{

    private ListView mLv_photos;
    private GridView mGv_photos;
    private Photobean.DataBean mDataBean;
    private List<Photobean.DataBean.NewsBean> mNews;
    private ImageView btn_phtoto;
    private boolean isListView=true;

    public phtosMenuDetailPager(Activity activity, ImageView btn_photo) {
        super(activity);
        this.btn_phtoto=btn_photo;

    }

    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.pager_photos_menu_details, null);
        mLv_photos = (ListView) view.findViewById(R.id.lv_photos);
        mGv_photos = (GridView) view.findViewById(R.id.gv_photos);
        Log.i("--2--", "initView: ");
        return view;
    }
    @Override
    public void initData() {
        btn_phtoto.setOnClickListener(this);
        String cache = CacheUtils.getCache(globalConstant.PHOTOS_URL, mactivity);
        //检查是否有缓存
        if (!TextUtils.isEmpty(cache)) {
            processData(cache);
        }
        getDataFromSever();
    }


    public void getDataFromSever() {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, globalConstant.PHOTOS_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processData(result);
                //设置缓存
                CacheUtils.setCache(globalConstant.PHOTOS_URL, result, mactivity);
                Log.i("--2--", "onSuccess: ");


            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("--2--", "onFailure: ");

            }
        });


    }

    private void processData(String result) {
        Gson gson = new Gson();
        Photobean photobean =  gson.fromJson(result, Photobean.class);
        mNews = photobean.getData().getNews();
        Log.i("--2--", "processData: "+mNews);
        mLv_photos.setAdapter(new photosListView());
        mGv_photos.setAdapter(new photosListView());

    }

    @Override
    public void onClick(View v) {
        if(isListView){
            mLv_photos.setVisibility(View.VISIBLE);
            mGv_photos.setVisibility(View.INVISIBLE);
            btn_phtoto.setImageResource(R.drawable.icon_pic_list_type);

        }else{
            mLv_photos.setVisibility(View.INVISIBLE);
            mGv_photos.setVisibility(View.VISIBLE);
            btn_phtoto.setImageResource(R.drawable.icon_pic_grid_type);

        }
        isListView=!isListView;

    }

    class photosListView extends BaseAdapter {
        private MybitmapsUtils mBitmap;
        public photosListView(){
            mBitmap=new MybitmapsUtils();
           // mBitmap.configDefaultLoadFailedImage(R.mipmap.news_pic_default);
        }

        @Override
        public int getCount() {
            return mNews.size();
        }

        @Override
        public Photobean.DataBean.NewsBean getItem(int position) {
            return mNews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                 convertView = View.inflate(mactivity, R.layout.list_photos, null);
                viewHolder=new ViewHolder();
                viewHolder.iv_image= (ImageView) convertView.findViewById(R.id.iv_image);
                viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            Photobean.DataBean.NewsBean newsBean = mNews.get(position);
            viewHolder.tv_title.setText(newsBean.getTitle());
            mBitmap.display(viewHolder.iv_image,newsBean.getListimage());
            return convertView;
        }
    }
    class  ViewHolder{
        private TextView tv_title;
        private ImageView iv_image;

    }
}
