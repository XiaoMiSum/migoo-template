package xyz.migoo.template.dal.dataobject.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.template.dal.dataobject.IdEnhanceDO;

/**
 * 岗位表
 *
 * @author ruoyi
 */
@TableName("sys_post")
@Data
@EqualsAndHashCode(callSuper = true)
public class Post extends IdEnhanceDO {

    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位编码
     */
    private String code;
    /**
     * 岗位排序
     */
    private Integer sort;
    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
