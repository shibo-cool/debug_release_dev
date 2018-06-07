package com.example.administrator.myapplication.fragment;

import android.view.View;

import com.example.administrator.myapplication.R;

public class FragmentMy extends BaseFragment {


    private View view;

    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(mActivity, R.layout.fragment_my, null);
        }
        return view;
    }
}