package ligang.huse.cn.zhbj.utils;

import android.content.Context;

/**
 * 像素与dp之间的相互转化
 */
public class DensityUtils {

    public  static int  dipTopx(float dip, Context context){
        float density = context.getResources().getDisplayMetrics().density;//拿到设备密度
        int  px= (int) (density*dip+0.5f);
        return  px;
    }

    public static float pxTodip(int px,Context context ){
        float density = context.getResources().getDisplayMetrics().density;
        float pxx = density / px;
        return pxx;

    }
}
