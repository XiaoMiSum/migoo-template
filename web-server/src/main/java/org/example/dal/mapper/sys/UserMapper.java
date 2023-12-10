package org.example.dal.mapper.sys;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.annotations.Mapper;
import org.example.controller.sys.user.vo.UserQueryReqVO;
import org.example.dal.dataobject.sys.User;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.LambdaQueryWrapperX;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapperX<User> {


    default User selectByUsername(String username) {
        return selectOne(new LambdaQueryWrapperX<User>()
                .eq(User::getUsername, username));
    }

    default PageResult<User> selectPage(UserQueryReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<User>()
                .eqIfPresent(User::getDeptId, reqVO.getDeptId())
                .eqIfPresent(User::getStatus, reqVO.getStatus())
                .gt(User::getId, 1)
                .and(StrUtil.isNotBlank(reqVO.getUsername()), it ->
                        it.like(User::getUsername, reqVO.getUsername())
                                .or().like(User::getName, reqVO.getUsername())
                )
                .orderByDesc(User::getId));
    }

    default List<User> selectList(Integer status) {
        return selectList(new LambdaQueryWrapperX<User>()
                .eqIfPresent(User::getStatus, status)
                .orderByDesc(User::getId));
    }
}
