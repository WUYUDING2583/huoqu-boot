package yuyi.family.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yuyi.family.common.CommonConstant;
import yuyi.family.common.util.FileUtil;
import yuyi.family.common.util.JSONUtil;
import yuyi.family.common.util.dbutil.UserDao;
import yuyi.family.pojo.User;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(getServletContext().getRealPath("/"));
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
		User user=(User)JSONUtil.JsonToObject(request.getParameter("userInfo"), User.class);
		System.out.println(user.getPortrait());
		String imagePath=FileUtil.Base64ToImage(user.getPortrait(),user,getServletContext().getRealPath("/"));
		if(null==imagePath||"".equals(imagePath)) {
			user.setRetCode(CommonConstant.SERVER_FAIL_CODE);
			user.setRetMessage("注册失败，请重试");
			PrintWriter out=response.getWriter();
			out.println(JSONUtil.ObjectToJson(user));
			return;
		}
		user.setPortrait(imagePath);
		UserDao dao=new UserDao();
		user.setRegister(true);
		System.out.println("user in register");
		System.out.println(user);
		if(dao.insertUser(user)) {
			user.setRetMessage("注册成功");
		}else {
			user.setRetCode(CommonConstant.SERVER_FAIL_CODE);
			user.setRetMessage("注册失败，请重试");
		}
		PrintWriter out=response.getWriter();
		out.println(JSONUtil.ObjectToJson(user));
	}

}
