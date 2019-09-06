package yuyi.family.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

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
import yuyi.family.pojo.CommonData;
import yuyi.family.pojo.SMSResult;

/**
 * Servlet implementation class SendMSG
 * ���Ͷ�����֤��
 */
public class SendMSG extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMSG() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String phone=request.getParameter("phone");
		String sessionId=request.getParameter("sessionId");
		SMSResult result=SMSUtil.sendSMSACode(phone);
		result.setTime(new Date().getTime());
		response.setHeader("content-type", "application/json");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");//!!!!!修改的地方
		CommonData data=new CommonData();
		if(result.getRetCode().equals(CommonConstant.SERVER_SUCCESS_CODE)) {
			if(result.getResult().result!=0) {
				data.setRetCode(CommonConstant.SERVER_FAIL_CODE);
				data.setRetMessage(result.getResult().errMsg);
			}else {
				HttpSession session;
				if(StringUtil.isNotEmpty(sessionId)) {
					session=SessionUtil.getSession(sessionId);
					if(session==null) {
						session=request.getSession();
					}
				}else {
					session=request.getSession();
				}
				session.setAttribute(phone, result);
				data.setSessionId(session.getId());
				SessionUtil.removeSession(session.getId());
				SessionUtil.addSession(session);
				System.out.println("set session:"+session.getId());
				System.out.println("code:"+result.getCode());
			}
		}else {
			data.setRetCode(CommonConstant.SERVER_FAIL_CODE);
			data.setRetMessage(result.getRetMessage());
		}
		PrintWriter out=response.getWriter();
		out.println(JSONUtil.ObjectToJson(data));
	}

}
