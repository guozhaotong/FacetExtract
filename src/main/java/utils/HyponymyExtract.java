package utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
//hyponymyExtract用于把现有的分面间的上下位关系都存储起来，moveOnceFacet用于把出现了不止一次的分面间上下位关系存储起来
public class HyponymyExtract
{

	public static void main(String[] args) throws IOException, RowsExceededException, BiffException, WriteException
	{
		String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
//		String OutputFileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\hyponymyExtract.xls";
//		hyponymyExtract(oriPath, oriPath + "otherFiles\\hyponymyExtract.xls");
		moveOnceFacet(oriPath + "otherFiles\\hyponymyExtract.xls", oriPath + "otherFiles\\hyponymyExtract_onceFilter.xls");
		System.out.println("done"); 
	}

	@SuppressWarnings("null")
	public static void hyponymyExtract(String oriPath, String outputfileName){
		String InputFilePath = oriPath + "6_layerFilter\\";
		String [] fileName = new File(InputFilePath + "2\\").list();
		WritableWorkbook wwb = null;
		try
		{
			wwb = Workbook.createWorkbook(new File(outputfileName));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/***********************1-2************************/
		WritableSheet ws = wwb.createSheet("1-2", 0);
		int row = 0;
		for(String name:fileName)
        {
        	System.out.println(name);
        	BufferedReader BR = null;
			try
			{
				BR = new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + "2\\" + name),"UTF-8"));
			} catch (UnsupportedEncodingException | FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		String lineString="";  //接收一行
    		String facetOne = "";
    		String facetTwo = "";
    		try
			{
				while (   (lineString=BR.readLine()  )!=null ) 
				{
					//上下位关系的判断
					int changeTwo = 0;
					if(lineString.length()<5 || (lineString.length()>4 && !lineString.substring(0, 5).equals("*****")))
						facetOne = lineString;
					else 
					{
						facetTwo = lineString;
						changeTwo = 1;
					}
					if(changeTwo == 1)
					{
						Label labelC = new Label(0, row, facetOne.trim()); 
						ws.addCell(labelC); 
						labelC = new Label(1, row++, facetTwo.replaceAll("\\*\\*\\*\\*\\*", "").trim()); 
						ws.addCell(labelC);
					}
				}
			} catch (WriteException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		try
			{
				BR.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //一定记得关闭文件
           
        }
		/***********************2-3************************/
		ws = wwb.createSheet("2-3", 1);
		row = 0;
		for(String name:fileName)
        {
        	System.out.println(name);
        	BufferedReader BR = null;
			try
			{
				BR = new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + "3\\" + name),"UTF-8"));
			} catch (UnsupportedEncodingException | FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		String lineString="";  //接收一行
    		String facetTwo = "";
    		String facetThr = "";
    		try
			{
				while (   (lineString=BR.readLine()  )!=null ) 
				{
					//上下位关系的判断
					int changeTwo = 0;
					if(lineString.length()>4 && lineString.substring(0, 5).equals("*****"))
						facetTwo = lineString;
					else if(lineString.length()>4 && lineString.substring(0, 5).equals("#####"))
					{
						facetThr = lineString;
						changeTwo = 1;
					}
					if(changeTwo == 1)
					{
						Label labelC = new Label(0, row, facetTwo.replaceAll("\\*\\*\\*\\*\\*", "").trim()); 
						ws.addCell(labelC); 
						labelC = new Label(1, row++, facetThr.replaceAll("##########", "").trim()); 
						ws.addCell(labelC);
					}
				}
			} catch (WriteException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		try
			{
				BR.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //一定记得关闭文件
           
        }
		 try
			{
				wwb.write();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
         try
			{
				wwb.close();
			} catch (WriteException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void moveOnceFacet(String InputFilePath, String OutputFilePath) throws BiffException, IOException , RowsExceededException, WriteException
    {
		Workbook wb=Workbook.getWorkbook(new File(InputFilePath));
		Sheet sheet = wb.getSheet(0); //get sheet(0)
		WritableWorkbook wwb = Workbook.createWorkbook(new File(OutputFilePath));
		WritableSheet ws = wwb.createSheet("1-2", 0);
		//traversal
		String formerContent1 = "";
		String formerContent2 = "";
		int facetTime = 0;
		int facetNum = 0;
		for(int i=0; i<sheet.getRows(); i++)
		{
			String content1 = sheet.getCell(0,i).getContents();
			String content2 = sheet.getCell(1,i).getContents();
			if(!(content1.equals(formerContent1) && content2.equals(formerContent2)))
			{
				formerContent1 = content1;
				formerContent2 = content2;
				facetTime = 0;
			}
			else if (facetTime == 0)
			{
				Label labelC = new Label(0, facetNum, content1.trim()); 
				ws.addCell(labelC);
				labelC = new Label(1, facetNum++, content2.trim()); 
				ws.addCell(labelC);
				formerContent1 = content1;
				formerContent2 = content2;
				facetTime = 1;
			}
		}
        System.out.println("1-2 Done.");
        sheet = wb.getSheet(1); //get sheet(0)
		ws = wwb.createSheet("2-3", 1);
		//traversal
		formerContent1 = "";
		formerContent2 = "";
		facetTime = 0;
		facetNum = 0;
		for(int i=0; i<sheet.getRows(); i++)
		{
			String content1 = sheet.getCell(0,i).getContents();
			String content2 = sheet.getCell(1,i).getContents();
			if(!(content1.equals(formerContent1) && content2.equals(formerContent2)))
			{
				formerContent1 = content1;
				formerContent2 = content2;
				facetTime = 0;
			}
			else if (facetTime == 0)
			{
				Label labelC = new Label(0, facetNum, content1.trim()); 
				ws.addCell(labelC);
				labelC = new Label(1, facetNum++, content2.trim()); 
				ws.addCell(labelC);
				formerContent1 = content1;
				formerContent2 = content2;
				facetTime = 1;
			}
		}
		System.out.println("2-3 Done.");
        wwb.write();    
        wwb.close();
//        wwb2.write();    
//        wwb2.close();
    }

}
