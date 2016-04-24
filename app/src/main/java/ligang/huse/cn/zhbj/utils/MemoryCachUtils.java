package ligang.huse.cn.zhbj.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * 内存缓存
 */
public class MemoryCachUtils {
    //使用了软引用保存和读取数据，防止oom
    //HashMap<String,SoftReference<Bitmap>> mMemoryCachUtils=new HashMap<>();


    //LruCach--least recentlly used 最近最少使用算法
    private LruCache<String,Bitmap> mLruCache;
    public MemoryCachUtils(){
        long maxMemory = Runtime.getRuntime().maxMemory();//获取
        Log.i("MemoryCachUtils", maxMemory+"");
        mLruCache=new LruCache<String,Bitmap>((int) (maxMemory/8)){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int count = value.getByteCount();
                return count;
            }
        };
    }

    //写缓存到内存中去
    public void setMemoryCachUtils(String url, Bitmap bitmap){
        //SoftReference<Bitmap> mbitmap = new SoftReference<>(bitmap);
        // mMemoryCachUtils.put(url,mbitmap);
        mLruCache.put(url,bitmap);




    }
    //从内存中读缓存
    public Bitmap getMemoryCachUtils(String url){
//        SoftReference<Bitmap> mbitmap = mMemoryCachUtils.get(url);
//       if(mbitmap!=null){
//           Bitmap bitmap = mbitmap.get();
//           return bitmap;
//       }
        return  mLruCache.get(url);

    }


}
