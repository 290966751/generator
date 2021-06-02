package com.base.codegen.mybatis3.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.internal.PluginAggregator;
import org.mybatis.generator.internal.rules.BaseRules;
import org.mybatis.generator.plugins.SerializablePlugin;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.JavaBeansUtil.*;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 扩展实体生成类
 */
public class BaseRecordGeneratorExt extends BaseRecordGenerator {

    public BaseRecordGeneratorExt(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.8", table.toString())); //$NON-NLS-1$
        Plugin plugins = context.getPlugins();
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        FullyQualifiedJavaType superClass = getSuperClass();
        if (superClass != null) {
            topLevelClass.setSuperClass(superClass);
            topLevelClass.addImportedType(superClass);
        }
        commentGenerator.addModelClassComment(topLevelClass, introspectedTable);

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();

        boolean hasBaseRecordBuilderClass = introspectedTable.getRules() instanceof BaseRules
                && ((BaseRules) introspectedTable.getRules()).getBaseRulesExt().generateBaseRecordBuilderClass();

        if (introspectedTable.isConstructorBased() || hasBaseRecordBuilderClass) {
            addParameterizedConstructor(topLevelClass, introspectedTable.getNonBLOBColumns());

            if (includeBLOBColumns() || hasBaseRecordBuilderClass) {
                addParameterizedConstructor(topLevelClass, introspectedTable.getAllColumns());
            }

            if (!introspectedTable.isImmutable()) {
                addDefaultConstructor(topLevelClass);
            }
        }

        String rootClass = getRootClass();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (RootClassInfo.getInstance(rootClass, warnings)
                    .containsProperty(introspectedColumn)) {
                continue;
            }

            Field field = getJavaBeansField(introspectedColumn, context, introspectedTable);
            if (plugins.modelFieldGenerated(field, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.BASE_RECORD)) {
                topLevelClass.addField(field);
                topLevelClass.addImportedType(field.getType());
            }

            Method method = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
            if (plugins.modelGetterMethodGenerated(method, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.BASE_RECORD)) {
                topLevelClass.addMethod(method);
            }

            if (!introspectedTable.isImmutable()) {
                method = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
                if (plugins.modelSetterMethodGenerated(method, topLevelClass,
                        introspectedColumn, introspectedTable,
                        Plugin.ModelClassType.BASE_RECORD)) {
                    topLevelClass.addMethod(method);
                }
            }
        }

        List<CompilationUnit> answer = new ArrayList<>();
        if (context.getPlugins().modelBaseRecordClassGenerated(
                topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
        }

        /**
         * 增加建造者（Builder）模式
         */
        this.addStaticBuilder(topLevelClass);

        return answer;
    }



    /**
     * 增加建造者（Builder）模式
     * @param topLevelClass
     * @return
     */
    private void addStaticBuilder(TopLevelClass topLevelClass) {
        if (!isModelBuilderEnabled(introspectedTable)) {
            return;
        }
        topLevelClass.addInnerClass(this.getStaticBuilder(topLevelClass));
        /**
         * 增加主类建造者（Builder）模式构造方法
         */
        this.addBuilderConstructor(topLevelClass);
    }

    /**
     * 增加建造者（Builder）模式
     * @param topLevelClass
     * @return
     */
    private InnerClass getStaticBuilder(TopLevelClass topLevelClass) {
        FullyQualifiedJavaType innerClassJavaType = this.getModelBuilderInstance();
        TopLevelClass builderClass = new TopLevelClass(innerClassJavaType);
        builderClass.setVisibility(JavaVisibility.PUBLIC);
        builderClass.setStatic(true);
        builderClass.addJavaDocLine("/**");
        builderClass.addJavaDocLine(" * 建造者（Builder）模式.");
        builderClass.addJavaDocLine(" */");
        Plugin plugins = context.getPlugins();

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();

//        addDefaultConstructor(builderClass);

        String rootClass = getRootClass();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (RootClassInfo.getInstance(rootClass, warnings).containsProperty(introspectedColumn)) {
                continue;
            }

            Field field = getJavaBeansFieldBuilder(introspectedColumn);
            if (plugins.modelFieldGenerated(field, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.BASE_RECORD)) {
                builderClass.addField(field);
                topLevelClass.addImportedType(field.getType());
            }

            Method method = getJavaBeansSetterBuilder(introspectedColumn, context, introspectedTable, innerClassJavaType);
            if (plugins.modelSetterMethodGenerated(method, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.BASE_RECORD)) {
                builderClass.addMethod(method);
            }
        }

        FullyQualifiedJavaType topLevelClassJavaType = topLevelClass.getType();
        Method method = new Method("build");
        StringBuilder sb = new StringBuilder();
        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setName("build");
        method.setReturnType(topLevelClassJavaType);
        sb.append("return new "); //$NON-NLS-1$
        sb.append(topLevelClassJavaType.getShortNameWithoutTypeArguments());
        sb.append("(this);"); //$NON-NLS-1$
//        List<Field> fields = builderClass.getFields();
//        if (!fields.isEmpty()) {
//            for (Field field : fields) {
//                sb.append("this.").append(field.getName()).append(",").append(" ");
//            }
//            sb.delete(sb.length() - 2, sb.length());
//        }
//        sb.append(");"); //$NON-NLS-1$
        method.addBodyLine(sb.toString());
        builderClass.addMethod(method);

//        context.getPlugins().modelBaseRecordClassGenerated(builderClass, introspectedTable);


        if (plugins instanceof PluginAggregator) {
            List<Plugin> pluginsList = ((PluginAggregator) plugins).getPlugins();
            for (Plugin plugin : pluginsList) {
                if (plugin instanceof SerializablePlugin) {
                    continue;
                }
                if (!plugin.modelBaseRecordClassGenerated(builderClass, introspectedTable)) {
                    break;
                }
            }
        }

        return builderClass;
    }

    /**
     * 建造者（Builder）模式为主类添加无参构造方法、有参构造方法、builder初始化方法
     * @param topLevelClass
     */
    private void addBuilderConstructor(TopLevelClass topLevelClass) {
        List<Method> customMethods = new ArrayList<>(3);
        /**
         * 建造者（Builder）模式默认为主类增加无参构造
         */
        int index = 1;
        if (!this.hasAddDefaultConstructor(topLevelClass)) {
            customMethods.add(this.getDefaultConstructor(topLevelClass));
            index = 0;
        }

        /**
         * 为主类增加Builder有参构造方法
         */
        FullyQualifiedJavaType modelBuilderJavaType = this.getModelBuilderInstance();
        String innerClassShortName = "builder";
        Method method = new Method(topLevelClass.getType().getShortName());
        StringBuilder sb = new StringBuilder();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
//        method.setName(topLevelClass.getType().getShortName());
        method.addParameter(new Parameter(modelBuilderJavaType, innerClassShortName));
        method.addBodyLine("super();"); //$NON-NLS-1$
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        if (!allColumns.isEmpty()) {
            for (IntrospectedColumn column : allColumns) {
                sb.setLength(0);
                sb.append("this.").append(column.getJavaProperty()).append(" = ").append(innerClassShortName).append(".").append(column.getJavaProperty()).append(";");
                method.addBodyLine(sb.toString()); //$NON-NLS-1$
            }
        }
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
//        topLevelClass.addMethod(index++, method);
        customMethods.add(method);

        /**
         * 添加建造者初始化
         */
        method = new Method("builder");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setStatic(true);
//        method.setName("builder");
        method.setReturnType(modelBuilderJavaType);
        sb.setLength(0);
        sb.append("return new ");
        sb.append(modelBuilderJavaType.getShortNameWithoutTypeArguments());
        sb.append("();");
        method.addBodyLine(sb.toString()); //$NON-NLS-1$
//        topLevelClass.addMethod(index++, method);
        customMethods.add(method);
        /**添加在方法最上面**/
        topLevelClass.addMethod(index, customMethods);
    }

    /**
     * 获取构造者模式class
     * @return
     */
    public FullyQualifiedJavaType getModelBuilderInstance() {
        return new FullyQualifiedJavaType(new StringBuilder(introspectedTable.getBaseRecordType()).append("Builder").toString()); //$NON-NLS-1$
    }

}
