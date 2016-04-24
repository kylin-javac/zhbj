package ligang.huse.cn.zhbj.utils;

import android.content.Context;

/**
 * 网络缓存//sp中缓存的是以url为key，以json对象为value的 键值对
 */
public class CacheUtils {
    //设置缓存的写入 写到sp中
    public static void  setCache(String url, String json, Context context){
        PreferenceUtils.SetString(context,url,json);
    }

    //设置缓存的读入
    public  static  String getCache(String url, Context context){
        return  PreferenceUtils.getString(context,url,"");
    }




}
