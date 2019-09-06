package yuyi.family.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yuyi.family.common.CommonConstant;
import yuyi.family.common.util.JSONUtil;
import yuyi.family.common.util.SMSUtil;
import yuyi.family.common.util.dbutil.UserDao;
import yuyi.family.pojo.FamilyMember;
import yuyi.family.pojo.User;

/**
 * Servlet implementation class GetUser
 */
public class GetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
//		System.out.println("get user");
		response.setHeader("content-type", "application/json");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");//!!!!!修改的地方
		String phone=request.getParameter("userPhone");
		System.out.println("phone:"+phone);
		UserDao dao=new UserDao();
		User user=dao.getUserInfo(phone);
		if(user==null) {//未注册
			user=new User();
		}else {
			int size=user.getPortrait().split("\\\\").length;
			user.setPortrait(CommonConstant.urlPath+user.getPortrait().split("\\\\")[size-2]+"/"+user.getPortrait().split("\\\\")[size-1]);
			if(user.getPhone().equals(CommonConstant.ADMINPHONE)) {
				user.setFamilyMemberPhone(dao.adminGetAllUser());
			}
			List<FamilyMember> familyMembers=new ArrayList<FamilyMember>();
			if(null!=user.getFamilyMemberPhone()) {
				familyMembers=dao.getFamilyMembersInfo(user.getFamilyMemberPhone());
				for(int i=0;i<familyMembers.size();i++) {
					int len=familyMembers.get(i).getPortrait().split("\\\\").length;
					familyMembers.get(i).setPortrait(CommonConstant.urlPath+familyMembers.get(i).getPortrait().split("\\\\")[len-2]+"/"+familyMembers.get(i).getPortrait().split("\\\\")[len-1]);
					Date date=new Date();
					if((date.getTime()-familyMembers.get(i).getTime())>1000*60*3) {//上一次定位时间与现在超过3分钟，发送短信提醒
						long userTime=dao.getLastSendMsgTime(phone);
						long familyTime=dao.getLastSendMsgTime(familyMembers.get(i).getPhone());
						if(userTime!=-1&&(date.getTime()-userTime)>1000*60*10) {//短信发送间隔为10分钟
							SMSUtil.noticeFamily(phone, familyMembers.get(i).getName(), familyMembers.get(i).getPhone(), familyMembers.get(i).getTime());
							dao.setSendMsgTime(phone);
						}else {
							user.setRetMessage("短信间隔在10分钟内");
						}
						if(familyTime!=-1&&(date.getTime()-familyTime)>1000*60*10) {
							SMSUtil.noticeUser(familyMembers.get(i).getPhone(), familyMembers.get(i).getTime());
							dao.setSendMsgTime(familyMembers.get(i).getPhone());
						}
					}
				}
				user.setFamilyMembers(familyMembers);
			}
		}
		System.out.println(JSONUtil.ObjectToJson(user));
		PrintWriter out=response.getWriter();
		out.println(JSONUtil.ObjectToJson(user));
	}

}
