package xyz.migoo.template.dal.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.template.dal.dataobject.sys.RoleMenu;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMenuMapper extends BaseMapperX<RoleMenu> {

    default List<RoleMenu> selectListByRoleId(Long roleId) {
        return selectList(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
    }

    default void insertList(Long roleId, Collection<Long> menuIds) {
        List<RoleMenu> list = menuIds.stream().map(menuId -> {
            RoleMenu entity = new RoleMenu();
            entity.setRoleId(roleId);
            entity.setMenuId(menuId);
            return entity;
        }).toList();
        list.forEach(this::insert);
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new QueryWrapper<RoleMenu>().eq("role_id", roleId)
                .in("menu_id", menuIds));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new QueryWrapper<RoleMenu>().eq("menu_id", menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
    }

    @Select("SELECT id FROM sys_role_menu WHERE update_time > #{maxUpdateTime} LIMIT 1")
    Long selectExistsByUpdateTimeAfter(Date maxUpdateTime);

}
