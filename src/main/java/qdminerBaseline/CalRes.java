package qdminerBaseline;

import experiment.AAppearedFacet;
import method.TxtToObject;
import model.Topic;

import java.util.HashSet;

/**
 * @author 郭朝彤
 * @date 2018/1/9.
 */
public class CalRes {
    static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\";
    static String topicName = "Complete_graph";

    public static void main(String[] args) {
        cal();
    }

    public static void cal() {
        Topic topic = TxtToObject.SaveTxtToObj(oriPath + "9_TransToChinese\\" + topicName + ".txt");
        HashSet<String> res = AAppearedFacet.FindFacetOfOneTopic(topic);
        HashSet<String> baseline = new HashSet<>();
        baseline.add("简介");
        baseline.add("特征");
        baseline.add("几何拓扑");
        baseline.add("例子");
        baseline.add("定义");
        baseline.add("解释");
        baseline.add("类型");
        baseline.add("应用");
//        baseline.add("删除");
        int same = (int) res.stream().filter(baseline::contains).count();
        double p = (double) same / baseline.size();
        double r = (double) same / res.size();
        double f = (2 * r * p) / (r + p);
        System.out.println("p = " + p);
        System.out.println("r = " + r);
        System.out.println("f = " + f);
    }
}
