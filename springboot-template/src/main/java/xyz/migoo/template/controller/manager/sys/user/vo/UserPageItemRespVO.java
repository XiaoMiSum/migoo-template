package xyz.migoo.template.controller.manager.sys.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageItemRespVO extends UserRespVO {

    /**
     * 所在部门
     */
    private Dept dept;

    @Data
    public static class Dept {

        private Long id;

        private String name;

    }
}
