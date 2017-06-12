package experiment;

import method.TxtToObject;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/6/7.
 */
public class BResult {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        GetWholeResult();
    }

    public static void GetWholeResult() {
        List<String> fileName = GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        int i = 0;
        ArrayList<Double> p1_micro = new ArrayList<Double>();
        ArrayList<Double> p2_micro = new ArrayList<Double>();
        ArrayList<Double> r1_micro = new ArrayList<Double>();
        ArrayList<Double> r2_micro = new ArrayList<Double>();
        ArrayList<Double> f1_micro = new ArrayList<Double>();
        ArrayList<Double> f2_micro = new ArrayList<Double>();
        String cont = "";
        for (String name : fileName) {
            System.out.println(name);
            Topic curTopic = TxtToObject.SaveTxtToObj(oriPath + "4_topicNameFilter\\" + name + ".txt");
            //resFacetSet里面是实验结果
            HashSet<String> facetSet = AAppearedFacet.FindFacetOfOneTopic(curTopic);
            HashSet<String> resFacetSet = ComplementFacet(facetSet, name);

            resFacetSet = GetOneRes(resFacetSet, name);
            //gtFacetSet里面是ground truth
            HashSet<String> gtFacetSet = new HashSet<>();
            List<String> gtFacetList = GetNameOrder(oriPath + "good ground truth\\" + name + ".txt");
            for (String s : gtFacetList) gtFacetSet.add(s);
            //开始计算实验结果p
            int groundTruthSize = gtFacetSet.size();
            int rightNum1 = 0;
            int rightNum2 = 0;
            int myResSize = resFacetSet.size();
            int origSize = facetSet.size();
            for (String s : resFacetSet) {
                if (gtFacetSet.contains(s)) {
                    rightNum1++;
                }
            }
            p1_micro.add((double) rightNum1 / myResSize);//precision
            for (String s : facetSet) {
                if (gtFacetSet.contains(s)) {
                    rightNum2++;
                }
            }
            if (origSize == 0) p2_micro.add(0.0);
            else p2_micro.add((double) rightNum2 / origSize);//precision
            //开始计算实验结果recall
            int sameNum1 = 0;
            int sameNum2 = 0;
            for (String s : gtFacetList) {
                if (resFacetSet.contains(s)) {
                    sameNum1++;
                }
                if (facetSet.contains(s)) {
                    sameNum2++;
                }
            }
            r1_micro.add((double) sameNum1 / groundTruthSize);//recall
            r2_micro.add((double) sameNum2 / groundTruthSize);//recall
            if ((r1_micro.get(i) + p1_micro.get(i)) == 0) f1_micro.add(0.0);
            else f1_micro.add(2 * r1_micro.get(i) * p1_micro.get(i) / (r1_micro.get(i) + p1_micro.get(i)));
            if ((r2_micro.get(i) + p2_micro.get(i)) == 0) f2_micro.add(0.0);
            else f2_micro.add(2 * r2_micro.get(i) * p2_micro.get(i) / (r2_micro.get(i) + p2_micro.get(i)));
//            System.out.println(r1_micro.get(i));
//            System.out.println(r2_micro.get(i));
//            if(r1_micro.get(i) < r2_micro.get(i))
//                System.out.println(name);
            cont = cont + p2_micro.get(i) + " " + r2_micro.get(i) + " " + f2_micro.get(i) + " " + p1_micro.get(i) + " "
                    + r1_micro.get(i) + " " + f1_micro.get(i) + "\n";
            i++;
        }
        try {
            FileUtils.write(new File(oriPath + "experiment\\metrics.txt"), cont, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 用于为分面做相应的补全
     *
     * @param set
     * @param name
     * @return
     */
    public static HashSet<String> ComplementFacet(HashSet<String> set, String name) {
        HashSet<String> facetSet = (HashSet<String>) set.clone();
        facetSet.add("definition");
        facetSet.add("property");
        facetSet.add("application");
        facetSet.add("example");
        if (GroundTruthComplement.IsUp(name)) {
            facetSet.add("type");
        }
        if (name.toLowerCase().contains("graph") || name.contains("tree") || name.contains("heap")) {
            facetSet.add("representation");
            facetSet.add("construction");
            facetSet.add("operation");
            facetSet.add("method");
        }
        if (name.toLowerCase().contains("list") || name.toLowerCase().contains("array") || name.toLowerCase().contains("queue")) {
            facetSet.add("dimension");
            facetSet.add("operation");
            facetSet.add("method");
        }
        if (name.contains("algorithm")) {
            facetSet.add("complexity");
            facetSet.add("mechanism");
        }
        if (name.contains("search")) {
            facetSet.add("algorithm");
        }
        return facetSet;
    }

    public static HashSet<String> GetOneRes(HashSet<String> hashSet, String name) {
        HashSet<String> set = (HashSet<String>) hashSet.clone();
        List<String> facetRes = GetNameOrder(oriPath + "experiment\\result\\" + name + ".txt");
        for (String s : facetRes) {
            set.add(s.trim());
        }
        return set;
    }

    public static List<String> GetNameOrder(String filePath) {
        List<String> fileName = null;
        try {
            fileName = FileUtils.readLines(new File(filePath), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
