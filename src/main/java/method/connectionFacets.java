package method;
//用来找到一个节点的父节点的分面，子节点的分面，和兄弟节点的分面。

import model.AllHyponymy;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class connectionFacets {
	public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
	public static String domain = "Data_structure";

	public static void main(String[] args) {
//		System.out.println(facetsOfOneNode("Binary_tree","Data_structure","M:\\Data mining data set\\Content\\"));
		FacetofChild();
	}

	public static void FacetofChild() {
		String InputFilePath = oriPath + "4_topicNameFilter\\";
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
			ArrayList<String> childTopic = FindRelationship.FindDirectChild(allHyponymy, name);
			System.out.println("Find facets of brother topics\t" + name);
			int layer = FindRelationship.findLayer(upLocation, dnLocation, name, domain);
			String cont = layer + "\nname:" + name + "\n\nfacets:\n";
			for (String child : childTopic) {
				Topic b = TxtToObject.SaveTxtToObj(InputFilePath + child + ".txt");
				List<Facet> facetList = b.getFacets();
				OperationToFacet.RemoveFacet(facetList, "definition");
				OperationToFacet.RemoveFacet(facetList, "example");
				OperationToFacet.RemoveFacet(facetList, "property");
				OperationToFacet.RemoveFacet(facetList, "application");
				b.setFacets(facetList);
				cont = cont + b.toString() + "\n";
			}
			try {
				FileUtils.write(new File(oriPath + "\\complementation\\child\\" + name + ".txt"), cont, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("deprecation")
	public static String facetsOfOneNode(String node,String root,String oriPath) {
		ArrayList<String> parentArray = FindRelationship.getRelation(node,root,oriPath).getParentNodes();
		String fString = "";
		String facets = "\n父节点的分面有：\n";
		HashSet<String> parentSet = new HashSet<>();
		for(String str : parentArray)
		{
			try {
				fString = FileUtils.readFileToString(new File("M:\\Data mining data set\\Content\\"
						+ "10_layerFilter\\3\\" + str + ".txt"));
				String[] everyFacet = fString.split("\n");
				for(String string : everyFacet)
				{
					if(parentSet.contains(string))
						continue;
					parentSet.add(string);
					facets = facets + string + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		facets = facets + "\n兄弟节点的分面有：\n";
		ArrayList<String> brotherArray = FindRelationship.getRelation(node,root,oriPath).getBrotherNodes();
		HashSet<String> brotherSet = new HashSet<>();
		for(String str : brotherArray)
		{
			try {
				fString = FileUtils.readFileToString(new File("M:\\我是研究生\\任务\\分面树的生成\\Content\\"
						+ "10_layerFilter\\3\\" + str + ".txt"));
				String[] everyFacet = fString.split("\n");
				for(String string : everyFacet)
				{
					if(brotherSet.contains(string))
						continue;
					brotherSet.add(string);
					facets = facets + string + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		facets = facets + "\n子节点的分面有：\n";
		ArrayList<String> childArray = FindRelationship.getRelation(node,root,oriPath).getChildNodes();
		HashSet<String> childSet = new HashSet<>();
		for(String str : childArray)
		{
			try {
				fString = FileUtils.readFileToString(new File("M:\\我是研究生\\任务\\分面树的生成\\Content\\"
						+ "10_layerFilter\\3\\" + str + ".txt"));
				String[] everyFacet = fString.split("\n");
				for(String string : everyFacet)
				{
					if(childSet.contains(string))
						continue;
					childSet.add(string);
					facets = facets + string + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return facets;
	}

}
