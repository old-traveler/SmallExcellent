package com.enjoy.hyc.util;

import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.bean.User;

import java.io.File;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

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
        void registerFail(String error);
    }

    public interface OnDealListener{
        void success();
        void fail();
    }

    public interface OnImageDealListener{
        void success(String path);
        void fail(String error);
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
                    listener.registerFail(e.getMessage());
                }
            }
        });
    }




    public static void setCurrentUser(User user){
        BmobUser.getCurrentUser(User.class);
    }

    public static User getCurrentUser(){
        return null;
    }

    public static void verifyMobileNumber(String number, final JobUtil.OnDeleteJobListener listener){
        User user=new User();
        user.setMobilePhoneNumber(number);
        user.setMobilePhoneNumberVerified(true);
        User cur = BmobUser.getCurrentUser(User.class);
        user.update(cur.getObjectId(),new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e==null){
                    listener.success();
                }else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    public static void amendPassword(String oldPassword, String newPassword, final JobUtil.OnDeleteJobListener listener){
        BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    listener.success();
                }else{
                    listener.fail(e.getMessage());
                }
            }

        });
    }

    public static void updateUserResume(User newUser, final JobUtil.OnDeleteJobListener listener){
        User bmobUser = BmobUser.getCurrentUser(User.class);
        newUser.update(bmobUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    listener.success();
                }else{
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    public static void updateUserHeadImage(String path, final OnImageDealListener listener){
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    User newUser=new User();
                    newUser.setHeadImageUrl(bmobFile.getUrl());
                    User bmobUser = BmobUser.getCurrentUser(User.class);
                    newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                listener.success(bmobFile.getFileUrl());
                            }else{
                                listener.fail(e.getMessage());
                            }
                        }
                    });
                }else{
                    listener.fail(e.getMessage());
                }
            }
        });
    }




}
