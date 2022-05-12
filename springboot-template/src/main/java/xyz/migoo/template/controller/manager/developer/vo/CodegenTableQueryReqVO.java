package xyz.migoo.template.controller.manager.developer.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.migoo.framework.common.pojo.PageParam;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CodegenTableQueryReqVO extends PageParam {

    private String tableName;

    private String tableComment;

}
