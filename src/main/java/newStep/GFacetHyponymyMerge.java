package newStep;

import method.OperationToFacet;
import method.OperationToTopic;
import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author 郭朝彤
 * @date 2017/4/24.
 */
public class GFacetHyponymyMerge {
    public static void main(String[] args) {
        String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
        String domain = "Data_structure";
        FacetHyponymyMerge(oriPath, domain);
    }

    public static void FacetHyponymyMerge(String oriPath, String domain) {
        String InputFilePath = oriPath + "6_inheritFacets\\";
        String OutputFilePath = oriPath + "7_facetHyponymyMerge";
        List<String> fileName = new ArrayList<>();
        try {
            fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File dirfile = new File(OutputFilePath);
        if (!dirfile.exists() && !dirfile.isDirectory())
            dirfile.mkdir();
        HashMap<String, Integer> hyponymyIntegerHashMap = GetFacetHyponymy(oriPath + "5_giveInstinctiveFacets\\", fileName);
        Topic hyponymy = PreprocessFacetHyponymy(hyponymyIntegerHashMap);
        for (String name : fileName) {
            System.out.println("Facet Hyponymy Merge\t" + name);
            Topic topic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            topic = FacetHyponymyRecognition(topic, hyponymy.getFacets());
            topic = OperationToTopic.OptimizeTopic(topic);
            TxtToObject.writeObjToTxt(topic, OutputFilePath + "\\" + name + ".txt");
        }
        System.out.println("done.");
    }

    /**
     * 用来为分面们识别出上下位关系。
     *
     * @param topic
     * @param facetHyponymy
     * @return
     */
    public static Topic FacetHyponymyRecognition(Topic topic, List<Facet> facetHyponymy) {
        if (facetHyponymy.size() == 0) {
            return topic;
        }
        topic = SplitMixedFacets(topic, facetHyponymy);
        topic = OperationToTopic.OptimizeTopic(topic);
        ArrayList<Integer> addHyponymyOrNot = new ArrayList<>();    //用于判断是否需要添加上下位关系，用一个分数score来判断是否添加。
        for (int i = 0; i < facetHyponymy.size(); i++) {
            addHyponymyOrNot.add(0);
        }
        for (int i = 0; i < facetHyponymy.size(); i++) {
            int score = 0;
            for (Facet facet1 : topic.getFacets()) {
                if (facetHyponymy.get(i).getName().trim().equals(facet1.getName())) {
                    score = score + 200;
                }
                for (Facet facet2OfFacetHyponymy : facetHyponymy.get(i).getNextFacets()) {
                    if (facet1.getName().trim().equals(facet2OfFacetHyponymy.getName())) {
                        score = score + 1;
                        break;
                    }
                }
            }
            addHyponymyOrNot.set(i, score);
        }
        List<Facet> newFacetList = OperationToFacet.clone(topic.getFacets());
        for (int i = 0; i < facetHyponymy.size(); i++) {
            if (addHyponymyOrNot.get(i) >= 201) {
                newFacetList = FacetProcessGreaterThan201(newFacetList, topic, facetHyponymy.get(i));
            } else if (addHyponymyOrNot.get(i) >= 2 && addHyponymyOrNot.get(i) < 200) {
                newFacetList = FacetProcessLessThan200(newFacetList, topic, facetHyponymy.get(i));
            }
        }
        newFacetList = FindFacetBySubstring(newFacetList);
        topic.setFacets(newFacetList);
        return topic;
    }

    public static List<Facet> FindFacetBySubstring(List<Facet> facetList) {
        List<Facet> newFacetList = OperationToFacet.clone(facetList);
        ArrayList<String> facetName = new ArrayList<>();
        for (Facet facet : facetList) {
            facetName.add(facet.getName());
        }
        for (String s1 : facetName) {
            for (String s2 : facetName) {
                if (s1.contains(s2) && !s1.equals(s2)) {
                    List<Facet> nextFacet = OperationToFacet.clone(OperationToFacet.GetFacet(newFacetList, s2).getNextFacets());
                    nextFacet.add(OperationToFacet.GetFacet(facetList, s1));
                    OperationToFacet.RemoveFacet(newFacetList, s1);
                    OperationToFacet.RemoveFacet(newFacetList, s2);
                    newFacetList.add(new Facet(s2, nextFacet));
                }
            }
        }
        return newFacetList;
    }

    /**
     * 用于把有两个下位分面一起出现的，拆分成两个分面。
     * 如，有一个分面，叫insertion and deletion. 拆分成两个，一个是insertion, 一个是deletion.
     *
     * @param topic
     * @param facetHyponymy
     * @return
     */
    public static Topic SplitMixedFacets(Topic topic, List<Facet> facetHyponymy) {
        List<Facet> newFacetList = OperationToFacet.clone(topic.getFacets());
        List<Facet> facetList2 = new ArrayList<>();
        for (int i = 0; i < topic.getFacets().size(); i++) {
            for (Facet facet1 : facetHyponymy) {
                int score = 0;
                String splitFacetName = "";
                for (Facet facet2 : facet1.getNextFacets()) {
                    String curFacetName = topic.getFacets().get(i).getName();
                    curFacetName = " " + curFacetName + " ";
                    if (curFacetName.contains(" " + facet2.getName() + " ") && !curFacetName.trim().equals(facet2.getName())) {
                        score++;
                        if (score == 1) {
                            splitFacetName = facet2.getName();
                            facetList2 = topic.getFacets().get(i).getNextFacets();
                            newFacetList = OperationToFacet.RemoveFacet(newFacetList, curFacetName.trim());
                        } else if (score == 2) {
                            newFacetList.add(new Facet(splitFacetName, facetList2));
                            newFacetList.add(new Facet(facet2.getName(), facetList2));
                        }
                    }
                }
            }
        }
        topic.setFacets(newFacetList);
        return topic;
    }

    /**
     * 当score值大于201时的处理方式。
     *
     * @param newFacetList
     * @param topic
     * @param facet
     * @return
     */
    public static List<Facet> FacetProcessLessThan200(List<Facet> newFacetList, Topic topic, Facet facet) {
        List<Facet> newFacetList2 = new ArrayList<>();
        for (int j = 0; j < topic.getFacets().size(); j++) {
            for (int k = 0; k < facet.getNextFacets().size(); k++) {
                if (topic.getFacets().get(j).getName().equals(facet.getNextFacets().get(k).getName())) {
                    OperationToFacet.RemoveFacet(newFacetList, topic.getFacets().get(j).getName());
                    newFacetList2.add(topic.getFacets().get(j));
                }
            }
        }
        newFacetList.add(new Facet(facet.getName(), newFacetList2));
        return newFacetList;
    }

    /**
     * 当score值在2-199之间的处理方式。
     *
     * @param newFacetList
     * @param topic
     * @param facet
     * @return
     */
    public static List<Facet> FacetProcessGreaterThan201(List<Facet> newFacetList, Topic topic, Facet facet) {
        OperationToFacet.RemoveFacet(newFacetList, facet.getName());
        List<Facet> newFacetList2 = new ArrayList<>();
        for (int j = 0; j < topic.getFacets().size(); j++) {
            if (topic.getFacets().get(j).getName().equals(facet.getName())) {
                newFacetList2 = OperationToFacet.MergeFacets(newFacetList2, topic.getFacets().get(j).getNextFacets());
            }
            for (int k = 0; k < facet.getNextFacets().size(); k++) {
                if (topic.getFacets().get(j).getName().equals(facet.getNextFacets().get(k).getName())) {
                    OperationToFacet.RemoveFacet(newFacetList, topic.getFacets().get(j).getName());
                    newFacetList2.add(topic.getFacets().get(j));
                }
            }
        }
        newFacetList.add(new Facet(facet.getName(), newFacetList2));
        return newFacetList;
    }

    /**
     * 用于把hashMap中只出现一次的去掉，然后把hashMap按照分面的上下位关系，存储到Topic里面。
     *
     * @param hashMap
     * @return
     */
    public static Topic PreprocessFacetHyponymy(HashMap<String, Integer> hashMap) {
        HashSet<String> facetName = new HashSet<>();
        List<Facet> emptyList = new ArrayList<>();
        Topic hyponymy = new Topic("hyponymy", emptyList);
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if (val.toString().equals("1")) {
                continue;
            }
            String[] upAndDn = key.toString().split(",");
            if (!facetName.contains(upAndDn[0])) {
                facetName.add(upAndDn[0]);
                Facet newFacet2 = new Facet(upAndDn[1], emptyList);
                List<Facet> facetList1 = new ArrayList<>();
                facetList1.add(newFacet2);
                Facet newFacet1 = new Facet(upAndDn[0], facetList1);
                List<Facet> existFacetList = OperationToFacet.clone(hyponymy.getFacets());
                existFacetList.add(newFacet1);
                hyponymy.setFacets(existFacetList);
            } else {
                Facet newFacet2 = new Facet(upAndDn[1], emptyList);
                List<Facet> existFacetList = OperationToFacet.clone(hyponymy.getFacets());
                List<Facet> existFacetList2 = OperationToFacet.GetFacet(existFacetList, upAndDn[0]).getNextFacets();
                existFacetList2.add(newFacet2);
                OperationToFacet.RemoveFacet(existFacetList, upAndDn[0]);
                Facet newFacet1 = new Facet(upAndDn[0], existFacetList2);
                existFacetList.add(newFacet1);
                hyponymy.setFacets(existFacetList);
            }
        }
        return hyponymy;
    }

    /**
     * 用于把出现过的上下位关系存在hashMap中，格式就是：上位，下位  出现次数。
     *
     * @param InputFilePath
     * @param fileName
     * @return
     */
    public static HashMap<String, Integer> GetFacetHyponymy(String InputFilePath, List<String> fileName) {
        HashMap<String, Integer> hyponymy = new HashMap<>();
        for (String name : fileName) {
            Topic topic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            for (Facet facet1 : topic.getFacets()) {
                for (Facet facet2 : facet1.getNextFacets()) {
                    String facetHyponymy = facet1.getName() + "," + facet2.getName();
                    if (hyponymy.containsKey(facetHyponymy))
                        hyponymy.put(facetHyponymy, hyponymy.get(facetHyponymy) + 1);
                    else
                        hyponymy.put(facetHyponymy, 1);
                }
            }
        }
        return hyponymy;
    }
}
