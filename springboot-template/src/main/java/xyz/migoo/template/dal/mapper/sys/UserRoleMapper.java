package xyz.migoo.template.dal.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.template.dal.dataobject.sys.UserRole;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapperX<UserRole> {

    default List<UserRole> selectListByUserId(Long userId) {
        return selectList(new QueryWrapper<UserRole>().eq("user_id", userId));
    }

    default void insertList(Long userId, Collection<Long> roleIds) {
        List<UserRole> list = roleIds.stream().map(roleId -> {
            UserRole entity = new UserRole();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            return entity;
        }).toList();
        list.forEach(this::insert);
    }

    default void deleteListByUserIdAndRoleIdIds(Long userId, Collection<Long> roleIds) {
        delete(new QueryWrapper<UserRole>().eq("user_id", userId)
                .in("role_id", roleIds));
    }

    default void deleteListByUserId(Long userId) {
        delete(new QueryWrapper<UserRole>().eq("user_id", userId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new QueryWrapper<UserRole>().eq("role_id", roleId));
    }

}
