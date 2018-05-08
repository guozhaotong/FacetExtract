package newStep;

import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author 郭朝彤
 * @date 2018/5/2.
 */
public class E统计词频 {
    public static String domain = "C_programming_language";
    static HashMap<String, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        try {
            List<String> fileList = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\otherFiles\\" + domain + "_topics.txt"), "utf-8");
            for (String name : fileList) {
                frequency(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> facet = new ArrayList<>(map.keySet());
        Collections.sort(facet, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(map.get(o1), map.get(o2));
            }
        });
        ArrayList<Integer> nums = new ArrayList<>();
        for (String f : facet) {
            nums.add(map.get(f));
        }
        for (int i = 0; i < nums.size(); i++) {
            System.out.println(facet.get(i));
        }
    }

    public static void frequency(String word) {
        Topic topic = TxtToObject.SaveTxtToObj("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\4_topicNameFilter\\" + word + ".txt");
        for (Facet f1 : topic.getFacets()) {
            for (String s1 : f1.getName().split(" ")) {
                if (!map.containsKey(s1)) {
                    map.put(s1, 1);
                } else {
                    map.put(s1, map.get(s1) + 1);
                }
                for (Facet f2 : f1.getNextFacets()) {
                    for (String s2 : f2.getName().split(" ")) {
                        if (!map.containsKey(s2)) {
                            map.put(s2, 1);
                        } else {
                            map.put(s2, map.get(s2) + 1);
                        }
                        for (Facet f3 : f2.getNextFacets()) {
                            for (String s3 : f3.getName().split(" ")) {
                                if (!map.containsKey(s3)) {
                                    map.put(s3, 1);
                                } else {
                                    map.put(s3, map.get(s3) + 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
