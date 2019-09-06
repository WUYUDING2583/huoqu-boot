package yuyi.family.pojo;

import lombok.Data;
import java.util.List;

@Data
public class User extends CommonData{
    private String phone;
    private String name;
    private String id;
    private boolean isRegister;//是否注册
    private String portrait;
    private List<String> familyMemberPhone;
    private List<FamilyMember> familyMembers;
}
