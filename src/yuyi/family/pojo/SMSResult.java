package yuyi.family.pojo;

import com.github.qcloudsms.SmsSingleSenderResult;

import lombok.Data;

@Data
public class SMSResult extends CommonData{
	private SmsSingleSenderResult result;
	private String code;
}
