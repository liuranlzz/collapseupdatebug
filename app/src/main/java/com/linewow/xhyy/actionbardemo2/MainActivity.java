package com.linewow.xhyy.actionbardemo2;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    TabLayout mtablayout;
    ViewPager mviewpager;
    ImageView avatarImg,bigImg;
    private Toolbar mainToolBar;
    private View viewPhoto,viewTopic;
    private TextView photoNum,topicNum,photoName,topicName;
    private ImageView photoImg,topicImg;
    private TextView locationTv;
    private String TAG="MainActivity";
    private  int i;
    private BlurredLayout blurredLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        mainToolBar= (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorcoltitle));
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorpurple));
        mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        mCollapsingToolbarLayout.setTitle("设置2");


        BlurredLayout layout = (BlurredLayout) findViewById(R.id.blured);
        layout.setBlurRadius(0, 50);


        mtablayout = (TabLayout) findViewById(R.id.tabs);
        mviewpager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mviewpager);

        mtablayout.setupWithViewPager(mviewpager);
        initTitleView();

        avatarImg= (ImageView) findViewById(R.id.main_avatar);
        avatarImg.setClickable(true);
        locationTv= (TextView) findViewById(R.id.main_location);
        bigImg= (ImageView) findViewById(R.id.backdrop);
        bigImg.setImageBitmap(null);
        EventBus.getDefault().register(MainActivity.this);

    }

    private void initTitleView() {
        viewPhoto=View.inflate(MainActivity.this,R.layout.item_title,null);
        viewTopic=View.inflate(MainActivity.this,R.layout.item_title,null);
        photoImg= (ImageView) viewPhoto.findViewById(R.id.item_title_img);
        topicImg= (ImageView) viewTopic.findViewById(R.id.item_title_img);
        photoName= (TextView) viewPhoto.findViewById(R.id.item_title_tv);
        photoNum= (TextView) viewPhoto.findViewById(R.id.item_title_num);
        topicName= (TextView) viewTopic.findViewById(R.id.item_title_tv);
        topicNum= (TextView) viewTopic.findViewById(R.id.item_title_num);
        topicImg.setImageResource(R.mipmap.icon_topic);
        topicName.setText("话题");
        TabLayout.Tab tagPhoto=mtablayout.getTabAt(0);
        tagPhoto.setCustomView(viewPhoto);
        TabLayout.Tab tabTopic=mtablayout.getTabAt(1);
        tabTopic.setCustomView(viewTopic);

    }

    private void setupViewPager(ViewPager mviewpager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new PhotoFrag(), "照片");
        adapter.addFragment(new ContentFrag(), "个人资料");
        mviewpager.setAdapter(adapter);
    }

    @Subscriber(tag = "photo",mode = ThreadMode.MAIN)
    public void getRefresh(String string){
        i++;
        Log.e(TAG,"刷新了"+i);
        if(i%2==1){
            Picasso.with(MainActivity.this).load("https://m.en1on1.com/upload/images/b7/6f/e0c5545ee0295adaa5466e0520b5BTeyRR_w1080.jpg").fit().centerCrop().into(bigImg);
            locationTv.setText("北京"+i);
            photoNum.setText("1");
        }else{
//            Picasso.with(MainActivity.this).load("https://m.en1on1.com/upload/images/2d/2e/fc481a41e365004d3f67c1c720b7L9Ljo8_w1080.jpg").fit().centerCrop().into(bigImg);
            locationTv.setText("北京"+i);
            bigImg.setImageBitmap(null);
            photoNum.setText("设置为空");
        }

    }


    public int DipToPixels(Context context, int dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float valueDips = dip;
        int valuePixels = (int) (valueDips * SCALE + 0.5f);
        return valuePixels;
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MainActivity.this);
    }
}
