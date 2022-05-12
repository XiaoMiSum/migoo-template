package xyz.migoo.template.dal.dataobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseUser extends IdEnhanceDO {

    private String phone;

    private String password;

    private String name;

    private String avatar;

    private Integer status;

    private Integer gender;
}
