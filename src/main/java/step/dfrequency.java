package step;
//用于统计一个文件夹下面所有的TXT文件中单词的出现频率
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import java.util.Set;

public class dfrequency
{

	public static void main(String[] args) throws IOException
	{
		String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
		int threshold = 3;							//设置一个阈值，当单词出现次数大于这个阈值时，就被保留下来，否则当做停用词去掉。
		Frequency(oriPath + "3_pluralFilter\\", threshold);
	}
	
	public static void Frequency(String InputFilePath, int threshold) throws IOException
    {
		String[] StopWords = {"and", "a", "an", "the"};
		for (int order = 1; order <= 3; order++)
		{
			String [] fileName = new File(InputFilePath + order + "\\").list();
			String result=null;
			Map<String, Integer> map = new HashMap<String, Integer>(); 
	        for(String name:fileName)
	        {
	    		BufferedReader  BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + order + "\\" + name),"UTF-8"));
	    		String lineString="";  //接收一行
	    		while (   (lineString=BR.readLine()  )!=null  ) 
	    		{
	    			lineString=lineString.replaceAll("\\s+", " ");  //用正则表达式替换一个以上的空格为空
	    			for (String str : lineString.split(" ")) 
	    			{  
	    				if(str.equals("")) continue;
	    	            if (map.containsKey(str)) 
	    	                map.put(str, map.get(str) + 1);  
	    	            else 
	    	                map.put(str, 1);  
	    	        }  
	    		}
	    		BR.close(); //一定要记得关闭输入输出流
	        }
	        map.remove("*****");
	        map.remove("##########");

			String wordFreqtxtCont = "";
			String stopWordtxtCont = "";
			Set set = map.entrySet();
			for(Iterator iter = set.iterator(); iter.hasNext();)
			{
				Map.Entry entry = (Map.Entry)iter.next();
				String key = (String)entry.getKey();
			    Integer value = (Integer)entry.getValue();
			    wordFreqtxtCont = wordFreqtxtCont + key +" :" + value + "\n";
			    //只出现了一次的词语，认为是停用词。
			    if(value <=threshold)	stopWordtxtCont = stopWordtxtCont + key + "\n";
			}
			//选取前10个频率最高的词语
	        for (int cycle = 0; cycle < 10; cycle++)
			{
				int max = 0;  
				for (Entry<String, Integer> entry : map.entrySet()) 
				{  
		            if (entry.getValue() > max) 
		            {  
		                result = entry.getKey();  
		                max = entry.getValue();
		            }  
			    }  
				System.out.println(result+"出现了"+max+"次");
				wordFreqtxtCont = wordFreqtxtCont + result+"出现了"+max+"次\n";
				
				map.remove(result);
			}
	        for (int i = 0; i < StopWords.length; i++)
			{
	        	stopWordtxtCont = stopWordtxtCont + StopWords[i] + "\n";
			}
	        FileUtils.write(new File(InputFilePath + "stopWord" + order + ".txt"), stopWordtxtCont.trim());
	        FileUtils.write(new File(InputFilePath + "wordFreq" + order + ".txt"), wordFreqtxtCont.trim());
			System.out.println("order" + order + " done.\nwait, calculating...\n");
		}
		System.out.println("All done.");
    }
}