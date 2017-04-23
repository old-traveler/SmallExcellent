package com.enjoy.hyc.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.enjoy.R;
import com.enjoy.hyc.bean.Job;

import java.util.List;

/**
 * Created by hyc on 2017/4/23 14:20
 */

public class QueryJobAdapter extends BaseQuickAdapter<Job> {


    public QueryJobAdapter(List<Job> data) {
        super(R.layout.item_query_job,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Job job) {
        baseViewHolder.setText(R.id.tv_name,job.getJobType());
        baseViewHolder.setText(R.id.tv_money,job.getJobSalary()+
                "/"+job.getBalanceMode().split("结")[0]);
        baseViewHolder.setText(R.id.tv_find_place,job.getCityName());
    }


}
