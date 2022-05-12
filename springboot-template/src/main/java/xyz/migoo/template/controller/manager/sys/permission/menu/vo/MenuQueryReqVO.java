package xyz.migoo.template.controller.manager.sys.permission.menu.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.util.json.JsonUtils;

@Getter
@Setter
public class MenuQueryReqVO {

    private String name;

    private Integer status;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
