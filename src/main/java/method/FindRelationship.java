package method;
//用来找到一个节点的祖先节点、孩子节点、兄弟节点。

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import model.oneNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class FindRelationship {

	public static void main(String[] args) {
        oneNode tarNode = getRelation("Binary_tree", "Data_structure", "M:\\我是研究生\\任务\\分面树的生成\\Facet\\");
        printNode(tarNode);
	}
	
	/**
	 * 用于获得一个节点的兄弟，祖先，子节点，并存在一个对象中
	 * @param node
	 * @return oneNode类
	 */
	public static oneNode getRelation(String node, String root, String oriPath) {
		Workbook wb;
		ArrayList<String> upLocation = new ArrayList<>();
		ArrayList<String> dnLocation = new ArrayList<>();
		try {//把excel里面的内容存到内存中
            wb = Workbook.getWorkbook(new File(oriPath + "otherFiles\\" + root + "上下位.xls"));
            Sheet sheet = wb.getSheet(0); //get sheet(0)
			for(int i = 1; i < sheet.getRows(); i++)
			{
				dnLocation.add(sheet.getCell(0,i).getContents());
				upLocation.add(sheet.getCell(1,i).getContents());
			}
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}

		oneNode topic = new oneNode();
		if(!NodeExist(upLocation, dnLocation, node))
		{
			System.err.println("所输入的节点不存在。");
			return topic;
		}
		topic.setNodeName(node);
		if (NodeExist(upLocation, dnLocation, root))
			topic.setLayer(findLayer(upLocation, dnLocation, node, root));
		else {
			System.err.println("所输入的根节点不存在。");
			return topic;
		}
		topic.setDisToLeaf(findDistToLeaf(upLocation, dnLocation, node));
		topic.setParentNodes(findParent(upLocation, dnLocation, node));
		topic.setBrotherNodes(findBrother(upLocation, dnLocation, node));
		topic.setChildNodes(findChild(upLocation, dnLocation, node));
		return topic;
	}

	/**
	 * 用于检测输入节点是否存在。
	 * @param upLocation 上下位关系中，上位关系那一列 
	 * @param dnLocation 上下位关系中，下位关系那一列
	 * @param node 目标节点
	 * @return 是否存在。
	 */
	public static boolean NodeExist(ArrayList<String> upLocation, ArrayList<String> dnLocation, String node) {
		for(String string : upLocation)
			if (string.equals(node))
				return true;
		for(String string : dnLocation)
			if (string.equals(node))
				return true;
		return false;
	}
	
	
	/**
     * 用于找到一个节点在领域树中距离叶子节点还有几层
     * @param upLocation 上下位关系中，上位关系那一列
	 * @param dnLocation 上下位关系中，下位关系那一列
	 * @param node 目标节点
	 * @return 层数。
	 */
	@SuppressWarnings("unchecked")
	public static int findDistToLeaf(ArrayList<String> upLocation, ArrayList<String> dnLocation, String node){
		int distToLeaf = 0;
		if(IsLeaf(upLocation, node))
			return distToLeaf;
		ArrayList<String> childNodePre = new ArrayList<>();
		HashSet<String> findedNode = new HashSet<>();
		findedNode.add(node);
		childNodePre.add(node);
		while(true)
		{
			ArrayList<String> childNode = new ArrayList<>();
			for(String string : childNodePre)
			{
				if(IsLeaf(upLocation, string))
					return distToLeaf;
				for(int i = 0; i < upLocation.size(); i++)
				{
					if(upLocation.get(i).equals(string))
					{
						if(!findedNode.contains(dnLocation.get(i)))
							childNode.add(dnLocation.get(i));
					}
				}
			}
			childNodePre = (ArrayList<String>) childNode.clone();
			childNode = new ArrayList<>();
			distToLeaf++;
		}
//		return distToLeaf;
	}
	
	/**
	 * 用于判断一个节点是不是叶子节点
	 * @param upLocation 上下位关系中，上位关系那一列
	 * @param node 目标节点
	 * @return 是否是叶子节点。
	 */
	public static boolean IsLeaf(ArrayList<String> upLocation, String node) {
		for(String string : upLocation)
			if (string.equals(node))
				return false;
		return true;
	}
	
	/**
	 * 用于找到一个节点在领域树中处于第几层
	 * @param upLocation 上下位关系中，上位关系那一列 
	 * @param dnLocation 上下位关系中，下位关系那一列
	 * @param node 目标节点
	 * @param rootNode 根节点
	 * @return 层数。
	 */
	@SuppressWarnings("unchecked")
	public static int findLayer(ArrayList<String> upLocation, ArrayList<String> dnLocation, String node, String rootNode){
		int layer = 0;
		if(node.equals(rootNode)) return layer;
		boolean findroot = false;
		ArrayList<String> nodePre = new ArrayList<>();
		ArrayList<String> nodeNow = new ArrayList<>();
		HashSet<String> findedNode = new HashSet<>();
		nodePre.add(node);
		findedNode.add(node);
		while(!findroot)
		{
			layer++;
			for(int i = 0; i < upLocation.size(); i++)
			{
				for(int j = 0; j < nodePre.size(); j++)
				{
					if (dnLocation.get(i).equals(nodePre.get(j))) {
						if(findedNode.contains(upLocation.get(i))){
							continue;
						}
						if(upLocation.get(i).equals(rootNode)) {
							findroot = true;
							break;
							}
						else {
							nodeNow.add(upLocation.get(i));
							findedNode.add(upLocation.get(i));
						}
					}
				}
				if(findroot) {
					break;
				}
			}
			nodePre = (ArrayList<String>) nodeNow.clone();
			nodeNow = new ArrayList<>();
		}
		return layer;
	}
	
	/**
	 * 用于找到一个特定节点node所有的祖先节点
	 * @param upLocation 上下位关系中，上位关系那一列 
	 * @param dnLocation 上下位关系中，下位关系那一列
	 * @param node 目标节点
	 * @return 所有祖先节点。
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<String> findParent(ArrayList<String> upLocation, ArrayList<String> dnLocation, String node){
		ArrayList<String> newNode = new ArrayList<>();//下一次要遍历的节点
		HashSet<String> findedNode = new HashSet<>();
		findedNode.add(node);
		ArrayList<String> parent = new ArrayList<>();
		ArrayList<String> directParent = new ArrayList<>();//亲父节点
		for(int i = 0; i < upLocation.size(); i++)
		{
			if(dnLocation.get(i).equals(node))
			{
				if(findedNode.contains(upLocation.get(i)))
					continue;
				findedNode.add(upLocation.get(i));
				parent.add(upLocation.get(i));
				newNode.add(upLocation.get(i));
				directParent.add(upLocation.get(i));
			}
		}
		while(newNode.size() != 0)
		{
			ArrayList<String> newnewNode = new ArrayList<>();
			for(int j = 0; j < newNode.size(); j++)
			{
				for(int i = 0; i < upLocation.size(); i++)
				{
					if(dnLocation.get(i).equals(newNode.get(j)))
					{
						if(findedNode.contains(upLocation.get(i)))
							continue;
						findedNode.add(upLocation.get(i));
						parent.add(upLocation.get(i));
						newnewNode.add(upLocation.get(i));
					}
				}
			}
			newNode = (ArrayList<String>) newnewNode.clone();
		}
		return parent;
	}
	
	/**
	 * 用于找到一个特定节点node所有的孩子节点（知道叶子节点）
	 * @param upLocation 上下位关系中，上位关系那一列
	 * @param dnLocation 上下位关系中，下位关系那一列
	 * @param node 目标节点
	 * @return 所有孩子节点。
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<String> findChild(ArrayList<String> upLocation, ArrayList<String> dnLocation, String node){
		ArrayList<String> newNode = new ArrayList<>();//下一次要遍历的节点
		HashSet<String> findedNode = new HashSet<>();
		findedNode.add(node);
		ArrayList<String> children = new ArrayList<>();
		for(int i = 0; i < upLocation.size(); i++)
		{
			if(upLocation.get(i).equals(node))
			{
				if(findedNode.contains(dnLocation.get(i)))
					continue;
				findedNode.add(dnLocation.get(i));
				children.add(dnLocation.get(i));
				newNode.add(dnLocation.get(i));
			}
		}
		while(newNode.size() != 0)
		{
			ArrayList<String> newnewNode = new ArrayList<>();
			for(int j = 0; j < newNode.size(); j++)
			{
				for(int i = 0; i < upLocation.size(); i++)
				{
					if(upLocation.get(i).equals(newNode.get(j)))
					{
						if(findedNode.contains(dnLocation.get(i)))
							continue;
						findedNode.add(dnLocation.get(i));
						children.add(dnLocation.get(i));
						newnewNode.add(dnLocation.get(i));
					}
				}
			}
			newNode = (ArrayList<String>) newnewNode.clone();
		}
		return children;
	}
	
	/**
	 * 用于找到一个特定节点node所有的兄弟节点
	 * @param upLocation 上下位关系中，上位关系那一列
	 * @param dnLocation 上下位关系中，下位关系那一列
	 * @param node 目标节点
	 * @return 所有兄弟节点。
	 */
	public static ArrayList<String> findBrother(ArrayList<String> upLocation, ArrayList<String> dnLocation, 
			String node){
		ArrayList<String> directParent = new ArrayList<>();//亲父节点
		for(int i = 0; i < upLocation.size(); i++)
		{
			if(dnLocation.get(i).equals(node))
			{
				directParent.add(upLocation.get(i));
			}
		}
		ArrayList<String> brothers = new ArrayList<>();
		for(int j = 0; j < directParent.size(); j++)
			for(int i = 0; i < upLocation.size(); i++)
			{
				if(upLocation.get(i).equals(directParent.get(j)) && !dnLocation.get(i).equals(node))
					brothers.add(dnLocation.get(i));
			}
		return brothers;
	}
	
	/**
	 * 用于输出一个节点所有的兄弟节点，孩子节点，祖先节点
	 * @param node 目标节点
	 * @return 空。
	 */
	public static void printNode(oneNode node) {
		int lay = node.getLayer();
		int dis = node.getDisToLeaf();
		System.out.println("该节点在领域树中处于第" + lay + "层，距离叶子节点还有" + dis + "层");
		ArrayList<String> p = node.getParentNodes();
		System.out.println("父节点有：");
		for(String str : p) 
			System.out.println(str);
		ArrayList<String> b = node.getBrotherNodes();
		System.out.println("\n兄弟节点有：");
		for(String str : b)
			System.out.println(str + "\t");
		ArrayList<String> c = node.getChildNodes();
		System.out.println("\n子节点有：");
		for(String str : c)
			System.out.println(str);
	}
}
