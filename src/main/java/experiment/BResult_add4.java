package experiment;

import method.GetHyponymy;
import method.TxtToObject;
import model.AllHyponymy;
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
public class BResult_add4 {
    public static String domain = "Data_structure";
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\";

    public static void main(String[] args) {
        GetWholeResult();
    }

    public static void GetWholeResult() {
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        int i = 0;
        ArrayList<Double> p1_micro = new ArrayList<>();
        ArrayList<Double> r1_micro = new ArrayList<>();
        ArrayList<Double> f1_micro = new ArrayList<>();
        List<String> facetOrder = BResult_delete4.GetNameOrder(oriPath + "experiment\\facet_order.txt");
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
            System.out.println(name);
            Topic curTopic = TxtToObject.SaveTxtToObj(oriPath + "4_topicNameFilter\\" + name + ".txt");
            //resFacetSet里面是实验结果
            HashSet<String> facetSet = AAppearedFacet.FindFacetOfOneTopic(curTopic);
//            HashSet<String> resFacetSet = BResult_delete4.ComplementFacet(facetSet, name);
            HashSet<String> resFacetSet = (HashSet<String>) facetSet.clone();
//            resFacetSet.add("definition");
//            resFacetSet.add("application");
//            resFacetSet.add("example");
//            resFacetSet.add("property");
//                    resFacetSet = BResult_delete4.GetOneRes(resFacetSet, name);
//            HashSet<String> resFacetSet = (HashSet<String>) facetSet.clone();
            //gtFacetSet里面是ground truth
            HashSet<String> gtFacetSet = new HashSet<>();
            List<String> gtFacetList = BResult_delete4.GetNameOrder(oriPath + "good ground truth\\" + name + ".txt");
            for (String s : gtFacetList) gtFacetSet.add(s.trim());
            gtFacetSet.add("definition");
            gtFacetSet.add("application");
            gtFacetSet.add("example");
            gtFacetSet.add("property");
            AllHyponymy allHyponymy = GetHyponymy.GetHyponymyFromExl(oriPath + "otherFiles\\" + domain + "上下位.xls");
            ArrayList<String> upLocation = allHyponymy.getUpLocation();
            if (upLocation.contains(name)) {
                gtFacetSet.add("type");
                resFacetSet.add("type");
            }
            HashSet<String> gtFacetSetClone = (HashSet<String>) gtFacetSet.clone();
//            for (String s : gtFacetSetClone) {
//                if (!facetOrder.contains(s)) {
//                    gtFacetSet.remove(s);
//                }
//            }
            if (resFacetSet.contains("history")) {
                gtFacetSet.add("history");
            }
//            System.out.println(resFacetSet);
//            System.out.println(gtFacetSet);
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
            //开始计算实验结果p
            int groundTruthSize = gtFacetSet.size();
            int rightNum1 = 0;
            int myResSize = resFacetSet.size();
            for (String s : resFacetSet) {
                if (gtFacetSet.contains(s)) {
                    rightNum1++;
                }
            }
            if (myResSize == 0) p1_micro.add(0.0);
//            else p1_micro.add((double) rightNum1 / myResSize);//precision
            else p1_micro.add((double) (rightNum1 + 147 - myResSize) / 147);//precision
            //开始计算实验结果recall
            int sameNum1 = 0;
            for (String s : gtFacetSet) {
                if (resFacetSet.contains(s)) {
                    sameNum1++;
                }
            }
            r1_micro.add((double) sameNum1 / groundTruthSize);//recall
            if ((r1_micro.get(i) + p1_micro.get(i)) == 0) f1_micro.add(0.0);
            else f1_micro.add(2 * r1_micro.get(i) * p1_micro.get(i) / (r1_micro.get(i) + p1_micro.get(i)));
            cont = cont + p1_micro.get(i) + " " + r1_micro.get(i) + " " + f1_micro.get(i) + "\n";
            i++;
//            System.out.println(gtFacetSet);
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
        facetSet.add("definition");
        facetSet.add("property");
        facetSet.add("application");
        facetSet.add("example");
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

}
