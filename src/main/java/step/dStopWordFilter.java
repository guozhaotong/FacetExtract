package step;
//用于消除停用词，并把空行给消除掉，把一级标题不存在的二级标题也消除掉
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.FileUtils;

public class dStopWordFilter
{
	public static void main(String[] args) throws IOException
	{
		String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
		StopWordFilter(oriPath);
	}
	
	public static void StopWordFilter(String oriPath) throws IOException
    {
		File dirfile =new File(oriPath + "4_stopWordFilter");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		String[] stopword = {"common","note"};
		for(int order = 1; order <= 3; order++)
		{
			//创建用于存储分面的1  2  3 文件夹
			File file =new File(oriPath + "4_stopWordFilter\\" + order);    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdir();    
			} 
			String InputFilePath = oriPath + "3_pluralFilter\\" + order + "\\";
			String OutputFilePath = oriPath + "4_stopWordFilter\\" + order + "\\";
			String stopWordPath = oriPath + "3_pluralFilter\\stopWord" + order + ".txt";
			String[] stopWords = new String[2000];
			int stopWordNum = 0;
			BufferedReader  BR=new BufferedReader(new InputStreamReader(new FileInputStream(stopWordPath),"UTF-8"));
			String lineString="";
			while (   (lineString=BR.readLine()) != null  ) 
    		{
				if(lineString.equals("breadth-first") || lineString.equals("depth-first"))
					continue;
    			stopWords[stopWordNum] = lineString;
    			stopWordNum++;
    		}
    		BR.close(); 
    		for (int i = 0; i < stopword.length; i++)
			{
    			stopWords[stopWordNum] = stopword[i];
    			stopWordNum++;
			}
    		String [] fileName = new File(InputFilePath).list();
	        for(String name:fileName)
	        {
	        	System.out.println("Stopword filter\t" + order + "\t" + name);
	    		BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
	    		lineString="";  //接收一行
	    		int cycle1 = 1;
	    		int cycle2 = 1;
	    		String txtCont = "";
	    		while (   (lineString=BR.readLine()  )!=null ) 
	    		{
	    			lineString = " " + lineString + " ";
	    			//进行停用词的消除
	    			for (int No = 0; No < stopWordNum; No++)
					{
	    				lineString = lineString.replaceAll(" " + stopWords[No] + " ", " ");
					}
	    			//空行不保存
	    			if (lineString.trim().equals(""))
	    			{
	    				cycle1 = 0;
	    				continue;
	    			}
	    			if (lineString.trim().length() > 4)
	    			{
		    			if (lineString.trim().substring(0, 5).equals("*****")) 
		    			{
		    				if (cycle1 ==0)
		    					continue;
		    				else if (lineString.trim().equals("*****"))
		    				{
		    					cycle2 = 0;
		    					continue;
		    				}
						}
	    			}
	    			if (lineString.trim().length() > 9)
	    			{
		    			if (lineString.trim().substring(0, 10).equals("##########")) 
		    			{
							if (cycle1 == 0 || cycle2 == 0)
								continue;
							else if (lineString.trim().equals("##########")) 
							{
								continue;
							}
						}
	    			}
	    			cycle1 = 1;
	    			cycle2 = 1;
	    			txtCont = txtCont + lineString.trim() + "\n";
	    		}
	    		FileUtils.write(new File(OutputFilePath + name), txtCont.trim());
	    		BR.close(); //一定记得关闭文件
	        }
    		System.out.println("Done.");
		}
    }
}
