package experiment;

import method.TxtToObject;
import model.Topic;

import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/5/25.
 */
public class test {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        String name = "Abstract_data_type";
        Topic curTopic = TxtToObject.SaveTxtToObj(oriPath + "4_topicNameFilter\\" + name + ".txt");
        HashSet<String> facetSet = AAppearedFacet.FindFacetOfOneTopic(curTopic);
        facetSet = BResult.ComplementFacet(facetSet, name);

        HashSet<String> resFacetSet = BResult.GetOneRes(facetSet, name);
        //gtFacetSet里面是ground truth
        HashSet<String> gtFacetSet = new HashSet<>();
        List<String> gtFacetList = BResult.GetNameOrder(oriPath + "good ground truth\\" + name + ".txt");
        for (String s : gtFacetList) gtFacetSet.add(s);
        System.out.println(facetSet);
        System.out.println(resFacetSet);
        System.out.println(gtFacetSet);
    }
}
