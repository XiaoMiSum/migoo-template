package xyz.migoo.template.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.util.json.JsonUtils;

@TableName("user")
@Getter
@Setter
public class Member extends BaseUser {


    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
