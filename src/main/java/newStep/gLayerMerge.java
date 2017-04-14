package newStep;
//根据分面的上下位关系表，识别出现的下位和上位之间的关系，还没有实现出现的两个下位加上上位的。
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class gLayerMerge
{

	public static void main(String[] args)
	{
		String oriPath = "M:\\Data mining data set\\Content\\";
		try
		{
			readExcel(oriPath);
		} catch (BiffException | IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Done.");

	}
	
	@SuppressWarnings("deprecation")
	public static void readExcel(String oriPath) throws BiffException, IOException
	{
		String InputFilePath = oriPath + "6_layerFilter\\";
		Workbook wb = Workbook.getWorkbook(new File(oriPath + "otherFiles\\hyponymyExtract_onceFilter.xls"));
		Sheet sheet = wb.getSheet(0); // get sheet(0)
		String[] upTopic = new String[sheet.getRows()];
		String[] dnTopic = new String[sheet.getRows()];
		int rowNum = sheet.getRows();
		for (int i = 0; i < rowNum; i++)
		{
			upTopic[i] = sheet.getCell(0, i).getContents();
			dnTopic[i] = sheet.getCell(1, i).getContents();
		}
//		for(int i = 0; i < upTopic.length; i++)
//			System.out.println(upTopic[i] + "\t" + dnTopic[i]);
		
		File dirfile =new File(oriPath + "7_layerMerge");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		String [] fileName = new File(InputFilePath + "1\\").list();
		for(int order = 1; order <= 3; order++)
		{
			//创建用于存储分面的1  2  3 文件夹
			File file =new File(oriPath + "7_layerMerge\\" + order);    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdir();    
			} 
			for(String name:fileName)
	        {
				System.out.println(order + "\t" + name);
				@SuppressWarnings("resource")
				BufferedReader BR=new BufferedReader(new InputStreamReader(new FileInputStream(oriPath + "6_layerFilter\\" + order + "\\" + name),"UTF-8"));
	    		String lineString = "";  //接收一行
	    		String[] txtCont = new String[50];
	    		String[] onceFacet = new String[50];
	    		int onceTopic = -1;
	    		while (   (lineString=BR.readLine()  )!=null ) 
	    		{
	    			if(!(lineString.contains("*****") || lineString.contains("##########")))
	    			{
	    				txtCont[++onceTopic] = lineString;
	    				onceFacet[onceTopic] = lineString;
	    			}
	    			else
	    				txtCont[onceTopic] = txtCont[onceTopic] + lineString;
	    		}
	    		int throwAway = 0;
	    		//有内容被其他包含了的去掉。例如，分面中有method 和 traversal method，就把traversal method去掉。
//	    		for(int i = 0; i <= onceTopic - throwAway; i++)
//	    		{
//	    			for(int j = 0; j <= onceTopic - throwAway; j++)
//	    			{
//	    				if( i != j && (" " + onceFacet[i] + " ").contains((" " + onceFacet[j] + " ")))
//	    				{
//	    					txtCont[j] = txtCont[j] + txtCont[i].replaceAll(onceFacet[i], "");
//	    					String rep = onceFacet[onceTopic - throwAway];
//	    					onceFacet[onceTopic - throwAway] = onceFacet[i];
//	    					onceFacet[i] = rep;
//	    					rep = txtCont[onceTopic - throwAway] ;
//	    					txtCont[onceTopic - throwAway] = txtCont[i];
//	    					txtCont[i] = rep;
//	    					throwAway++;
//	    					break;
//	    				}
//	    			}
//	    		}
//	    		onceTopic = onceTopic - throwAway;
//	    		throwAway = 0;
//	    		for(int i = 0; i <= onceTopic - throwAway; i++)
//	    		{
//	    			for (int j = 0; j < rowNum; j++)
//	    				if(onceFacet[i].equals(dnTopic[j]))
//	    					for(int k = 0; k <= onceTopic - throwAway; k++)
//	    						if(i != k && onceFacet[k].equals(upTopic[j]))
//	    						{
//	    							txtCont[k] = txtCont[k] + "*****" + txtCont[i].split("##########")[0].replaceAll("\\*\\*\\*\\*\\*", "##########");
//	    							String rep = onceFacet[onceTopic - throwAway] ;
//	    	    					onceFacet[onceTopic - throwAway] = onceFacet[i];
//	    	    					onceFacet[i] = rep;
//	    	    					rep = txtCont[onceTopic - throwAway] ;
//	    	    					txtCont[onceTopic - throwAway] = txtCont[i];
//	    	    					txtCont[i] = rep;
//	    	    					throwAway++;
//	    	    					j = rowNum + 1;
//	    	    					i--;
//	    							break;
//	    						}
//	    		}
	    		String cont = "";
	    		for(int i = 0; i <= onceTopic; i++)
	    		{
	    			String[] str = txtCont[i].split("\\*\\*\\*\\*\\*");
	    			txtCont[i] = str[0];
	    			for(int j = 1; j < str.length; j++)
	    			{
	    				txtCont[i] = txtCont[i] + "\n*****" + str[j];
	    			}
	    			str = txtCont[i].split("##########");
	    			txtCont[i] = str[0];
	    			for(int j = 1; j < str.length; j++)
	    			{
	    				txtCont[i] = txtCont[i] + "\n##########" + str[j];
	    			}
	    			cont = cont + txtCont[i] + "\n";
	    		}
	    		FileUtils.write(new File(oriPath + "7_layerMerge\\" + order + "\\" + name), cont);
	        }
//			System.out.println();
		}
	}

}
