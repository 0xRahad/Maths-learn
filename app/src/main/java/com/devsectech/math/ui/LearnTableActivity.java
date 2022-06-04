package com.devsectech.math.ui;

import android.content.Intent;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.devsectech.math.R;
import com.devsectech.math.adapter.BottomPagerAdapter;
import com.devsectech.math.adapter.TopPagerAdapter;
import com.devsectech.math.model.LearnModel;
import com.devsectech.math.utils.CenteredToolbar;
import com.devsectech.math.utils.Constant;
import com.google.android.material.tabs.TabLayout;

import static com.devsectech.math.utils.Constant.setDefaultLanguage;


public class LearnTableActivity extends AppCompatActivity {

    BottomPagerAdapter bottomPagerAdapter;
    ViewPager topViewPager;
    ViewPager bottomViewPager;
    TabLayout tabLayout;
    CardView btn_play;
    private int table_no = 1;
    private int table_page = 0;
    public static int height;
    public static int width;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultLanguage(this);
        setContentView(R.layout.activity_learn_table);
        init();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LearnTableActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void init() {


        if (Constant.getLearnModel(this) != null) {
            table_no = Constant.getLearnModel(this).table_no;
            table_page = Constant.getLearnModel(this).table_page;
            if (table_no == 0) {
                table_no = 1;
            }
        }


        CenteredToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.learn_table));
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(LearnTableActivity.this, MainActivity.class);
            startActivity(intent);
        });

        tabLayout = findViewById(R.id.tabLayout);
        btn_play = findViewById(R.id.btn_play);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;


        int count = 6;
        if (width == Constant.DEVICE_1080) {
            count = 7;
        } else if (width == Constant.DEVICE_720) {
            count = 8;
        }


        bottomViewPager = findViewById(R.id.bottomViewPager);
        topViewPager = findViewById(R.id.topViewPager);
        topViewPager.setAdapter(new TopPagerAdapter(this, count, position -> {
            table_no = position;
            setBottomPager();
        }));


        topViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                if (position == 0) {
                    table_no = 1;

                } else {
                    table_no = 11;
                }

                setBottomPager();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setBottomPager();


        btn_play.setOnClickListener(view -> {

            LearnModel learnModel = Constant.getLearnModel(this);

            Log.e("learnModel==", "" + learnModel);
            if (learnModel != null) {
                learnModel.table_no = table_no;
                learnModel.table_page = table_page;
                Constant.saveLearnModel(this, learnModel);
                startActivity(new Intent(this, LearnQuizActivity.class));
            }


        });


    }


    public void setBottomPager() {
        bottomPagerAdapter = new BottomPagerAdapter(this, width);
        bottomPagerAdapter.setTableNo(table_no);
        bottomViewPager.setAdapter(bottomPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(bottomViewPager);
        bottomViewPager.setCurrentItem(table_page);


        bottomViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                table_page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
