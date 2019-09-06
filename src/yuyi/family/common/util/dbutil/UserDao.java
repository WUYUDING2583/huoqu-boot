package yuyi.family.common.util.dbutil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yuyi.family.common.CommonConstant;
import yuyi.family.common.util.JSONUtil;
import yuyi.family.pojo.FamilyMember;
import yuyi.family.pojo.User;

public class UserDao extends BaseDao {
	
	//插入发送用户短信的时间
	public void setSendMsgTime(String phone) {
		String sql="update user set msgTime=? where phone=?";
		Connection conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,new Date().getTime());
			pstmt.setString(2,phone);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
            try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//获取上一次发送短信的时间
	public long getLastSendMsgTime(String phone) {
		 String sql = "select msgTime from user where phone=?;";
         Connection conn;
         conn = getConnection();
	        long time ;
	        try {
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, phone);
	            ResultSet rst = pstmt.executeQuery();
	            if (rst.next()) {
	                time=rst.getLong("msgTime");
	            }else {
	            	time=-1;
	            }
	            pstmt.close();
	            conn.close();
	            return time;
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return -1;
	        }finally {
	            try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	
	//根据家人手机号获取家人信息
	public List<FamilyMember> getFamilyMembersInfo(List<String> familyMemberPhone) {
		String sql = "select * from user where phone=?;";
        String message = null;
        List<FamilyMember> list=new ArrayList<FamilyMember>();
        Connection conn;
        conn = getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for(int i=0;i<familyMemberPhone.size();i++) {
                FamilyMember familyMember=new FamilyMember();
                pstmt.setString(1, familyMemberPhone.get(i));
                ResultSet rst = pstmt.executeQuery();
                if (rst.next()) {
                	familyMember.setName(rst.getString("name").trim());
                	familyMember.setId(rst.getString("id").trim());
                	familyMember.setFamilyMemberPhone((List<String>)JSONUtil.JsonToObject(rst.getString("familyMember").trim(), List.class));
                	familyMember.setPhone(rst.getString("phone").trim());
                	familyMember.setPortrait(rst.getString("image").trim());
                    if(rst.getInt("isRegister")==1) {
                    	familyMember.setRegister(true);
                    }else {
                    	familyMember.setRegister(false);
                    }
                }else {
                	familyMember=null;
                }
                list.add(familyMember);
            }
            //获取最近三个小时的记录减少搜索时间
            sql="select address,time from location where phone=? and time>?";
            pstmt = conn.prepareStatement(sql);
            for(int i=0;i<familyMemberPhone.size();i++) {
                FamilyMember familyMember=new FamilyMember();
                pstmt.setString(1, familyMemberPhone.get(i));
                pstmt.setLong(2, new Date().getTime()-1000*60*60*3);
                ResultSet rst = pstmt.executeQuery();
                if (rst.last()) {
                	list.get(i).setAddress(rst.getString("address").trim());
                	list.get(i).setTime(rst.getLong("time"));
                }else {
                	list.get(i).setAddress("最近三小时内无定位记录");
                	list.get(i).setTime(0);
                }
            }
            pstmt.close();
            conn.close();
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
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

    // 根据手机号获取用户
    public User getUserInfo(String phone) {
        String sql = "select * from user where phone=?;";
        String message = null;
        User user=new User();
        Connection conn;
        conn = getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) {
                user.setName(rst.getString("name").trim());
                user.setId(rst.getString("id").trim());
                user.setFamilyMemberPhone((List<String>)JSONUtil.JsonToObject(rst.getString("familyMember").trim(), List.class));
                user.setPhone(rst.getString("phone").trim());
                user.setPortrait(rst.getString("image").trim());
                if(rst.getInt("isRegister")==1) {
                	user.setRegister(true);
                }else {
                	user.setRegister(false);
                }
            }else {
            	user=null;
            }
            pstmt.close();
            conn.close();
            return user;
        } catch (Exception e) {
            // TODO Auto-generated catch block
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
    
    //插入用户信息
    public boolean insertUser(User user) {
    	String sql="insert into user values(?,?,?,?,?,?)";
		Connection conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,user.getPhone());
			pstmt.setString(2,user.getName());
			pstmt.setString(3,user.getId());
			if(user.isRegister()) {
				pstmt.setInt(4, 1);
			}else {
				pstmt.setInt(4, 0);
			}
			pstmt.setString(5, user.getPortrait());
			pstmt.setString(6, JSONUtil.ObjectToJson(new ArrayList<String>()));
			pstmt.executeUpdate();
			pstmt.close();
            conn.close();
            return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally {
            try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
    	String sql = "update user set name =?,isRegister=?,image=?,familyMember=? where phone=?;";
		Connection conn;
		conn = getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setBoolean(2, user.isRegister());
			pstmt.setString(3, user.getPortrait());
			pstmt.setString(4,JSONUtil.ObjectToJson(user.getFamilyMemberPhone()));
			pstmt.setString(5, user.getPhone());
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}finally {
            try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 新增家人
     * @param currentUserPhone
     * @param familyMemberPhone
     * @return
     */
    public boolean addFamilyMember(String currentUserPhone,String familyMemberPhone) {
    	User currentUser=getUserInfo(currentUserPhone);
    	User familyMember=getUserInfo(familyMemberPhone);
    	currentUser.getFamilyMemberPhone().add(familyMemberPhone);
    	familyMember.getFamilyMemberPhone().add(currentUserPhone);
    	if(updateUser(currentUser)) {
    		if(updateUser(familyMember)) {
    			return true;
    		}
    		return false;
    	}
    	return false;
    }
    
    public List<String> adminGetAllUser(){
    	String sql = "select phone from user;";
		Connection conn;
		conn = getConnection();
		List<String> list=new ArrayList<String>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {
            	if(!rst.getString("phone").trim().equals(CommonConstant.ADMINPHONE)) {
            		list.add(rst.getString("phone"));
            	}
            }
			pstmt.close();
			conn.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
}

