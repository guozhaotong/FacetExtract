package test;

import newStep.ITranToChinese;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/5/3.
 */
public class Translate {
    public static String domain = "C_programming_language";

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            List<String> list = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\otherFiles\\候选分面.txt"), "utf-8");
            for (String s : list) {
                stringBuilder.append(ITranToChinese.trans(s));
                stringBuilder.append("\n");
            }
            FileUtils.write(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\otherFiles\\分面中英文对应.txt"), stringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
