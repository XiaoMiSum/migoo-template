package xyz.migoo.template.controller.login;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.util.collection.SetUtils;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotations.CurrentUser;
import xyz.migoo.template.controller.login.vo.AuthLoginReqVO;
import xyz.migoo.template.controller.login.vo.AuthLoginRespVO;
import xyz.migoo.template.controller.login.vo.AuthMenuRespVO;
import xyz.migoo.template.controller.manager.member.vo.MemberQueryReqVO;
import xyz.migoo.template.controller.manager.sys.user.vo.UserQueryReqVO;
import xyz.migoo.template.convert.AuthConvert;
import xyz.migoo.template.dal.dataobject.BaseUser;
import xyz.migoo.template.dal.dataobject.Member;
import xyz.migoo.template.dal.dataobject.sys.Menu;
import xyz.migoo.template.dal.dataobject.sys.User;
import xyz.migoo.template.enums.MenuTypeEnum;
import xyz.migoo.template.service.UserService;
import xyz.migoo.template.service.login.TokenService;
import xyz.migoo.template.service.manager.sys.permission.PermissionService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
public class LoginController {

    @Resource
    private TokenService tokenService;
    @Resource(name = "userServiceImpl")
    private UserService<User, UserQueryReqVO> userService;
    @Resource(name = "memberServiceImpl")
    private UserService<Member, MemberQueryReqVO> memberService;
    @Resource
    private PermissionService permissionService;

    @PostMapping("/sign-in")
    public Result<AuthLoginRespVO> login(@RequestBody AuthLoginReqVO req) {
        AuthLoginRespVO resp = new AuthLoginRespVO();
        resp.setToken(tokenService.signIn(req));
        return Result.getSuccessful(resp);
    }

    @PostMapping("/sign-out")
    public Result<?> logout() {
        // 框架层已经做了退出 这里只需要提供一个接口，不需要做实际业务
        return Result.getSuccessful();
    }

    @PostMapping("/signup")
    public Result<?> signup(@RequestBody Object customer) {
        // todo 用户注册
        return Result.getSuccessful();
    }

    @GetMapping("/user-info")
    public Result<?> getUserInfo(@CurrentUser LoginUser loginUser) {
        boolean isAdminClient = LoginUser.Client.isAdminClient(loginUser.getClient());
        BaseUser user = isAdminClient ? userService.get(loginUser.getId()) : memberService.get(loginUser.getId());
        if (!isAdminClient) {
            return Result.getSuccessful(AuthConvert.INSTANCE.convert(user, Lists.newArrayList()));
        }
        Set<Long> roleIds = permissionService.getUserRoleIds(user.getId(), SetUtils.asSet(CommonStatusEnum.ENABLE.getStatus()));
        List<Menu> menuList = permissionService.getRoleMenusFromCache(
                roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType(), MenuTypeEnum.BUTTON.getType()),
                SetUtils.asSet(CommonStatusEnum.ENABLE.getStatus()),
                LoginUser.Client.isAdminClient(loginUser.getClient()));
        return Result.getSuccessful(AuthConvert.INSTANCE.convert(user, menuList));
    }

    @GetMapping("user-menus")
    public Result<List<AuthMenuRespVO>> getMenus(@CurrentUser LoginUser loginUser) {
        if (!LoginUser.Client.isAdminClient(loginUser.getClient())) {
            return Result.getSuccessful();
        }
        Set<Long> roleIds = permissionService.getUserRoleIds(loginUser.getId(), SetUtils.asSet(1));
        // 获得用户拥有的菜单列表
        List<Menu> menuList = permissionService.getRoleMenusFromCache(
                roleIds,
                SetUtils.asSet(MenuTypeEnum.DIR.getType(), MenuTypeEnum.MENU.getType()),
                SetUtils.asSet(CommonStatusEnum.ENABLE.getStatus()),
                LoginUser.Client.isAdminClient(loginUser.getClient()));
        // 转换成 Tree 结构返回
        return Result.getSuccessful(AuthConvert.INSTANCE.convert(menuList));
    }
}
