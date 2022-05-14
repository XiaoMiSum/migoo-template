package xyz.migoo.template.dal.dataobject.developer.gennerator;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.template.dal.dataobject.IdEnhanceDO;

@TableName(value = "developer_codegen_table", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class CodegenTable extends IdEnhanceDO {

    // ========== 表相关字段 ==========

    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表描述
     */
    private String tableComment;
    /**
     * 备注
     */
    private String remark;

    // ========== 类相关字段 ==========

    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 类名称（首字母大写）
     * <p>
     * 例如说，SysUser、SysMenu、SysDictData 等等
     */
    private String className;
    /**
     * 类描述
     */
    private String classComment;
    /**
     * 作者
     */
    private String author;

    // ========== 生成相关字段 ==========

    /**
     * 模板类型
     * <p>
     * 枚举 {@link CodegenTemplateTypeEnum}
     */
    private Integer templateType;

    private Integer parentMenuId;


}