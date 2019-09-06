package yuyi.family.common.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class JSONUtil {
	
	private static String json="{\"result\":0,\"errmsg\":\"OK\",\"ext\":\"\",\"sid\":\"2034:0817162849539327548648206113531\",\"fee\":1}";

    private static Gson gson=new Gson();

    public static String ObjectToJson(Object object){
        return gson.toJson(object);
    }

    public static Object JsonToObject(String json,Class pojoClass){
        return gson.fromJson(json,pojoClass);
    }
    
    public static Map<String,Object> JsonToMap(){
    	Map<String,Object> output=new HashMap<String,Object>();
    	output=gson.fromJson(json, Map.class);
    	System.out.println(output.get("result"));
    	return output;
    }
    
    public static List JsonToList(String json, Type type){
        return gson.fromJson(json, type);
    }
}
