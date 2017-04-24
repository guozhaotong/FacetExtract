package newStep;
//通过英文中单复数的变换规则，用于把所有的复数单词都转换成单数，并把大写的首字母都转换成小写。

import method.TraveralAndChange;
import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;
import utils.Inflector;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CPluralFilter
{

	public static void main(String[] args) throws IOException
	{
		String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
		String domain = "Data_structure";
		PluralFilter(oriPath,domain);
	}

	static TraveralAndChange traveralAndChange = facet -> PluralToSingle(facet);

	public static void PluralFilter(String oriPath, String domain) {
		File dirfile =new File(oriPath + "3_pluralFilter");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		}
		String InputFilePath = oriPath + "2_UselessFilter\\";
		List<String> fileName = null;
		try {
			fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String name : fileName) {
			System.out.println("Plural Filter\t" + name);
			Topic curtopic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
			traveralAndChange.traversalAllAndChange(curtopic);
			TxtToObject.writeObjToTxt(curtopic, oriPath + "3_pluralFilter\\" + name + ".txt");
		}
		System.out.println("Done.");
    }

	public static Facet PluralToSingle(Facet facet) {
		String curFacet = facet.getName();
		curFacet = curFacet.replaceAll("\\s+", " ");  //用正则表达式替换空格为空
		String newStr = "";
		for (String str : curFacet.split(" ")) {
			if (str.toLowerCase().equals("data"))
				newStr = newStr + " " + "data";
			else if (str.toLowerCase().equals("analysis"))
				newStr = newStr + " " + "analysis";
			else
				newStr = newStr + " " + Inflector.getInstance().singularize(str).toLowerCase();
		}
		facet.setName(newStr.trim());
		return facet;
	}
}
