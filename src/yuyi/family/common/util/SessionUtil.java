package yuyi.family.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionUtil {
	private static Map<String,HttpSession> sessionList=new HashMap<String,HttpSession>();
	
	public static void addSession(HttpSession session) {
		if(session!=null) {
			sessionList.put(session.getId(),session);
		}
	}
	
	public static void removeSession(String sessionId) {
		if(StringUtil.isNotEmpty(sessionId)) {
			sessionList.remove(sessionId);
		}
	}
	
	public static HttpSession getSession(String sessionId) {
		if(StringUtil.isNotEmpty(sessionId)) {
			return sessionList.get(sessionId);
		}
		return null;
	}

}
