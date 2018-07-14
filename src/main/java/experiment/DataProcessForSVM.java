package experiment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/7/25.
 */
public class DataProcessForSVM {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";


    public static void main(String[] args) {
        createData();
        createTrainTest();
    }

    public static void createTrainTest() {
        List<String> cont = BResult_delete4.GetNameOrder(oriPath + "data.libsvm");
        StringBuffer train = new StringBuffer("");
        StringBuffer test = new StringBuffer("");
        for (int i = 0; i < 130; i++) {
            train.append(cont.get(i) + "\n");
        }
        for (int i = 130; i < cont.size(); i++) {
            test.append(cont.get(i) + "\n");
        }
        try {
            FileUtils.write(new File(oriPath + "train.libsvm"), train.toString(), "utf-8");
            FileUtils.write(new File(oriPath + "CalcSummarySim.libsvm"), test.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

    public static void createData() {
        HashSet<String> facetSet = new HashSet<>();
        List<String> facetList = new ArrayList<>();
        StringBuffer cont = new StringBuffer("");
        List<String> nameList = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\Data_structure_topics.txt");
        for (String name : nameList) {
            List<String> content = new ArrayList<>();
            try {
                content = FileUtils.readLines(new File(oriPath + "good ground truth\\" + name + ".txt"), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            facetSet.addAll(content);
        }
        for (String s : facetSet) {
            facetList.add(s);
        }
        for (String name : nameList) {
            List<String> content = new ArrayList<>();
            try {
                content = FileUtils.readLines(new File(oriPath + "good ground truth\\" + name + ".txt"), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Integer> labelNum = new ArrayList<>();
            for (int i = 0; i < facetList.size(); i++) {
                if (content.contains(facetList.get(i))) {
                    labelNum.add(i + 1);
                }
            }
            for (int i : labelNum) {
                cont.append(i + ",");
            }
            cont.deleteCharAt(cont.length() - 1);
            for (int i : labelNum) {
                cont.append("\t" + i + ":1");
            }
            cont.append("\n");
        }

        try {
            FileUtils.write(new File(oriPath + "data.libsvm"), cont.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(facetList.size() + "done");
    }


}
