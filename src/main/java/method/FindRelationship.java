package method;
//用来找到一个节点的祖先节点、孩子节点、兄弟节点。
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class FindRelationship {

	public static void main(String[] args) {
		oneNode tarNode = getRelation("Binary_tree");
		printNode(tarNode);
	}
	
	public static oneNode getRelation(String node) {
		oneNode topic = new oneNode();
		topic.setNodeName(node);
		Workbook wb;
		ArrayList<String> upLocation = new ArrayList<>();
		ArrayList<String> dnLocation = new ArrayList<>();
		try {//把excel里面的内容存到内存中
			wb = Workbook.getWorkbook(new File("M:\\我是研究生\\任务\\分面树的生成\\Mooc\\上下位关系\\数据结构上下位关系-新构建.xls"));
			Sheet sheet = wb.getSheet(0); //get sheet(0)
			for(int i = 1; i < sheet.getRows(); i++)
			{
				dnLocation.add(sheet.getCell(0,i).getContents());
				upLocation.add(sheet.getCell(1,i).getContents());
			}
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		
		topic.setParentNodes(findParent(upLocation, dnLocation, node));
		topic.setBrotherNodes(findBrother(upLocation, dnLocation, node));
		topic.setChildNodes(findChild(upLocation, dnLocation, node));
		return topic;
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
	public static ArrayList<String> findBrother(ArrayList<String> upLocation, ArrayList<String> dnLocation, String node){
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
	 * @param upLocation 上下位关系中，上位关系那一列
	 * @param dnLocation 上下位关系中，下位关系那一列
	 * @param node 目标节点
	 * @return 空。
	 */
	public static void printNode(oneNode node) {
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
