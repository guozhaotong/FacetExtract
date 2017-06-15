package experiment;

import method.TxtToObject;
import model.Topic;

import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/6/13.
 */
public class statistics {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        HowManyFacets();
    }

    public static void HowManyFacets() {
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        int algorithmNum = 0;
        int graphNum = 0;
        int arrayNum = 0;
        int dataTypeNum = 0;
        int otherNum = 0;
        int algorithmlen = 0;
        int graphlen = 0;
        int arraylen = 0;
        int dataTypelen = 0;
        int otherlen = 0;
        for (String name : fileName) {
            Topic topic = TxtToObject.SaveTxtToObj(oriPath + "4_topicNameFilter\\" + name + ".txt");
            HashSet<String> facets = AAppearedFacet.FindFacetOfOneTopic(topic);
            facets.remove("definition");
            facets.remove("application");
            facets.remove("example");
            facets.remove("property");
//            List<String> facets = BResult_delete4.GetNameOrder(oriPath + "good ground truth\\" + name + ".txt");
            if (name.toLowerCase().contains("algorithm")) {
                algorithmNum++;
                algorithmlen = algorithmlen + facets.size();
            } else if (name.toLowerCase().contains("tree") || name.toLowerCase().contains("graph") || name.toLowerCase().contains("heap")) {
                graphNum++;
                graphlen = graphlen + facets.size();
            } else if (name.toLowerCase().contains("array") || name.toLowerCase().contains("list") || name.toLowerCase().contains("queue")) {
                arrayNum++;
                arraylen = arraylen + facets.size();
            } else if (name.toLowerCase().contains("data_type")) {
                dataTypeNum++;
                dataTypelen = dataTypelen + facets.size();
            } else {
                otherNum++;
                otherlen = otherlen + facets.size();
            }
        }
        System.out.print("algorithm:" + algorithmlen + " " + algorithmNum + " ");
        System.out.println((float) algorithmlen / algorithmNum);
        System.out.print("graph:" + graphlen + " " + graphNum + " ");
        System.out.println((float) graphlen / graphNum);
        System.out.print("array:" + arraylen + " " + arrayNum + " ");
        System.out.println((float) arraylen / arrayNum);
        System.out.print("data type:" + dataTypelen + " " + dataTypeNum + " ");
        System.out.println((float) dataTypelen / dataTypeNum);
        System.out.print("other:" + otherlen + " " + otherNum + " ");
        System.out.println((otherlen / otherNum));
    }
}
