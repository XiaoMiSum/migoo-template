package xyz.migoo.template.convert.manager.sys;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.template.controller.manager.sys.user.vo.*;
import xyz.migoo.template.dal.dataobject.sys.Dept;
import xyz.migoo.template.dal.dataobject.sys.User;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    User convert(UserAddReqVO addReq);

    User convert(UserUpdateReqVO updateReq);

    User convert(UserPasswordReqVO reqVO);

    List<UserSimpleRespVO> convert(List<User> list);

    UserPageItemRespVO convert(User user);

    UserPageItemRespVO.Dept convert(Dept bean);
}
