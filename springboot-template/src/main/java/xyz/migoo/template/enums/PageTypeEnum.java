package xyz.migoo.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaomi
 * Created on 2022/5/14 15:57
 */
@Getter
@AllArgsConstructor
public enum PageTypeEnum {

    MEMBER(1),
    MANAGER(2);

    /**
     * 类型
     */
    private final Integer type;
}
