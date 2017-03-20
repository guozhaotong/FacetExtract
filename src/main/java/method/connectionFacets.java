package method;
//用来找到一个节点的父节点的分面，子节点的分面，和兄弟节点的分面。
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;

public class connectionFacets {

	public static void main(String[] args) {
		System.out.println(facetsOfOneNode("Binary_tree","Data_structure"));
	}
	
	@SuppressWarnings("deprecation")
	public static String facetsOfOneNode(String node,String root) {
		ArrayList<String> parentArray = FindRelationship.getRelation(node,root).getParentNodes();
		String fString = "";
		String facets = "\n父节点的分面有：\n";
		HashSet<String> parentSet = new HashSet<>();
		for(String str : parentArray)
		{
			try {
				fString = FileUtils.readFileToString(new File("M:\\我是研究生\\任务\\分面树的生成\\Content\\"
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
		ArrayList<String> brotherArray = FindRelationship.getRelation(node,root).getBrotherNodes();
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
		ArrayList<String> childArray = FindRelationship.getRelation(node,root).getChildNodes();
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
