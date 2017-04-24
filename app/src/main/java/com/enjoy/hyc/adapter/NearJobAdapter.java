package com.enjoy.hyc.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.enjoy.R;
import com.enjoy.hyc.bean.Job;

import java.util.List;

/**
 * Created by hyc on 2017/4/24 17:04
 */

public class NearJobAdapter extends BaseQuickAdapter<Job>{

    public void setOnNearJobItemClick(OnNearJobItemClick onNearJobItemClick) {
        this.onNearJobItemClick = onNearJobItemClick;
    }

    public interface OnNearJobItemClick{
        int TYPE_LOCATION=0x11;
        int TYPE_NAVIGATION=0x12;
        int TYPE_DETAILS=0x13;
        void onClick(int type,Job job);
    }

    private OnNearJobItemClick onNearJobItemClick;


    public NearJobAdapter(List<Job> data) {
        super(R.layout.item_near_job,data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final Job job) {
        baseViewHolder.setText(R.id.item_job_name,job.getJobType());
        baseViewHolder.setText(R.id.item_job_area,job.getJobRegion());
        baseViewHolder.setText(R.id.item_job_number,job.getRecruitmentNumber()+"人");
        baseViewHolder.setText(R.id.item_salary,job.getJobSalary()+"/"+job.getBalanceMode().split("结")[0]);
        baseViewHolder.setOnClickListener(R.id.iv_near, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNearJobItemClick!=null){
                    onNearJobItemClick.onClick(OnNearJobItemClick.TYPE_LOCATION,job);
                }
            }
        });
        baseViewHolder.setOnClickListener(R.id.iv_navigation, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNearJobItemClick!=null){
                    onNearJobItemClick.onClick(OnNearJobItemClick.TYPE_NAVIGATION,job);
                }
            }
        });
        baseViewHolder.setOnClickListener(R.id.item_job_details, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNearJobItemClick!=null){
                    onNearJobItemClick.onClick(OnNearJobItemClick.TYPE_DETAILS,job);
                }
            }
        });


    }
}
