package newStep;

import experiment.BResult_delete4;
import method.FindRelationship;
import method.GetHyponymy;
import method.TxtToObject;
import model.AllHyponymy;
import model.Topic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/20.
 */
public class FAddExpRes {
    static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    static String domain = "Data_structure";

    public static void main(String[] args) {
        AddExpRes();
    }

    public static void AddExpRes() {
        String InputFilePath = oriPath + "5_giveInstinctiveFacets\\";
        String OutputFilePath = oriPath + "6_AddExpRes\\";
        File dirfile = new File(OutputFilePath);
        if (!dirfile.exists() && !dirfile.isDirectory())
            dirfile.mkdir();
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        AllHyponymy allHyponymy = GetHyponymy.GetHyponymyFromExl(oriPath + "otherFiles\\" + domain + "上下位.xls");
        ArrayList<String> upLocation = allHyponymy.getUpLocation();
        ArrayList<String> dnLocation = allHyponymy.getDnLocation();
        ArrayList<String> childOfAlg = FindRelationship.findChild(upLocation, dnLocation, "Computer_algorithm");
        for (String name : fileName) {
            System.out.println(name);
            Topic topic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            if (!topic.containsFacet("implementation")) {
                topic.addFacet("implementation");
            }
            if (name.toLowerCase().contains("graph") || name.contains("tree") || name.contains("heap")) {
                if (!topic.containsFacet("representation")) {
                    topic.addFacet("representation");
                }
                if (!topic.containsFacet("construction")) {
                    topic.addFacet("construction");
                }
                if (!topic.containsFacet("operation")) {
                    topic.addFacet("operation");
                }
                if (!topic.containsFacet("method")) {
                    topic.addFacet("method");
                }
            }
            if (name.toLowerCase().contains("list") || name.toLowerCase().contains("array") || name.toLowerCase().contains("queue")) {
                if (!topic.containsFacet("method")) {
                    topic.addFacet("method");
                }
                if (!topic.containsFacet("dimension")) {
                    topic.addFacet("dimension");
                }
                if (!topic.containsFacet("operation")) {
                    topic.addFacet("operation");
                }
            }
            if (name.contains("algorithm") || childOfAlg.contains(name)) {
                if (!topic.containsFacet("complexity")) {
                    topic.addFacet("complexity");
                }
                if (!topic.containsFacet("mechanism")) {
                    topic.addFacet("mechanism");
                }
            }
            if (name.contains("search")) {
                if (!topic.containsFacet("algorithm")) {
                    topic.addFacet("algorithm");
                }
            }
            TxtToObject.writeObjToTxt(topic, OutputFilePath + "\\" + name + ".txt");
        }
    }
}
