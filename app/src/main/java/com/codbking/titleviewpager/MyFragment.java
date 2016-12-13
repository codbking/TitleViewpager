package com.codbking.titleviewpager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {


    @BindView(R.id.text)
    TextView mText;

    private String title;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getArguments()!=null){
            title=getArguments().getString("title");
        }
    }

    public static MyFragment newInstance(String title) {
        
        Bundle args = new Bundle();
        args.putString("title",title);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        mText.setText(title);
        return view;
    }

}
