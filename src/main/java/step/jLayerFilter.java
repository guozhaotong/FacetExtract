package step;
//用于把只有一个下级标题的去掉，这样一个标题不会只有一个下级标题
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import org.apache.commons.io.FileUtils;
import jxl.Sheet;
import jxl.Workbook;

public class jLayerFilter
{
	public static void main(String[] args) throws Exception
	{
		String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
		LayerFilterFilter(oriPath);
	}
	
	public static void LayerFilterFilter(String oriPath) throws IOException, FileNotFoundException, Exception
	{
		File dirfile =new File(oriPath + "10_layerFilter\\");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		String [] fileName = new File(oriPath + "9_deduplication\\txt\\1\\").list();
		for(int order = 1; order <= 3; order++)
		{
			File file =new File(oriPath + "10_layerFilter\\" + order);    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdir();    
			} 
		}
		File file =new File(oriPath + "10_layerFilter\\3-1");    
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    file .mkdir();    
		} 
		file =new File(oriPath + "10_layerFilter\\3-2");    
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    file .mkdir();    
		} 
		Workbook wb = Workbook.getWorkbook(new File(oriPath + "otherFiles\\数据结构上下位关系-filter.xls"));
		Sheet sheet = wb.getSheet(0); // get sheet(0)
		HashSet<String> set=new HashSet<String>();
		for (int i = 1; i < sheet.getRows(); i++)
		{
			set.add(sheet.getCell(1, i).getContents());
		}
		
		for(String name:fileName)
		{
        	System.out.println("LayerFilter\t" + name);
			/***********************处理文件夹1************************/
        	BufferedReader BR=new BufferedReader(new InputStreamReader(new FileInputStream(oriPath + "9_deduplication\\txt\\1\\" + name),"UTF-8"));
    		String lineString="";  //接收一行
			String txtCont = "";
    		while (   (lineString=BR.readLine()  )!=null ) 
    		{
    			txtCont = txtCont + lineString + "\n";
    		}
    		BR.close();
        	if(set.contains(name.replaceAll(".txt", "")))
        	{
        		if(!txtCont.contains("type\n"))
        			txtCont = "type\n" + txtCont;
        	}
    		if(!txtCont.contains("property\n"))
    			txtCont = "property\n" + txtCont;
    		if(!txtCont.contains("definition\n"))
    			txtCont = "definition\n" + txtCont;
			FileUtils.write(new File(oriPath + "10_layerFilter\\1\\" + name), txtCont);
			/***********************处理文件夹2************************/
        	BR=new BufferedReader(new InputStreamReader(new FileInputStream(oriPath + "9_deduplication\\txt\\2\\" + name),"UTF-8"));
    		lineString="";  //接收一行
			txtCont = "";
			String layer2 = "";
			int lay2 = 0;
    		while (   (lineString=BR.readLine()  )!=null ) 
    		{
    			if (  !( lineString.contains("*****") )  )
				{
    				txtCont = txtCont + lineString + "\n";
    				lay2 = 0;
				}
    			else if (lay2 == 0)
    			{
    				layer2 = lineString + "\n" ;
    				lay2 = 1;
    			}
    			else 
    			{
					txtCont = txtCont + layer2 + lineString + "\n";
					layer2 = "";
				}
    		}
    		BR.close();
        	if(set.contains(name.replaceAll(".txt", "")))
        	{
        		if(!txtCont.contains("type\n"))
        			txtCont = "type\n" + txtCont;
        	}
    		if(!txtCont.contains("property\n"))
    			txtCont = "property\n" + txtCont;
    		if(!txtCont.contains("definition\n"))
    			txtCont = "definition\n" + txtCont;
			FileUtils.write(new File(oriPath + "10_layerFilter\\2\\" + name), txtCont);
			/***********************处理文件夹3************************/
			//去二级下面只有一个的三级
			BR=new BufferedReader(new InputStreamReader(new FileInputStream(oriPath + "9_deduplication\\txt\\3\\" + name),"UTF-8"));
    		lineString="";  //接收一行
			txtCont = "";
			layer2 = "";
			lay2 = 0;
			String layer3 = "";
			int lay3 = 0;
    		while (   (lineString=BR.readLine()  )!=null ) 
    		{
    			if (  !( lineString.contains("*****")  || lineString.contains("##########"))  )
				{
    				txtCont = txtCont + "\n" + lineString ;
				}
    			else if (  lineString.contains("*****"))
				{
    				txtCont = txtCont + "\n" + lineString ;
    				lay3 = 0;
				}
    			else if (lay3 == 0)
    			{
    				layer3 = lineString;
    				lay3 = 1;
    			}
    			else 
    			{
					txtCont = txtCont + layer3 + lineString;
					layer3 = "";
				}
    		}
    		BR.close();
			FileUtils.write(new File(oriPath + "10_layerFilter\\3-1\\" + name), txtCont.trim());
			
			BR=new BufferedReader(new InputStreamReader(new FileInputStream(oriPath + "10_layerFilter\\3-1\\" + name),"UTF-8"));
    		lineString="";  //接收一行
			txtCont = "";
			layer3 = "";
			lay3 = 0;
    		while (   (lineString=BR.readLine()  )!=null ) 
    		{
    			if (  !( lineString.contains("*****")  || lineString.contains("##########"))  &&  layer2.contains("##########"))
    			{
    				String replace = layer2.split("##########")[0];
    				txtCont = txtCont + layer2.substring(replace.length()) + lineString + "\n";
    				lay2 = 0;
    			}
    			else if (  !( lineString.contains("*****")  || lineString.contains("##########")) )
    			{
    				txtCont = txtCont + lineString + "\n";
    				lay2 = 0;
				}
    			else if (lineString.contains("*****") && lay2 == 0)
    			{
    				layer2 = lineString + "\n" ;
    				lay2 = 1;
    			}
    			else if(lineString.contains("*****") && lay2 > 0)
    			{
					txtCont = txtCont + layer2 + lineString + "\n";
					layer2 = "";
				}
    			else 
    			{
    				txtCont = txtCont + lineString + "\n";
				}
    		}
    		BR.close();
			FileUtils.write(new File(oriPath + "10_layerFilter\\3-2\\" + name), txtCont.trim());
			
			BR=new BufferedReader(new InputStreamReader(new FileInputStream(oriPath + "10_layerFilter\\3-2\\" + name),"UTF-8"));
			lineString="";  //接收一行
			txtCont = "";
    		while (   (lineString=BR.readLine()  )!=null ) 
    		{
    			if (  !( lineString.contains("*****")  || lineString.contains("##########"))  )
				{
    				txtCont = txtCont + lineString + "\n";
				}
    			else if (lineString.contains("*****") && lineString.contains("##########"))
    			{
    				String[] str = lineString.split("##########");
    				txtCont = txtCont + str[0] + "\n";
    				for(int No = 1; No < str.length; No++)
    				{
    					txtCont = txtCont + "##########" + str[No] + "\n";
    				}
    			}
    			else if(lineString.contains("*****"))
    			{
    				txtCont = txtCont + lineString + "\n";
				}
    			else 
    			{
    				String[] str = lineString.split("##########");
    				for(int No = 1; No < str.length; No++)
    				{
    					txtCont = txtCont + "*****" + str[No] + "\n";
    				}
				}
    		}
    		BR.close();
        	if(set.contains(name.replaceAll(".txt", "")))
        	{
        		if(!txtCont.contains("type\n"))
        			txtCont = "type\n" + txtCont;
        	}
    		if(!txtCont.contains("property\n"))
    			txtCont = "property\n" + txtCont;
    		if(!txtCont.contains("definition\n"))
    			txtCont = "definition\n" + txtCont;
			FileUtils.write(new File(oriPath + "10_layerFilter\\3\\" + name), txtCont);
		}
		deleDirectory(oriPath + "10_layerFilter\\3-1");
		deleDirectory(oriPath + "10_layerFilter\\3-2");
        System.out.println("Done.");
	}
	
	public static void deleDirectory(String DirectoryPath)
	{
		String [] fileName = new File(DirectoryPath + "\\").list();
		for(String name:fileName)
		{
			File file = new File(DirectoryPath + "\\" + name);
			if (file.isFile()) 
				file.delete();
		}
		File file = new File(DirectoryPath);
		if (file.isDirectory()) 
			file.delete();
	}
}
