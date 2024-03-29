package yuyi.family.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;

import com.mysql.jdbc.Blob;

import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;
import yuyi.family.pojo.User;

public class FileUtil {  

    //图片转化成base64字符串  
	public static String ImageToBase64(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	      
	//base64字符串转化成图片  
	public static String Base64ToImage(String base,User user,String rootPath) { // 对字节数组字符串进行Base64解码并生成图片
		if (base == null) // 图像数据为空
			return "";
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(base);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = rootPath+"\\Image";// 新生成的图片
			File validateCodeFolder = new File(imgFilePath);
			if (!validateCodeFolder.exists()) {
				validateCodeFolder.mkdirs();
			}
			imgFilePath+="\\"+user.getPhone()+".jpg";
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return imgFilePath;
		} catch (Exception e) {
			return "";
		}
	}
	
} 
