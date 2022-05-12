package xyz.migoo.template.controller.manager.sys.permission.role;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.template.controller.manager.sys.permission.role.vo.*;
import xyz.migoo.template.convert.manager.sys.RoleConvert;
import xyz.migoo.template.dal.dataobject.sys.Role;
import xyz.migoo.template.service.manager.sys.permission.RoleService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:role:query')")
    public Result<PageResult<RoleRespVO>> getRolePage(RoleQueryReqVO req) {
        return Result.getSuccessful(roleService.getPage(req));
    }

    @PostMapping("")
    @PreAuthorize("@ss.hasPermission('system:role:add')")
    public Result<?> addRole(@RequestBody RoleAddReqVO reqVO) {
        roleService.verify(reqVO.getCode());
        roleService.add(RoleConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @PutMapping("")
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    public Result<?> updateRole(@RequestBody RoleUpdateReqVO reqVO) {
        roleService.update(RoleConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:role:edit')")
    public Result<?> getRole(@PathVariable Long id) {
        return Result.getSuccessful(RoleConvert.INSTANCE.convert(roleService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:role:remove')")
    public Result<?> removeRole(@PathVariable Long id) {
        roleService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<RoleSimpleRespVO>> getSimpleMenus() {
        // 获得角色列表，只要开启状态的
        List<Role> list = roleService.getList(CommonStatusEnum.ENABLE.getStatus());
        return Result.getSuccessful(RoleConvert.INSTANCE.convert(list));
    }

    @GetMapping("/{roleId}/menu")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public Result<Set<Long>> getRoleMenus(@PathVariable("roleId") Long roleId) {
        return Result.getSuccessful(roleService.getRoleMenuList(roleId));
    }

    @PostMapping("/{roleId}/menu")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public Result<?> assignRoleMenus(@Valid @RequestBody PermissionAssignRoleMenuReqVO reqVO) {
        roleService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return Result.getSuccessful(true);
    }
}
