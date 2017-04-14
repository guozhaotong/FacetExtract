package newStep;
//通过英文中单复数的变换规则，用于把所有的复数单词都转换成单数，并把大写的首字母都转换成小写。
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.FileUtils;

import utils.Inflector;

public class cPluralFilter
{
	public static void main(String[] args) throws IOException
	{
		String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
		String domain = "Data_structure";
		PluralFilter(oriPath,domain);
	}
	
	public static void PluralFilter(String oriPath, String domain) throws IOException
    {
		File dirfile =new File(oriPath + "3_pluralFilter");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		String InputFilePath = oriPath + "1_origin\\";
		List<String> fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"),"utf-8");
        for(String name:fileName)
        {
        	
        }
//		for(int order = 1; order <= 3; order++)
//		{
//			//创建用于存储分面的1  2  3 文件夹
//			File file =new File(oriPath + "3_pluralFilter\\" + order);    
//			if  (!file .exists()  && !file .isDirectory())      
//			{       
//			    file .mkdir();    
//			} 
//			String InputFilePath = oriPath + "2_UselessFilter\\" + order + "\\";
//			String [] fileName = new File(InputFilePath).list();
//	        for(String name:fileName)
//	        {
//	        	System.out.println("Plural filter\t" + order + "\t" + name);
//	    		BufferedReader  BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
//	    		String lineString="";  //接收一行
//	    		String txtCont = "";
//	    		while (   (lineString=BR.readLine()  )!=null  ) 
//	    		{
//	    			lineString=lineString.replaceAll("\\s+", " ");  //用正则表达式替换空格为空
//	    			String newStr = "";
//	    			for (String str : lineString.split(" ")) {  
//	    				if(str.toLowerCase().equals("data"))
//	    					newStr  = newStr + " " + "data";
//	    				else if (str.toLowerCase().equals("analysis"))
//	    					newStr  = newStr + " " + "analysis";
//	    				else
//	    					newStr  = newStr + " " + Inflector.getInstance().singularize(str).toLowerCase();
//	    	        }  
//	    			txtCont = txtCont + newStr.trim() + "\n";
//	    		}
//	    		FileUtils.write(new File(oriPath + "3_pluralFilter\\" + order + "\\" + name), txtCont.trim());
//	    		BR.close(); //关闭文件
//	        }
//		}
		System.out.println("Done.");
    }
}
