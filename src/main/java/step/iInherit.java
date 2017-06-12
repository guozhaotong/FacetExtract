package step;

import java.util.HashSet;

public class iInherit {

	public static void main(String[] args) {
		String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
		inherit("Data_structure", oriPath);
	}
	
	@SuppressWarnings("deprecation")
	public static void inherit(String rootTopic,String oriPath) {
//		File dirfile =new File(oriPath + "8_inheritFacets");
//		if  (!dirfile .exists()  && !dirfile .isDirectory())
//		    dirfile .mkdir();
//		String InputFilePath = oriPath + "7_giveInstinctiveFacets\\3\\";
//		String [] fileName = new File(InputFilePath).list();
//		for(String name : fileName)
//		{
//			name = name.replaceAll(".txt", "");
//			System.out.println(name);
//			HashSet<String> facetSelf = oneNodeFacets(InputFilePath + name + ".txt");
//			ArrayList<String> parent = FindRelationship.getRelation(name,rootTopic,oriPath).getParentNodes();
//			HashSet<String> parentFacet = GetArraysFacets(parent, name, oriPath, rootTopic);
//			sureAndCand stanfordCoreNLP = InheritFromBrothers(name, oriPath, rootTopic);
//			HashSet<String> BrotherfacetCand = stanfordCoreNLP.getCandFacet();
//			HashSet<String> BrotherfacetSure = stanfordCoreNLP.getSureFacet();
//			ArrayList<String> children = FindRelationship.getRelation(name, rootTopic, oriPath).getChildNodes();
//			HashSet<String> childrenFacet = GetArraysFacets(children, name, oriPath, rootTopic);
//			HashSet<String> SureFacet = mergeHashSet(facetSelf, parentFacet, BrotherfacetSure);
//			HashSet<String> CandFacet = mergeHashSet(BrotherfacetCand, childrenFacet);
//			String cont = "sure:\n";
//			for(String str : SureFacet)
//				cont = cont + str + "\n";
//			cont = cont + "\ncandidate:\n";
//			for(String str : CandFacet)
//				cont = cont + str + "\n";
//			try {
//				FileUtils.write(new File(oriPath + "8_inheritFacets\\" + name + ".txt"), cont);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
    }
	
	public static HashSet<String> oneNodeFacets(String InputFilePath) {
		HashSet<String> facets = new HashSet<>();
//		try {
//			@SuppressWarnings("deprecation")
//			String fString = FileUtils.readFileToString(new File(InputFilePath));
//			String[] everyFacet = fString.split("\n");
//			for(String string : everyFacet)
//			{
//				string = string.replaceAll("\\*\\*\\*\\*\\*", "");
//				string = string.replaceAll("##########", "");
//				facets.add(string.trim());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        return facets;
	}

//	public static HashSet<String> GetArraysFacets(ArrayList<String> array, String name, String oriPath, String root) {
//
//		String allParentNode = "";
//		HashSet<String> parentFacet = new HashSet<>();
//		for(String parentNode : array)
//		{
//			allParentNode = allParentNode + ancientFacets.facetsOfOneNode(parentNode).replaceAll("\t", "");
//		}
//		String allf = "";
//		for(String f : allParentNode.split("\n"))
//		{
//			if (parentFacet.contains(f)) {
//				continue;
//			}
//			parentFacet.add(f);
//			allf = allf + f + "\n";
//		}
//		return parentFacet;
//	}

//	public static sureAndCand InheritFromBrothers(String name, String oriPath, String root) {
//		sureAndCand facetsOfBrothers = new sureAndCand();
//		oneNode thisNode = FindRelationship.getRelation(name, root, oriPath);
//		thisNode.setNodeName(name);
//		int brotherNum = thisNode.getBrotherNodes().size();
//		int layer = thisNode.getLayer();
//		double thresholdHigh = (0.9 - 0.075 * layer) * brotherNum;
//		double thresholdLow = (0.67 - 0.075 * layer) * brotherNum;
//		HashMap<String, Integer> brotherFacetMap = brotherFacets.getTopicsFacets(thisNode.getBrotherNodes());
//		HashSet<String> sureFacets = new HashSet<>();
//		HashSet<String> candFacets = new HashSet<>();
//		for (String key : brotherFacetMap.keySet())
//		{
//			if(brotherFacetMap.get(key) >= thresholdHigh)
//			{
//				if(!sureFacets.contains(key))
//					sureFacets.add(key);
//			}
//			else if (brotherFacetMap.get(key) < thresholdLow)
//				continue;
//			else if(!candFacets.contains(key))
//				candFacets.add(key);
//		}
//		facetsOfBrothers.setSureFacet(sureFacets);
//		facetsOfBrothers.setCandFacet(candFacets);
//		return facetsOfBrothers;
//	}

	@SafeVarargs
	public static HashSet<String> mergeHashSet(HashSet<String>... list) {
		HashSet<String> mergedHashSet = new HashSet<>();
		for (HashSet<String> hashSet : list) {
			for(String str : hashSet)
				if(!mergedHashSet.contains(str))
					mergedHashSet.add(str);
		}
		return mergedHashSet;
	}
}
