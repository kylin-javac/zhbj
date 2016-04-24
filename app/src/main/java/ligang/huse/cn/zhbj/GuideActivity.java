package ligang.huse.cn.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import ligang.huse.cn.zhbj.utils.DensityUtils;
import ligang.huse.cn.zhbj.utils.PreferenceUtils;

public class GuideActivity extends Activity {

    private ViewPager vp_guide;
    private int[] images;
    private ArrayList<ImageView> imageViews;
    private LinearLayout ll_container;
    private ImageView red_point;
    private int poiondis;//两个点之间的距离
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        red_point = (ImageView) findViewById(R.id.red_point);
        btn_start = (Button) findViewById(R.id.btn_start);
        images = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
        initViewePager();
        vp_guide.setAdapter(new MyViewPager());
        //给viewpager设置监听事件
        vp_guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滑动过程中的回调
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //设置小红点的滑动效果
                int leftmargin = (int) (poiondis * positionOffset + position * poiondis);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) red_point.getLayoutParams();
                params.leftMargin = leftmargin;
                red_point.setLayoutParams(params);
            }

            @Override
            //某个页面被选中
            public void onPageSelected(int position) {
                //设置开始进入按钮是否显示
                if (position == imageViews.size() - 1) {
                    btn_start.setVisibility(View.VISIBLE);
                } else {
                    btn_start.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //视图树.测量两个圆点之间的距离
        red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


            @Override
            public void onGlobalLayout() {
                red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                poiondis = ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft();

            }
        });

        //设置开始进入按钮的点击事件
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.SetBoolean(getApplicationContext(), "is_first_enter", false);
                startActivity(new Intent(getApplicationContext(), Main0Activity.class));
                Log.i("---->", "enter: 我进入了主页面");
                finish();
            }
        });

    }



    //初始化viewPager中的数据
    public void initViewePager() {
        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundResource(images[i]);
            imageViews.add(imageView);
            //添加灰色小圆点
            ImageView point = new ImageView(getApplicationContext());
            point.setImageResource(R.drawable.shape_point_gray);
            //设置小圆点之间的距离
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = DensityUtils.dipTopx(10,this);
            }
            point.setLayoutParams(params);
            ll_container.addView(point);
        }


    }

    class MyViewPager extends PagerAdapter {


        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
