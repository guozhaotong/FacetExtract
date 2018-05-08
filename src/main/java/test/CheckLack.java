package test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/5/2.
 */
public class CheckLack {
    public static String domain = "C_programming_language";

    public static void main(String[] args) {
        findLack();
    }

    public static void findLack() {
        try {
            List<String> expectNames = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\otherFiles\\" + domain + "_topics.txt"), "utf-8");
            System.out.println(expectNames.size());
            File file = new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\1_origin");
            File[] realFiles = file.listFiles();
            HashSet<String> hashSet = new HashSet<>();
            for (File f : realFiles) {
                hashSet.add(f.getName().replaceAll(".txt", ""));
            }
            System.out.println(hashSet.size());
            HashSet<String> expectSet = new HashSet<>(expectNames);
            System.out.println(expectSet.size());
            for (String s : expectNames) {
                if (!hashSet.contains(s)) {
                    System.out.println(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
