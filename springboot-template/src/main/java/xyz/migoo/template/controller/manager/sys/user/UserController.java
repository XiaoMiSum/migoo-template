package xyz.migoo.template.controller.manager.sys.user;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.util.collection.CollectionUtils;
import xyz.migoo.template.controller.manager.sys.user.vo.*;
import xyz.migoo.template.convert.manager.sys.UserConvert;
import xyz.migoo.template.dal.dataobject.sys.Dept;
import xyz.migoo.template.dal.dataobject.sys.User;
import xyz.migoo.template.service.UserService;
import xyz.migoo.template.service.manager.sys.dept.DeptService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource(name = "userServiceImpl")
    private UserService<User, UserQueryReqVO> userService;
    @Resource
    private DeptService deptService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public Result<PageResult<UserPageItemRespVO>> getUserPage(UserQueryReqVO req) {
        // 获得用户分页列表
        PageResult<User> pageResult = userService.getPage(req);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return Result.getSuccessful(new PageResult<>(pageResult.getTotal()));
        }
        // 获得拼接需要的数据
        Collection<Long> deptIds = CollectionUtils.convertList(pageResult.getList(), User::getDeptId);
        Map<Long, Dept> deptMap = deptService.getDeptMap(deptIds);
        // 拼接结果返回
        List<UserPageItemRespVO> userList = Lists.newArrayListWithCapacity(pageResult.getList().size());
        pageResult.getList().forEach(user -> {
            UserPageItemRespVO respVO = UserConvert.INSTANCE.convert(user);
            respVO.setDept(UserConvert.INSTANCE.convert(deptMap.get(user.getDeptId())));
            userList.add(respVO);
        });
        return Result.getSuccessful(new PageResult<>(userList, pageResult.getTotal()));
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:user:add')")
    public Result<?> addUser(@Valid @RequestBody UserAddReqVO user) {
        userService.verify(user.getPhone());
        userService.add(UserConvert.INSTANCE.convert(user));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:user:edit')")
    public Result<?> updateUser(@Valid @RequestBody UserUpdateReqVO user) {
        userService.update(UserConvert.INSTANCE.convert(user));
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:user:edit')")
    public Result<?> getUser(@PathVariable Long id) {
        return Result.getSuccessful(UserConvert.INSTANCE.convert(userService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:user:remove')")
    public Result<?> removeUser(@PathVariable Long id) {
        userService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<UserSimpleRespVO>> getSimpleMenus() {
        // 获得用户列表，只要开启状态的
        List<User> list = userService.get(CommonStatusEnum.ENABLE.getStatus());
        return Result.getSuccessful(UserConvert.INSTANCE.convert(list));
    }

    @GetMapping("/{userId}/role")
    public Result<Set<Long>> getUserRoles(@PathVariable("userId") Long userId) {
        return Result.getSuccessful(userService.getUserRoleList(userId));
    }

    @PostMapping("/{userId}/role")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public Result<?> assignUserRole(@Valid @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        userService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return Result.getSuccessful(true);
    }

    @PostMapping("/password")
    @PreAuthorize("@ss.hasPermission('system:user:reset-password')")
    public Result<?> resetPassword(@RequestBody @Valid UserPasswordReqVO reqVO) {
        userService.update(UserConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful(true);
    }
}
