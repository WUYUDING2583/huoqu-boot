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
import yuyi.family.common.util.StringUtil;
import yuyi.family.common.util.TimeUtil;
import yuyi.family.common.util.dbutil.LocationDao;
import yuyi.family.pojo.FamilyMemberLocations;
import yuyi.family.pojo.LocationResult;

/**
 * Servlet implementation class GetFamilyMemberLocation
 */
public class GetFamilyMemberLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFamilyMemberLocation() {
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
		response.setHeader("content-type", "application/json");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");//!!!!!修改的地方
		String phone=request.getParameter("phone");
		long startTime=Long.parseLong(request.getParameter("startTime"));
		long endTime=Long.parseLong(request.getParameter("endTime"));
		long lastGetLocationsTime=0;
		if(StringUtil.isNotEmpty(request.getParameter("lastGetLocationsTime"))) {
			lastGetLocationsTime=Long.parseLong(request.getParameter("lastGetLocationsTime"));
		}
		LocationDao dao=new LocationDao();
		List<LocationResult> familyMemberLocations;
		if(lastGetLocationsTime!=0) {
			familyMemberLocations=dao.getFamilyMemberLocations(phone, lastGetLocationsTime, endTime);
		}else {
			familyMemberLocations=dao.getFamilyMemberLocations(phone, startTime, endTime);
		}
		FamilyMemberLocations data=new FamilyMemberLocations();
		if(null==familyMemberLocations) {
			familyMemberLocations=new ArrayList<LocationResult>();
			data.setRetCode(CommonConstant.SERVER_FAIL_CODE);
		}
		data.setFamilyMemberLocations(familyMemberLocations);
		System.out.println(phone);
		System.out.println(startTime);
		System.out.println(endTime);
		System.out.println(JSONUtil.ObjectToJson(data));
		PrintWriter out=response.getWriter();
		out.println(JSONUtil.ObjectToJson(data));
	}

}
