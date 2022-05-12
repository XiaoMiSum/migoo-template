package xyz.migoo.template.dal.dataobject.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.migoo.template.dal.dataobject.IdEnhanceDO;

/**
 * 角色和菜单关联
 *
 * @author ruoyi
 */
@TableName(value = "sys_role_menu", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends IdEnhanceDO {

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;

}
