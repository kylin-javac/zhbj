package ligang.huse.cn.zhbj.view;

import android.content.Context;
import android.text.Selection;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ligang.huse.cn.zhbj.R;

/**
 * 自定义下拉刷新的listview
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener {
    public static final int STATE_PULL_TO_REFESH = 1;//下拉刷新
    public static final int STATE_RELEASE_TO_REFESH = 2;//松开刷新
    public static final int STATE_REFRESHING = 3;//正在刷新
    private int mcurrent = STATE_PULL_TO_REFESH;


    private View mheaderView;
    private int startY = -1;
    private int endY;
    private int mheaderViewHeight;
    private RotateAnimation up;
    private RotateAnimation down;
    private TextView tv_title;
    private TextView tv_time;
    private ProgressBar pb_loading;
    private ImageView iv_arrow;
    private View mfootView;
    private OnRefreshListern mOnfreshList;
    private int mfootViewHeight;
    private boolean onloadMore;

    public PullToRefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFootView();

    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFootView();

    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFootView();

    }


    public void Animation() {
        //向上动画
        up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        up.setDuration(200);
        up.setFillAfter(true);

        //向下动画
        down = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        down.setDuration(200);
        down.setFillAfter(true);


    }

    public void initHeaderView() {
        mheaderView = View.inflate(getContext(), R.layout.pull_to_head_refres, null);
        tv_title = (TextView) mheaderView.findViewById(R.id.tv_title);
        tv_time = (TextView) mheaderView.findViewById(R.id.tv_time);
        pb_loading = (ProgressBar) mheaderView.findViewById(R.id.pb_loading);
        iv_arrow = (ImageView) mheaderView.findViewById(R.id.iv_arrow);
        // 隐藏头布局(首先要把布局先画出来，然后再测量高度)
        mheaderView.measure(0, 0);
        mheaderViewHeight = mheaderView.getMeasuredHeight();
        Log.i("---->", ":" + mheaderViewHeight);
        mheaderView.setPadding(0, -mheaderViewHeight, 0, 0);
        this.addHeaderView(mheaderView);//添加头布局
        Animation();
    }

    /**
     * 初始化脚布局
     */
    public void initFootView(){
        mfootView = View.inflate(getContext(), R.layout.pull_to_foot_refres, null);
        mfootView.measure(0,0);
        mfootViewHeight = mfootView.getMeasuredHeight();
        mfootView.setPadding(0,-mfootViewHeight,0,0);
        this.addFooterView(mfootView);
        this.setOnScrollListener(this);//listView自己本身的滑动监听
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = (int) ev.getY();
                }
                endY = (int) ev.getY();
                int dy = endY - startY;
                if (dy > 0 && getFirstVisiblePosition() == 0) {//这句话的意思表示当前滑动的距离大于0并且是第一个选项时，才开始下拉刷新
                    int padding = dy - mheaderViewHeight;//这句话表示的意思是用滑动的距离减去头布局原来隐藏的高度，就得到了在屏幕显示的距离
                    mheaderView.setPadding(0, padding, 0, 0);
                    if (padding > 0 && mcurrent != STATE_RELEASE_TO_REFESH) {
                        Log.i("---->", ":" + "STATE_RELEASE_TO_REFESH");
                        //改为"松开刷新"
                        mcurrent = STATE_RELEASE_TO_REFESH;
                        refreshState();
                    } else if (padding < 0 && mcurrent != STATE_PULL_TO_REFESH) {
                        //改为"下拉刷新"
                        mcurrent = STATE_PULL_TO_REFESH;
                        refreshState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mcurrent == STATE_RELEASE_TO_REFESH) {
                    mcurrent = STATE_REFRESHING;//在松开手的时候还处于"松开刷新"状态，就改状态为"正在刷新"
                    refreshState();
                    mheaderView.setPadding(0, 0, 0, 0);
                    /**
                     * 第四步进行回调
                     */
                    if (mOnfreshList != null) {
                        mOnfreshList.OnRefresh();
                    }
                } else if (mcurrent == STATE_PULL_TO_REFESH) {
                    //在松开手的时候就已经处于"下拉刷新"状态，那么就不必要刷新了
                    mheaderView.setPadding(0, -mheaderViewHeight, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void refreshState() {
        switch (mcurrent) {
            case STATE_PULL_TO_REFESH://"下拉刷新"
                tv_title.setText("下拉刷新");
                iv_arrow.setVisibility(VISIBLE);
                pb_loading.setVisibility(INVISIBLE);
                iv_arrow.startAnimation(down);
                break;
            case STATE_RELEASE_TO_REFESH://"松开刷新"
                tv_title.setText("松开刷新");
                iv_arrow.setVisibility(VISIBLE);
                pb_loading.setVisibility(INVISIBLE);
                iv_arrow.startAnimation(up);
                break;
            case STATE_REFRESHING://"正在刷新"
                tv_title.setText("正在刷新");
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(INVISIBLE);
                pb_loading.setVisibility(VISIBLE);
                break;
        }

    }

    //监听完成之后该做的事
    public void Competeled(boolean success) {
        if(!onloadMore){
            mheaderView.setPadding(0, -mheaderViewHeight, 0, 0);
            pb_loading.setVisibility(INVISIBLE);
            tv_title.setText("下拉刷新");
            iv_arrow.setVisibility(VISIBLE);
            mcurrent = STATE_PULL_TO_REFESH;
            if (success) {
                updataTime();
            }
        }else{
            mfootView.setPadding(0,-mfootViewHeight,0,0);
            onloadMore=false;
        }

    }

    //更新时间
    private void updataTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        tv_time.setText(time);
    }

    /**
     * 第二步对外提供一个方法
     */
    public void setOnRefreshListener(OnRefreshListern mlisten) {
        mOnfreshList = mlisten;
    }

    /**
     * 滑动状态发生改变
     * @param view
     * @param scrollState
     */

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE){
            if(getLastVisiblePosition()==getCount()-1&&!onloadMore){
                onloadMore=true;
                Log.i("----->", "加载更多...... ");
                mfootView.setPadding(0,0,0,0);
                setSelection(getCount()-1);
                //通知主页面加载数据
                if(mOnfreshList!=null){
                    mOnfreshList.OnloadMore();
                }

            }
        }

    }

    /**
     * 滑动过程回调
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 第一步定义一个接口
     */
    public interface OnRefreshListern {
         void OnRefresh();
        void OnloadMore();
    }
}
