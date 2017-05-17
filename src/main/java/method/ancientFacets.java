package method;
//用来找到一个节点的祖先节点的所有分面。

import model.AllHyponymy;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ancientFacets {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String InputFilePath = oriPath + "7_giveInstinctiveFacets\\";

    public static void main(String[] args) {
//		System.out.println(facetsOfOneNode("Binary_tree"));
//		allAncientFacet("Data_mining");		//这里输入总的根节点。
        ParentFacet("Data_structure");
        String a = "";
        a.trim();
        System.out.println("done.");
    }

    public static void allAncientFacet(String root) {
        File dirfile = new File(oriPath + "Content\\complementation\\ancient");
        if (!dirfile.exists() && !dirfile.isDirectory()) {
            dirfile.mkdir();
        }
        dirfile = new File(oriPath + "Content\\complementation\\ancient\\inherit\\");
        if (!dirfile.exists() && !dirfile.isDirectory()) {
            dirfile.mkdir();
        }
        dirfile = new File(oriPath + "Content\\complementation\\ancient\\statistics\\");
        if (!dirfile.exists() && !dirfile.isDirectory()) {
            dirfile.mkdir();
        }
        String[] fileName = new File(InputFilePath + "3\\").list();
        for (String name : fileName) {
            name = name.replaceAll(".txt", "");
            String cont = name + "(" + FindRelationship.getRelation(name, root, oriPath).getLayer() + "):\n"
                    + facetsOfOneNode(name) + "\n";
            System.out.println(name);
            ArrayList<String> parent = new ArrayList<>();
            parent = FindRelationship.getRelation(name, root, oriPath).getParentNodes();
            String allParentNode = "";
            HashSet<String> parentFacet = new HashSet<>();
            for (String parentNode : parent) {
                cont = cont + parentNode + "(" + FindRelationship.getRelation(parentNode, root, oriPath).getLayer() + "):\n"
                        + facetsOfOneNode(parentNode) + "\n";
                allParentNode = allParentNode + facetsOfOneNode(parentNode).replaceAll("\t", "");
            }
            cont = cont + "all";
            String allf = "";
            for (String f : allParentNode.split("\n")) {
                if (parentFacet.contains(f)) {
                    continue;
                }
                parentFacet.add(f);
                allf = allf + f + "\n";
            }
            cont = cont + "[" + parentFacet.size() + "]:\n" + allf;
            try {
                FileUtils.write(new File(oriPath + "Content\\complementation\\ancient\\statistics\\" + name + ".txt"), cont, "utf-8");
                FileUtils.write(new File(oriPath + "Content\\complementation\\ancient\\inherit\\" + name + ".txt"), allf, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String facetsOfOneNode(String node) {
        String content = "";
        try {
            @SuppressWarnings("deprecation")
            String fString = FileUtils.readFileToString(new File(InputFilePath + "3\\" + node + ".txt"));
            String[] everyFacet = fString.split("\n");
            for (String string : everyFacet) {
                string = string.replaceAll("\\*\\*\\*\\*\\*", "");
                string = string.replaceAll("##########", "");
                content = content + "\t" + string.trim() + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void ParentFacet(String domain) {
        File dirfile = new File(oriPath + "Content\\complementation\\parent");
        if (!dirfile.exists() && !dirfile.isDirectory()) {
            dirfile.mkdir();
        }
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
            System.out.println("Find parent facet\t" + name);
            ArrayList<String> parentList = FindRelationship.findDirectParent(upLocation, dnLocation, name);
            String cont = FindRelationship.findLayer(upLocation, dnLocation, name, domain) + "\n";
            for (String parentName : parentList) {
                Topic p = TxtToObject.SaveTxtToObj(oriPath + "4_topicNameFilter\\" + parentName + ".txt");
                List<Facet> facetList = p.getFacets();
                facetList = RemoveInstinctiveFacets(facetList);
                p.setFacets(facetList);
                cont = cont + p.toString() + "\n\n";
            }
            try {
                FileUtils.write(new File(oriPath + "complementation\\parent\\" + name + ".txt"), cont, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Facet> GetParentfacet(ArrayList<String> parentNode, String filePath) {
        List<Facet> parentFacetList = new ArrayList<>();
        if (parentNode.size() == 1) {
            parentFacetList = TxtToObject.SaveTxtToObj(filePath + parentNode.get(0) + ".txt").getFacets();
            parentFacetList = RemoveInstinctiveFacets(parentFacetList);
        }
        if (parentNode.size() > 1) {
            for (String parentName : parentNode) {
                parentFacetList = OperationToFacet.MergeFacets(parentFacetList, TxtToObject.SaveTxtToObj(oriPath +
                        "4_topicNameFilter\\" + parentName + ".txt").getFacets());
                parentFacetList = RemoveInstinctiveFacets(parentFacetList);
            }
        }
        return parentFacetList;
    }

    public static List<Facet> RemoveInstinctiveFacets(List<Facet> facetList) {
        facetList = OperationToFacet.RemoveFacet(facetList, "definition");
        facetList = OperationToFacet.RemoveFacet(facetList, "property");
        facetList = OperationToFacet.RemoveFacet(facetList, "application");
        facetList = OperationToFacet.RemoveFacet(facetList, "example");
        return facetList;
    }
}
