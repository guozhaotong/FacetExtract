package newStep;
//用于把空行去掉，其对应的下级标题也都去掉
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shilei.deduplication;

public class iDeduplication
{
	public static void main(String[] args) throws IOException
	{
		String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
		nullRollFilter(oriPath);
	}
	
	public static void nullRollFilter(String oriPath) throws IOException, FileNotFoundException
	{
		File dirfile =new File(oriPath + "8_deduplication");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		File dirfile1 =new File(oriPath + "9_deduplication\\" + "txt");    
		if  (!dirfile1 .exists()  && !dirfile1 .isDirectory())      
		{       
		    dirfile1 .mkdir();    
		} 
		File dirfile2 =new File(oriPath + "9_deduplication\\" + "json");    
		if  (!dirfile2 .exists()  && !dirfile2 .isDirectory())      
		{       
		    dirfile2 .mkdir();    
		} 
		for(int order = 1; order <= 3; order++)
		{
			//创建用于存储分面的1  2  3 文件夹
			File filetxt =new File(oriPath + "9_deduplication\\txt\\" + order);    
			if  (!filetxt .exists()  && !filetxt .isDirectory())      
			{       
			    filetxt .mkdir();    
			} 
			File filejson =new File(oriPath + "9_deduplication\\json\\" + order);    
			if  (!filejson .exists()  && !filejson .isDirectory())      
			{       
				filejson .mkdir();    
			} 
			String InputFilePath = oriPath + "8_upperMerge\\" + order + "\\";
			String OutputFilePath = oriPath + "9_deduplication\\";
			String [] fileName = new File(InputFilePath).list();
	        for(String name:fileName)
	        {
	        	String facetOne = "";
	        	String facetTwo = "";
	        	deduplication object = new deduplication();
	        	System.out.println(order + "\t" + name);
	        	BufferedReader BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
	    		object.add("definition");
	    		object.add("property");
	    		String lineString="";  //接收一行
	    		while (   (lineString=BR.readLine()  )!=null ) 
	    		{
	    			if(lineString.trim().length()>4)
			    	{
	    				if(!lineString.trim().substring(0, 5).equals("*****") && !lineString.trim().substring(0, 5).equals("#####"))
			    		{
			    			facetOne = lineString.trim();
				    		object.add(facetOne);
			    		}
	    				else if (lineString.trim().substring(0, 5).equals("*****")) 
			    		{
			    			facetTwo = lineString.replaceAll("\\*\\*\\*\\*\\*", "").trim();
				    		object.add(facetOne,facetTwo);
						}
			    		else 
			    		{
				    		object.add(facetOne,facetTwo,lineString.replaceAll("##########", "").trim());
						}
		    		}
	    			else
	    			{
	    				facetOne = lineString.trim();
			    		object.add(facetOne);
	    			}
	    		}
	    		BR.close(); //一定记得关闭文件
	    		String json = "";
	    		ObjectMapper mapper = new ObjectMapper();
	    		try
	    		{
	    			json = mapper.writeValueAsString(object.getResult());
	    			FileUtils.write(new File(OutputFilePath + "json\\" + order + "\\" + name.replaceAll(".txt", ".json")), json);
	    			String jsonProc = json.replaceAll("\"", "").replaceAll(":\\{},", "\n").replaceAll(":\\[]},", "\n").replaceAll(":\\{", "\n*****")
	    					.replaceAll(":\\[],", "\n*****").replaceAll(":\\[]}", "").replaceAll("}", "").replaceAll(":\\[", "\n##########")
	    					.replaceAll("],", "\n*****").replaceAll("\\{", "").replaceAll(",", "\n##########");
	    			if(jsonProc.length()>4 && jsonProc.substring(jsonProc.length()-5).equals("*****"))
	    				jsonProc = jsonProc.substring(0, jsonProc.length()-5);
	    			FileUtils.write(new File(OutputFilePath + "txt\\" + order + "\\" + name), jsonProc);
	    		} catch (JsonProcessingException e)
	    		{
	    			e.printStackTrace();
	    		} catch (IOException e)
	    		{
	    			e.printStackTrace();
	    		}
	        }
		}
        System.out.println("Done.");
	}
	
}
