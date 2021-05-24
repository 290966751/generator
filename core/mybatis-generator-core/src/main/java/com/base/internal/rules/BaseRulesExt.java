package com.base.internal.rules;

import com.base.config.CustomPropertyRegistry;
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

    public boolean generateDeleteByMap() {
        if (isModelOnly) {
            return false;
        }
        return tableConfiguration.getTableConfigurationMethodEnabled().isEnableDeleteByMap()
                && introspectedTable.getPrimaryKeyColumns().size() == 1
                && (introspectedTable.hasBLOBColumns() || introspectedTable
                .hasBaseColumns());
    }

    /**
     * 是否初始化实体Builder类
     * @return
     */
    public boolean generateBaseRecordBuilderClass() {
        if (introspectedTable.getContext().getSqlMapGeneratorConfiguration() == null
                && introspectedTable.getContext().getJavaClientGeneratorConfiguration() == null) {
            // this is a model only context - don't generate the builder class
            return false;
        }

        if (isModelOnly) {
            return false;
        }
        if (!introspectedTable.getRules().generateBaseRecordClass() || introspectedTable.isImmutable()) {
            return false;
        }
        String property = introspectedTable.getContext().getJavaModelGeneratorConfiguration().getProperty(CustomPropertyRegistry.MODEL_GENERATOR_EXAMPLE_PROJECT);
        return StringUtility.stringHasValue(property) && StringUtility.isTrue(property);
    }

}
