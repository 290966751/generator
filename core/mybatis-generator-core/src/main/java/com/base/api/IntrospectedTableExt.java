package com.base.api;

import java.util.EnumMap;
import java.util.Map;

/**
 * table扩展实现，自定义方法配置
 */
public class IntrospectedTableExt {

    protected enum InternalAttributeExt {

        ATTR_DELETE_BY_PRIMARY_KEYS_STATEMENT_ID,
        ATTR_UPDATE_BY_MAP_STATEMENT_ID,

        ;
    }

    public void calculateXmlAttributes() {
        this.setDeleteByPrimaryKeysStatementId("deleteByPrimaryKeys"); //$NON-NLS-1$
        this.setUpdateByMapStatementId("updateByMap"); //$NON-NLS-1$
    }


    /** Internal attributes are used to store commonly accessed items by all code generators. */
    protected final Map<IntrospectedTableExt.InternalAttributeExt, String> internalAttributes =
            new EnumMap<>(IntrospectedTableExt.InternalAttributeExt.class);

    public void setDeleteByPrimaryKeysStatementId(String s) {
        internalAttributes.put(IntrospectedTableExt.InternalAttributeExt.ATTR_DELETE_BY_PRIMARY_KEYS_STATEMENT_ID, s);
    }

    public String getDeleteByPrimaryKeysStatementId() {
        return internalAttributes.get(IntrospectedTableExt.InternalAttributeExt.ATTR_DELETE_BY_PRIMARY_KEYS_STATEMENT_ID);
    }

    public void setUpdateByMapStatementId(String s) {
        internalAttributes.put(IntrospectedTableExt.InternalAttributeExt.ATTR_UPDATE_BY_MAP_STATEMENT_ID, s);
    }

    public String getUpdateByMapStatementId() {
        return internalAttributes.get(IntrospectedTableExt.InternalAttributeExt.ATTR_UPDATE_BY_MAP_STATEMENT_ID);
    }

}
