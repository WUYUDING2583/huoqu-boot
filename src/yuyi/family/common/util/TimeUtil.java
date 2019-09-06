package yuyi.family.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {


    public static String timeStamp2TimeFormat(long timeStamp){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeStamp);
        String timeFormat = simpleDateFormat.format(date);
        return timeFormat;
    }
    
    public static String timeStamp2LittleTimeFormat(long timeStamp){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(timeStamp);
        String timeFormat = simpleDateFormat.format(date);
        return timeFormat;
    }
    

    public static long timeFormat2TimeStamp(String timeFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeStamp=-1;
        try {
            Date date = simpleDateFormat.parse(timeFormat);
            timeStamp = date.getTime();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }
    
    /**
     * 获取今日零时的时间戳
     * @return
     */
    public static long getTodyTimeStamp() {
    	Date date=new Date();
    	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		return timeFormat2TimeStamp(dateFormat.format(date)+" 00:00:00");
    }
    
    /**
     * 获取某日零时的时间戳
     * @param date
     * @return
     */
    public static long getDayTimeStamp(String date){
        date+=" 00:00:00";
        return timeFormat2TimeStamp(date);
    }
}
