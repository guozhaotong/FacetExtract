package utils;
//通过对有道词典进行页面解析，把文件中的单词转换成单数形式。

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

public class PluraltoSingle
{

	public static void main(String[] args) throws IOException
	{
//		String lineString = "Arrays Definitions";
//		String[] eachWord = lineString.split(" ");
//		for (String str : lineString.split(" "))
//		{
//			System.out.println(str);
//		}
		String single = transfer("Data");
		System.out.println(single);
		
		
//		for(int order = 1; order <= 3; order++)
//		{
//			String InputFilePath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\2_UselessFilter\\" + order + "\\";
//			String OutputFilePath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\3_pluralFilter\\" + order + "\\";
//			String [] fileName = getFileName(InputFilePath);
//	        for(String name:fileName)
//	        {
//	        	System.out.println(order + "\t" + name);
//	    		BufferedReader  BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
//	    		String lineString="";  //接收一行
//	    		while (   (lineString=BR.readLine()  )!=null  ) 
//	    		{
//	    			lineString=lineString.replaceAll("\\stanfordCoreNLP+", " ");  //用正则表达式替换空格为空
//	    			String newStr = "";
//	    			for (String str : lineString.split(" ")) {  
//	    				if(!str.equals(""))
//	    					newStr  = newStr + " " + transfer(str);
//	    	        }  
//	    			writetxt(OutputFilePath + name, newStr + "\n");
//	    		}
//	    		BR.close(); //关闭文件
//	        }
//		}
//        System.out.println("Done.");
	}

	public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }
	
	public static void writetxt(String txtPath, String txtCont)
	{
		try
		{
			File f = new File(txtPath);
			if (!f.exists())
			{
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f,true), "UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(txtCont);
			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String transfer(String word) throws IOException
	{
		String newWord = ".";
		try{
			Document doc = Jsoup.connect("http://dict.youdao.com/w/eng/" + word + "/#keyfrom=dict2.index").timeout(100000).get();
			Elements ele = doc.select("#phrsListTab > div.trans-container > ul > li");
			if(ele.size() == 0)
			{
				newWord = word.toLowerCase();
			}
			else{
				String[] words =ele.first().text().split("的复数");
				
				
				if(words.length == 1)
					newWord = word.toLowerCase();
				else{
					words = words[0].split("\\（");
//					if(words[words.length - 1].substring(beginIndex, endIndex))
					newWord = words[words.length - 1];
				}
			}
		}catch(Exception e)
		{
			newWord = word;
		}
		return newWord;
	}
}
