package newStep;

import method.OperationToFacet;
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
        String InputFilePath = oriPath + "5_giveInstinctiveFacets\\";
//        String OutputFilePath = oriPath + "7_facetHyponymyMerge";
//        File dirfile = new File(OutputFilePath);
//        if  (!dirfile .exists()  && !dirfile .isDirectory())
//            dirfile .mkdir();
        List<String> fileName = new ArrayList<>();
        try {
            fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Integer> hyponymyIntegerHashMap = GetFacetHyponymy(InputFilePath, fileName);
        Topic hyponymy = PreprocessFacetHyponymy(hyponymyIntegerHashMap);
        System.out.println(hyponymy.toString());
    }

    public static Topic FacetHyponymyRecognition(Topic topic, List<Facet> facetHyponymy) {

        return topic;
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
