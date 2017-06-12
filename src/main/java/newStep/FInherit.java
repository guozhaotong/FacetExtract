package newStep;
//用于继承父节点的分面，兄弟节点的大于阈值的分面。

import method.FindRelationship;
import method.GetHyponymy;
import method.OperationToFacet;
import method.TxtToObject;
import model.AllHyponymy;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FInherit {

    public static void main(String[] args) {
        String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
        String domain = "Data_structure";
        inherit(oriPath, domain);
    }

    public static void inherit(String oriPath, String domain) {
        String InputFilePath = oriPath + "5_giveInstinctiveFacets\\";
        String OutputFilePath = oriPath + "6_inheritFacets";
        File dirfile = new File(OutputFilePath);
        if (!dirfile.exists() && !dirfile.isDirectory())
            dirfile.mkdir();
        List<String> fileName = new ArrayList<>();
        try {
            fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        AllHyponymy allHyponymy = GetHyponymy.GetHyponymyFromExl(oriPath + "otherFiles\\" + domain + "上下位.xls");
        ArrayList<String> upLocation = allHyponymy.getUpLocation();
        ArrayList<String> dnLocation = allHyponymy.getDnLocation();
        for (String name : fileName) {
            System.out.println("Inherit\t" + name);
            Topic curtopic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            curtopic = InheritFromAncient(InputFilePath, curtopic, upLocation, dnLocation);
            TxtToObject.writeObjToTxt(curtopic, OutputFilePath + "\\" + name + ".txt");
        }
    }

    public static Topic InheritFromAncient(String oriPath, Topic topic, ArrayList<String> upLocation, ArrayList<String> dnLocation) {
        List<Facet> topicFacetList = topic.getFacets();
        ArrayList<String> parents = FindRelationship.findParent(upLocation, dnLocation, topic.getName());
        for (String parentName : parents) {
            File file = new File(oriPath + parentName + ".txt");
            if (!file.exists())
                continue;
            Topic parentTopic = TxtToObject.SaveTxtToObj(oriPath + parentName + ".txt");
            List<Facet> parentTopicFacetList = parentTopic.getFacets();
            topic.setFacets(OperationToFacet.MergeFacets(topicFacetList, parentTopicFacetList));
        }
        return topic;
    }

    public static Topic InheritFromBrother(String oriPath, String domain, Topic topic, ArrayList<String> upLocation, ArrayList<String> dnLocation) {
        List<Facet> firFacet = topic.getFacets();
        HashSet<String> firFacetName = new HashSet<>();
        if (!firFacet.isEmpty()) {
            for (Facet facet : topic.getFacets()) {
                firFacetName.add(facet.getName());
            }
        }
        int layerInDomainTree = FindRelationship.findLayer(upLocation, dnLocation, topic.getName(), domain);
        ArrayList<String> brothers = FindRelationship.findBrother(upLocation, dnLocation, topic.getName());
        HashMap<String, Integer> facetAndTimes = getTopicsFacets(brothers, oriPath);
        double threshold = (-0.075 * layerInDomainTree + 0.9) * brothers.size();
        HashSet<String> newFacets = new HashSet<>();
        Iterator iter = facetAndTimes.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if ((int) entry.getValue() > threshold) {
                if (!firFacetName.contains((String) entry.getKey()))
                    newFacets.add((String) entry.getKey());
            }
        }
        for (String s : newFacets) {
            List<Facet> emptyList = new ArrayList<>();
            Facet f = new Facet("stanfordCoreNLP", emptyList);
            firFacet.add(f);
        }
        topic.setFacets(firFacet);
        return topic;
    }

    public static HashMap<String, Integer> getTopicsFacets(ArrayList<String> topics, String oriPath) {
        HashMap<String, Integer> facetAndTimes = new HashMap<>();
        for (String topic : topics) {
            File file = new File(oriPath + topic + ".txt");
            if (!file.exists())
                continue;
            Topic curTopic = TxtToObject.SaveTxtToObj(oriPath + topic + ".txt");
            for (Facet firF : curTopic.getFacets()) {
                if (facetAndTimes.containsKey(firF.getName()))
                    facetAndTimes.put(firF.getName(), facetAndTimes.get(firF.getName()) + 1);
                else
                    facetAndTimes.put(firF.getName(), 1);
            }
        }
        return facetAndTimes;
    }
}
