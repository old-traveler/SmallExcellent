package com.enjoy.hyc.main;

import com.enjoy.base.BasePresenter;

/**
 * Created by hyc on 2017/4/18 11:03
 */

public class MainPresenter extends BasePresenter<MainView> {

    public void initFragment(){
        mvpView.refreshImageSrc(0);
        mvpView.setDefaultFragment();
    }


    public void replaceFragment(int position){
        mvpView.refreshImageSrc(position);
        mvpView.replaceFragment(position);
    }

}
