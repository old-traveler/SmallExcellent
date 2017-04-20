package com.enjoy.hyc.main;


import android.content.Context;

/**
 * Created by hyc on 2017/4/18 11:02
 */

public interface MainView {

    void setDefaultFragment();

    void replaceFragment(int position);

    void refreshImageSrc(int position);

    void getLocationMessage();

    void isVisibleBottomLayout(boolean isShow);

}
