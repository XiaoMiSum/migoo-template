package xyz.migoo.template.controller.manager.developer.generator.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodegenDetailRespVO {

    private CodegenTableRespVO table;

    private List<CodegenColumnRespVO> columns;

}