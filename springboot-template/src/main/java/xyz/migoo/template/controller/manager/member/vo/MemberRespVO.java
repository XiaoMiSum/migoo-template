package xyz.migoo.template.controller.manager.member.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberRespVO extends MemberBaseVO {
    
    private Long id;

    private Integer status;

    private Date createTime;
}
