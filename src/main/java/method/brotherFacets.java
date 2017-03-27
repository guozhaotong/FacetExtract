package method;
//用来找到一个节点的父节点的分面，子节点的分面，和兄弟节点的分面。
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class brotherFacets {
	public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
	public static String InputFilePath = oriPath + "7_giveInstinctiveFacets\\3\\";
	
	public static void main(String[] args) {
//		System.out.println(facetsOfOneNode("Binary_tree"));
		allBrotherFacet("Data_structure");
		System.out.println("done.");
	}
	
	@SuppressWarnings("deprecation")
	public static void allBrotherFacet(String root) {
		File dirfile =new File(oriPath + "complementation\\brother");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{
		    dirfile .mkdir();
		}
		String [] fileName = new File(InputFilePath).list();
//		System.out.println(fileName);
		for(String name : fileName)
		{
			name = name.replaceAll(".txt", "");
			String cont = name + "(" + FindRelationship.getRelation(name, root).getLayer() + ")"
					+ "(" + FindRelationship.getRelation(name, root).getDisToLeaf() + "):\n"
					+ facetsOfOneNode(name) + "\n";
			System.out.println(name);
			ArrayList<String> brother = new ArrayList<>();
			brother = FindRelationship.getRelation(name,root).getBrotherNodes();
			String allParentNode = "";
			@SuppressWarnings({ "unchecked", "rawtypes" })
			HashMap<String,Integer> brotherFacet = new HashMap();
			for(String parentNode : brother)
			{
				cont = cont + parentNode + "(" + FindRelationship.getRelation(parentNode, root).getLayer() + "):\n"
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
				FileUtils.write(new File(oriPath + "complementation\\brother\\" + name + ".txt"), cont);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
