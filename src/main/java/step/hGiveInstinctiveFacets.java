package step;
//把所有的主题都加上其天生的几个分面（如果原来没有的话），definition, property, application, example
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import org.apache.commons.io.FileUtils;
import jxl.read.biff.BiffException;

public class hGiveInstinctiveFacets
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
		String InstinctiveFacets[] = {"definition","property","application","example"};
		String InputFilePath = oriPath + "6_layerFilter\\";
		File dirfile =new File(oriPath + "7_giveInstinctiveFacets");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		String [] fileName = new File(InputFilePath + "3\\").list();
		for(int order = 1; order <= 3; order++)
		{
			//创建用于存储分面的1  2  3 文件夹
			File file =new File(oriPath + "7_giveInstinctiveFacets\\" + order);    
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
	    		HashSet<String> appearedFacet = new HashSet<>();
	    		String cont = "";
	    		while (   (lineString=BR.readLine()  )!=null ) 
	    		{
	    			lineString =  " " + lineString + " ";
	    			if(lineString.indexOf("advantage") != -1) lineString = "property";
	    			if(lineString.indexOf(" performance ") != -1) lineString = "property";
	    			if(lineString.indexOf(" characterization ") != -1) lineString = "property";
	    			if(lineString.indexOf(" analysis ") != -1) lineString = "property";
	    			if(lineString.indexOf(" comparison ") != -1) lineString = "property";
	    			if(lineString.indexOf(" implemantation ") != -1) lineString = "implementation";
	    			if(lineString.indexOf(" language support ") != -1) lineString = "implementation";
	    			if(lineString.indexOf(" pseudocode ") != -1) lineString = "implementation";
	    			if(lineString.indexOf(" usage ") != -1) lineString = "application";
	    			if(lineString.indexOf(" using ") != -1) lineString = "application";
	    			if(lineString.indexOf(" use ") != -1) lineString = "application";
	    			if(lineString.indexOf(" description ") != -1) lineString = "definition";
	    			if(lineString.trim().equals("concept")) lineString = "definition";
	    			if (appearedFacet.contains(lineString.replaceAll("\\*\\*\\*\\*\\*", "").
	    					replaceAll("##########", "").trim())) {
						continue;
					}
	    			cont = cont + lineString.trim() + "\n";
	    			appearedFacet.add(lineString.trim().replaceAll("\\*\\*\\*\\*\\*", "")
	    					.replaceAll("##########", "").trim());
	    		}
	    		for(String string : InstinctiveFacets)
	    		{
	    			if (appearedFacet.contains(string)) {
						continue;
					}
	    			cont = cont + string + "\n";
	    		}
	    		FileUtils.write(new File(oriPath + "7_giveInstinctiveFacets\\" + order + "\\" + name), cont);
	        }
		}
	}

}
