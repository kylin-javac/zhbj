package ligang.huse.cn.zhbj.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 本地缓存
 */
public class LocalCacheUtils {
    public static  final  String LOACL_CACHE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/zhbj";
    private Bitmap mBitmap;

    //写本地缓存
    public void setLocalCache(String url,Bitmap bitmap){

        File dir=new File(LOACL_CACHE_PATH);
        if(!dir.exists()||!dir.isDirectory()){
            dir.mkdirs();
        }
        try {
            String filename = MD5Encoder.encode(url);
            File cachefile = new File(dir, filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(cachefile));//写入文件中
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //读本地缓存
    public Bitmap getLocalCache(String url){
        try {
            File cachefile = new File(LOACL_CACHE_PATH, MD5Encoder.encode(url));
            if(cachefile.exists()){
                mBitmap = BitmapFactory.decodeStream(new FileInputStream(cachefile));//从文件中读出来
                return  mBitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return  null;
    }


}
