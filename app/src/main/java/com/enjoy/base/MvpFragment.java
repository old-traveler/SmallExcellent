package com.enjoy.base;

import android.os.Bundle;
import android.view.View;

/**
 * Created by hyc on 2017/4/10 15:33
 */

public abstract class MvpFragment <P extends BasePresenter> extends BaseFragment{
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
