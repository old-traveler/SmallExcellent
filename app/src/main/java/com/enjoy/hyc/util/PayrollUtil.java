package com.enjoy.hyc.util;

import com.enjoy.base.LogUtils;
import com.enjoy.hyc.bean.Moonlighting;
import com.enjoy.hyc.bean.Payroll;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hyc on 2017/4/27 11:18
 */

public class PayrollUtil {

    public static int otherIncome=0;

    public static List<Payroll> countPayrollByMoonlighting(List<Moonlighting> moonlightings){
        List<Payroll> payrolls=new ArrayList<>();
        payrolls=new ArrayList<>();
        long currentTime=System.currentTimeMillis();
        String today=new SimpleDateFormat("yyyy/MM/dd").format(new Date(currentTime));

        for (Moonlighting moon:moonlightings) {
            if (!countDateAfterTime(moon.getJob().getDeadline(),today)){
                LogUtils.log(moon.getJob().getDeadline());
                long temp= currentTime - (moon.getJob().getWorkDayTime()-1)*24L*60L*60L*1000L;
                //对应的兼职的开始时间
                String strDate=new SimpleDateFormat("yyyy/MM/dd").format(new Date(temp));
                LogUtils.log("兼职开始的时间："+strDate);
                if (!countDateAfterTime(moon.getJob().getDeadline(),strDate)){
                    //该兼职已完成

                    for (int i = 1; i <=moon.getJob().getWorkDayTime(); i++) {
                        String time="";
                        Payroll p=new Payroll();
                        p.setIncome(moon.getJob().getJobSalary()/moon.getJob().getWorkDayTime());
                        p.setAddress(moon.getJob().getContactAddress());
                        p.setType(moon.getJob().getJobType());
                        try {
                            time=new SimpleDateFormat("yyyy/MM/dd").format(new Date(stringToDate(moon
                                    .getJob().getDeadline()).getTime()+i*24L*60L*60L*1000L));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            LogUtils.log("时间格式错误");
                            continue;
                        }
                        p.setTime(time);
                        payrolls.add(p);
                    }
                }else {
                    //兼职正在进行中
                    Date date = null;
                    try {
                        date=stringToDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date(currentTime)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date fdate= null;
                    try {
                        fdate = stringToDate(moon.getJob().getDeadline());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int haveDay=daysOfTwo(fdate,date)-1;
                    for (int i = 0; i < haveDay+1; i++) {
                        String time="";
                        Payroll p=new Payroll();
                        p.setIncome(moon.getJob().getJobSalary()/moon.getJob().getWorkDayTime());
                        p.setAddress(moon.getJob().getContactAddress());
                        p.setType(moon.getJob().getJobType());
                        try {
                            time=new SimpleDateFormat("yyyy/MM/dd").format(new Date(stringToDate(moon
                                    .getJob().getDeadline()).getTime()+(i+1)*24L*60L*60L*1000L));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            LogUtils.log("时间格式错误");
                            continue;
                        }
                        p.setTime(time);
                        payrolls.add(p);
                    }
                }
            }
        }

        if (payrolls.size()>0){
            sort(payrolls);
        }
        return payrolls;
    }



    private static boolean countDateAfterTime(String date,String time){
        return Integer.parseInt(date.split("/")[0])>Integer.parseInt(time.split("/")[0])
                ||Integer.parseInt(date.split("/")[1])>Integer.parseInt(time.split("/")[1])
                ||Integer.parseInt(date.split("/")[2])>Integer.parseInt(time.split("/")[2]);
    }

    public static Date stringToDate(String time) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy/MM/dd");
        return formatter.parse(time);
    }

    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    public static void sort(List<Payroll> payrolls){
        Collections.sort(payrolls, new Comparator<Payroll>() {
            @Override
            public int compare(Payroll o1, Payroll o2) {
                return countDateAfterTime(o1.getTime(),o2.getTime())?-1:1;

            }
        });

    }

    public static int countIncomeType(String type,String mayType,List<Payroll> payrolls){
        int income=0;
        for (Payroll payroll:payrolls){

            if (payroll.getType().equals(type) || payroll.getType().equals(mayType)){
                income+=payroll.getIncome();
            }
        }
        otherIncome+=income;
        return income;
    }

    public static int countTotal(List<Payroll> payrolls){
        int income=0;
        for (Payroll payroll:payrolls){
            income+=payroll.getIncome();
        }
        return income;
    }

    public static int countTodayIncome(List<Payroll> payrolls){
        int income=0;
        String strDate=new SimpleDateFormat("yyyy/MM/dd").format(new Date(System.currentTimeMillis()));
        for (Payroll payroll:payrolls) {
            if (payroll.getTime().equals(strDate)){
                income+=payroll.getIncome();
            }
        }
        return income;
    }

    public static int countOther(List<Payroll> payrolls){
        int income=countTotal(payrolls)-otherIncome;
        otherIncome=0;
        return income;
    }


}
