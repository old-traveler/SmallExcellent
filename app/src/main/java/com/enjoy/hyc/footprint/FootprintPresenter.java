package com.enjoy.hyc.footprint;

import com.enjoy.base.BasePresenter;
import com.enjoy.hyc.bean.JobCache;
import com.enjoy.hyc.util.JobCacheUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by hyc on 2017/4/26 15:50
 */

public class FootprintPresenter extends BasePresenter<FootprintContract> {


    public void initFootprintData(){
        List<JobCache> browses=DataSupport.findAll(JobCache.class);
        if (browses!=null & browses.size()>0){
            mvpView.initRecycleViewData(browses);
        }else {
            mvpView.noFootprintData();
        }
    }

    public void isDelete(){
        mvpView.isDeleteAllFootprint();
    }

    public void deleteAll(){
        JobCacheUtil.deleteAll();
        mvpView.noFootprintData();
    }

}
