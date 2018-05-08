package test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/5/8.
 */
public class GroundTruth相似度 {
    public static void main(String[] args) {
        try {
            List<String> fileList = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\otherFiles\\Data_structure_topics.txt"), "utf-8");
            ArrayList<Double> list = new ArrayList<>();
            for (String file1 : fileList) {
                for (String file2 : fileList) {
                    List<String> list1 = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\good ground truth\\" + file1 + ".txt"), "utf-8");
                    List<String> list2 = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\good ground truth\\" + file2 + ".txt"), "utf-8");
                    HashSet<String> set1 = new HashSet<>(list1);
                    HashSet<String> set2 = new HashSet<>(list2);
                    list.add(similarity(set1, set2));
                }
            }
            Double sum = 0.0;
            for (double d : list) {
                sum += d;
            }
            System.out.println(sum / ((double) list.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double similarity(HashSet<String> set1, HashSet<String> set2) {
        int num1 = set1.size(), num2 = set2.size();
        set1.addAll(set2);
        int total = set1.size();
        int diff = num1 + num2 - total;
        return ((double) diff / (double) total);
    }
}
