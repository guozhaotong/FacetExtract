package newStep;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.Proxy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 郭朝彤
 * @date 2018/5/2.
 */
public class AATopicChToEn {
    static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public static String domain = "C语言";

    public static void main(String[] args) {
        Proxy.start();
        getChTopicEnName();
    }

    public static void getChTopicEnName() {
        ArrayList<String> chName = getDomainTopic();
        StringBuilder stringBuilder = new StringBuilder("");
        for (String s : chName) {
            String r = TopicChToEn(s);
            System.out.println(r);
            if (!r.equals("")) {
                stringBuilder.append(r);
                stringBuilder.append("\n");
            }
        }
        try {
            FileUtils.write(new File("C:\\Users\\tong\\Desktop\\" + domain + "英文主题.txt"), stringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getDomainTopic() {
        ArrayList<String> res = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("C:\\Users\\tong\\Desktop\\" + domain + "主题标注文档.xls"));
            Sheet sheet = workbook.getSheet(0);
            for (int i = 1; i < sheet.getRows(); i++) {
                if ("1".equals(sheet.getCell(2, i).getContents())) {
                    res.add(sheet.getCell(1, i).getContents());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        workbook.close();
        System.out.println(res);
        return res;
    }

    public static String TopicChToEn(String word) {
        Document doc = null;
        String res = "";
        try {
            logger.info("正在链接页面...");
            String url = "https://zh.wikipedia.org/wiki/" + word;
            doc = Jsoup
                    .connect(url)
                    .cookie("GeoIP", "CN:SN:Xi'an:34.26:108.93:v4")
                    .header("accept-language", "zh-CN,zh;q=0.9")
                    .timeout(100000)
                    .get();
            Elements element_title = doc.select("#p-lang > div > ul > li.interlanguage-link.interwiki-en > a");
            if (element_title.size() == 0) {
                logger.info(word + "没有解析到对应英文维基主题。");
                return "";
            }
            res = element_title.first().attr("title").replaceAll(" – 英语", "").replaceAll(" ", "_");
        } catch (IOException e) {
            logger.error(word + "没有对应英文维基页面。" + e.getMessage(), e);
        }
        return res;
    }
}
