package xyz.migoo.template.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.util.collection.CollectionUtils;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.template.controller.login.vo.AuthLoginReqVO;
import xyz.migoo.template.controller.login.vo.AuthMenuRespVO;
import xyz.migoo.template.controller.login.vo.AuthUserInfoRespVO;
import xyz.migoo.template.dal.dataobject.BaseUser;
import xyz.migoo.template.dal.dataobject.sys.Menu;
import xyz.migoo.template.enums.MenuIdEnum;

import java.util.*;

@Mapper
public interface AuthConvert {
    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    /**
     * 转换为登录用户对象
     *
     * @param user 管理员或会员
     * @param req  登录信息
     * @return 登录用户对象
     */
    default LoginUser convert(BaseUser user, AuthLoginReqVO req) {
        return new LoginUser().setId(user.getId())
                .setName(user.getName())
                .setUpdateTime(new Date())
                .setEnabled(Objects.equals(user.getStatus(), CommonStatusEnum.ENABLE.getStatus()))
                .setUsername(req.getUsername())
                .setPassword(user.getPassword())
                .setClient(req.getClient());
    }

    default AuthUserInfoRespVO convert(BaseUser user, List<Menu> menus) {
        return new AuthUserInfoRespVO()
                .setName(user.getName())
                .setAvatar(user.getAvatar())
                .setPermissions(CollectionUtils.convertSet(menus, Menu::getPermission));
    }

    AuthMenuRespVO convert(Menu menu);

    default List<AuthMenuRespVO> convert(List<Menu> menuList) {
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(Menu::getSort));
        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<Long, AuthMenuRespVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getId(), convert(menu)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(MenuIdEnum.ROOT.getId())).forEach(childNode -> {
            // 获得父节点
            AuthMenuRespVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                        childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return CollectionUtils.filterList(treeNodeMap.values(), node -> MenuIdEnum.ROOT.getId().equals(node.getParentId()));
    }
}
