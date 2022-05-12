package xyz.migoo.template.convert.manager.sys;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.template.controller.manager.sys.permission.role.vo.RoleAddReqVO;
import xyz.migoo.template.controller.manager.sys.permission.role.vo.RoleRespVO;
import xyz.migoo.template.controller.manager.sys.permission.role.vo.RoleSimpleRespVO;
import xyz.migoo.template.controller.manager.sys.permission.role.vo.RoleUpdateReqVO;
import xyz.migoo.template.dal.dataobject.sys.Role;

import java.util.List;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    Role convert(RoleAddReqVO reqVO);

    Role convert(RoleUpdateReqVO reqVO);

    RoleRespVO convert(Role role);

    List<RoleSimpleRespVO> convert(List<Role> list);
}
