package xyz.migoo.template.service.manager.developer.generator.inner;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DatabaseTable {

    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表描述
     */
    private String tableComment;
    /**
     * 创建时间
     */
    private Date createTime;
}
