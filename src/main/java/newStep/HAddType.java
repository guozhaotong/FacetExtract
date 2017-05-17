package newStep;

import method.GetHyponymy;
import method.OperationToTopic;
import method.TxtToObject;
import model.AllHyponymy;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/4/24.
 */
public class HAddType {
    public static void main(String[] args) {
        String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
        String domain = "Data_structure";
        FacetHyponymyMerge(oriPath, domain);
    }

    public static void FacetHyponymyMerge(String oriPath, String domain) {
        String InputFilePath = oriPath + "7_facetHyponymyMerge\\";
        String OutputFilePath = oriPath + "8_AddType";
        List<String> fileName = new ArrayList<>();
        try {
            fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File dirfile = new File(OutputFilePath);
        if (!dirfile.exists() && !dirfile.isDirectory())
            dirfile.mkdir();
        AllHyponymy allHyponymy = GetHyponymy.GetHyponymyFromExl(oriPath + "otherFiles\\" + domain + "上下位.xls");
        for (String name : fileName) {
            System.out.println("Add type\t" + name);
            Topic topic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            topic = AddType(topic, allHyponymy);
            topic = OperationToTopic.OptimizeTopic(topic);
            TxtToObject.writeObjToTxt(topic, OutputFilePath + "\\" + name + ".txt");
        }
        System.out.println("done.");
    }

    public static Topic AddType(Topic topic, AllHyponymy allHyponymy) {
        ArrayList<String> upLocation = allHyponymy.getUpLocation();
        if (upLocation.contains(topic.getName())) {
            List<Facet> emptyList = new ArrayList<>();
            Facet type = new Facet("type", emptyList);
            List<Facet> facetList = topic.getFacets();
            facetList.add(type);
            topic.setFacets(facetList);
        }
        return topic;
    }

}