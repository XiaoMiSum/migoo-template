package xyz.migoo.template.dal.mapper.developer;

import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;
import xyz.migoo.template.controller.manager.developer.vo.CodegenTableQueryReqVO;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenTable;

@Mapper
public interface CodegenTableMapper extends BaseMapperX<CodegenTable> {
    default CodegenTable selectByTableName(String tableName) {
        return selectOne("table_name", tableName);
    }

    default PageResult<CodegenTable> selectPage(CodegenTableQueryReqVO pageReqVO) {
        return selectPage(pageReqVO, new QueryWrapperX<CodegenTable>()
                .likeIfPresent("table_name", pageReqVO.getTableName())
                .likeIfPresent("table_comment", pageReqVO.getTableComment()));
    }
}
