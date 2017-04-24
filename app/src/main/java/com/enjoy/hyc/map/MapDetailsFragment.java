package com.enjoy.hyc.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.enjoy.R;
import com.enjoy.base.BaseFragment;
import com.enjoy.base.LogUtils;
import com.enjoy.base.MapUtil;
import com.enjoy.base.route.RouteActivity;
import com.enjoy.hyc.adapter.NearJobAdapter;
import com.enjoy.hyc.bean.Job;
import com.enjoy.hyc.jobdetails.JobDetailsActivity;
import com.enjoy.hyc.jobdetails.JobDetailsPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hyc on 2017/4/18 12:15
 */

public class MapDetailsFragment extends BaseFragment implements MapContract, AMap.OnMyLocationChangeListener
        , TextWatcher, Inputtips.InputtipsListener, View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener, AMap.OnMarkerClickListener {
    /**
     * the mapView of Gaode
     */
    @Bind(R.id.mv_main)
    MapView mapView;
    /**
     * the input box
     */
    @Bind(R.id.atv_map_input)
    AutoCompleteTextView mKeywordText;
    /**
     * the button of clean input text
     */
    @Bind(R.id.iv_clean_input)
    ImageView ivCleanInput;
    /**
     * the listView used to show the retrieve data
     */
    @Bind(R.id.lv_map)
    ListView minPutListView;
    /**
     * the controls of enter route plan
     */
    @Bind(R.id.iv_enter_route)
    ImageView ivEnterRoute;
    /**
     * show the data of near
     */
    @Bind(R.id.rv_map_data)
    RecyclerView rvMapData;

    private AMap aMap;

    private MyLocationStyle myLocationStyle;

    private MapPresenter mMapPresenter;

    private UiSettings mUiSettings;

    private static String city = "株洲";

    private List<String> queryResults;

    private ProgressDialog progressDialog = null;

    private Marker geoMarker;

    private GeocodeSearch geocodeSearch;

    private NearJobAdapter mNearJobAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        mMapPresenter = new MapPresenter();
        mMapPresenter.attachView(this);
        mMapPresenter.initMapView(city);
        MapPresenter.mMapView = mapView;
        ivCleanInput.setOnClickListener(this);
        mKeywordText.addTextChangedListener(this);
        ivEnterRoute.setOnClickListener(this);
        return view;
    }

    /**
     * 初始化
     */
    @Override
    public void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
            geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setScaleControlsEnabled(true);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
        progressDialog = new ProgressDialog(getActivity());
        geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(this);
        rvMapData.setLayoutManager(new LinearLayoutManager(mActivity));
        mNearJobAdapter=new NearJobAdapter(null);
        rvMapData.setAdapter(mNearJobAdapter);

    }

    @Override
    public ListView getListView() {
        return minPutListView;
    }

    @Override
    public List<String> getQueryResults() {
        return queryResults;
    }

    @Override
    public void showDialog() {
        minPutListView.setVisibility(View.GONE);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("正在获取地址");
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (progressDialog != null & progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void getLocationByName(String name) {
        GeocodeQuery query = new GeocodeQuery(name, city);
        geocodeSearch.getFromLocationNameAsyn(query);
    }

    @Override
    public void startRoutePlan() {

        startActivity(new Intent(getActivity(), RouteActivity.class));
    }

    @Override
    public void loadNearJobInformationComplete(List<Job> jobs) {
        mNearJobAdapter.addData(jobs);
        mMapPresenter.setListenerForAdapter();
    }

    @Override
    public void setItemClickListener(NearJobAdapter.OnNearJobItemClick onNearJobItemClick) {
        mNearJobAdapter.setOnNearJobItemClick(onNearJobItemClick);
    }

    @Override
    public void startDetailsView(Job job) {
        JobDetailsPresenter.jobContent=job;
        mActivity.startActivity(new Intent(mActivity,JobDetailsActivity.class));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location != null) {
            Bundle bundle = location.getExtras();
            if (bundle != null) {
                RouteActivity.mStartPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
                RouteActivity.mStartPoint_bus = new LatLonPoint(location.getLatitude(), location.getLongitude());
            } else {
                LogUtils.log("bundle is null");
            }

        } else {
            LogUtils.log("location is null");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            mapView.setVisibility(View.GONE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, city);
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(getActivity(), inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();


    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    /**
     * 输入提示结果的回调
     *
     * @param tipList
     * @param rCode
     */
    @Override
    public void onGetInputtips(final List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            minPutListView.setVisibility(View.VISIBLE);
            List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
            queryResults = null;
            queryResults = new ArrayList<>();
            HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put("name",mKeywordText.getText().toString());
            map1.put("address","");
            listString.add(map1);
            queryResults.add(mKeywordText.getText().toString());
            for (int i = 0; i < tipList.size(); i++) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", tipList.get(i).getName());
                map.put("address", tipList.get(i).getDistrict());
                listString.add(map);
                queryResults.add(tipList.get(i).getName());
            }
            SimpleAdapter aAdapter = new SimpleAdapter(getActivity(), listString, R.layout.item_layout,
                    new String[]{"name", "address"}, new int[]{R.id.poi_field_id, R.id.poi_value_id});
            minPutListView.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        } else {
            LogUtils.log("参数代码：" + rCode);
            minPutListView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clean_input:
                minPutListView.setVisibility(View.GONE);
                mKeywordText.setText("");
                break;
            case R.id.iv_enter_route:
                mMapPresenter.enterRoutePlan();
                break;
        }
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        MapUtil.convertToLatLng(address.getLatLonPoint()), 15));
                geoMarker.setPosition(MapUtil.convertToLatLng(address
                        .getLatLonPoint()));
                String addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
                        + address.getFormatAddress();
                geoMarker.setTitle("查询位置");
                geoMarker.setSnippet(address.getFormatAddress());
                geoMarker.showInfoWindow();
                RouteActivity.mEndPoint = address.getLatLonPoint();
                RouteActivity.mEndPoint_bus = address.getLatLonPoint();
                if (mMapPresenter.isType_navigation){
                    mMapPresenter.enterRoutePlan();
                    mMapPresenter.isType_navigation=false;
                }
            } else {
                Toast.makeText(mActivity, "查询不到结果", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivity, "查询错误", Toast.LENGTH_SHORT).show();
            LogUtils.log("查询错误" + rCode);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        city=event;
        mMapPresenter.loadNearData(event);
    }
}
