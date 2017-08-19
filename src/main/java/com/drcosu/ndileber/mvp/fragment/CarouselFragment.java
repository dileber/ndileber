package com.drcosu.ndileber.mvp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.drcosu.ndileber.adapter.ImageCarouselPageAdapter;
import com.drcosu.ndileber.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.drcosu.ndileber.tools.log.ULog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class CarouselFragment extends BaseFragment{

    /**
     * 请求更新显示的View。
     */
    protected static final int MSG_UPDATE_IMAGE  = 1;
    /**
     * 请求暂停轮播。
     */
    protected static final int MSG_KEEP_SILENT   = 2;
    /**
     * 请求恢复轮播。
     */
    protected static final int MSG_BREAK_SILENT  = 3;
    /**
     * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
     * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
     * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
     */
    protected static final int MSG_PAGE_CHANGED  = 4;

    //轮播间隔时间
    protected static final long MSG_DELAY = 3000;


    ViewPager viewPager;
    private int currentItem = 0;

    private MyHandler mHandler ;

    private WeakReference<CarouselFragment> weakReference;
    private LinearLayout linearLayout;


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CarouselFragment carouselFragment = weakReference.get();
            if (carouselFragment==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (mHandler.hasMessages(MSG_UPDATE_IMAGE)){
                mHandler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    viewPager.setCurrentItem(currentItem);
                    //准备下次播放
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }







    public View imageViews(String url) {
        SimpleDraweeView simpleDraweeView  = new SimpleDraweeView(getActivity());
        simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Uri uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);
        return simpleDraweeView;
    }



    public CarouselFragment() {
        // Required empty public constructor
    }

    private final static String URL_NAME = "urlName";

    public static CarouselFragment newInstance(ArrayList<String> url) {
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(URL_NAME, url);
        CarouselFragment fragment = new CarouselFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    List<String> url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getStringArrayList(URL_NAME);
        }
    }

    List<View> point = null;
    private View point() {
        View view = new View(getActivity());
        view.setBackgroundResource(R.drawable.grey_solid_round);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
        //view.setPadding(5,5,5,5);
        params.setMargins(3, 5, 3, 5);
        view.setLayoutParams(params);
        point.add(view);
        return view;
    }

    View temp=null;

    /**
     * 设置选中颜色
     * @param i
     */
    public void setPointBackground(int i){
        if(i<0||i>=point.size()){
            return;
        }
        if(temp != null){
            if(temp.equals(point.get(i))){
                return;
            }
            temp.setBackgroundResource(R.drawable.grey_solid_round);
        }
        temp = point.get(i);
        temp.setBackgroundResource(R.drawable.red_solid_round);
    }

    @Override
    protected int layoutViewId() {
        return R.layout.fragment_carousel;
    }

    @Override
    protected View layoutView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        viewPager = getView(view,R.id.carousel_viewpager);
        linearLayout = getView(view,R.id.carousel_point);
        init();
    }

    private void init(){
        weakReference = new WeakReference<CarouselFragment>(this);
        mHandler = new MyHandler();
        point = new ArrayList<View>();
        imageAdapter();
    }

    private void imageAdapter() {
        List<View> list = new ArrayList<View>();
        for(int i = 0;i<url.size();i++){
            list.add(imageViews(url.get(i)));
            linearLayout.addView(point());
        }
        viewPager.setAdapter(new ImageCarouselPageAdapter(list));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int select = position % point.size();
                ULog.w("<><><><>"+position+select);
                setPointBackground(select);
                mHandler.sendMessage(Message.obtain(mHandler, MSG_PAGE_CHANGED, position, 0));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mHandler.sendEmptyMessage(MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });

        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间，使用户看不到边界
        //开始轮播效果
        start();

    }

    private void start(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
            }
        });

    }




    /**
     * fragment 隐藏和显示后调用
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示
            mHandler.sendEmptyMessage(MSG_KEEP_SILENT);
        } else {// 重新显示到最前端中
            mHandler.sendEmptyMessage(MSG_BREAK_SILENT);
        }
    }

    @Override
    protected void show() {

    }

    @Override
    protected void hidden() {

    }
}
