package xyz.migoo.template.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import xyz.migoo.framework.common.util.json.JsonUtils;

@TableName("user")
public class User extends IdEnhanceDO {

    private String phone;

    private String password;

    private String realName;

    private String avatar;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
