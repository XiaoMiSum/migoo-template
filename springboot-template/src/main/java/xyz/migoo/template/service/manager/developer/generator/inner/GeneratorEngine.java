package xyz.migoo.template.service.manager.developer.generator.inner;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.util.collection.CollectionUtils;
import xyz.migoo.framework.common.util.date.DateUtils;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;
import xyz.migoo.template.dal.dataobject.IdEnhanceDO;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenColumn;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenTable;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.map.MapUtil.getStr;
import static cn.hutool.core.text.CharSequenceUtil.*;

@Component
public class GeneratorEngine {

    /**
     * 模板配置
     * key：模板在 resources 的地址
     * value：生成的路径
     */
    private static final Map<String, String> TEMPLATES = MapUtil.<String, String>builder(new LinkedHashMap<>())
            // Java Main
            .put(javaTemplatePath("controller/vo/baseVO"),
                    javaFilePath("controller/${table.moduleName}/${table.businessName}/vo/${table.className}BaseVO"))
            .put(javaTemplatePath("controller/vo/createReqVO"),
                    javaFilePath("controller/${table.moduleName}/${table.businessName}/vo/${table.className}CreateReqVO"))
            .put(javaTemplatePath("controller/vo/pageReqVO"),
                    javaFilePath("controller/${table.moduleName}/${table.businessName}/vo/${table.className}PageReqVO"))
            .put(javaTemplatePath("controller/vo/respVO"),
                    javaFilePath("controller/${table.moduleName}/${table.businessName}/vo/${table.className}RespVO"))
            .put(javaTemplatePath("controller/vo/updateReqVO"),
                    javaFilePath("controller/${table.moduleName}/${table.businessName}/vo/${table.className}UpdateReqVO"))
            .put(javaTemplatePath("controller/vo/exportReqVO"),
                    javaFilePath("controller/${table.moduleName}/${table.businessName}/vo/${table.className}ExportReqVO"))
            .put(javaTemplatePath("controller/vo/excelVO"),
                    javaFilePath("controller/${table.moduleName}/${table.businessName}/vo/${table.className}ExcelVO"))
            .put(javaTemplatePath("controller/controller"),
                    javaFilePath("controller/${table.moduleName}/${table.businessName}/${table.className}Controller"))
            .put(javaTemplatePath("convert/convert"),
                    javaFilePath("convert/${table.moduleName}/${table.businessName}/${table.className}Convert"))
            .put(javaTemplatePath("dal/do"),
                    javaFilePath("dal/dataobject/${table.moduleName}/${table.businessName}/${table.className}DO"))
            .put(javaTemplatePath("dal/mapper"),
                    javaFilePath("dal/mysql/${table.moduleName}/${table.businessName}/${table.className}Mapper"))
            .put(javaTemplatePath("enums/errorcode"),
                    javaFilePath("enums/${simpleModuleName_upperFirst}ErrorCodeConstants"))
            .put(javaTemplatePath("service/serviceImpl"),
                    javaFilePath("service/${table.moduleName}/${table.businessName}/impl/${table.className}ServiceImpl"))
            .put(javaTemplatePath("service/service"),
                    javaFilePath("service/${table.moduleName}/${table.businessName}/${table.className}Service"))
            // Vue
            .put(vueTemplatePath("views/index.vue"),
                    vueFilePath("views/${table.moduleName}/${classNameVar}/index.vue"))
            .put(vueTemplatePath("api/api.js"),
                    vueFilePath("api/${table.moduleName}/${classNameVar}.js"))
            // SQL
            .put("generator/sql/sql.vm", "sql/sql.sql")
            .build();
    /**
     * 模板引擎，由 hutool 实现
     */
    private final TemplateEngine templateEngine;
    /**
     * 全局通用变量映射
     */
    private final Map<String, Object> globalBindingMap = new HashMap<>();

    @Value("${migoo.generator.base-package}")
    private String basePackage;

    public GeneratorEngine() {
        // 初始化 TemplateEngine 属性
        TemplateConfig config = new TemplateConfig();
        config.setResourceMode(TemplateConfig.ResourceMode.CLASSPATH);
        this.templateEngine = new VelocityEngine(config);
    }

    private static String javaTemplatePath(String path) {
        return "generator/java/" + path + ".vm";
    }

    private static String javaFilePath(String path) {
        return "java/${basePackage}/" + path + ".java";
    }

    private static String vueTemplatePath(String path) {
        return "generator/vue/" + path + ".vm";
    }

    private static String vueFilePath(String path) {
        return "vue/" + path;
    }

    @PostConstruct
    private void initGlobalBindingMap() {
        // 全局配置
        globalBindingMap.put("basePackage", basePackage);
        // 全局 Java Bean
        globalBindingMap.put("CommonResultClassName", Result.class.getName());
        globalBindingMap.put("PageResultClassName", PageResult.class.getName());
        // VO 类，独有字段
        globalBindingMap.put("PageParamClassName", PageParam.class.getName());
        // DO 类，独有字段
        globalBindingMap.put("baseDOFields", GeneratorBuilder.BASE_DO_FIELDS);
        globalBindingMap.put("BaseDOClassName", IdEnhanceDO.class.getName());
        globalBindingMap.put("QueryWrapperClassName", QueryWrapperX.class.getName());
        globalBindingMap.put("BaseMapperClassName", BaseMapperX.class.getName());
        // Util 工具类
        globalBindingMap.put("DateUtilsClassName", DateUtils.class.getName());

    }

    public Map<String, String> execute(CodegenTable table, List<CodegenColumn> columns) {
        // 创建 bindingMap
        Map<String, Object> bindingMap = new HashMap<>(globalBindingMap);
        bindingMap.put("table", table);
        bindingMap.put("columns", columns);
        // 主键字段
        bindingMap.put("primaryColumn", CollectionUtils.findFirst(columns, CodegenColumn::getPrimaryKey));
        // moduleName 相关
        String simpleModuleName = GeneratorBuilder.get().getSimpleModuleName(table.getTableName());
        bindingMap.put("simpleModuleName", simpleModuleName);
        bindingMap.put("simpleModuleName_upperFirst", upperFirst(simpleModuleName));
        // className 相关
        String simpleClassName = subAfter(table.getClassName(), upperFirst(simpleModuleName), false);
        bindingMap.put("simpleClassName", simpleClassName);
        bindingMap.put("simpleClassName_underlineCase", toUnderlineCase(simpleClassName));
        bindingMap.put("classNameVar", lowerFirst(simpleClassName));
        String simpleClassNameStrikeCase = toSymbolCase(simpleClassName, '-');
        bindingMap.put("simpleClassName_strikeCase", simpleClassNameStrikeCase);
        // permission 前缀
        bindingMap.put("permissionPrefix", table.getTableName() + ":" + simpleClassNameStrikeCase);
        // 执行生成
        final Map<String, String> result = Maps.newLinkedHashMapWithExpectedSize(TEMPLATES.size());
        TEMPLATES.forEach((vmPath, filePath) -> {
            filePath = formatFilePath(filePath, bindingMap);
            String content = templateEngine.getTemplate(vmPath).render(bindingMap);
            result.put(filePath, content);
        });
        return result;
    }

    private String formatFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StrUtil.replace(filePath, "${basePackage}",
                getStr(bindingMap, "basePackage").replaceAll("\\.", "/"));
        filePath = StrUtil.replace(filePath, "${simpleModuleName_upperFirst}",
                getStr(bindingMap, "simpleModuleName_upperFirst"));
        filePath = StrUtil.replace(filePath, "${classNameVar}",
                getStr(bindingMap, "classNameVar"));

        // table 包含的字段
        CodegenTable table = (CodegenTable) bindingMap.get("table");
        filePath = StrUtil.replace(filePath, "${table.moduleName}", table.getModuleName());
        filePath = StrUtil.replace(filePath, "${table.businessName}", table.getBusinessName());
        filePath = StrUtil.replace(filePath, "${table.className}", table.getClassName());
        return filePath;
    }

}
