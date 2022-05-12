package xyz.migoo.template.dal.dataobject.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.template.dal.dataobject.IdEnhanceDO;

/**
 * 用户和角色关联
 *
 * @author ruoyi
 */
@TableName(value = "sys_user_role", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends IdEnhanceDO {
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 角色 ID
     */
    private Long roleId;

}
