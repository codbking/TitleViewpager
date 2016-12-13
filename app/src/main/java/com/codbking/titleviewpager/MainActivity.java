package com.codbking.titleviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.codbking.library.view.TitleViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pager)
    TitleViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPager.put("aa",MyFragment.newInstance("aa"));
        mPager.put("aaaaa",MyFragment.newInstance("aaaaa"));
        mPager.put("aaaaaaaaaaa",MyFragment.newInstance("aaaaaaaaaaa"));
        mPager.put("aaaaaaaaaaaaaaa",MyFragment.newInstance("aaaaaaaaaaaaaaa"));
        mPager.put("aaaaaaaaaaaaaaaaaaaa",MyFragment.newInstance("aaaaaaaaaaaaaaaaaaaa"));
        mPager.put("aaaaaaaaaaaaaaaaaaaaaaaa",MyFragment.newInstance("aaaaaaaaaaaaaaaaaaaaaaaa"));
        mPager.put("aaaaaaaaaaaaaaaaaaaaaaaaaaaa",MyFragment.newInstance("aaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        mPager.load();
    }

}
