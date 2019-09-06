package yuyi.family.common.util.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yuyi.family.common.util.TimeUtil;
import yuyi.family.pojo.LocationResult;

public class LocationDao extends BaseDao{
	
	/**
	 * 获取家人某段时间的定位信息
	 * @param phone
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<LocationResult> getFamilyMemberLocations(String phone,long startTime,long endTime){
		String sql="select * from location where phone=?";
		if(startTime==endTime) {
			sql+=" and time=?;";
		}else {
			sql+=" and time>? and time<?;";
		}
		Connection conn=getConnection();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, phone);
			pstmt.setLong(2, startTime);
			if(startTime!=endTime) {
				pstmt.setLong(3, endTime);
			}
			ResultSet rst=pstmt.executeQuery();
			List<LocationResult> list=getListFromRST(rst);
			pstmt.close();
			conn.close();
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	
	private List<LocationResult> getListFromRST(ResultSet rst) throws SQLException{
		List<LocationResult> list=new ArrayList<LocationResult>();
		while(rst.next()) {
			LocationResult location=new LocationResult();
			location.setUserPhone(rst.getString("phone").trim());
			location.setLatitude(rst.getDouble("latitude"));
			location.setLongtitude(rst.getDouble("longitude"));
			location.setAddress(rst.getString("address"));
			location.setAccuracy(rst.getFloat("accuracy"));
			location.setTime(rst.getLong("time"));
			list.add(location);
		}
		return list;
	}
	
	//插入定位信息
	public boolean insertLocations(List<LocationResult> list) {
		String sql="insert into location values(?,?,?,?,?,?)";
			Connection conn = getConnection();
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				for(int i=0;i<list.size();i++) {
					pstmt.setString(1, list.get(i).getUserPhone());
					pstmt.setDouble(2, list.get(i).getLatitude());
					pstmt.setDouble(3, list.get(i).getLongtitude());
					pstmt.setString(4, list.get(i).getAddress());
					pstmt.setFloat(5, list.get(i).getAccuracy());
					pstmt.setLong(6, list.get(i).getTime());
					pstmt.executeUpdate();
				}
				pstmt.close();
	            conn.close();
	            return true;
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return false;
	}

}
