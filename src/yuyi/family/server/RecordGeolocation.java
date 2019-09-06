package yuyi.family.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yuyi.family.common.CommonConstant;
import yuyi.family.common.util.JSONUtil;
import yuyi.family.common.util.dbutil.LocationDao;
import yuyi.family.pojo.CommonData;
import yuyi.family.pojo.LocationResult;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class Geolocation
 */
public class RecordGeolocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordGeolocation() {
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
		String params=request.getParameter("geolocations");
		List<LocationResult> locationResultList=(List<LocationResult>)JSONUtil.JsonToList(params,new TypeToken<List<LocationResult>>(){}.getType());
		LocationDao dao=new LocationDao();
		CommonData data=new CommonData();
		if(!dao.insertLocations(locationResultList)) {
			data.setRetCode(CommonConstant.SERVER_FAIL_CODE);
			data.setRetMessage("数据库存储失败");
		}
		PrintWriter out=response.getWriter();
		out.println(JSONUtil.ObjectToJson(data));
	}

}
