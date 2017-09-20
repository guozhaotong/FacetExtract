package experiment;


import model.Facet;
import model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/5/25.
 */
public class test {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        List<Facet> empList = new ArrayList<>();
        Facet facet1 = new Facet("1", empList);
        Facet facet2 = new Facet("2", empList);
        Facet facet3 = new Facet("3", empList);
        List<Facet> facetList = new ArrayList<>();
        facetList.add(facet1);
        facetList.add(facet2);
        facetList.add(facet3);
        Topic topic = new Topic("testTopic", facetList);
        topic.addFacet("4");
        System.out.println(topic.toString());
    }


}
