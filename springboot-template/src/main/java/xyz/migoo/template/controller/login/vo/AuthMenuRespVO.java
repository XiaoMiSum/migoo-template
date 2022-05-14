package xyz.migoo.template.controller.login.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthMenuRespVO {

    /**
     * 菜单id
     */
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否可见
     */
    private Integer visible;

    /**
     * 子路由
     */
    private List<AuthMenuRespVO> children;
}
