package com.enjoy.hyc.map;

import android.view.View;
import android.widget.AdapterView;
import com.enjoy.base.BasePresenter;

/**
 * Created by hyc on 2017/4/19 20:27
 */

public class MapPresenter extends BasePresenter<MapView> implements AdapterView.OnItemClickListener{

    public static com.amap.api.maps.MapView mMapView=null;

    private boolean isSelectedDestination=false;

    public void initMapView(){
        mvpView.initMap();
        mvpView.getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mvpView.showDialog();
        mvpView.getLocationByName(mvpView.getQueryResults().get(position));
        isSelectedDestination=true;
    }

    public void enterRoutePlan(){
        if (isSelectedDestination){
            mvpView.startRoutePlan();

        }
    }
}
