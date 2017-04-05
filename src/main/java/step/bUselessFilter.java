package step;
//用于提取中心词，并把像“See also”这样无用的词语消除掉。
//把逗号，小括号及其括号内内容消除掉。
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.FileUtils;

public class bUselessFilter
{
	public static void main(String[] args) throws IOException
	{
		String oriPath = "M:\\Data mining data set\\Content\\";
		UselessFilter(oriPath);
	}
	
	public static void UselessFilter(String oriPath) throws IOException
    {
		String[] prep = {"of","for","at","in","on","over","with","to","by","about","under","after"};
		String[] UselessWordsBreak = {"see also","references","external links","further reading"};
		String[] UselessWordsContinue = {"overview","other","type","types"};
		File dirfile =new File(oriPath + "2_UselessFilter");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		for(int order = 1; order <= 3; order++)
		{
			//创建用于存储分面的1  2  3 文件夹
			File file =new File(oriPath + "2_UselessFilter\\" + order);    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdir();    
			} 
			String InputFilePath = oriPath + "1_origin\\" + order + "\\";
			String [] fileName = new File(InputFilePath).list();
	        for(String name:fileName)
	        {
	        	System.out.println("Useless filter\t" + order + "\t" + name);
	    		BufferedReader  BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
	    		String lineString="";  //接收一行
	    		int cycle = 1;
	    		int record = 1;
	    		int need = 1;
	    		String txtCont = "";
	    		while (   (lineString=BR.readLine()  )!=null && cycle == 1 ) 
	    		{
	    			lineString = lineString.toLowerCase();
	    			cycle = 1;
	    			record = 1;
	    			//如果前面遇到了overview这样的单词，那么他的二级标题和三级标题都不要了。
	    			if(need == 0 && lineString.length()>4 && lineString.replaceAll(" ", "").substring(0, 5).equals("*****"))
	    				continue;
	    			else if(need == 0 && lineString.length()>4 && lineString.replaceAll(" ", "").substring(0, 5).equals("#####"))
	    				continue;
	    			else need = 1;
	    			//若遇见这样内容的目录，就停止保存，也就是认为，在出现See also等短语之后，就不会再出现有用的分面了。
	    			for (int No = 0; No < UselessWordsBreak.length; No++)
					{
						if(lineString.equals(UselessWordsBreak[No]))
						{cycle = 0;break;}
					}
	    			for (int No = 0; No < UselessWordsContinue.length; No++)
					{
						if((" " + lineString + " ").toLowerCase().contains(" " + UselessWordsContinue[No] + " "))
						{record = 0; break;}
					}
	    			if(cycle == 0)
	    				break;
	    			if(record == 0)
	    			{
	    				need = 0;
	    				continue;
	    			}
	    			lineString = lineString.split("\\(")[0];//有小括号的，都只保留小括号前面的部分。
	    			lineString = lineString.replaceAll(",", "");
	    			//在这里进行中心词的提取，认为of for等介词之前的那个单词是中心词。
	    			for(int prepNo = 0; prepNo < prep.length; prepNo++)
	    			{
	    				if(lineString.toLowerCase().contains(" " + prep[prepNo] + " "))
	    					lineString = lineString.split(" " + prep[prepNo] + " ")[0].trim();
//	    				else if(lineString.contains("*****" + prep[prepNo] + " "))
//	    				{
//	    					lineString = "*****";
//	    					break;
//	    				}
//	    				else if(lineString.contains("##########" + prep[prepNo] + " "))
//	    				{
//	    					lineString = "##########";
//	    					break;
//						}
	    			}
	    			txtCont = txtCont + lineString.trim() + "\n";
	    		}
	    		txtCont = txtCont.replaceAll("\\*\\*\\*\\*\\* ", "*****");
	    		txtCont = txtCont.replaceAll("\\*\\*\\*\\*\\*", "***** ");
    			FileUtils.write(new File(oriPath + "2_UselessFilter\\" + order + "\\" + name), txtCont.trim());
	    		BR.close(); //一定要记得关闭输入输出流
	        }
		}
		System.out.println(" Done.");
    }
}
