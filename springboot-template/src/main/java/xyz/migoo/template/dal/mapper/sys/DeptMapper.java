package xyz.migoo.template.dal.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;
import xyz.migoo.template.controller.manager.sys.dept.vo.DeptQueryReqVO;
import xyz.migoo.template.dal.dataobject.sys.Dept;

import java.util.Date;
import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapperX<Dept> {

    default List<Dept> selectList(DeptQueryReqVO reqVO) {
        return selectList(new QueryWrapperX<Dept>()
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus()));
    }

    default Dept selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new QueryWrapper<Dept>().eq("parent_id", parentId)
                .eq("name", name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(new QueryWrapper<Dept>().eq("parent_id", parentId));
    }

    default boolean selectExistsByUpdateTimeAfter(Date maxUpdateTime) {
        return selectOne(new QueryWrapper<Dept>().select("id")
                .gt("update_time", maxUpdateTime).last("LIMIT 1")) != null;
    }
}
