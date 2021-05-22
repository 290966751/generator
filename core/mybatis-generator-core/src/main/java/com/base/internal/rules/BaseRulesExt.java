package com.base.internal.rules;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 加载自定义方法判断
 */
public class BaseRulesExt {

    private final TableConfiguration tableConfiguration;

    private final IntrospectedTable introspectedTable;

    private final boolean isModelOnly;

    public BaseRulesExt(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
        this.tableConfiguration = introspectedTable.getTableConfiguration();
        String modelOnly = tableConfiguration.getProperty(PropertyRegistry.TABLE_MODEL_ONLY);
        this.isModelOnly = StringUtility.isTrue(modelOnly);
    }

    public boolean generateDeleteByPrimaryKey() {
        if (isModelOnly) {
            return false;
        }
        return tableConfiguration.getTableConfigurationMethodEnabled().isEnableDeleteByPrimaryKeys()
                && introspectedTable.getPrimaryKeyColumns().size() == 1;
    }

    public boolean generateUpdateByMap() {
        if (isModelOnly) {
            return false;
        }
        return tableConfiguration.getTableConfigurationMethodEnabled().isEnableUpdateByMap()
                && introspectedTable.getPrimaryKeyColumns().size() == 1
                && (introspectedTable.hasBaseColumns() || introspectedTable
                .hasBLOBColumns());
    }

}
