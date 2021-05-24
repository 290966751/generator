package com.base.codegen.mybatis3.model;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 实现实体的Builder类
 */
public class BaseRecordBuilderGenerator extends AbstractJavaGenerator {

    public BaseRecordBuilderGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.8", table.toString())); //$NON-NLS-1$


        return null;
    }
}
