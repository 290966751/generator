package com.base.config;

import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;
import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * 判断table自定义方法是否加载
 */
public class TableConfigurationParseTable implements Serializable {

    private static final long serialVersionUID = -47849488003887591L;

    public static class TableConfigurationMethodEnabled {

        public TableConfigurationMethodEnabled() {
            this.enableDeleteByPrimaryKeys = false;
        }

        private boolean enableDeleteByPrimaryKeys;

        public boolean isEnableDeleteByPrimaryKeys() {
            return enableDeleteByPrimaryKeys;
        }

        public void setEnableDeleteByPrimaryKeys(boolean enableDeleteByPrimaryKeys) {
            this.enableDeleteByPrimaryKeys = enableDeleteByPrimaryKeys;
        }
    }

    /**
     * 判断table自定义方法是否加载
     * @param context
     * @param node
     * @param tc
     * @param attributes
     */
    public void parseTable(Context context, Node node, TableConfiguration tc, Properties attributes) {

        String enableInsert = attributes.getProperty("enableDeleteByPrimaryKeys"); //$NON-NLS-1$
        if (stringHasValue(enableInsert)) {
            tc.getTableConfigurationMethodEnabled().setEnableDeleteByPrimaryKeys(isTrue(enableInsert));
        }
    }
}
