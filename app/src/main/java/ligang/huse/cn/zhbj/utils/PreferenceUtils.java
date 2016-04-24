package ligang.huse.cn.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharedPreferences工具类
 */
public class PreferenceUtils {
    //获取boolean值
    public static boolean getBoolean(Context context,String key,boolean value){
        SharedPreferences sp=context.getSharedPreferences("config.xml",context.MODE_PRIVATE);
       return sp.getBoolean(key,value);
    }
    //设置boolean值
    public static void  SetBoolean(Context context,String key,boolean value){
        SharedPreferences sp=context.getSharedPreferences("config.xml",context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    //获取String值
    public static String getString(Context context,String key,String value){
        SharedPreferences sp=context.getSharedPreferences("config.xml",context.MODE_PRIVATE);
        return sp.getString(key,value);
    }
    //设置String值
    public static void  SetString(Context context,String key,String value){
        SharedPreferences sp=context.getSharedPreferences("config.xml",context.MODE_PRIVATE);
         sp.edit().putString(key,value).commit();
    }


    //获取int值
    public static int getint(Context context,String key,int value){
        SharedPreferences sp=context.getSharedPreferences("config.xml",context.MODE_PRIVATE);
        return sp.getInt(key,value);
    }

    //设置int值
    public static void Setint(Context context,String key,int value){
        SharedPreferences sp=context.getSharedPreferences("config.xml",context.MODE_PRIVATE);
         sp.edit().putInt(key,value);
    }


}
