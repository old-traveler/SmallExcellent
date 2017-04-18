package com.enjoy.hyc.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Bind(R.id.btn_location_fragment)
    Button btnLocationFragment;
    @Bind(R.id.btn_query_part_time)
    Button btnQueryPartTime;
    @Bind(R.id.btn_personal_fragment)
    Button btnPersonalFragment;

    private List<Fragment> fragments;

    private int currentPosition=0;

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


    @OnClick({R.id.btn_location_fragment, R.id.btn_query_part_time, R.id.btn_personal_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_location_fragment:
                mvpPresenter.replaceFragment(0);
                break;
            case R.id.btn_query_part_time:
                mvpPresenter.replaceFragment(1);
                break;
            case R.id.btn_personal_fragment:
                mvpPresenter.replaceFragment(2);
                break;
        }
    }

    @Override
    public void setDefaultFragment() {
        fragments=new ArrayList<>();
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
        if (position!=currentPosition){
            FragmentManager mainFragmentManager = getFragmentManager();
            FragmentTransaction transaction = mainFragmentManager.beginTransaction();
            transaction.replace(R.id.fl_main_fragment,fragments.get(position));
            currentPosition=position;
            transaction.commit();
        }
    }

}
