import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import yuyi.family.common.CommonConstant;
import yuyi.family.common.util.JSONUtil;
import yuyi.family.common.util.NUMUtil;
import yuyi.family.common.util.SMSUtil;
import yuyi.family.common.util.TimeUtil;
import yuyi.family.common.util.dbutil.LocationDao;
import yuyi.family.common.util.dbutil.UserDao;
import yuyi.family.pojo.LocationResult;
import yuyi.family.pojo.User;

public class test {
	public static void main(String[] args) {
		Date date=new Date();
		long time=Long.parseLong("1567755820201");
		System.out.println(TimeUtil.timeStamp2TimeFormat(time));
	
	}
	
}
