package xyz.migoo.template.controller.manager.developer;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.util.servlet.ServletUtils;
import xyz.migoo.template.controller.manager.developer.vo.CodegenDetailRespVO;
import xyz.migoo.template.controller.manager.developer.vo.CodegenPreviewRespVO;
import xyz.migoo.template.controller.manager.developer.vo.CodegenTableQueryReqVO;
import xyz.migoo.template.controller.manager.developer.vo.CodegenTableRespVO;
import xyz.migoo.template.convert.manager.developer.GeneratorConvert;
import xyz.migoo.template.service.manager.developer.generator.GeneratorService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Resource
    private GeneratorService generatorService;

    @GetMapping("")
    @PreAuthorize("@ss.hasPermission('developer:generator:query')")
    public Result<PageResult<CodegenTableRespVO>> getPage(@Valid CodegenTableQueryReqVO pageReqVO) {
        return Result.getSuccessful(GeneratorConvert.INSTANCE.convert(generatorService.getPage(pageReqVO)));
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('developer:generator:create')")
    public Result<?> add(@RequestBody String sql) {
        generatorService.add(sql);
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('developer:generator:edit')")
    public Result<CodegenDetailRespVO> get(@PathVariable("id") Long id) {
        return Result.getSuccessful(GeneratorConvert.INSTANCE.convert(generatorService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('developer:generator:delete')")
    public Result<CodegenDetailRespVO> remove(@PathVariable("id") Long id) {
        generatorService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/{id}/preview")
    @PreAuthorize("@ss.hasPermission('developer:generator:preview')")
    public Result<List<CodegenPreviewRespVO>> previewCodegen(@PathVariable("id") Long id) {
        Map<String, String> codes = generatorService.generationCodes(id);
        return Result.getSuccessful(GeneratorConvert.INSTANCE.convert(codes));
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("@ss.hasPermission('developer:generator:download')")
    public void downloadCodegen(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        // 生成代码
        Map<String, String> codes = generatorService.generationCodes(id);
        // 构建 zip 包
        String[] paths = codes.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = codes.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // 输出
        ServletUtils.writeAttachment(response, "generator.zip", outputStream.toByteArray());
    }


}
