package com.enjoy.hyc.personal;

import com.enjoy.base.BasePresenter;

/**
 * Created by hyc on 2017/4/22 09:57
 */

public class PersonalPresenter extends BasePresenter<PersonalContract> {
    /**
     * enter the corresponding activity
     */
    public void enterActivity(Class<?> activity){
        mvpView.startActivity(activity);
    }
}
