package newStep;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/5/7.
 */
public class ASavaSummaryAllTopics {
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        try {
            List<String> fileList = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\otherFiles\\" + domain + "_topics.txt"), "utf-8");
            for (String file : fileList) {
                System.out.println(file);
                Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + file).timeout(100000).get();
                Elements element_title = doc.select("#mw-content-text > div > p");
                Iterator iterator = element_title.iterator();
                StringBuilder stringBuilder = new StringBuilder("");
                while (iterator.hasNext()) {
                    Element element = (Element) iterator.next();
                    if ("".equals(element.text())) {
                        break;
                    }
                    stringBuilder.append(element.text());
                    stringBuilder.append("\n");
                }
                FileUtils.write(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\0_summary\\" + file + ".txt"), stringBuilder.toString(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
