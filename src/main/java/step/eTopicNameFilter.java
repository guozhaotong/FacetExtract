package step;
//把主题词语去掉，比如有一个主题叫array，就把分面中出现的所有array都去掉。

import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

public class eTopicNameFilter
{
	public static void main(String[] args) throws IOException, BiffException
	{
        String oriPath = "M:\\Data mining data set\\Content\\";
		TopicNameFilter(oriPath);
	}
	
	@SuppressWarnings("deprecation")
	public static void TopicNameFilter(String oriPath) throws IOException, BiffException
    {
        File dirfile =new File(oriPath + "5_topicNameFilter");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		}
//		for(int order = 1; order <= 3; order++)
//		{
//			//创建用于存储分面的1  2  3 文件夹
//			File file =new File(oriPath + "5_topicNameFilter\\" + order);
//			if  (!file .exists()  && !file .isDirectory())
//			{
//			    file.mkdir();
//			}
//			String InputFilePath = oriPath + "4_stopWordFilter\\" + order + "\\";
//			String OutputFilePath = oriPath + "5_topicNameFilter\\" + order + "\\";
//			String topicWordPath = "M:\\Data mining data set\\topic.txt";
//			String[] topicWords = FileUtils.readFileToString(new File(topicWordPath)).toLowerCase().split("\n");
////			String[] topicWords = new String[2000];
////			Workbook wb=Workbook.getWorkbook(new File(topicWordPath));
////			Sheet sheet = wb.getSheet(0); //get sheet(0)
////			int i=0;
////			for(i=0; i<sheet.getRows(); i++)
////			{
////				topicWords[i] = sheet.getCell(0,i).getContents();
////			}
////			topicWords[i++] = "terminology";
////			topicWords[i++] = "improvemants";
//			String [] fileName = new File(InputFilePath).list();
//	        for(String name:fileName)
//	        {
//	        	System.out.println("Topic name filter\t" + order + "\t" + name);
//	        	BufferedReader BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
//	    		String lineString="";  //接收一行
//	    		String layer1 = "";
//	    		String layer2 = "";
//	    		int facet1 = 1;
//	    		int facet2 = 1;
//	    		int facet3 = 1;
//	    		String txtCont = "";
//	    		while (   (lineString=BR.readLine()  )!=null )
//	    		{
//	    			lineString = lineString.trim();
//	    			if(!lineString.contains("*****") && !lineString.contains("##########"))
//	    			{
//	    				facet1 = 1;
//	    				facet2 = 1;
//	    				facet3 = 1;
//	    				layer1 = lineString;
//	    			}
//	    			else if(lineString.contains("*****") && facet1 == 1)
//	    				layer2 = lineString;
//	    			else if(lineString.contains("*****") && facet1 == 0)
//	    				continue;
//	    			else if (lineString.contains("##########") && (facet1 == 0 || facet2 == 0))
//	    				continue;
//	    			if(lineString.contains("*****") && lineString.replaceAll("\\*\\*\\*\\*\\*", "").trim().equals(layer1))
//	    				continue;
//	    			else if(lineString.contains("##########") && lineString.replaceAll("##########", "").trim().equals(layer2))
//	    				continue;
//	    			//进行停用词的消除
//	    			for (String stanfordCoreNLP : topicWords)
//					{
//	    				lineString = lineString.replaceAll(stanfordCoreNLP, "");
//	    				if(lineString.trim().equals(""))
//	    					facet1 = 0;
//	    				else if(lineString.trim().equals("*****"))
//	    					facet2 = 0;
//	    				else if(lineString.trim().equals("##########"))
//	    					facet3 = 0;
//					}
//	    			if(facet1 == 1 && facet2 == 1 && facet3 == 1)
//	    				txtCont = txtCont + lineString.trim() + "\n";
//	    		}
//	    		FileUtils.write(new File(OutputFilePath + name), txtCont.trim());
//	    		BR.close(); //一定记得关闭文件
//	        }
//		}
        System.out.println("Done.");
    }
}
