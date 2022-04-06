package xyz.migoo.template.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;

public abstract class IdEnhanceDO extends BaseDO {

    @TableId
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
