package xyz.migoo.template.service.manager.developer.generator;

import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.developer.vo.CodegenTableQueryReqVO;
import xyz.migoo.template.controller.manager.developer.vo.CodegenUpdateReqVO;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenTable;

import java.util.Map;

public interface GeneratorService {

    GeneratorDTO get(Long id);

    void add(String sql);

    PageResult<CodegenTable> getPage(CodegenTableQueryReqVO pageReqVO);

    void remove(Long id);

    Map<String, String> generationCodes(Long id);

    void update(CodegenUpdateReqVO req);
}
