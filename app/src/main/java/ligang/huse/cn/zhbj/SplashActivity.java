package ligang.huse.cn.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

import ligang.huse.cn.zhbj.utils.PreferenceUtils;

public class SplashActivity extends Activity {
private RelativeLayout ll_splash;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        ll_splash = (RelativeLayout) findViewById(R.id.ll_splash);

        //旋转动画
        RotateAnimation rotateAnimation=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);//设置时间
        rotateAnimation.setFillAfter(true);//保持动画结束状态

        //缩放动画
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        //渐变动画
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);//从无到有
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        //动画集合
        AnimationSet set=new AnimationSet(true);
        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        //启动动画集合
        ll_splash.startAnimation(set);

        //给动画设置监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {//动画开始

            }

            @Override
            public void onAnimationEnd(Animation animation) {//动画结束

                boolean is_first_enter= PreferenceUtils.getBoolean(SplashActivity.this,"is_first_enter",true);
                Intent intent;
                if(is_first_enter){
                    //进入新手引导页
                    intent=new Intent(SplashActivity.this,GuideActivity.class);
                }else{
                    //进入主页面
                    intent=new Intent(SplashActivity.this,Main0Activity.class);
                }
                startActivity(intent);
                finish();//当前页面销毁
            }

            @Override
            public void onAnimationRepeat(Animation animation) {//动画重置

            }
        });



    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
