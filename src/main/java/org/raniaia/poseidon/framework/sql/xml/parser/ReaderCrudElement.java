package org.raniaia.poseidon.framework.sql.xml.parser;

import org.jdom2.Content;
import org.jdom2.Element;
import org.raniaia.poseidon.framework.exception.runtime.ExpressionException;
import org.raniaia.poseidon.framework.exception.runtime.MapperXMLException;
import org.raniaia.poseidon.framework.provide.PoseidonProvideConstant;
import org.raniaia.poseidon.framework.sql.xml.node.XMLNode;
import org.raniaia.poseidon.framework.sql.xml.node.XMLMapperNode;
import org.raniaia.poseidon.framework.sql.xml.node.XMLDynamicSqlNode;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 读取Mapper并解析
 * Copyright: Create by tiansheng on 2019/12/17 10:58
 */
@SuppressWarnings("SpellCheckingInspection")
public class ReaderCrudElement {

    /**
     * 解析Mapper中标签的方法
     */
    private MapperLabelParser xmlparser;

    public XMLMapperNode reader(List<Element> cruds, MapperLabelParser xmlparser) {

        this.xmlparser = xmlparser;
        XMLMapperNode xmlMapperNode = new XMLMapperNode(xmlparser.getCurrentBuilder());

        for (Element crud : cruds) {

            XMLDynamicSqlNode dynamicSql = new XMLDynamicSqlNode();

            // 获取crud标签属性
            String type = crud.getName();
            String result = crud.getAttributeValue("result");
            String mappername = crud.getAttributeValue("name");
            dynamicSql.setType(type);
            dynamicSql.setResult(result);
            dynamicSql.setName(mappername);
            if (StringUtils.isEmpty(mappername)) {
                throw new ExpressionException("tag: mapper attribute name cannot null from builder '" + xmlparser.getCurrentBuilder() + "'");
            }
            xmlparser.setCurrentMapper(mappername);
            //
            // 解析mapper
            //
            parserMapperContent(crud.getContent(), dynamicSql);
            xmlMapperNode.add(dynamicSql);

        }

        return xmlMapperNode;
    }

    /**
     * 解析mapper标签下的内容
     * @param contents
     */
    public void parserMapperContent(List<Content> contents, XMLDynamicSqlNode dynamicSql) {
        List<XMLNode> nodes = new LinkedList<>();
        parseContents(contents, nodes);
        for (XMLNode node : nodes) {
            dynamicSql.addNode(node);
        }
    }

    /**
     * 解析标签
     * @param contents
     * @return
     */
    public void parseContents(List<Content> contents, List<XMLNode> nodes) {

        for (Content content : contents) {

            if (content.getCType() == Content.CType.Text) {
                String text = StringUtils.trim(content.getValue());
                if (!StringUtils.isEmpty(text)) {
                    nodes.add(new XMLNode(PoseidonProvideConstant.TEXT, text));
                }
                continue;
            }

            if (content.getCType() == Content.CType.Element) {
                Element element = ((Element) content);
                //
                // if
                //
                if (PoseidonProvideConstant.IF.equals(element.getName())) {
                    nodes.add(xmlparser.ifOrEels(element));
                    continue;
                }

                //
                // choose
                //
                if (PoseidonProvideConstant.CHOOSE.equals(element.getName())) {
                    nodes.add(xmlparser.choose(element));
                    continue;
                }

                //
                // foreach
                //
                if (PoseidonProvideConstant.FOREACH.equals(element.getName())) {
                    XMLNode forNode = xmlparser.foreach(element);
                    if (element.getContent().size() > 0) {
                        List<XMLNode> forNodes = new LinkedList<>();
                        parseContents(element.getContent(), forNodes);
                        for (XMLNode node : forNodes) {
                            forNode.addChild(node);
                        }
                    }
                    nodes.add(forNode);
                    continue;
                }

                //
                // parameter
                //
                if (PoseidonProvideConstant.PARAMETER.equals(element.getName())) {
                    XMLNode parameter = new XMLNode(element.getName(),StringUtils.trim(element.getText()));
                    nodes.add(parameter);
                    continue;
                }

                throw new MapperXMLException("unknown lbael '" + element.getName() + "' error location in mapper: "
                        + xmlparser.getCurrentBuilder() + " mapper " + xmlparser.getCurrentMapper());
            }

        }
    }

}
