package com.base.config;

import com.base.codegen.mybatis3.model.BaseRecordBuilderGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.internal.rules.BaseRules;
import org.mybatis.generator.internal.rules.Rules;

import java.util.List;

/**
 * 自定义model实现
 */
public class CustomJavaModelGenerators {

    private final IntrospectedTable introspectedTable;

    public CustomJavaModelGenerators(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
    }

    public Rules getRules() {
        return introspectedTable.getRules();
    }

    protected String getModelProject() {
        return introspectedTable.getContext().getJavaModelGeneratorConfiguration().getTargetProject();
    }

    protected void initializeAbstractGenerator(
            AbstractGenerator abstractGenerator, List<String> warnings,
            ProgressCallback progressCallback) {
        if (abstractGenerator == null) {
            return;
        }
        abstractGenerator.setContext(introspectedTable.getContext());
        abstractGenerator.setIntrospectedTable(introspectedTable);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

    public void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback, List<AbstractJavaGenerator> javaGenerators) {
        if (this.getRules().generateBaseRecordClass()
                && !introspectedTable.isImmutable()
                && this.getRules() instanceof BaseRules
                && ((BaseRules) this.getRules()).getBaseRulesExt().generateBaseRecordBuilderClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordBuilderGenerator(this.getModelProject());
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaGenerators.add(javaGenerator);
        }

    }
}
