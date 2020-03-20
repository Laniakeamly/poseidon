package org.raniaia.poseidon.framework.sql.xml.parser;

import org.jdom2.Element;
import org.raniaia.poseidon.framework.exception.runtime.ExpressionException;
import org.raniaia.poseidon.framework.provide.PoseidonProvideConstant;
import org.raniaia.poseidon.framework.tools.StringUtils;

/**
 * 解析工具类
 * Copyright: Create by tiansheng on 2019/12/17 0:26
 */
public class XMLParserUtils {

    /**
     * 获取test标签中的内容
     * @param element
     * @return
     */
    public String getIfLabelTestAttribute(Element element){
        String test = element.getAttributeValue(PoseidonProvideConstant.IF_TEST);
        if(StringUtils.isEmpty(test))
            throw new ExpressionException("tag: if label attribute test content cannot null");
        return test;
    }

    /**
     * 清除xml中不合法的换行
     * @param str
     * @return
     */
    public String trim(String str) {
        return StringUtils.trim(str);
    }

}
