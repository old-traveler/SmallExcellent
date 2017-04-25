package com.enjoy.hyc.util;

import com.enjoy.hyc.bean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 用户工具类 Created by hyc on 2017/4/25 19:13
 */

public class UserUtil {

    public interface OnLoginListener{
        void success();
        void loginFail(String error);
    }

    public interface OnRegisterListener{
        void success();
        void loginFail(String error);
    }



    public static void login(User user, final OnLoginListener listener){
        user.login(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    listener.success();
                }else {
                    listener.loginFail(e.getMessage());
                }
            }
        });
    }

    public static void register(User user, final OnRegisterListener listener){
        user.signUp(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    listener.success();
                }else {
                    listener.loginFail(e.getMessage());
                }
            }
        });
    }

    public static void setCurrentUser(User user){

    }

    public static User getCurrentUser(){
        return null;
    }

}
