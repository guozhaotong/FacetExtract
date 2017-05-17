package method;


import model.Facet;
import model.Topic;

import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
//		Topic topic = TxtToObject.SaveTxtToObj("M:\\我是研究生\\任务\\分面树的生成\\Facet\\1_origin\\" +
//				"Graph_(abstract_data_type).txt");
//		System.out.println(topic);
		List<Facet> emptyList = new ArrayList<>();
//		List<Facet> operaList = new ArrayList<>();
//		operaList.add(new Facet("insertion", emptyList));
//		operaList.add(new Facet("deletion", emptyList));
//		operaList.add(new Facet("traversal", emptyList));
//		List<Facet> finalList = topic.getFacets();
		List<Facet> finalList = new ArrayList<>();
//		OperationToFacet.RemoveFacet(finalList,"Operations");
//		OperationToFacet.RemoveFacet(finalList,"See also");
//		OperationToFacet.RemoveFacet(finalList,"References");
//		OperationToFacet.RemoveFacet(finalList,"External links");
//		finalList.add(new Facet("Operation", operaList));
//		finalList.add(new Facet("representation", emptyList));
		finalList.add(new Facet("Definition", emptyList));
		finalList.add(new Facet("Property", emptyList));
		finalList.add(new Facet("Implementation", emptyList));
		finalList.add(new Facet("Example", emptyList));
		Topic topic = new Topic("Data_type", finalList);
//		topic.setFacets(finalList);
		System.out.println(topic);
		TxtToObject.writeObjToTxt(topic, "M:\\我是研究生\\任务\\分面树的生成\\Facet\\1_origin\\Data_type.txt");
	}


}
