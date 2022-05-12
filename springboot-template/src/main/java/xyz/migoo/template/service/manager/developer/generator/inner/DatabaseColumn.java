package xyz.migoo.template.service.manager.developer.generator.inner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatabaseColumn {

    /**
     * 表名称
     */
    private String tableName;
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 字段描述
     */
    private String columnComment;
    /**
     * 是否允许为空
     */
    private Boolean nullable;
    /**
     * 是否主键
     */
    private Boolean primaryKey;
    /**
     * 是否自增
     */
    private Boolean autoIncrement;
    /**
     * 排序字段
     */
    private Integer ordinalPosition;
}
