package xyz.migoo.template.controller.manager.member.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Getter
@Setter
public class MemberQueryReqVO extends PageParam {

    private String name;

    private String phone;

    private Integer status;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
