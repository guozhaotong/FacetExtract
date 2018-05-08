package newStep;
//把主题词语去掉，比如有一个主题叫array，就把分面中出现的所有array都去掉。

import method.OperationToTopic;
import method.TraveralAndChange;
import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DTopicNameFilter {
    public static void main(String[] args) {
        String oriP = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
//		String domain = "Data_structure";
//        String domain = "Data_mining";
        String domain = "C_programming_language";
        String oriPath = oriP + domain + "\\";
        TopicNameFilter(oriPath, domain);
    }

//	static TraveralAndChange traveralAndChange= facet -> removeTopicWord(facet);

    public static void TopicNameFilter(String oriPath, String domain) {
        File dirfile = new File(oriPath + "4_topicNameFilter");
        if (!dirfile.exists() && !dirfile.isDirectory())
            dirfile.mkdir();
        String InputFilePath = oriPath + "3_pluralFilter\\";
        String OutputFilePath = oriPath + "4_topicNameFilter\\";
        List<String> fileName = new ArrayList<>();
        try {
            fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> finalFileName = new ArrayList<>();
        for (String name : fileName) {
            finalFileName.add(name.toLowerCase().split("\\(")[0].replaceAll(domain.toLowerCase(), "")
                    .replaceAll("_", " ").trim());
        }
        TraveralAndChange traveralAndChange = facet -> removeTopicWord(facet, finalFileName);
        for (String name : fileName) {
            System.out.println("Topic name filter\t" + name);
            Topic curtopic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            traveralAndChange.traversalAllAndChange(curtopic);
            curtopic = OperationToTopic.OptimizeTopic(curtopic);
            TxtToObject.writeObjToTxt(curtopic, OutputFilePath + name + ".txt");
        }
        System.out.println("Done.");
    }

    public static Facet removeTopicWord(Facet facet, List<String> topicName) {
        String curFacet = facet.getName();
        curFacet = curFacet.replaceAll("\\s+", " ");  //用正则表达式替换多个空格为空格
        for (String tName : topicName) {
            if (curFacet.trim().equals(tName.toLowerCase()))
                curFacet = "";
            else
                curFacet = curFacet.replaceAll("common", "");
        }
        facet.setName(curFacet.trim());
        return facet;
    }
}
