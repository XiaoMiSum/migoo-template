package xyz.migoo.template.service.manager.developer.generator;

import cn.hutool.core.collection.CollUtil;
import org.apache.commons.collections4.KeyValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.migoo.framework.common.exception.ErrorCode;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.developer.vo.CodegenTableQueryReqVO;
import xyz.migoo.template.controller.manager.developer.vo.CodegenUpdateReqVO;
import xyz.migoo.template.convert.manager.developer.GeneratorConvert;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenColumn;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenTable;
import xyz.migoo.template.dal.mapper.developer.CodegenColumnMapper;
import xyz.migoo.template.dal.mapper.developer.CodegenTableMapper;
import xyz.migoo.template.service.manager.developer.generator.inner.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static xyz.migoo.template.enums.ErrorCodeConstants.CODEGEN_COLUMN_NOT_EXISTS;
import static xyz.migoo.template.enums.ErrorCodeConstants.CODEGEN_TABLE_NOT_EXISTS;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Resource
    private GeneratorEngine generatorEngine;
    @Resource
    private CodegenTableMapper codegenTableMapper;
    @Resource
    private CodegenColumnMapper codegenColumnMapper;

    @Override
    public GeneratorDTO get(Long id) {
        GeneratorDTO generator = new GeneratorDTO();
        generator.setTable(codegenTableMapper.selectById(id));
        generator.setColumns(codegenColumnMapper.selectListByTableId(id));
        return generator;
    }

    @Override
    public void add(String sql) {
        try {
            KeyValue<DatabaseTable, List<DatabaseColumn>> result = GeneratorSQLParser.parse(sql);
            this.dropIfExists(result.getKey().getTableName());
            // ?????? ToolCodegenTableDO ?????????????????? DB ???
            CodegenTable table = GeneratorBuilder.get().buildTable(result.getKey());
            codegenTableMapper.insert(table);
            // ?????? ToolCodegenColumnDO ?????????????????? DB ???
            List<CodegenColumn> columns = GeneratorBuilder.get().buildColumns(result.getValue());
            columns.forEach(column -> {
                column.setTableId(table.getId());
                codegenColumnMapper.insert(column);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ServiceExceptionUtil.get(new ErrorCode(999, "????????????"));
        }
    }

    @Override
    public PageResult<CodegenTable> getPage(CodegenTableQueryReqVO pageReqVO) {
        return codegenTableMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        codegenTableMapper.deleteById(id);
        codegenColumnMapper.deleteByTableId(id);
    }

    @Override
    public Map<String, String> generationCodes(Long id) {
        // ????????????????????????
        CodegenTable table = codegenTableMapper.selectById(id);
        if (Objects.isNull(table)) {
            throw ServiceExceptionUtil.get(CODEGEN_TABLE_NOT_EXISTS);
        }
        List<CodegenColumn> columns = codegenColumnMapper.selectListByTableId(table.getId());
        if (CollUtil.isEmpty(columns)) {
            throw ServiceExceptionUtil.get(CODEGEN_COLUMN_NOT_EXISTS);
        }

        // ????????????
        return generatorEngine.execute(table, columns);
    }

    @Override
    public void update(CodegenUpdateReqVO req) {
        // ????????????????????????
        if (codegenTableMapper.selectById(req.getTable().getId()) == null) {
            throw ServiceExceptionUtil.get(CODEGEN_TABLE_NOT_EXISTS);
        }
        // ?????? table ?????????
        CodegenTable codegenTable = GeneratorConvert.INSTANCE.convert(req.getTable());
        codegenTableMapper.updateById(codegenTable);
        // ?????? column ????????????
        List<CodegenColumn> CodegenColumns = GeneratorConvert.INSTANCE.convert02(req.getColumns());
        CodegenColumns.forEach(column -> codegenColumnMapper.updateById(column));
    }

    private void dropIfExists(String tableName) {
        CodegenTable table = codegenTableMapper.selectByTableName(tableName);
        if (Objects.nonNull(table)) {
            codegenTableMapper.deleteById(table.getId());
            codegenColumnMapper.deleteByTableId(table.getId());
        }
    }

}
