package com.enjoy.base;

import android.util.Log;

/**
 * Created by hyc on 2017/4/10 19:50
 */

public class LogUtils  {

    public static String HYC="hyc";

    public static String TAG="TAG";

    public static String ZLY="zly";

    public static void logD(String msg){
        Log.d("TAG",msg);
    }

    public static void logD(String tag,String msg){
        Log.d(tag,msg);
    }

}
