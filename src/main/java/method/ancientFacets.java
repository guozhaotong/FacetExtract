package method;
//用来找到一个节点的祖先节点的所有分面。
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.text.AbstractDocument.Content;

import org.apache.commons.io.FileUtils;

public class ancientFacets {
	public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
	public static String InputFilePath = oriPath + "7_giveInstinctiveFacets\\";
	
	public static void main(String[] args) {
//		System.out.println(facetsOfOneNode("Binary_tree"));
		allAncientFacet("Data_mining");		//这里输入总的根节点。
		System.out.println("done.");
	}
	
	@SuppressWarnings("deprecation")
	public static void allAncientFacet(String root) {
		File dirfile =new File(oriPath + "Content\\complementation\\ancient");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		}
		dirfile =new File(oriPath + "Content\\complementation\\ancient\\inherit\\");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		}
		dirfile =new File(oriPath + "Content\\complementation\\ancient\\statistics\\");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		}
		String [] fileName = new File(InputFilePath + "3\\").list();
		for(String name : fileName)
		{
			name = name.replaceAll(".txt", "");
			String cont = name + "(" + FindRelationship.getRelation(name, root,oriPath).getLayer() + "):\n"
					+ facetsOfOneNode(name) + "\n";
			System.out.println(name);
			ArrayList<String> parent = new ArrayList<>();
			parent = FindRelationship.getRelation(name,root,oriPath).getParentNodes();
			String allParentNode = "";
			HashSet<String> parentFacet = new HashSet<>();
			for(String parentNode : parent)
			{
				cont = cont + parentNode + "(" + FindRelationship.getRelation(parentNode, root,oriPath).getLayer() + "):\n"
					 + facetsOfOneNode(parentNode) + "\n";
				allParentNode = allParentNode + facetsOfOneNode(parentNode).replaceAll("\t", "");
			}
			cont = cont + "all";
			String allf = "";
			for(String f : allParentNode.split("\n"))
			{
				if (parentFacet.contains(f)) {
					continue;
				}
				parentFacet.add(f);
				allf = allf + f + "\n";
			}
			cont = cont + "[" + parentFacet.size() + "]:\n" + allf;
			try {
				FileUtils.write(new File(oriPath + "Content\\complementation\\ancient\\statistics\\" + name + ".txt"), cont);
				FileUtils.write(new File(oriPath + "Content\\complementation\\ancient\\inherit\\" + name + ".txt"), allf);
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

}
