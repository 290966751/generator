package com.base.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * DeleteByMap xml实现
 */
public class DeleteByMapElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                "id", introspectedTable.getIntrospectedTableExt().getDeleteByMapStatementId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", "java.util.Map")); //$NON-NLS-1$
        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        for (IntrospectedColumn introspectedColumn :
                ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns())) {
            sb.setLength(0);
            sb.append("_parameter.containsKey('");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append("')"); //$NON-NLS-1$
            XmlElement isMapContasinsKeyElement = new XmlElement("if"); //$NON-NLS-1$
            isMapContasinsKeyElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            sb.append(',');

            isMapContasinsKeyElement.addElement(new TextElement(sb.toString()));
            dynamicElement.addElement(isMapContasinsKeyElement);
        }
        IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
        sb.setLength(0);
        sb.append(primaryKeyColumn.getJavaProperty());
        if (primaryKeyColumn.getJavaProperty().endsWith("s")) {
            sb.append("List");
        } else {
            sb.append("s");
        }
        String primaryKeysName = sb.toString();

        sb.setLength(0);
        sb.append("WHERE "); //$NON-NLS-1$
        sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
        answer.addElement(new TextElement(sb.toString()));

        XmlElement chooseElement = new XmlElement("choose"); //$NON-NLS-1$
        XmlElement whenElement = new XmlElement("when");
        sb.setLength(0);
        sb.append("_parameter.containsKey('").append(primaryKeysName).append("')"); //$NON-NLS-1$
        whenElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

        sb.setLength(0);
        sb.append(" IN ");
        XmlElement innerForEach = new XmlElement("foreach"); //$NON-NLS-1$
        innerForEach.addAttribute(new Attribute("collection", primaryKeysName)); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addAttribute(new Attribute("item", primaryKeyColumn.getJavaProperty())); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addAttribute(new Attribute("open", "(")); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addAttribute(new Attribute("separator", ",")); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addAttribute(new Attribute("close", ")")); //$NON-NLS-1$ //$NON-NLS-2$
        innerForEach.addElement(new TextElement(MyBatis3FormattingUtilities.getParameterClause(primaryKeyColumn)));
        whenElement.addElement(new TextElement(sb.toString()));
        whenElement.addElement(innerForEach);
        chooseElement.addElement(whenElement);

        XmlElement otherwiseElement = new XmlElement("otherwise");
        sb.setLength(0);
        sb.append(" = ");
        sb.append(MyBatis3FormattingUtilities.getParameterClause(primaryKeyColumn));
        otherwiseElement.addElement(new TextElement(sb.toString()));
        chooseElement.addElement(otherwiseElement);
        answer.addElement(chooseElement);

        if (context.getPlugins()
                .sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
