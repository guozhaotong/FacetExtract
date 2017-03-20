package utils;
//用于把原来的主题上下位关系中，没有在192个数据结构领域的术语中出现的上下位关系删掉。
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class hyponymyProc {
	public static void main(String file_path[]) throws IOException, BiffException, RowsExceededException, WriteException 
	{	
		String InputFileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\数据结构上下位关系-子连父new.xls";
		String OutputfileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\数据结构上下位关系-use.xls";
		hyponymyFilter(InputFileName, OutputfileName);
		System.out.println("done"); 
	}
	public static void hyponymyFilter(String inputfileName, String outputfileName) throws BiffException, IOException , RowsExceededException, WriteException{
		String namePath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\Data_structure_topics.xls";
		Workbook wb=Workbook.getWorkbook(new File(namePath));
		Sheet sheet = wb.getSheet(0); //get sheet(0)
		HashSet<String> topic = new HashSet<String>();
		for(int i=0; i<sheet.getRows(); i++)
			topic.add(sheet.getCell(0,i).getContents());
		HashSet<Integer> no = new HashSet<Integer>();
		wb=Workbook.getWorkbook(new File(inputfileName));
		sheet = wb.getSheet(0); //get sheet(0)
		String[] downPos = new String[sheet.getRows()+1];
		String[] upPos = new String[sheet.getRows()+1];
		for(int i=1; i<sheet.getRows(); i++)
		{
			downPos[i] = sheet.getCell(0,i).getContents();
			upPos[i] = sheet.getCell(1, i).getContents();
			if(topic.contains(downPos[i]))
				no.add(i);
			if(topic.contains(upPos[i]))
				no.add(i);
		}

		WritableWorkbook wwb = Workbook.createWorkbook(new File(outputfileName));
		WritableSheet ws = wwb.createSheet("sheet0", 0);
		Label labelC = new Label(0, 0, "下位"); 
		ws.addCell(labelC); 
		labelC = new Label(1, 0, "上位"); 
		ws.addCell(labelC); 
		//traversal
		int writenum = 1;
		for(int i=1; i<sheet.getRows(); i++)
		{
			if(!no.contains(i))
			{
				labelC = new Label(0, writenum, downPos[i]); 
				ws.addCell(labelC); 
				labelC = new Label(1, writenum++, upPos[i]); 
				ws.addCell(labelC); 
			}
		}
        wwb.write();    
        wwb.close();
        System.out.println("done.");
	}
}
    