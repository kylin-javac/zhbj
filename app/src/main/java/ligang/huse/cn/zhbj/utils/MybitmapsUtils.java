package ligang.huse.cn.zhbj.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

/**
 * 自定义三级缓存图片
 * 原理  (1)优先从内存中加载图片(速度最快，不耗流量)
 *      (2)其次从sdcard中加载图片(速度快，不耗流量)
 *     (3)最后从网络加载图片(速度慢，浪费流量)
 */
public class MybitmapsUtils {
    private NetcachUtils mNetcachUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCachUtils mMemoryCachUtils;

    public MybitmapsUtils() {
        mLocalCacheUtils = new LocalCacheUtils();
        mMemoryCachUtils=new MemoryCachUtils();
        mNetcachUtils = new NetcachUtils(mLocalCacheUtils,mMemoryCachUtils);
    }

    public void display(ImageView imageView, String url) {
        Bitmap memoryCach = mMemoryCachUtils.getMemoryCachUtils(url);
        if(memoryCach!=null){
            imageView.setImageBitmap(memoryCach);
            Log.i("MybitmapsUtils", "我是从内存缓存来的.....");
            return;
        }

        Bitmap bitmap = mLocalCacheUtils.getLocalCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            mMemoryCachUtils.setMemoryCachUtils(url,bitmap);
            return;
        }
        mNetcachUtils.getBitmapFromNet(imageView, url);
    }
}
