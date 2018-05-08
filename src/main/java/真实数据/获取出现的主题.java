package 真实数据;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author 郭朝彤
 * @date 2018/4/19.
 */
public class 获取出现的主题 {
    public static String domain = "人工智能";

    public static void main(String[] args) {
        getAppearedTopics("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "原始分面.xls");
    }

    public static void getAppearedTopics(String fileName) {
        ArrayList<String> list = 获取出现的分面.readExlCol(fileName, 0);
        HashSet<String> set = new HashSet<>(list);
        StringBuilder stringBuilder = new StringBuilder("");
        for (String s : set) {
            stringBuilder.append(s + "\n");
        }
        try {
            FileUtils.write(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "主题.txt"), stringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
