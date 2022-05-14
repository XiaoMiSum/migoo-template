package xyz.migoo.template.dal.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;
import xyz.migoo.template.controller.manager.sys.permission.role.vo.RoleQueryReqVO;
import xyz.migoo.template.dal.dataobject.sys.Role;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapperX<Role> {

    default Role selectByName(String name) {
        return selectOne(new QueryWrapperX<Role>().eq("name", name));
    }

    default Role selectByCode(String code) {
        return selectOne(new QueryWrapperX<Role>().eq("code", code));
    }

    default List<Role> selectListByStatus(@Nullable Collection<Integer> statuses) {
        return selectList(new QueryWrapperX<Role>().in("status", statuses));
    }

    default boolean selectExistsByUpdateTimeAfter(Date maxUpdateTime) {
        return selectOne(new QueryWrapper<Role>().select("id")
                .gt("update_time", maxUpdateTime).last("LIMIT 1")) != null;
    }

    default PageResult<Role> selectPage(RoleQueryReqVO req) {
        return selectPage(req, new QueryWrapperX<Role>()
                .likeIfPresent("name", req.getName())
                .likeIfPresent("code", req.getCode())
                .eqIfPresent("status", req.getStatus())
                .orderByAsc("id"));
    }

}
