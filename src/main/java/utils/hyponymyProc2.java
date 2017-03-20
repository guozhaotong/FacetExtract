package utils;
//用于把原来的主题之间的上下位关系，改成下位节点只连接一个其上位节点。
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class hyponymyProc2 {
	public static void main(String file_path[]) throws IOException, BiffException, RowsExceededException, WriteException 
	{	
		String InputFileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\数据结构上下位关系.xls";
		String OutputfileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\数据结构上下位关系-子连父new.xls";
		hyponymyFilter(InputFileName, OutputfileName);
		System.out.println("done"); 
	}
	public static void hyponymyFilter(String inputfileName, String outputfileName) throws BiffException, IOException , RowsExceededException, WriteException{
		Workbook wb=Workbook.getWorkbook(new File(inputfileName));
		Sheet sheet = wb.getSheet(0); //get sheet(0)
		System.out.println(sheet.getRows());
		ArrayList<String> downPos = new ArrayList<>(); 
		ArrayList<String> upPos = new ArrayList<>(); 
		
		HashSet<String> flagUp = new HashSet<String>(); 
		
		HashSet<Integer> no = new HashSet<>(); 


		for(int i=1; i<sheet.getRows(); i++)
		{
			flagUp.add(sheet.getCell(1, i).getContents());
			downPos.add(sheet.getCell(0,i).getContents());
			upPos.add( sheet.getCell(1, i).getContents());
		}
		
		
		for(String str:flagUp)
		{
			HashMap<String,Integer> cand=new HashMap<>(); 
			for(int i = 0; i < upPos.size(); i++)
			{
				if(str.equals(upPos.get(i)))
				{
					cand.put(downPos.get(i),i);
				}
			}
			for(int i = 0; i < upPos.size(); i++)
			{
				if(cand.containsKey(upPos.get(i)) &&cand.containsKey(downPos.get(i)) )
				{
					no.add(cand.get(downPos.get(i)) );
					System.out.println(str+"\t"+cand.get(downPos.get(i))+"\t"+downPos.get(i));
				}
			}
		}

		
		WritableWorkbook wwb = Workbook.createWorkbook(new File(outputfileName));
		WritableSheet ws = wwb.createSheet("sheet0", 0);
		Label labelC = new Label(0, 0, "下位"); 
		ws.addCell(labelC); 
		labelC = new Label(1, 0, "上位"); 
		ws.addCell(labelC); 
		//traversal
		int writenum = 1;
		for(int i=0; i<downPos.size(); i++)
		{
			if(!no.contains(i))
			{
				labelC = new Label(0, writenum, downPos.get(i)); 
				ws.addCell(labelC); 
				labelC = new Label(1, writenum++, upPos.get(i)); 
				ws.addCell(labelC); 
			}
		}
        wwb.write();    
        wwb.close();
        System.out.println("done.");
	}
}
    