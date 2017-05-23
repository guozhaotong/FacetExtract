package method;
//用来找到一个节点兄弟节点的分面。

import model.AllHyponymy;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class brotherFacets {
	public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
	public static String InputFilePath = oriPath + "4_topicNameFilter\\";
	public static String domain = "Data_structure";
	
	public static void main(String[] args) {
//		System.out.println(facetsOfOneNode("Binary_tree"));
//		allBrotherFacet();
		ListBrotherFacet();
		System.out.println("done.");
	}

	public static void ListBrotherFacet() {
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
			System.out.println("Find facets of brother topics\t" + name);
			int layer = FindRelationship.findLayer(upLocation, dnLocation, name, domain);
			String cont = layer + "\nname:" + name + "\n\nfacets:\n";
			ArrayList<String> brotherTopicName = FindRelationship.findBrother(upLocation, dnLocation, name);
			for (String brother : brotherTopicName) {
				Topic b = TxtToObject.SaveTxtToObj(InputFilePath + brother + ".txt");
				List<Facet> facetList = b.getFacets();
				OperationToFacet.RemoveFacet(facetList, "definition");
				OperationToFacet.RemoveFacet(facetList, "example");
				OperationToFacet.RemoveFacet(facetList, "property");
				OperationToFacet.RemoveFacet(facetList, "application");
				b.setFacets(facetList);
				cont = cont + b.toString() + "\n";
			}
			try {
				FileUtils.write(new File(oriPath + "\\complementation\\Brothers\\" + name + ".txt"), cont, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void allBrotherFacet() {
		File dirfile =new File(oriPath + "complementation\\brother");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		    dirfile .mkdir();
		String [] fileName = new File(InputFilePath).list();
//		System.out.println(fileName);
		for(String name : fileName)
		{
			name = name.replaceAll(".txt", "");
			String cont = name + "(" + FindRelationship.getRelation(name, domain, oriPath).getLayer() + ")"
					+ "(" + FindRelationship.getRelation(name, domain, oriPath).getDisToLeaf() + "):\n"
					+ facetsOfOneNode(name) + "\n";
			System.out.println(name);
			ArrayList<String> brother = new ArrayList<>();
			brother = FindRelationship.getRelation(name, domain, oriPath).getBrotherNodes();
			String allParentNode = "";
			HashMap<String,Integer> brotherFacet = new HashMap<>();
			for(String parentNode : brother)
			{
				cont = cont + parentNode + "(" + FindRelationship.getRelation(parentNode, domain, oriPath).getLayer() + "):\n"
					 + facetsOfOneNode(parentNode) + "\n";
				allParentNode = allParentNode + facetsOfOneNode(parentNode).replaceAll("\t", "");
			}
			for(String f : allParentNode.split("\n"))
			{
				if (brotherFacet.containsKey(f)) 
					brotherFacet.put(f, brotherFacet.get(f) + 1);  
	            else 
	            	brotherFacet.put(f, 1);
			}
			cont = cont + "all[" + brotherFacet.size() + "]:\n" + sortMap(brotherFacet).replaceAll("=", "\t");
			try {
				FileUtils.write(new File(oriPath + "complementation\\brother\\" + name + ".txt"), cont, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static HashMap<String, Integer> getTopicsFacets(ArrayList<String> brother) {
		HashMap<String,Integer> brotherFacet = new HashMap<>();
		String allParentNode = "";
		for(String parentNode : brother)
			allParentNode = allParentNode + facetsOfOneNode(parentNode).replaceAll("\t", "");
		for(String f : allParentNode.split("\n"))
		{
			if (brotherFacet.containsKey(f)) 
				brotherFacet.put(f, brotherFacet.get(f) + 1);  
            else 
            	brotherFacet.put(f, 1);
		}
		return brotherFacet;
	}
	
	public static String facetsOfOneNode(String node) {
		String content = "";
			try {
				@SuppressWarnings("deprecation")
				String fString = FileUtils.readFileToString(new File(InputFilePath + node + ".txt"));
				String[] everyFacet = fString.split("\n");
				for(String string : everyFacet)
				{
					string = string.replaceAll("\\*\\*\\*\\*\\*", "");
					string = string.replaceAll("##########", "");
					content = content + "\t" + string.trim() + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		return content;
	}

	public static String sortMap(HashMap<String, Integer> map) {
		List<Map.Entry<String, Integer>> list=new ArrayList<Map.Entry<String, Integer>>(map.entrySet());//!!!  
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){//按value值比较从大到小  
                @Override  
                public int compare(Entry<String, Integer> o1,Entry<String, Integer> o2) {                
                    return o2.getValue()-o1.getValue();  
                }});  
         
         String result = list.toString();
         result = result.replaceAll("\\[", "").replaceAll("]", "").replaceAll(", ", "\n");
		return result;
	}
	
}
