package method;

import model.Topic;

public class test {

	public static void main(String[] args) {
//		Topic dataStructure = TxtToObject.SaveTxtToObj("M:\\我是研究生\\任务\\分面树的生成\\Facet\\5_giveInstinctiveFacets\\" +
//				"Data_structure.txt");
//		System.out.println(dataStructure.toString());
//		Topic Tree = TxtToObject.SaveTxtToObj("M:\\我是研究生\\任务\\分面树的生成\\Facet\\5_giveInstinctiveFacets\\" +
//				"Tree_(data_structure).txt");
//		System.out.println(Tree.toString());
//		AllHyponymy allHyponymy = GetHyponymy.GetHyponymyFromExl("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" +
//				"otherFiles\\Data_structure上下位.xls");
//		ArrayList<String> upLocation = allHyponymy.getUpLocation();
//		ArrayList<String> dnLocation = allHyponymy.getDnLocation();
//		ArrayList<String> parents = FindRelationship.findParent(upLocation,dnLocation,"AVL_tree");
//		System.out.println(parents);
		Topic Tree = TxtToObject.SaveTxtToObj("M:\\我是研究生\\任务\\分面树的生成\\Facet\\5_giveInstinctiveFacets\\" +
				"Associative_array.txt");
//		System.out.println(Tree.toString());
		Tree = OperationToTopic.removeEmptyFacet(Tree);
		Tree = OperationToTopic.Deduplication(Tree);
		System.out.println(Tree.toString());
	}


}
