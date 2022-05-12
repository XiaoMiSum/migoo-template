package xyz.migoo.template.dal.mapper.developer;

import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenColumn;

import java.util.List;

@Mapper
public interface CodegenColumnMapper extends BaseMapperX<CodegenColumn> {

    default List<CodegenColumn> selectListByTableId(Long tableId) {
        return selectList("table_id", tableId);
    }

    default void deleteByTableId(Long tableId) {
        delete(new QueryWrapperX<CodegenColumn>().eq("table_id", tableId));
    }

}
