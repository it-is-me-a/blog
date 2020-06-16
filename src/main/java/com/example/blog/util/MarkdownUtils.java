package com.example.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Node;
import org.commonmark.node.Link;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * 把 Markdown 转换为 HTML 的方法
 */
public class MarkdownUtils {

    /**
     * markdown格式转换成HTML格式
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        //给每个标题生成一个id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }


    public static void main(String[] args) {
        /*表格的测试*/
        String table = "| hello | hi   | 哈哈哈   |\n" +
                "| ----- | ---- | ----- |\n" +
                "| 斯维尔多  | 士大夫  | f啊    |\n" +
                "| 阿什顿发  | 非固定杆 | 撒阿什顿发 |\n" +
                "\n";
        /*链接的测试*/
        String a = "[imCoding 爱编程](http://www.lirenmi.cn)";

        /*标红的测试*/
        String s1 = "`这是行内代码`";
        /*加粗的测试*/
        String s2 = "**加粗的test**";
        String s3 = "~~带删除线的test~~";
        String s4 = "*斜体的test*";


        /*System.out.println(markdownToHtmlExtensions(s1));
        System.out.println(markdownToHtmlExtensions(s2));
        System.out.println(markdownToHtmlExtensions(s3));
        System.out.println(markdownToHtmlExtensions(s4));*/
        System.out.println(markdownToHtmlExtensions(a));
    }
}
