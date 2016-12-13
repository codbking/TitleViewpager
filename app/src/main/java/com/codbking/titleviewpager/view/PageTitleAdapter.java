package com.codbking.titleviewpager.view;

import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.titleviewpager.R;

import java.util.ArrayList;
import java.util.List;

class PageTitleAdapter extends FragmentPagerAdapter {

        private List<TitlePageBar> fragmentList = new ArrayList<>();

        public PageTitleAdapter(FragmentManager fm,List<TitlePageBar> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

       public void setData(List<TitlePageBar> fragmentList){
           fragmentList.clear();
           fragmentList.addAll(fragmentList);
           notifyDataSetChanged();
       }

        public View getTitleView(ViewGroup parent,int postion) {
            
            TextView textView = new TextView(parent.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);
            textView.setPadding(Utils.px(10),0,Utils.px(5),0);
            textView.setSingleLine();

            ColorStateList colorStateList = Utils.createColorStateList(parent.getContext(), R.color.text_body,
                  R.color.main_color);

            textView.setTextColor(colorStateList);
            textView.setText(getPageTitle(postion));

            return textView;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentList.get(position).title;
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position).fragment;
        }

    }