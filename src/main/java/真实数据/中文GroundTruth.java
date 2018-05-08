package 真实数据;

import newStep.ITranToChinese;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/4/26.
 */
public class 中文GroundTruth {
    public static void main(String[] args) {
        String domain = "Data_Structure";
//        HashSet<String> set = getAppearedFacets(domain);
//        zhEnCompare(set, domain);
        getZhEn(domain);
    }

    public static HashMap<String, String> getZhEn(String domain) {
        HashMap<String, String> map = new HashMap<>();
        try {
            List<String> list = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\otherFiles\\分面中英文对应.txt"), "utf-8");
            for (String s : list) {
                String[] strings = s.split(" ");
                map.put(s.replaceAll(strings[strings.length - 1], "").trim(), strings[strings.length - 1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void zhEnCompare(HashSet<String> set, String domain) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (String s : set) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
            stringBuilder.append(ITranToChinese.trans(s));
            stringBuilder.append("\n");
        }
        try {
            FileUtils.write(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\otherFiles\\分面中英文对应.txt"), stringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashSet<String> getAppearedFacets(String domain) {
        HashSet<String> res = new HashSet<>();
        try {
            List<String> fileName = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\otherFiles\\" + domain + "_topics.txt"), "utf-8");
            for (String s : fileName) {
                List<String> facets = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\good ground truth\\" + s + ".txt"), "utf-8");
                for (String s1 : facets) {
                    res.add(s1.trim().replaceAll("-", " "));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
