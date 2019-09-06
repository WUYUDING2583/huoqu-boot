package yuyi.family.common.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import yuyi.family.common.CommonConstant;
import yuyi.family.pojo.AddFamilyMemberPojo;
import yuyi.family.pojo.SMSResult;

import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SMSUtil {
	
	/**
	 * 提醒用户app停止运行
	 * @param phone
	 * @param time
	 * @return
	 */
	public static SMSResult noticeUser(String phone,long time) {
		SMSResult output=new SMSResult();
        try {
        	String[] params = {TimeUtil.timeStamp2LittleTimeFormat(time)};
            SmsSingleSender ssender = new SmsSingleSender(CommonConstant.SMSConfig.appid, CommonConstant.SMSConfig.appkey);
        	SmsSingleSenderResult result = ssender.sendWithParam("86", phone,
        	      CommonConstant.SMSConfig.NOTICEUSERID, params, CommonConstant.SMSConfig.smsSign, "", "");  
        	output.setResult(result);
        } catch (HTTPException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        }
        System.out.println(output);
    	return output;
	}
	
	/**
	 * 提醒家人该用户app已停止运行
	 * @param phone
	 * @param time
	 * @return
	 */
	public static SMSResult noticeFamily(String familyPhone,String userName,String userPhone,long time) {
		SMSResult output=new SMSResult();
        try {
        	String[] params = {userName,userPhone,TimeUtil.timeStamp2LittleTimeFormat(time)};
            SmsSingleSender ssender = new SmsSingleSender(CommonConstant.SMSConfig.appid, CommonConstant.SMSConfig.appkey);
        	SmsSingleSenderResult result = ssender.sendWithParam("86", familyPhone,
        	      CommonConstant.SMSConfig.NOTICEFAMILYID, params, CommonConstant.SMSConfig.smsSign, "", "");  
        	output.setResult(result);
        } catch (HTTPException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        }
        System.out.println(output);
    	return output;
	}

	/**
	 * 发送短信验证码
	 * @param phone
	 * @return
	 */
    public static SMSResult sendSMSACode(String phone){
    	SMSResult output=new SMSResult();
        try {
        	String code=NUMUtil.RandomNumber(4);
        	output.setCode(code);
        	String[] params = {code,CommonConstant.SMSConfig.validTime+""};
            SmsSingleSender ssender = new SmsSingleSender(CommonConstant.SMSConfig.appid, CommonConstant.SMSConfig.appkey);
        	SmsSingleSenderResult result = ssender.sendWithParam("86", phone,
        	      CommonConstant.SMSConfig.SMSACID, params, CommonConstant.SMSConfig.smsSign, "", "");  
        	output.setResult(result);
        } catch (HTTPException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        }
        System.out.println(output);
    	return output;
    }
    
    public static SMSResult sendInviteMsg(AddFamilyMemberPojo familyMember){
    	SMSResult output=new SMSResult();
        try {
        	String[] params = {familyMember.getName(),familyMember.getPhone(),CommonConstant.CONTENTPROVIDER};
            SmsSingleSender ssender = new SmsSingleSender(CommonConstant.SMSConfig.appid, CommonConstant.SMSConfig.appkey);
        	SmsSingleSenderResult result = ssender.sendWithParam("86", familyMember.getAddMemberPhone(),
        	      CommonConstant.SMSConfig.SMSINVITEID, params, CommonConstant.SMSConfig.smsSign, "", "");  
        	output.setResult(result);
        } catch (HTTPException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            output.setRetCode(CommonConstant.SERVER_FAIL_CODE);
            output.setRetMessage(e.getMessage());
        }
        System.out.println(output);
    	return output;
    }
}

