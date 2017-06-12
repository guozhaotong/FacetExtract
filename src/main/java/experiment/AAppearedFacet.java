package experiment;

import method.FindRelationship;
import method.GetHyponymy;
import method.TxtToObject;
import model.AllHyponymy;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/5/25.
 */
public class AAppearedFacet {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String args[]) {
        List<String> fileName = null;
        try {
            fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        FindAppearedFacet(fileName);
//        FacetRepresentation(fileName);
        CreateMatrixP0(fileName);
    }

    public static void CreateMatrixP0(List<String> fileName) {
        Double[][] p0 = new Double[fileName.size()][fileName.size()];
        for (int i = 0; i < fileName.size(); i++) {
            for (int j = 0; j < fileName.size(); j++) {
                p0[i][j] = Double.valueOf(0);
            }
        }
        AllHyponymy allHyponymy = GetHyponymy.GetHyponymyFromExl(oriPath + "otherFiles\\" + domain + "上下位.xls");
        ArrayList<String> upLocation = allHyponymy.getUpLocation();
        ArrayList<String> dnLocation = allHyponymy.getDnLocation();
        ArrayList<Double> parentToChild = new ArrayList<>();
        parentToChild.add((double) 1);
        parentToChild.add((double) 1);
        parentToChild.add(0.9819);
        parentToChild.add(0.9522);
        parentToChild.add(0.9239);
        parentToChild.add((double) 1);
        ArrayList<Double> childToParent = new ArrayList<>();
        childToParent.add((double) 0);
        childToParent.add(0.4481);
        childToParent.add(0.3923);
        childToParent.add(0.05);
        childToParent.add(0.17);
        childToParent.add(0.47);
        HashMap<String, Integer> fileNameMap = new HashMap<>();
        int i = 0;
        for (String name : fileName) {
            fileNameMap.put(name, i++);
        }
        DecimalFormat df = new DecimalFormat("#0.0000");
        for (i = 0; i < fileName.size(); i++) {
            System.out.println(fileName.get(i));
            p0[i][i] = Double.valueOf(1);
            int layer = FindRelationship.findLayer(upLocation, dnLocation, fileName.get(i), domain);
            ArrayList<String> parentTopic = FindRelationship.findDirectParent(upLocation, dnLocation, fileName.get(i));
            if (parentTopic.size() > 0) {
                for (String parent : parentTopic) {
                    p0[i][fileNameMap.get(parent)] = childToParent.get(layer);
                }
            }
            ArrayList<String> brotherTopic = FindRelationship.findBrother(upLocation, dnLocation, fileName.get(i));
            if (brotherTopic.size() > 0) {
                for (String brother : brotherTopic) {
                    p0[i][fileNameMap.get(brother)] = 0.45 + (0.075 * layer);
                }
            }
            ArrayList<String> childTopic = FindRelationship.FindDirectChild(allHyponymy, fileName.get(i));
            if (childTopic.size() > 0) {
                for (String child : childTopic) {
                    p0[i][fileNameMap.get(child)] = parentToChild.get(layer);
                }
            }
        }
        String cont = "";
        for (i = 0; i < fileName.size(); i++) {
            for (int j = 0; j < fileName.size(); j++) {
//                cont = cont + j + ":" + df.format(p0[i][j]) + " ";
                cont = cont + df.format(p0[i][j]) + " ";
            }
            cont = cont + "\n";
        }
        try {
            FileUtils.write(new File(oriPath + "experiment\\p0.txt"), cont, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }


    /**
     * 用于生成表示主题有什么分面的向量。每个主题表示成一个向量，有哪个分面，那个位置相应的就为1，其他地方为0.
     * 其中，hashMap是用来存储所有分面的，key是String类型，表示出现过的分面，Value是Integer类型，表示顺序。表示顺序。表示顺序。喔~
     */
    public static void FacetRepresentation(List<String> fileName) {
        List<String> facetName = null;
        try {
            facetName = FileUtils.readLines(new File(oriPath + "experiment\\facet_order.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Integer> facetNameMap = new HashMap<>();
        int i = 0;
        for (String name : facetName) {
            facetNameMap.put(name, i++);
        }
        for (String name : fileName) {
            ArrayList<String> topicRepresentation = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                topicRepresentation.add("0");
            }
            System.out.println(name);
            Topic topic = TxtToObject.SaveTxtToObj(oriPath + "4_topicNameFilter\\" + name + ".txt");
            String cont = RepresentTopic(topicRepresentation, facetNameMap, topic);
            try {
                FileUtils.write(new File(oriPath + "experiment\\facet_representation\\" + name + ".txt"), cont, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("done");
    }

    /**
     * 用于把ArrayList相应位置的值设置成1.
     *
     * @param arrayList
     * @param hashMap
     * @param topic
     * @return
     */
    public static String RepresentTopic(ArrayList<String> arrayList, HashMap<String, Integer> hashMap, Topic topic) {
        for (Facet facet : topic.getFacets()) {
            if (hashMap.containsKey(facet.getName())) {
                arrayList.set(hashMap.get(facet.getName()), "1");
            }
            for (Facet secFacet : facet.getNextFacets()) {
                if (hashMap.containsKey(secFacet.getName())) {
                    arrayList.set(hashMap.get(secFacet.getName()), "1");
                }
                for (Facet thiFacet : secFacet.getNextFacets())
                    if (hashMap.containsKey(thiFacet.getName())) {
                        arrayList.set(hashMap.get(thiFacet.getName()), "1");
                    }
            }
        }
        String cont = "";
        for (String s : arrayList) {
            cont = cont + s + " ";
        }
        return cont;
    }

    /**
     * 用于找到所有的主题所包含的所有分面列表。
     * 注意，其中，每个主题固有的那4个分面已经被去除。
     */
    public static void FindAppearedFacet(List<String> fileName) {
        HashSet<String> set = new HashSet<>();
        String filePath = oriPath + "4_topicNameFilter\\";
        for (String name : fileName) {
            Topic topic = TxtToObject.SaveTxtToObj(filePath + name + ".txt");
            set.addAll(FindFacetOfOneTopic(topic));
        }
        set.remove("definition");
        set.remove("property");
        set.remove("example");
        set.remove("application");
        String facetName = "";
        System.out.println(set.size());
        for (String string : set) {
            facetName = facetName + string + "\n";
        }
        System.out.println(facetName);
        try {
            FileUtils.write(new File(oriPath + "experiment\\facet_order.txt"), facetName, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done.");
    }

    /**
     * 用于统计一个主题所拥有的分面。
     *
     * @param oneTopic
     * @return
     */
    public static HashSet<String> FindFacetOfOneTopic(Topic oneTopic) {
        HashSet<String> hashSet = new HashSet<>();
        for (Facet facet : oneTopic.getFacets()) {
            hashSet.add(facet.getName());
            for (Facet secFacet : facet.getNextFacets()) {
                hashSet.add(secFacet.getName());
                for (Facet thiFacet : secFacet.getNextFacets())
                    hashSet.add(thiFacet.getName());
            }
        }
        return hashSet;
    }
}
