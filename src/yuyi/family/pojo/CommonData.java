package yuyi.family.pojo;

import java.io.Serializable;

import lombok.Data;
import yuyi.family.common.CommonConstant;

@Data
public class CommonData  implements Serializable{
	private String retCode=CommonConstant.SERVER_SUCCESS_CODE;
	private String retMessage;
	private String sessionId;
	private long time;//当前时间戳
}
