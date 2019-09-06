package yuyi.family.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuyi.family.common.CommonConstant;
import yuyi.family.common.util.JSONUtil;
import yuyi.family.common.util.SMSUtil;
import yuyi.family.common.util.SessionUtil;
import yuyi.family.common.util.StringUtil;
import yuyi.family.common.util.dbutil.UserDao;
import yuyi.family.pojo.AddFamilyMemberPojo;
import yuyi.family.pojo.CommonData;
import yuyi.family.pojo.FamilyMember;
import yuyi.family.pojo.SMSResult;
import yuyi.family.pojo.User;

/**
 * Servlet implementation class AddFamilyMember
 */
public class AddFamilyMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFamilyMember() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		response.setHeader("content-type", "application/json");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");//!!!!!修改的地方
		String currentUserPhone=request.getParameter("currentUserPhone");
		String familyMemberPhone=request.getParameter("familyMemberPhone");
		System.out.println("currentUserPhone:"+currentUserPhone);
		System.out.println("familyMemberPhone:"+familyMemberPhone);
		UserDao dao=new UserDao();
		User data=new User();
		User currentUser=dao.getUserInfo(currentUserPhone);
		for(int i=0;i<currentUser.getFamilyMemberPhone().size();i++) {
			if(familyMemberPhone.equals(currentUser.getFamilyMemberPhone().get(i))) {
				data.setRetCode(CommonConstant.SERVER_FAIL_CODE);
				data.setRetMessage("您已经添加过该家人");
				PrintWriter out=response.getWriter();
				out.println(JSONUtil.ObjectToJson(data));
				return;
			}
		}
		if(!dao.addFamilyMember(currentUserPhone, familyMemberPhone)) {
			data.setRetCode(CommonConstant.SERVER_FAIL_CODE);
			data.setRetMessage(CommonConstant.ReturnMsg.ADDFAMILYFAILRESULT);
		}else {
			data=dao.getUserInfo(currentUserPhone);
			data.setRetMessage(CommonConstant.ReturnMsg.ADDFAMILYSUCCESSRESULT);
			int size=data.getPortrait().split("\\\\").length;
			data.setPortrait(CommonConstant.urlPath+data.getPortrait().split("\\\\")[size-2]+"/"+data.getPortrait().split("\\\\")[size-1]);
		
			List<FamilyMember> familyMembers=new ArrayList<FamilyMember>();
			if(null!=data.getFamilyMemberPhone()) {
				familyMembers=dao.getFamilyMembersInfo(data.getFamilyMemberPhone());
				for(int i=0;i<familyMembers.size();i++) {
					int len=familyMembers.get(i).getPortrait().split("\\\\").length;
					familyMembers.get(i).setPortrait(CommonConstant.urlPath+familyMembers.get(i).getPortrait().split("\\\\")[len-2]+"/"+familyMembers.get(i).getPortrait().split("\\\\")[len-1]);
				}
				data.setFamilyMembers(familyMembers);
			}
		}
		PrintWriter out=response.getWriter();
		out.println(JSONUtil.ObjectToJson(data));
		
	}

}
