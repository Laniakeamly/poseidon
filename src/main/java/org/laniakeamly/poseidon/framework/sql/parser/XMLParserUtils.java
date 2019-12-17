package org.laniakeamly.poseidon.framework.sql.parser;

import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.exception.ExpressionException;
import org.laniakeamly.poseidon.framework.tools.NewlineBuilder;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * 解析工具类
 * Create by 2BKeyboard on 2019/12/17 0:26
 */
public class XMLParserUtils {

    /**
     * 获取test标签中的内容
     * @param element
     * @return
     */
    public String getIfLabelTestAttribute(Element element){
        String test = element.getAttributeValue("test");
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
