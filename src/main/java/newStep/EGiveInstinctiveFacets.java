package newStep;
//把所有的主题都加上其天生的几个分面（如果原来没有的话），definition, property, application, example

import method.OperationToTopic;
import method.TraveralAndChange;
import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EGiveInstinctiveFacets {

    public static void main(String[] args) {
        String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
        String domain = "Data_structure";
        GiveInstinctiveFacets(oriPath, domain);
    }

    public static void GiveInstinctiveFacets(String oriPath, String domain) {
        String InstinctiveFacets[] = {"definition", "property", "application", "example"};
        String InputFilePath = oriPath + "4_topicNameFilter\\";
        String OutputFilePath = oriPath + "5_giveInstinctiveFacets";
        File dirfile = new File(OutputFilePath);
        if (!dirfile.exists() && !dirfile.isDirectory())
            dirfile.mkdir();
        List<String> fileName = new ArrayList<>();
        try {
            fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        TraveralAndChange traveralAndChange = facet -> changeBadWord(facet, InstinctiveFacets);
        for (String name : fileName) {
            System.out.println("giveInstinctiveFacets\t" + name);
            Topic curtopic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            traveralAndChange.traversalAllAndChange(curtopic);
            HashSet<String> appearedFacet = new HashSet<>();
            List<Facet> firFacets = curtopic.getFacets();
            for (Facet f : firFacets) {
                appearedFacet.add(f.getName());
            }
            List<Facet> emptyFacetList = new ArrayList<>();
            for (String s : InstinctiveFacets) {
                if (!appearedFacet.contains(s)) {
                    Facet newFacet = new Facet(s, emptyFacetList);
                    firFacets.add(newFacet);
                }
            }
            curtopic = OperationToTopic.OptimizeTopic(curtopic);
            TxtToObject.writeObjToTxt(curtopic, OutputFilePath + "\\" + name + ".txt");
        }
        System.out.println("done.");
    }

    public static Facet changeBadWord(Facet facet, String[] InstinctiveFacets) {
        String lineString = facet.getName();
        lineString = lineString.replaceAll("\\s+", " ");  //用正则表达式替换多个空格为空格
        lineString = " " + lineString + " ";
        for (String s : InstinctiveFacets) {
            if (lineString.contains(" " + s) || lineString.contains(s + " "))
                lineString = "";
        }
        if (lineString.contains("advantage")) lineString = "property";
        if (lineString.contains(" performance ")) lineString = "property";
        if (lineString.contains(" efficiency ")) lineString = "property";
        if (lineString.contains(" characterization ")) lineString = "property";
        if (lineString.contains(" analysis ")) lineString = "property";
        if (lineString.contains(" comparison ")) lineString = "property";
        if (lineString.contains(" implemantation ")) lineString = "implementation";
        if (lineString.contains(" implemantations ")) lineString = "implementation";
        if (lineString.contains(" language support ")) lineString = "implementation";
        if (lineString.contains(" pseudocode ")) lineString = "implementation";
        if (lineString.contains(" usage ")) lineString = "application";
        if (lineString.contains(" using ")) lineString = "application";
        if (lineString.contains(" use ")) lineString = "application";
        if (lineString.contains(" description ")) lineString = "definition";
        if (lineString.trim().equals("concept")) lineString = "definition";
        facet.setName(lineString.trim());
        return facet;
    }

}
