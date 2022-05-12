package xyz.migoo.template.service.manager.developer.generator.inner;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import xyz.migoo.framework.mybatis.core.dataobject.BaseDO;
import xyz.migoo.template.convert.manager.developer.GeneratorConvert;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenColumn;
import xyz.migoo.template.dal.dataobject.developer.gennerator.CodegenTable;

import java.math.BigDecimal;
import java.util.*;

import static cn.hutool.core.text.CharSequenceUtil.*;

public class GeneratorBuilder {

    /**
     * {@link BaseDO} 的字段
     */
    public static final Set<String> BASE_DO_FIELDS = new HashSet<>();
    /**
     * Module 名字的映射
     * <p>
     * key：模块的完整名
     * value：模块的缩写名
     */
    private static final Map<String, String> MODULE_NAMES = MapUtil.<String, String>builder()
            .put("system", "sys")
            .put("infra", "inf")
            .put("tool", "tool")
            .build();
    /**
     * 字段名与 {@link CodegenColumnListConditionEnum} 的默认映射
     * 注意，字段的匹配以后缀的方式
     */
    private static final Map<String, CodegenColumnListConditionEnum> COLUMN_LIST_OPERATION_CONDITION_MAPPINGS =
            MapUtil.<String, CodegenColumnListConditionEnum>builder()
                    .put("name", CodegenColumnListConditionEnum.LIKE)
                    .put("time", CodegenColumnListConditionEnum.BETWEEN)
                    .put("date", CodegenColumnListConditionEnum.BETWEEN)
                    .build();
    /**
     * 字段名与 {@link CodegenColumnHtmlTypeEnum} 的默认映射
     * 注意，字段的匹配以后缀的方式
     */
    private static final Map<String, CodegenColumnHtmlTypeEnum> COLUMN_HTML_TYPE_MAPPINGS =
            MapUtil.<String, CodegenColumnHtmlTypeEnum>builder()
                    .put("status", CodegenColumnHtmlTypeEnum.RADIO)
                    .put("sex", CodegenColumnHtmlTypeEnum.RADIO)
                    .put("type", CodegenColumnHtmlTypeEnum.SELECT)
                    .put("image", CodegenColumnHtmlTypeEnum.UPLOAD_IMAGE)
                    .put("file", CodegenColumnHtmlTypeEnum.UPLOAD_FILE)
                    .put("content", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("time", CodegenColumnHtmlTypeEnum.DATETIME)
                    .put("date", CodegenColumnHtmlTypeEnum.DATETIME)
                    .build();
    /**
     * 新增操作，不需要传递的字段
     */
    private static final Set<String> CREATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * 修改操作，不需要传递的字段
     */
    private static final Set<String> UPDATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet();
    /**
     * 列表操作的条件，不需要传递的字段
     */
    private static final Set<String> LIST_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * 列表操作的结果，不需要返回的字段
     */
    private static final Set<String> LIST_OPERATION_RESULT_EXCLUDE_COLUMN = Sets.newHashSet();
    /**
     * Java 类型与 MySQL 类型的映射关系
     */
    private static final Map<String, Set<String>> JAVA_TYPE_MAPPINGS = MapUtil.<String, Set<String>>builder()
            .put(Boolean.class.getSimpleName(), Sets.newHashSet("bit"))
            .put(Integer.class.getSimpleName(), Sets.newHashSet("tinyint", "smallint", "mediumint", "int"))
            .put(Long.class.getSimpleName(), Collections.singleton("bigint"))
            .put(Double.class.getSimpleName(), Sets.newHashSet("float", "double"))
            .put(BigDecimal.class.getSimpleName(), Sets.newHashSet("decimal", "numeric"))
            // 长文本\短文本
            .put(String.class.getSimpleName(), Sets.newHashSet("tinytext", "text", "mediumtext", "longtext",
                    "char", "varchar", "nvarchar", "varchar2"))
            .put(Date.class.getSimpleName(), Sets.newHashSet("datetime", "time", "date", "timestamp"))
            .put("byte[]", Sets.newHashSet("blob"))
            .build();
    private static final GeneratorBuilder builder = new GeneratorBuilder();

    static {
        Arrays.stream(BaseDO.class.getDeclaredFields()).forEach(field -> BASE_DO_FIELDS.add(field.getName()));
        // 处理 OPERATION 相关的字段
        CREATE_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        UPDATE_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_EXCLUDE_COLUMN.remove("createTime");
        LIST_OPERATION_RESULT_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_RESULT_EXCLUDE_COLUMN.remove("createTime");
    }

    private GeneratorBuilder() {

    }

    public static GeneratorBuilder get() {
        return builder;
    }

    public CodegenTable buildTable(DatabaseTable schemaTable) {
        CodegenTable table = GeneratorConvert.INSTANCE.convert(schemaTable);
        initTableDefault(table);
        return table;
    }

    /**
     * 初始化 Table 表的默认字段
     *
     * @param table 表定义
     */
    private void initTableDefault(CodegenTable table) {
        // 驼峰 + 首字母大写
        table.setClassName(upperFirst(toCamelCase(table.getTableName())));
        // 去除结尾的表，作为类描述
        table.setClassComment(subBefore(table.getTableComment(),
                '表', true));
        table.setAuthor("");
    }

    public List<CodegenColumn> buildColumns(List<DatabaseColumn> schemaColumns) {
        List<CodegenColumn> columns = GeneratorConvert.INSTANCE.convert(schemaColumns);
        columns.forEach(this::initColumnDefault);
        return columns;
    }

    /**
     * 初始化 Column 列的默认字段
     *
     * @param column 列定义
     */
    private void initColumnDefault(CodegenColumn column) {
        // 处理 Java 相关的字段的默认值
        processColumnJava(column);
        // 处理 CRUD 相关的字段的默认值
        processColumnOperation(column);
        // 处理 UI 相关的字段的默认值
        processColumnUi(column);
    }

    private void processColumnJava(CodegenColumn column) {
        // 处理 javaField 字段
        column.setJavaField(toCamelCase(column.getColumnName()));
        // 处理 dictType 字段，暂无
        // 处理 javaType 字段
        String dbType = subBefore(column.getDataType(), '(', false);
        JAVA_TYPE_MAPPINGS.entrySet().stream()
                .filter(entry -> entry.getValue().contains(dbType))
                .findFirst().ifPresent(entry -> column.setJavaType(entry.getKey()));
        if (column.getJavaType() == null) {
            throw new IllegalStateException(String.format("column(%s) 的数据库类型(%s) 找不到匹配的 Java 类型",
                    column.getColumnName(), column.getDataType()));
        }
    }

    private void processColumnOperation(CodegenColumn column) {
        // 处理 createOperation 字段
        column.setCreateOperation(!CREATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // 对于主键，创建时无需传递
        // 处理 updateOperation 字段
        column.setUpdateOperation(!UPDATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                || column.getPrimaryKey()); // 对于主键，更新时需要传递
        // 处理 listOperation 字段
        column.setListOperation(!LIST_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // 对于主键，列表过滤不需要传递
        // 处理 listOperationCondition 字段
        COLUMN_LIST_OPERATION_CONDITION_MAPPINGS.entrySet().stream()
                .filter(entry -> StrUtil.endWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setListOperationCondition(entry.getValue().getCondition()));
        if (column.getListOperationCondition() == null) {
            column.setListOperationCondition(CodegenColumnListConditionEnum.EQ.getCondition());
        }
        // 处理 listOperationResult 字段
        column.setListOperationResult(!LIST_OPERATION_RESULT_EXCLUDE_COLUMN.contains(column.getJavaField()));
    }

    private void processColumnUi(CodegenColumn column) {
        // 基于后缀进行匹配
        COLUMN_HTML_TYPE_MAPPINGS.entrySet().stream()
                .filter(entry -> StrUtil.endWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setHtmlType(entry.getValue().getType()));
        // 如果是 Boolean 类型时，设置为 radio 类型.
        // 其它类型，因为字段名可以相对保障，所以不进行处理。例如说 date 对应 datetime 类型.
        if (Boolean.class.getSimpleName().equals(column.getJavaType())) {
            column.setHtmlType(CodegenColumnHtmlTypeEnum.RADIO.getType());
        }
        // 兜底，设置默认为 input 类型
        if (column.getHtmlType() == null) {
            column.setHtmlType(CodegenColumnHtmlTypeEnum.INPUT.getType());
        }
    }

    /**
     * 获得模块的缩略名
     *
     * @param fullModuleName 模块的完整名
     * @return 缩略名
     */
    public String getSimpleModuleName(String fullModuleName) {
        return MODULE_NAMES.getOrDefault(fullModuleName, fullModuleName);
    }

    /**
     * 获得模块的完整名
     *
     * @param shortModuleName 模块的缩略名
     * @return 完整名
     */
    public String getFullModuleName(String shortModuleName) {
        return MODULE_NAMES.entrySet().stream()
                // 匹配
                .filter(entry -> entry.getValue().equals(shortModuleName))
                // 返回key
                .findFirst().map(Map.Entry::getKey)
                // 兜底返回 shortModuleName
                .orElse(shortModuleName);
    }

}
