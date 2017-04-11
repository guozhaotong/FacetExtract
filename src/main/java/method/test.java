package method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Collections;  
import java.util.Comparator;  
import java.util.Map.Entry;

import step.iInherit;  

public class test {

	public static void main(String[] args) {
		sureAndCand s = iInherit.InheritFromBrothers("Binary_tree", "M:\\我是研究生\\任务\\分面树的生成\\Facet\\", 
				"Data_structure");
		HashSet<String> sure = s.getSureFacet();
		HashSet<String> cand = s.getCandFacet();
		System.out.println("sure:");
		System.out.println(sure);
		System.out.println("cand:");
		System.out.println(cand);
	}
	

}
