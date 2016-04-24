package ligang.huse.cn.zhbj.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网路缓存工具类
 */
public class NetcachUtils {
    private ImageView mImage;
    private Bitmap mBitmap;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCachUtils mMemoryCachUtils;
    private String mUrl;

    public NetcachUtils(LocalCacheUtils localCacheUtils,MemoryCachUtils memoryCachUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCachUtils=memoryCachUtils;

    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        new BitmapTask().execute(imageView, url);//启动AsyncTask

    }


    class BitmapTask extends AsyncTask<Object, Integer, Bitmap> {



        //预加载，运行在主线程
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("--NetcachUtils--", "onPreExecute: ");
        }

        //正在加载，运行在子线程(核心方法1)
        @Override
        protected Bitmap doInBackground(Object... params) {
            mImage = (ImageView) params[0];
            mUrl = (String) params[1];
           //mImage.setTag(mUrl);
            //下载图片
            Bitmap bitmap = download(mUrl);

            Log.i("--NetcachUtils--", "doInBackground: ");
            return bitmap;
        }

        //加载更新的进度，运行在主线程
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Log.i("--NetcachUtils--", "onProgressUpdate: ");

        }

        //加载完成后，运行在主线程(核心方法2)
        @Override
        protected void onPostExecute(Bitmap aVoid) {
            super.onPostExecute(aVoid);
            mImage.setImageBitmap(aVoid);
            mLocalCacheUtils.setLocalCache(mUrl,aVoid);//写到本地内存
            mMemoryCachUtils.setMemoryCachUtils(mUrl,aVoid);//写到内存

            Log.i("--NetcachUtils--", "onPostExecute: ");


        }
    }

    private Bitmap download(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");//设置请求方式
            conn.setConnectTimeout(5000);//设置连接超时时间
            conn.setReadTimeout(5000);//设置读取超时时间
            int code = conn.getResponseCode();//获取请求码
            if (code == 200) {
                InputStream inputStream = conn.getInputStream();
                mBitmap = BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return mBitmap;
    }
}
