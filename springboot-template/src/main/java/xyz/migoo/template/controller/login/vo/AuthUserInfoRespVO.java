package xyz.migoo.template.controller.login.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserInfoRespVO {

    private String name;

    private String avatar;

    private Set<String> permissions;

}
