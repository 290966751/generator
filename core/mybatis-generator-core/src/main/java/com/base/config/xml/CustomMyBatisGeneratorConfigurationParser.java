//package com.base.config.xml;
//
//import org.mybatis.generator.config.Context;
//import org.mybatis.generator.config.TableConfiguration;
//import org.mybatis.generator.config.xml.MyBatisGeneratorConfigurationParser;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import java.util.Properties;
//
//import static org.mybatis.generator.internal.util.StringUtility.isTrue;
//import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
//
//public class CustomMyBatisGeneratorConfigurationParser extends MyBatisGeneratorConfigurationParser {
//
//    public CustomMyBatisGeneratorConfigurationParser(Properties extraProperties) {
//        super(extraProperties);
//    }
//
//    @Override
//    protected void parseTable(Context context, Node node) {
//        TableConfiguration tc = new TableConfiguration(context);
//        context.addTableConfiguration(tc);
//
//        Properties attributes = parseAttributes(node);
//
//        String catalog = attributes.getProperty("catalog"); //$NON-NLS-1$
//        if (stringHasValue(catalog)) {
//            tc.setCatalog(catalog);
//        }
//
//        String schema = attributes.getProperty("schema"); //$NON-NLS-1$
//        if (stringHasValue(schema)) {
//            tc.setSchema(schema);
//        }
//
//        String tableName = attributes.getProperty("tableName"); //$NON-NLS-1$
//        if (stringHasValue(tableName)) {
//            tc.setTableName(tableName);
//        }
//
//        String domainObjectName = attributes.getProperty("domainObjectName"); //$NON-NLS-1$
//        if (stringHasValue(domainObjectName)) {
//            tc.setDomainObjectName(domainObjectName);
//        }
//
//        String alias = attributes.getProperty("alias"); //$NON-NLS-1$
//        if (stringHasValue(alias)) {
//            tc.setAlias(alias);
//        }
//
//        String enableInsert = attributes.getProperty("enableInsert"); //$NON-NLS-1$
//        if (stringHasValue(enableInsert)) {
//            tc.setInsertStatementEnabled(isTrue(enableInsert));
//        }
//
//        String enableSelectByPrimaryKey = attributes
//                .getProperty("enableSelectByPrimaryKey"); //$NON-NLS-1$
//        if (stringHasValue(enableSelectByPrimaryKey)) {
//            tc.setSelectByPrimaryKeyStatementEnabled(
//                    isTrue(enableSelectByPrimaryKey));
//        }
//
//        String enableSelectByExample = attributes
//                .getProperty("enableSelectByExample"); //$NON-NLS-1$
//        if (stringHasValue(enableSelectByExample)) {
//            tc.setSelectByExampleStatementEnabled(
//                    isTrue(enableSelectByExample));
//        }
//
//        String enableUpdateByPrimaryKey = attributes
//                .getProperty("enableUpdateByPrimaryKey"); //$NON-NLS-1$
//        if (stringHasValue(enableUpdateByPrimaryKey)) {
//            tc.setUpdateByPrimaryKeyStatementEnabled(
//                    isTrue(enableUpdateByPrimaryKey));
//        }
//
//        String enableDeleteByPrimaryKey = attributes
//                .getProperty("enableDeleteByPrimaryKey"); //$NON-NLS-1$
//        if (stringHasValue(enableDeleteByPrimaryKey)) {
//            tc.setDeleteByPrimaryKeyStatementEnabled(
//                    isTrue(enableDeleteByPrimaryKey));
//        }
//
//        String enableDeleteByExample = attributes
//                .getProperty("enableDeleteByExample"); //$NON-NLS-1$
//        if (stringHasValue(enableDeleteByExample)) {
//            tc.setDeleteByExampleStatementEnabled(
//                    isTrue(enableDeleteByExample));
//        }
//
//        String enableCountByExample = attributes
//                .getProperty("enableCountByExample"); //$NON-NLS-1$
//        if (stringHasValue(enableCountByExample)) {
//            tc.setCountByExampleStatementEnabled(
//                    isTrue(enableCountByExample));
//        }
//
//        String enableUpdateByExample = attributes
//                .getProperty("enableUpdateByExample"); //$NON-NLS-1$
//        if (stringHasValue(enableUpdateByExample)) {
//            tc.setUpdateByExampleStatementEnabled(
//                    isTrue(enableUpdateByExample));
//        }
//
//        String selectByPrimaryKeyQueryId = attributes
//                .getProperty("selectByPrimaryKeyQueryId"); //$NON-NLS-1$
//        if (stringHasValue(selectByPrimaryKeyQueryId)) {
//            tc.setSelectByPrimaryKeyQueryId(selectByPrimaryKeyQueryId);
//        }
//
//        String selectByExampleQueryId = attributes
//                .getProperty("selectByExampleQueryId"); //$NON-NLS-1$
//        if (stringHasValue(selectByExampleQueryId)) {
//            tc.setSelectByExampleQueryId(selectByExampleQueryId);
//        }
//
//        String modelType = attributes.getProperty("modelType"); //$NON-NLS-1$
//        if (stringHasValue(modelType)) {
//            tc.setConfiguredModelType(modelType);
//        }
//
//        String escapeWildcards = attributes.getProperty("escapeWildcards"); //$NON-NLS-1$
//        if (stringHasValue(escapeWildcards)) {
//            tc.setWildcardEscapingEnabled(isTrue(escapeWildcards));
//        }
//
//        String delimitIdentifiers = attributes
//                .getProperty("delimitIdentifiers"); //$NON-NLS-1$
//        if (stringHasValue(delimitIdentifiers)) {
//            tc.setDelimitIdentifiers(isTrue(delimitIdentifiers));
//        }
//
//        String delimitAllColumns = attributes.getProperty("delimitAllColumns"); //$NON-NLS-1$
//        if (stringHasValue(delimitAllColumns)) {
//            tc.setAllColumnDelimitingEnabled(isTrue(delimitAllColumns));
//        }
//
//        String mapperName = attributes.getProperty("mapperName"); //$NON-NLS-1$
//        if (stringHasValue(mapperName)) {
//            tc.setMapperName(mapperName);
//        }
//
//        String sqlProviderName = attributes.getProperty("sqlProviderName"); //$NON-NLS-1$
//        if (stringHasValue(sqlProviderName)) {
//            tc.setSqlProviderName(sqlProviderName);
//        }
//
//        /**
//         * 判断自定义的方法是否实现
//         */
////        Properties attributes = parseAttributes(node);
//        String enableDeleteByPrimaryKeys = attributes.getProperty("enableDeleteByPrimaryKeys");
//        if (stringHasValue(enableDeleteByPrimaryKeys)) {
//            tc.setUpdateByPrimaryKeyStatementEnabled(
//                    isTrue(enableUpdateByPrimaryKey));
//        }
//
//
//        NodeList nodeList = node.getChildNodes();
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node childNode = nodeList.item(i);
//
//            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
//                continue;
//            }
//
//            if ("property".equals(childNode.getNodeName())) { //$NON-NLS-1$
//                parseProperty(tc, childNode);
//            } else if ("columnOverride".equals(childNode.getNodeName())) { //$NON-NLS-1$
//                parseColumnOverride(tc, childNode);
//            } else if ("ignoreColumn".equals(childNode.getNodeName())) { //$NON-NLS-1$
//                parseIgnoreColumn(tc, childNode);
//            } else if ("ignoreColumnsByRegex".equals(childNode.getNodeName())) { //$NON-NLS-1$
//                parseIgnoreColumnByRegex(tc, childNode);
//            } else if ("generatedKey".equals(childNode.getNodeName())) { //$NON-NLS-1$
//                parseGeneratedKey(tc, childNode);
//            } else if ("domainObjectRenamingRule".equals(childNode.getNodeName())) { //$NON-NLS-1$
//                parseDomainObjectRenamingRule(tc, childNode);
//            } else if ("columnRenamingRule".equals(childNode.getNodeName())) { //$NON-NLS-1$
//                parseColumnRenamingRule(tc, childNode);
//            }
//        }
//    }
//}
