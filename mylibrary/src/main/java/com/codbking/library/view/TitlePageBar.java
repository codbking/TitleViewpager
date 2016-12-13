package com.codbking.library.view;

import android.support.v4.app.Fragment;

public  class TitlePageBar {
        public Fragment fragment;
        public String title;

        public TitlePageBar(String title, Fragment fragment) {
            this.fragment = fragment;
            this.title = title;
        }
    }