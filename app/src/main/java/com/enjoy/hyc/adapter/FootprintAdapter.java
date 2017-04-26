package com.enjoy.hyc.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.enjoy.R;
import com.enjoy.hyc.bean.JobCache;
import com.enjoy.hyc.jobdetails.JobDetailsActivity;
import com.enjoy.hyc.jobdetails.JobDetailsPresenter;

import java.util.List;

/**
 * Created by hyc on 2017/4/26 16:19
 */

public class FootprintAdapter extends BaseQuickAdapter<JobCache> {


    public FootprintAdapter(List<JobCache> data) {
        super(R.layout.item_browse,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final JobCache jobCache) {
        baseViewHolder.setText(R.id.tv_footprint_name,jobCache.getJobDetailName());
        baseViewHolder.setText(R.id.tv_footprint_time,jobCache.getTime());
        baseViewHolder.setOnClickListener(R.id.rl_item_footprint, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobDetailsPresenter.jobContent=jobCache.toJob();
                mContext.startActivity(new Intent(mContext, JobDetailsActivity.class));
            }
        });
    }
}
