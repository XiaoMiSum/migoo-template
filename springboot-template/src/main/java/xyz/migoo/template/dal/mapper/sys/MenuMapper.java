package xyz.migoo.template.dal.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;
import xyz.migoo.template.controller.manager.sys.permission.menu.vo.MenuQueryReqVO;
import xyz.migoo.template.dal.dataobject.sys.Menu;

import java.util.Date;
import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapperX<Menu> {

    default Menu selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new QueryWrapper<Menu>().eq("parent_id", parentId)
                .eq("name", name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(new QueryWrapper<Menu>().eq("parent_id", parentId));
    }

    default List<Menu> selectList(String name, Integer status) {
        return selectList(new QueryWrapperX<Menu>().likeIfPresent("name", name)
                .eqIfPresent("status", status));
    }

    default boolean selectExistsByUpdateTimeAfter(Date maxUpdateTime) {
        return selectOne(new QueryWrapper<Menu>().select("id")
                .gt("update_time", maxUpdateTime).last("LIMIT 1")) != null;
    }
    
    default List<Menu> selectList(MenuQueryReqVO reqVO) {
        return selectList(new QueryWrapperX<Menu>().likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus()));
    }

}
