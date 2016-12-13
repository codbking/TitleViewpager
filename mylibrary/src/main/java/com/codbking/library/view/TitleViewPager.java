package com.codbking.library.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


import com.codbking.library.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by codbking on 2016/4/8.
 */
public class TitleViewPager extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private PageTitleAdapter mTitelPageAdapter;
    private LinearLayout mIndicator;
    private TitleLayout mTitles;
    private HorizontalScrollView scrollView;

    public enum Type {
        FIT,//适应宽度,会根据宽度平分
        FIX,//固定宽度
        WRAPPER;//自使用内容

        static Type getType(int value) {
            switch (value) {
                case 0:
                    return Type.FIT;
                case 1:
                    return Type.FIX;
                case 2:
                    return Type.WRAPPER;
            }
            return Type.FIT;
        }
    }

    private Type mType = Type.WRAPPER;

    private int indicatorColor=-1;
    private int indicatorHeight;
    private int titleWidth = -1;

    private List<TitlePageBar> mTitleBars = new ArrayList<>();

    public TitleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.TitleViewPager);

        indicatorColor = a.getColor(R.styleable.TitleViewPager_tvp_indicatorColor, -1);
        indicatorHeight = (int) a.getDimension(R.styleable.TitleViewPager_tvp_indicatorHeight, Utils.px(2));
        mType = Type.getType(a.getInt(R.styleable.TitleViewPager_tvp_pager_type, 0));
        titleWidth = a.getDimensionPixelOffset(R.styleable.TitleViewPager_tvp_titleWidth, -1);
        a.recycle();
        initLayout();
    }

    public void put(String title, Fragment fragment) {
        mTitleBars.add(new TitlePageBar(title, fragment));
    }

    public void load() {
        setData(mTitleBars);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public void setData(List<TitlePageBar> data) {
        if (mTitelPageAdapter == null) {
            mTitelPageAdapter = new PageTitleAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), data);
            mViewPager.setAdapter(mTitelPageAdapter);
            init();
        } else {
            mTitelPageAdapter.setData(data);
            init();
        }
    }

    private void initLayout() {
        View rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.widget_title_viewpager
                        , null);

        this.scrollView = (HorizontalScrollView) rootView.findViewById(R.id.scrollView);
        mIndicator = (LinearLayout) rootView.findViewById(R.id.container_indicator);
        mTitles = (TitleLayout) rootView.findViewById(R.id.container_title);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.addOnPageChangeListener(mTitles);
        addView(rootView);

        //setvalue
        mTitles.setIndicatorColor(indicatorColor);
        mTitles.setIndicatorHeight(indicatorHeight);
    }


    public LinearLayout.LayoutParams getTitleLayoutParams(LinearLayout.LayoutParams params) {

        if (params == null) {
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        int count = mTitelPageAdapter.getCount();
        if (count < 1) {
            return params;
        }

        switch (mType) {
            case FIT:
                int width = (Utils.getScreemWidth((Activity) getContext()) - getPaddingLeft() - getPaddingRight()) / count;
                params.width = width;
                params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                break;
            case FIX:
                width = (Utils.getScreemWidth((Activity) getContext()) - getPaddingLeft() - getPaddingRight()) / count;
                if (titleWidth < 0) {
                    params.width = width;
                } else {
                    params.width = titleWidth;
                }
                params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                break;
            case WRAPPER:
                break;
        }
        return params;
    }


    public void init() {

        for (int i = 0; i < mTitelPageAdapter.getCount(); i++) {

            View view = mTitelPageAdapter.getTitleView(mTitles, i);
            mTitles.addView(view, getTitleLayoutParams((LinearLayout.LayoutParams) view.getLayoutParams()));
            view.setTag(i);

            final int selectItem = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(selectItem);
                }
            });

            if (i == 0) {
                view.setSelected(true);
            }
        }

        if (mTitelPageAdapter.getCount() == 1) {
            mIndicator.setVisibility(GONE);
            mTitles.setVisibility(GONE);
        } else {
            mIndicator.setVisibility(VISIBLE);
            mTitles.setVisibility(VISIBLE);
        }
    }

    public void notifyDataChange() {
        mIndicator.removeAllViews();
        mTitles.removeAllViews();
        init();
        mTitelPageAdapter.notifyDataSetChanged();
    }

    public void setSelect(int position) {
        for (int i = 0; i < mTitles.getChildCount(); i++) {
            View view = mTitles.getChildAt(i);
            Object tag = view.getTag();
            if (tag != null) {
                int index = (int) tag;
                view.setSelected(index == position);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset
            , int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setSelect(position);
        animate(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    Runnable mTabSelector = null;

    private void animate(int position) {
        final View tabView = mTitles.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                scrollView.smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }







}
