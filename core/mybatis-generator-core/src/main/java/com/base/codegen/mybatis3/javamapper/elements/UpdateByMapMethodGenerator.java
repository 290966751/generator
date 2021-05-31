package com.base.codegen.mybatis3.javamapper.elements;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.Set;
import java.util.TreeSet;

/**
 * updateByMap 接口实现
 */
public class UpdateByMapMethodGenerator extends AbstractJavaMapperMethodGenerator {

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        Method method = new Method(introspectedTable.getIntrospectedTableExt().getDeleteByMapStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());

        // no primary key class - fields are in the base class
        // if more than one PK field, then we need to annotate the
        // parameters
        // for MyBatis
        FullyQualifiedJavaType type = new FullyQualifiedJavaType("java.util.Map");
        type.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
        type.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
        importedTypes.add(type);
        Parameter parameter = new Parameter(type, "params");
        method.addParameter(parameter);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

//        addMapperAnnotations(method);

        if (context.getPlugins().clientDeleteByPrimaryKeyMethodGenerated(
                method, interfaze, introspectedTable)) {
//            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

}
