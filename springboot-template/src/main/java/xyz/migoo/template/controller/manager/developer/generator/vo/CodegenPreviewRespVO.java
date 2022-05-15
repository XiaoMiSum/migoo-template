package xyz.migoo.template.controller.manager.developer.generator.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodegenPreviewRespVO {

    private String filePath;

    private String code;

}