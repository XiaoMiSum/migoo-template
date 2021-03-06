package xyz.migoo.template.controller.manager.sys.permission.menu;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.template.controller.manager.sys.permission.menu.vo.*;
import xyz.migoo.template.convert.manager.sys.MenuConvert;
import xyz.migoo.template.dal.dataobject.sys.Menu;
import xyz.migoo.template.service.manager.sys.permission.MenuService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public Result<List<MenuRespVO>> getMenus(MenuQueryReqVO reqVO) {
        List<Menu> list = menuService.get(reqVO);
        list.sort(Comparator.comparing(Menu::getSort));
        return Result.getSuccessful(MenuConvert.INSTANCE.convert(list));
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:menu:add')")
    public Result<?> createMenu(@Valid @RequestBody MenuAddReqVO reqVO) {
        menuService.add(MenuConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:menu:edit')")
    public Result<?> updateMenu(@Valid @RequestBody MenuUpdateReqVO reqVO) {
        menuService.update(MenuConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:menu:remove')")
    public Result<?> deleteMenu(@PathVariable("id") Long id) {
        menuService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:menu:edit')")
    public Result<MenuRespVO> getMenu(@PathVariable("id") Long id) {
        return Result.getSuccessful(MenuConvert.INSTANCE.convert(menuService.get(id)));
    }

    @GetMapping("/simple")
    public Result<List<MenuSimpleRespVO>> getSimpleMenus() {
        // ??????????????????????????????????????????
        MenuQueryReqVO reqVO = new MenuQueryReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<Menu> list = menuService.get(reqVO);
        // ???????????????????????????
        list.sort(Comparator.comparing(Menu::getSort));
        return Result.getSuccessful(MenuConvert.INSTANCE.convert0(list));
    }
}
