package xyz.migoo.template.convert.manager.sys;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.template.controller.manager.sys.permission.menu.vo.MenuAddReqVO;
import xyz.migoo.template.controller.manager.sys.permission.menu.vo.MenuRespVO;
import xyz.migoo.template.controller.manager.sys.permission.menu.vo.MenuSimpleRespVO;
import xyz.migoo.template.controller.manager.sys.permission.menu.vo.MenuUpdateReqVO;
import xyz.migoo.template.dal.dataobject.sys.Menu;

import java.util.List;

@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuRespVO> convert(List<Menu> list);

    List<MenuSimpleRespVO> convert0(List<Menu> list);

    MenuRespVO convert(Menu menu);

    Menu convert(MenuAddReqVO addReq);

    Menu convert(MenuUpdateReqVO updateReq);
}
