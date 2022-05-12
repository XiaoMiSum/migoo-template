package xyz.migoo.template.dal.mapper.sys;

import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;
import xyz.migoo.template.controller.manager.sys.user.vo.UserQueryReqVO;
import xyz.migoo.template.dal.dataobject.sys.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapperX<User> {


    default User selectByPhone(String phone) {
        return selectOne("phone", phone);
    }

    default PageResult<User> selectPage(UserQueryReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<User>()
                .likeIfPresent("phone", reqVO.getPhone())
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("dept_id", reqVO.getDeptId())
                .eqIfPresent("status", reqVO.getStatus())
                .orderByDesc("id"));
    }

    default List<User> selectList(Integer status) {
        return selectList(new QueryWrapperX<User>()
                .eqIfPresent("status", status)
                .orderByDesc("id"));
    }
}
