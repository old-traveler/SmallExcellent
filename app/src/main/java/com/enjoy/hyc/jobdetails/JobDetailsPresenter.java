package com.enjoy.hyc.jobdetails;

import com.enjoy.base.BasePresenter;
import com.enjoy.hyc.bean.Job;

/**
 * Created by hyc on 2017/4/24 15:58
 */

public class JobDetailsPresenter extends BasePresenter<JobDetailsContract> {

    public static Job jobContent;

    public JobDetailsPresenter(JobDetailsContract jobDetailsContract){
        attachView(jobDetailsContract);
    }

    public void loadJobContent(){
        if (jobContent!=null){
            mvpView.loadJobContent(jobContent);
        }
    }

}
