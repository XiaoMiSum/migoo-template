package xyz.migoo.template.convert.manager.developer;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.developer.vo.CodegenColumnRespVO;
import xyz.migoo.template.controller.manager.developer.vo.CodegenDetailRespVO;
import xyz.migoo.template.controller.manager.developer.vo.CodegenPreviewRespVO;
import xyz.migoo.template.controller.manager.developer.vo.CodegenTableRespVO;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenColumn;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenTable;
import xyz.migoo.template.service.manager.developer.generator.GeneratorDTO;
import xyz.migoo.template.service.manager.developer.generator.inner.DatabaseColumn;
import xyz.migoo.template.service.manager.developer.generator.inner.DatabaseTable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface GeneratorConvert {

    GeneratorConvert INSTANCE = Mappers.getMapper(GeneratorConvert.class);

    default CodegenDetailRespVO convert(GeneratorDTO bean) {
        CodegenDetailRespVO respVO = new CodegenDetailRespVO();
        respVO.setTable(convert(bean.getTable()));
        respVO.setColumns(convert01(bean.getColumns()));
        return respVO;
    }

    CodegenTableRespVO convert(CodegenTable bean);

    CodegenTable convert(DatabaseTable bean);

    CodegenColumn convert(DatabaseColumn bean);

    List<CodegenColumnRespVO> convert01(List<CodegenColumn> list);

    List<CodegenColumn> convert(List<DatabaseColumn> list);

    PageResult<CodegenTableRespVO> convert(PageResult<CodegenTable> page);

    default List<CodegenPreviewRespVO> convert(Map<String, String> codes) {
        return codes.entrySet().stream()
                .map(entry -> new CodegenPreviewRespVO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
