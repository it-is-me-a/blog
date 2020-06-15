package com.example.blog.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Emma
 * @create 2020 - 06 - 13 - 12:38
 * 高考倒计时器
 */
public class TimerUtil {

    public static String fun(){
        String answer = "Hello!";

        Date a = new Date();
        //获取今天是一年中的第几天
        SimpleDateFormat daynow = new SimpleDateFormat("D");
        String dayns = daynow.format(a);//获取今天是一年中的第几天
        int day = Integer.parseInt(dayns);//将string转为int
        //获取今年是那一年
        SimpleDateFormat yearnow = new SimpleDateFormat("yyyy");
        String yearns = yearnow.format(a);
        int year = Integer.parseInt(yearns); //将String类型转换为int类型

        //如果是平年：高考为一年的158天和159天
        int day1 = 157; //6月6号,应该把day1设为6月6号，因为从6月6到7剩下的是半天，不是一天
        int day2 = 159; //6月8号
        int dayz = 365; //总天数


        if(year%4==0&&year%100!=0||year%400==0){
            //如果是闰年：高考为一年的159天和160天
            dayz = dayz+1;
            day1 = day1+1;
            day2 = day2+1;

            if(year == 2020){
                //高考在7月6日与7月8日
                day1 = day1+30;
                day2 = day2+30;
            }
        }

        if(day<day1){
            answer="距离今年高考，还剩"+ (day1-day) +"天！";
        }
        if(day == day1){
            answer="距离今年高考，还剩半天！";
        }
        if(day==(day1+1) || day==day2){
            answer="今天高考!加油哦";
        }
        if(day>day2){
            if((year+1)%4==0&&(year+1)%100!=0||(year+1)%400==0) {
                //如果明年是闰年
                answer="距离明年高考，还剩" + (dayz-day+159) + "天！";
            }else{
                //如果是闰年：高考为一年的159天和160天
                answer="距离明年高考，还剩" + (dayz-day+158) + "天！";
            }
        }
        return answer;
    }


    //Test
	/*public static void main(String[] args){
        fun();
	}*/

}
