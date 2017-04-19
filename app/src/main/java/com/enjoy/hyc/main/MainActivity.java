package com.enjoy.hyc.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.map.MapDetailsFragment;
import com.enjoy.hyc.personal.PersonalFragment;
import com.enjoy.hyc.query.QueryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {


    @Bind(R.id.iv_map)
    ImageView ivMap;
    @Bind(R.id.rl_location_fragment)
    RelativeLayout rlLocationFragment;
    @Bind(R.id.iv_query)
    ImageView ivQuery;
    @Bind(R.id.rl_query_part_time)
    RelativeLayout rlQueryPartTime;
    @Bind(R.id.iv_personal)
    ImageView ivPersonal;
    @Bind(R.id.rl_personal_fragment)
    RelativeLayout rlPersonalFragment;
    @Bind(R.id.ll_main_button)
    LinearLayout llMainButton;
    @Bind(R.id.fl_main_fragment)
    FrameLayout flMainFragment;
    private List<Fragment> fragments;

    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mvpPresenter.attachView(this);
        mvpPresenter.initFragment();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void setDefaultFragment() {
        fragments = new ArrayList<>();
        fragments.add(new MapDetailsFragment());
        fragments.add(new QueryFragment());
        fragments.add(new PersonalFragment());
        FragmentManager mainFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mainFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_main_fragment, fragments.get(0));
        transaction.commit();
    }

    @Override
    public void replaceFragment(int position) {
        if (position != currentPosition) {
            FragmentManager mainFragmentManager = getFragmentManager();
            FragmentTransaction transaction = mainFragmentManager.beginTransaction();
            transaction.replace(R.id.fl_main_fragment, fragments.get(position));
            currentPosition = position;
            transaction.commit();
        }
    }

    @OnClick({R.id.rl_location_fragment, R.id.rl_query_part_time, R.id.rl_personal_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_location_fragment:
                mvpPresenter.replaceFragment(0);
                break;
            case R.id.rl_query_part_time:
                mvpPresenter.replaceFragment(1);
                break;
            case R.id.rl_personal_fragment:
                mvpPresenter.replaceFragment(2);
                break;
        }
    }


    @Override
    public void refreshImageSrc(int position) {
        ivMap.setImageResource(R.drawable.ic_map_normal);
        ivPersonal.setImageResource(R.drawable.ic_personal_normal);
        ivQuery.setImageResource(R.drawable.ic_query_normal);
        switch (position){
            case 0:ivMap.setImageResource(R.drawable.ic_map_selected);
                break;
            case 1:ivQuery.setImageResource(R.drawable.ic_query_selected);
                break;
            case 2:ivPersonal.setImageResource(R.drawable.ic_personal_selected);
                break;
        }
    }
}
