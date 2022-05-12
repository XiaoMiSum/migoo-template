package xyz.migoo.template.service.manager.developer.generator;

import lombok.Data;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenColumn;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenTable;

import java.util.List;

@Data
public class GeneratorDTO {

    private CodegenTable table;

    private List<CodegenColumn> columns;
}
