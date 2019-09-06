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
import javax.servlet.http.HttpSession;

import yuyi.family.common.CommonConstant;
import yuyi.family.common.util.FileUtil;
import yuyi.family.common.util.JSONUtil;
import yuyi.family.common.util.SessionUtil;
import yuyi.family.common.util.dbutil.LocationDao;
import yuyi.family.common.util.dbutil.UserDao;
import yuyi.family.pojo.FamilyMember;
import yuyi.family.pojo.LocationResult;
import yuyi.family.pojo.SMSResult;
import yuyi.family.pojo.User;

/**
 * Servlet implementation class VerifySMS
 */
public class VerifySMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifySMS() {
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
		String phone=request.getParameter("phone");
		String code=request.getParameter("code");
		String sessionId=request.getParameter("sessionId");
		System.out.println("request");
		System.out.println("phone:"+phone);
		System.out.println("code:"+code);
		System.out.println("sessionId:"+sessionId);
		HttpSession session=SessionUtil.getSession(sessionId);
		response.setHeader("content-type", "application/json");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");//!!!!!修改的地方
		User user=new User();
		try {
			SMSResult verifyCode=(SMSResult)session.getAttribute(phone);
			if(new Date().getTime()-verifyCode.getTime()<CommonConstant.SMSConfig.validTime*60*1000) {//判断验证码是否超时
				if(code.equals(verifyCode.getCode())) {//若验证码正确则删除session
					SessionUtil.removeSession(sessionId);
					user=getUser(phone);
					if(user==null) {//为空则没有注册
						user=new User();
						user.setRegister(false);
					}else {//已注册
						user.setRegister(true);
						int size=user.getPortrait().split("\\\\").length;
						user.setPortrait(CommonConstant.urlPath+user.getPortrait().split("\\\\")[size-2]+"/"+user.getPortrait().split("\\\\")[size-1]);
						//将用户头像转成base64传送
//						user.setPortrait(FileUtil.ImageToBase64(user.getPortrait()));
						UserDao dao=new UserDao();
						List<FamilyMember> familyMembers=new ArrayList<FamilyMember>();
						if(null!=user.getFamilyMemberPhone()) {
							for(int i=0;i<user.getFamilyMemberPhone().size();i++) {
								familyMembers=dao.getFamilyMembersInfo(user.getFamilyMemberPhone());
							}
							user.setFamilyMembers(familyMembers);
						}
					}
				}else {
					user.setRetCode(CommonConstant.SERVER_FAIL_CODE);
					user.setRetMessage("验证码错误");
				}
			}else {
				user.setRetCode(CommonConstant.SERVER_FAIL_CODE);
				user.setRetMessage("验证码超时请重新发送");
				SessionUtil.removeSession(sessionId);
			}
		}catch(Exception e) {
			e.printStackTrace();
			user.setRetCode(CommonConstant.SERVER_FAIL_CODE);
			user.setRetMessage(e.getMessage());
		}
		
		PrintWriter out=response.getWriter();
		out.println(JSONUtil.ObjectToJson(user));
	}
	
	private User getUser(String phone) {
		User user=new User();
		UserDao dao=new UserDao();
		user=dao.getUserInfo(phone);
		return user;
	}

}
