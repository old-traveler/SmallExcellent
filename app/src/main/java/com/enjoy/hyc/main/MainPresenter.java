package com.enjoy.hyc.main;

import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.enjoy.base.BasePresenter;
import com.enjoy.base.LogUtils;
import com.enjoy.base.SmallApplication;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hyc on 2017/4/18 11:03
 */

public class MainPresenter extends BasePresenter<MainContract> implements AMapLocationListener {

    private static MainPresenter mainPresenter;

    public String currentCityName;

    public synchronized static MainPresenter getMainPresenter(){
        if (mainPresenter==null){
            mainPresenter=new MainPresenter();
        }
        return mainPresenter;
    }

    public void initFragment(){
        mvpView.getLocationMessage();
        mvpView.refreshImageSrc(0);
        mvpView.setDefaultFragment();
    }


    public void replaceFragment(int position){
        mvpView.refreshImageSrc(position);
        mvpView.replaceFragment(position);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode()==0){
            LogUtils.log("定位完成"+aMapLocation.getCity());
            currentCityName=aMapLocation.getCity();
            Toast.makeText(SmallApplication.getContext(), "当前位置："+aMapLocation.getAddress(), Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(aMapLocation.getCity());
        }else {
            LogUtils.log(aMapLocation.getErrorInfo());
        }
    }

    public void isHideBottom(boolean is){
        mvpView.isVisibleBottomLayout(is);
    }
}
