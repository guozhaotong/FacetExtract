package newStep;
//用于把一个主题与其上位主题的content组合到一起
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class hUpperMerge {
	public static void main(String file_path[]) throws IOException, BiffException, RowsExceededException, WriteException 
	{	
        String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
		UpperMerge(oriPath);
	}
	
	public static void UpperMerge(String oriPath) throws IOException, BiffException, RowsExceededException, WriteException 
    {
        File dirfile =new File(oriPath + "8_upperMerge");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		for(int order = 1; order <= 3; order++)
		{
			//创建用于存储分面的1  2  3 文件夹
			File file =new File(oriPath + "8_upperMerge\\" + order);    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdir();    
			} 
			String InputFilePath = oriPath + "7_layerMerge\\" + order + "\\";
			String OutputFilePath = oriPath + "8_upperMerge\\" + order + "\\";
			String [] fileName = getFileName(InputFilePath);
	        for(String name:fileName)
	        {
	        	System.out.println("Upper merge\t" + order + "\t" + name);
	        	BufferedReader BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
	    		String lineString="";  //接收一行
	    		while (   (lineString=BR.readLine()  )!=null ) 
	    		{
	    			writetxt(OutputFilePath + name, lineString + "\n");
	    		}
	    		Workbook wb=Workbook.getWorkbook(new File("E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\数据结构上下位关系.xls"));
	    		Sheet sheet = wb.getSheet(0); //get sheet(0)
	    		for(int i=0; i<sheet.getRows(); i++)
	    		{
	    			if(sheet.getCell(0,i).getContents().equals(name.replaceAll(".txt", "")))
	    			{
	    				while(sheet.getCell(0,i).getContents().equals(name.replaceAll(".txt", "")))
	    				{
	    					try
	    					{
	    						BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + sheet.getCell(1,i).getContents() + ".txt"),"UTF-8"));
		    		    		while (   (lineString=BR.readLine()  )!=null ) 
		    		    		{
		    		    			writetxt(OutputFilePath + name, lineString + "\n");
		    		    		}
		    					i++;
	    					}catch(FileNotFoundException e)
	    					{i++;continue;}
	    				}
	    				break;
	    			}
	    		}
	    		BR.close(); //一定记得关闭文件 
	        }
    		System.out.println("done");
		}
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
}
    