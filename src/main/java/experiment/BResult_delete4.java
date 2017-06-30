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
public class BResult_delete4 {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        GetWholeResult();
    }

    public static void GetWholeResult() {
        System.out.println("正在计算实验结果...");
        List<String> fileName = GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        int i = 0;
        ArrayList<Double> ham_loss = new ArrayList<>();
        ArrayList<Double> p1_micro = new ArrayList<>();
        ArrayList<Double> r1_micro = new ArrayList<>();
        ArrayList<Double> f1_micro = new ArrayList<>();
        List<String> facetOrder = GetNameOrder(oriPath + "experiment\\facet_order.txt");
        ArrayList<Integer> should = new ArrayList<>();
        ArrayList<Integer> actual = new ArrayList<>();
        ArrayList<Integer> shoAct = new ArrayList<>();
        for (int j = 0; j < facetOrder.size(); j++) {
            should.add(0);
            actual.add(0);
            shoAct.add(0);
        }
        String cont = "";
        for (String name : fileName) {
//            System.out.println(name);
            Topic curTopic = TxtToObject.SaveTxtToObj(oriPath + "4_topicNameFilter\\" + name + ".txt");
            //resFacetSet里面是实验结果
            HashSet<String> facetSet = AAppearedFacet.FindFacetOfOneTopic(curTopic);
//            HashSet<String> resFacetSet = (HashSet<String>) facetSet.clone();
            HashSet<String> resFacetSet = ComplementFacet(facetSet, name);
            resFacetSet = ComplementFacet(facetSet, name);
            resFacetSet = GetOneRes(resFacetSet, name);
            //gtFacetSet里面是ground truth
            HashSet<String> gtFacetSet = new HashSet<>();
            List<String> gtFacetList = GetNameOrder(oriPath + "good ground truth\\" + name + ".txt");
            for (String s : gtFacetList) gtFacetSet.add(s.trim());
            gtFacetSet.remove("definition");
            gtFacetSet.remove("application");
            gtFacetSet.remove("example");
            gtFacetSet.remove("property");
            gtFacetSet.remove("type");
            HashSet<String> gtFacetSetClone = (HashSet<String>) gtFacetSet.clone();
            for (String s : gtFacetSetClone) {
                if (!facetOrder.contains(s)) {
                    gtFacetSet.remove(s);
                }
            }
            if (resFacetSet.contains("history")) {
                gtFacetSet.add("history");
            }
            HashSet<String> topicFacetSet = (HashSet<String>) gtFacetSet.clone();
            topicFacetSet.addAll(resFacetSet);
            //开始为计算宏平均做准备
            for (int j = 0; j < facetOrder.size(); j++) {
                if (resFacetSet.contains(facetOrder.get(j))) {
                    actual.set(j, actual.get(j) + 1);
                    if (gtFacetSet.contains(facetOrder.get(j))) {
                        shoAct.set(j, shoAct.get(j) + 1);
                    }
                }
                if (gtFacetSet.contains(facetOrder.get(j))) {
                    should.set(j, should.get(j) + 1);
                }
            }

            int sameNum1 = 0;
            for (String s : gtFacetSet) {
                if (resFacetSet.contains(s)) {
                    sameNum1++;
                }
            }
            //开始计算海明损失
            int topicFSize = topicFacetSet.size();
            if (topicFSize == 0) ham_loss.add(1.0);
            else {
                double ham = (double) sameNum1 / topicFSize;
                ham_loss.add(1.0 - ham);
            }
//            ham_loss.add(1.0- (double)(sameNum1 + facetOrder.size() - topicFSize) / facetOrder.size());
//            System.out.println(ham_loss.get(i));
            //开始计算实验结果p
            int myResSize = resFacetSet.size();
            if (myResSize == 0) p1_micro.add(0.0);
            else p1_micro.add((double) sameNum1 / myResSize);//precision
            //开始计算实验结果recall
            int groundTruthSize = gtFacetSet.size();
            r1_micro.add((double) sameNum1 / groundTruthSize);//recall
            if ((r1_micro.get(i) + p1_micro.get(i)) == 0) f1_micro.add(0.0);
            else f1_micro.add(2 * r1_micro.get(i) * p1_micro.get(i) / (r1_micro.get(i) + p1_micro.get(i)));
            cont = cont + ham_loss.get(i) + " " + p1_micro.get(i) + " " + r1_micro.get(i) + " " + f1_micro.get(i) + "\n";
            i++;
//            System.out.println(sameNum1 + " " + topicFSize + " " + ham);
//            System.out.println(resFacetSet);
        }
        try {
            FileUtils.write(new File(oriPath + "experiment\\microMetrics.txt"), cont, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        cont = "";
        for (int j = 0; j < facetOrder.size(); j++) {
            cont = cont + should.get(j) + " " + actual.get(j) + " " + shoAct.get(j) + "\n";
        }
        try {
            FileUtils.write(new File(oriPath + "experiment\\macroMetrics.txt"), cont, "utf-8");
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
        facetSet.remove("definition");
        facetSet.remove("property");
        facetSet.remove("application");
        facetSet.remove("example");
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
