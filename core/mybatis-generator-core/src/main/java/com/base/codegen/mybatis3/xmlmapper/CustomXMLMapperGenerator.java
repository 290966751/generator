package com.base.codegen.mybatis3.xmlmapper;

import com.base.codegen.mybatis3.xmlmapper.elements.DeleteByMapElementGenerator;
import com.base.codegen.mybatis3.xmlmapper.elements.DeleteByPrimaryKeysElementGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.DeleteByPrimaryKeyElementGenerator;
import org.mybatis.generator.internal.rules.BaseRules;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 自定义xml生成
 */
public class CustomXMLMapperGenerator extends XMLMapperGenerator {

    public CustomXMLMapperGenerator() {
        super();
    }

    @Override
    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.12", table.toString())); //$NON-NLS-1$
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                namespace));

        context.getCommentGenerator().addRootComment(answer);

        addResultMapWithoutBLOBsElement(answer);
        addResultMapWithBLOBsElement(answer);
        addExampleWhereClauseElement(answer);
        addMyBatis3UpdateByExampleWhereClauseElement(answer);
        addBaseColumnListElement(answer);
        addBlobColumnListElement(answer);
        addSelectByExampleWithBLOBsElement(answer);
        addSelectByExampleWithoutBLOBsElement(answer);
        addSelectByPrimaryKeyElement(answer);
        addDeleteByPrimaryKeyElement(answer);
        addDeleteByExampleElement(answer);
        addInsertElement(answer);
        addInsertSelectiveElement(answer);
        addCountByExampleElement(answer);
        addUpdateByExampleSelectiveElement(answer);
        addUpdateByExampleWithBLOBsElement(answer);
        addUpdateByExampleWithoutBLOBsElement(answer);
        addUpdateByPrimaryKeySelectiveElement(answer);
        addUpdateByPrimaryKeyWithBLOBsElement(answer);
        addUpdateByPrimaryKeyWithoutBLOBsElement(answer);

        /**
         * 自定义方法
         */
        this.addDeleteByPrimaryKeysElement(answer);
        this.addDeleteByMapElement(answer);

        return answer;
    }

    protected void addDeleteByPrimaryKeysElement(XmlElement parentElement) {
        if (introspectedTable.getRules() instanceof BaseRules
                && ((BaseRules) introspectedTable.getRules()).getBaseRulesExt().generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByPrimaryKeysElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addDeleteByMapElement(XmlElement parentElement) {
        if (introspectedTable.getRules() instanceof BaseRules
                && ((BaseRules) introspectedTable.getRules()).getBaseRulesExt().generateDeleteByMap()) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByMapElementGenerator();
            initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }
}
