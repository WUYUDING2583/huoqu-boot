package yuyi.family.common;

import yuyi.family.server.Register;

public class CommonConstant{

    public static class SMSConfig{
        // 腾讯云 appId
        public static int appid =1400244217; // SDK AppID ��1400��ͷ
        // 腾讯云 appKey
        public static String appkey = "ebbe021156ca27d220e3cef80879ee1f";
        // 发送短信号码
        public static String[] phoneNumbers = {"21212313123", "12345678902", "12345678903"};
        // 模板id
        public static int SMSACID = 397475; // 短信验证码模板id
        // 模板id
        public static int SMSINVITEID = 404472; // 邀请短信模板id
        public static int NOTICEFAMILYID=413125;//提醒家人模板id
        public static int NOTICEUSERID=413119;//提醒用户模板id
        // 短信签名
        public static String smsSign = "活趣公众号"; 
        //短信有效时间
        public static int validTime=5;
    }
    
    public static class ReturnMsg{
    	public static String ADDFAMILYSUCCESSRESULT="添加家人成功";
    	public static String ADDFAMILYFAILRESULT="由于某种原因，添加失败";
    }
    public static String SERVER_SUCCESS_CODE="200";
    public static String SERVER_FAIL_CODE="400";
    
    public static String rootPath=getRealPath();
    
    public static String urlPath="http://www.jsjzx.top/Family/";
//    public static String urlPath="http://192.168.43.124:8080/Family/";
    
    public static String CONTENTPROVIDER="";
    
    public static String ADMINPHONE="15868859587";
    
    private static String getRealPath() {
		String[] pathArray=Register.class.getResource("/").getPath().split("/");
		String path="";
		for(int i=1;i<pathArray.length-2;i++) {
			path+=pathArray[i]+"\\";
		}
    	return path;
    }
}