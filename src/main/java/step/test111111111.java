package step;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author 郭朝彤
 * @date 2017/6/13.
 */
public class test111111111 {
    public static void main(String[] args) {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://en.wikipedia.org/wiki/Array_data_structure").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(doc.text());
    }
}
