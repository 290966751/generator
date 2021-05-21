package com.base.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class DeleteByPrimaryKeysElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getDeleteByPrimaryKeysStatementId())); //$NON-NLS-1$
        String parameterClass = "java.util.List"; //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterClass));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append(" AND "); //$NON-NLS-1$
            } else {
                sb.append("WHERE "); //$NON-NLS-1$
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" IN "); //$NON-NLS-1$
            XmlElement innerForEach = new XmlElement("foreach"); //$NON-NLS-1$
            String javaProperty = introspectedColumn.getJavaProperty(null);
            innerForEach.addAttribute(new Attribute("collection", "list")); //$NON-NLS-1$ //$NON-NLS-2$
            innerForEach.addAttribute(new Attribute("item", javaProperty)); //$NON-NLS-1$ //$NON-NLS-2$
            innerForEach.addAttribute(new Attribute("open", "(")); //$NON-NLS-1$ //$NON-NLS-2$
            innerForEach.addAttribute(new Attribute("close", ")")); //$NON-NLS-1$ //$NON-NLS-2$
            innerForEach.addAttribute(new Attribute("separator", ",")); //$NON-NLS-1$ //$NON-NLS-2$
            innerForEach.addElement(new TextElement(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn)));
            answer.addElement(new TextElement(sb.toString()));
            answer.addElement(innerForEach);
        }

        if (context.getPlugins()
                .sqlMapDeleteByPrimaryKeyElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
