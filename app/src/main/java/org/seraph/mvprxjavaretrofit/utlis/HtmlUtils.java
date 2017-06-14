package org.seraph.mvprxjavaretrofit.utlis;

import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 过滤html标签，以img标签为分割构建list数据
 * img标签类型 <img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
 * date：2017/5/19 15:47
 * author：xiongj
 * mail：417753393@qq.com
 **/

public class HtmlUtils {


    public static final String IMG = "img";

    public static final String TEXT = "txt";

    //图片为分割点
    private static List<HtmlBean> list = new ArrayList<>();


    public static List<HtmlBean> htmlToListData(String html) {
        list.clear();
        initTextImageList(html);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).str = html2Txt(list.get(i).str);
        }
        return list;
    }


    /**
     * 把html内容转为文本
     *
     * @param html 需要处理的html文本
     */
    private static String html2Txt(String html) {
        html = html.replaceAll("\\<head>[\\s\\S]*?</head>(?i)", "");//去掉head
        html = html.replaceAll("\\<!--[\\s\\S]*?-->", "");//去掉注释
        html = html.replaceAll("\\<![\\s\\S]*?>", "");
        html = html.replaceAll("\\<style[^>]*>[\\s\\S]*?</style>(?i)", "");//去掉样式
        html = html.replaceAll("\\<script[^>]*>[\\s\\S]*?</script>(?i)", "");//去掉js
        html = html.replaceAll("\\<w:[^>]+>[\\s\\S]*?</w:[^>]+>(?i)", "");//去掉word标签
        html = html.replaceAll("\\<xml>[\\s\\S]*?</xml>(?i)", "");
        html = html.replaceAll("\\<html[^>]*>|<body[^>]*>|</html>|</body>(?i)", "");
        html = html.replaceAll("\\\r\n|\n|\r", " ");//去掉换行
        html = html.replaceAll("\\<br[^>]*>(?i)", "\n");
        html = html.replaceAll("\\</p>(?i)", "\n");
        html = html.replaceAll("\\<[^>]+>", ""); //去掉所有的<>标签
        html = html.replaceAll("\\&ldquo;", "\\“");
        html = html.replaceAll("\\&rdquo;", "\\”");
        html = html.replaceAll("\\&mdash;", "\\-");
        html = html.replaceAll("\\&hellip;", "\\...");
        html = html.replaceAll("\\ ", " ");
        return html;
    }


    private static void initTextImageList(String content) {
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        if (m_img.find()) {
            //获取到匹配的<img />标签中的内容
            String str_img = m_img.group(2);
            //开始匹配<img />标签中的src
            Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
            Matcher m_src = p_src.matcher(str_img);
            String str_src;
            if (m_src.find()) {
                str_src = m_src.group(3);
            } else {
                str_src = "";
            }
            //数据解析完成，则进行数据拆分组装
            //替换第一个找到的数据标签
            String tempStr = m_img.replaceFirst("######img######");
            if (tempStr.equals("######img######")) {
                //如果只有当前标签则
                if (!StringUtils.isEmpty(str_src)) {
                    list.add(new HtmlBean(HtmlUtils.IMG, str_src));
                }
                return;
            }
            String[] tempStrs = tempStr.split("######img######");
            if (!StringUtils.isEmpty(tempStrs[0])) {
                list.add(new HtmlBean(HtmlUtils.TEXT, tempStrs[0]));
            }
            if (!StringUtils.isEmpty(str_src)) {
                list.add(new HtmlBean(HtmlUtils.IMG, str_src));
            }
            //接着匹配
            //结束匹配<img />标签中的src
            //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
            initTextImageList(tempStrs[1]);
        } else {
            if (!StringUtils.isEmpty(content)) {
                list.add(new HtmlBean(HtmlUtils.TEXT, content));
            }
        }
    }

    public static class HtmlBean {
        public String type;
        public String str;

        public HtmlBean(String type, String str) {
            this.type = type;
            this.str = str;
        }
    }


}
