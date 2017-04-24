package method;

import model.Facet;
import model.Topic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/4/18.
 */
public class OperationToTopic {
    public static void main(String[] args) {
        Topic topic = new Topic();
        topic.setName("test_node");
        Facet facet1 = new Facet("operation");
        Facet facet2 = new Facet("insertion");
        Facet facet31 = new Facet("");
//        Facet facet31 = new Facet("leaf node");
        Facet facet32 = new Facet("internal node");
        List<Facet> l3 = new ArrayList<>();
        l3.add(facet31);
        l3.add(facet32);
        facet2.setNextFacets(l3);
        List<Facet> l2 = new ArrayList<>();
        l2.add(facet2);
        facet1.setNextFacets(l2);
        List<Facet> l1 = new ArrayList<>();
        l1.add(facet1);
        topic.setFacets(l1);
        Traversal traversal = facet -> System.out.println(facet.getName());
//        traversal.traversalAllFacets(topic);
//        topic = removeOneceFacet(topic);//应用事例
        topic = removeEmptyFacet(topic);
        traversal.traversalAllFacets(topic);
    }

    /**
     * 用于主题分面的优化。首先去掉空的分面，然后进行去重，最后把只有一个下级分面的去掉。
     *
     * @param topic
     * @return
     */
    public static Topic OptimizeTopic(Topic topic) {
        topic = OperationToTopic.removeEmptyFacet(topic);
        topic = OperationToTopic.Deduplication(topic);
        topic = OperationToTopic.removeOneceFacet(topic);
        return topic;
    }

    /**
     * 用于一个topic中，所有的分面的消重。遍历分面列表。如果当前分面没有出现过，就不做任何操作，如果当前分面出现过了，比如出现了两次operation，
     * 就把两个operation的下位分面merge成一个List，然后赋予operation。
     *
     * @param topic
     * @return
     */
    public static Topic Deduplication(Topic topic) {
        //对一级分面的消重。
        HashSet<String> facetName = new HashSet<>();
        List<Facet> facetList = topic.getFacets();
        List<Facet> newFacetList = new ArrayList<>();
        for (Facet facet : facetList) {
            if (!facetName.contains(facet.getName())) {
                facetName.add(facet.getName());
                newFacetList.add(facet);
            } else {
                List<Facet> list1 = facet.getNextFacets();
                Facet appearedFacet = OperationToFacet.GetFacet(newFacetList, facet.getName());
                List<Facet> list2 = appearedFacet.getNextFacets();
                List<Facet> list3 = OperationToFacet.MergeFacets(list1, list2);
                newFacetList = OperationToFacet.RemoveFacet(newFacetList, facet.getName());
                appearedFacet.setNextFacets(list3);
                newFacetList.add(appearedFacet);
            }
        }
        topic.setFacets(newFacetList);
        //对第二级分面的消重。
        facetList = OperationToFacet.clone(newFacetList);
        newFacetList = new ArrayList<>();
        for (Facet facet1 : facetList) {
            Facet facet = new Facet(facet1.getName());
            HashSet<String> facet2Name = new HashSet<>();
            List<Facet> facet2List = new ArrayList<>();
            for (Facet facet2 : facet1.getNextFacets()) {
                if (!facet2Name.contains(facet2.getName()) && !facetName.contains(facet2.getName())) {
                    facet2Name.add(facet2.getName());
                    facet2List.add(facet2);
                }
            }
            facet.setNextFacets(facet2List);
            newFacetList.add(facet);
        }
        topic.setFacets(newFacetList);
        //对第三级分面的消重
        facetList = OperationToFacet.clone(newFacetList);
        newFacetList = new ArrayList<>();
        for (Facet facet1 : facetList) {
            List<Facet> facet2List = new ArrayList<>();
            for (Facet facet2 : facet1.getNextFacets()) {
                HashSet<String> facet3Name = new HashSet<>();
                List<Facet> facet3List = new ArrayList<>();
                for (Facet facet3 : facet2.getNextFacets()) {
                    if (!facet3Name.contains(facet3.getName()) && !facetName.contains(facet3.getName())) {
                        facet3Name.add(facet3.getName());
                        facet3List.add(facet3);
                    }
                }
                facet2List.add(new Facet(facet2.getName(), facet3List));
            }
            newFacetList.add(new Facet(facet1.getName(), facet2List));
        }
        topic.setFacets(newFacetList);
        return topic;
    }

    /**
     * 把没有内容的分面删除掉。
     *
     * @param topic
     * @return
     */
    public static Topic removeEmptyFacet(Topic topic) {
        if (!topic.getFacets().isEmpty())
            for (int k = 0; k < topic.getFacets().size(); k++) {
                Facet facet = topic.getFacets().get(k);
                if (!facet.getNextFacets().isEmpty()) {
                    for (int j = 0; j < facet.getNextFacets().size(); j++) {
                        Facet secFacet = facet.getNextFacets().get(j);
                        if (!secFacet.getNextFacets().isEmpty()) {
                            for (int i = 0; i < secFacet.getNextFacets().size(); i++) {
                                if (secFacet.getNextFacets().get(i).getName().equals("")) {
                                    List<Facet> newThird = secFacet.getNextFacets();
                                    newThird.remove(i--);
                                    secFacet.setNextFacets(newThird);
                                }
                            }
                        }
                        if (secFacet.getName().equals("")) {
                            List<Facet> newSec = facet.getNextFacets();
                            newSec.remove(j--);
                            facet.setNextFacets(newSec);
                        }
                    }
                }
                if (facet.getName().equals("")) {
                    List<Facet> newFir = topic.getFacets();
                    newFir.remove(k);
                    topic.setFacets(newFir);
                }
            }
        return topic;
    }

    /**
     * 用于把一个主题只有一个下级分面的下级分面去掉。如operation的下级分面只有insertion，insertion又有leaf node 和internal node，
     * 处理后就变成operation直接有leaf node和internal node了。
     *
     * @param topic 主题
     * @return Topic
     */
    public static Topic removeOneceFacet(Topic topic) {
        List<Facet> emptyList = new ArrayList<>();
        if (!topic.getFacets().isEmpty())
            for (Facet facet : topic.getFacets()) {
                if (!facet.getNextFacets().isEmpty())
                    for (Facet secFacet : facet.getNextFacets()) {
                        if (secFacet.getNextFacets().size() == 1) {
                            secFacet.setNextFacets(emptyList);
                        }
                    }
            }

        if (!topic.getFacets().isEmpty())
            for (Facet facet : topic.getFacets()) {
                if (facet.getNextFacets().size() == 1) {
                    facet.setNextFacets(facet.getNextFacets().get(0).getNextFacets());
                }
            }
        return topic;
    }
}
