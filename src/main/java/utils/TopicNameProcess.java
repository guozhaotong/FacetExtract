package utils;
//用于把原来的主题的名称中，下划线都改成空格，横杠也都改成空格。领域单词在主题中去掉，多个空格合并成为一个空格。
import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class TopicNameProcess {
	public static void main(String file_path[]) throws IOException,  BiffException, RowsExceededException, WriteException 
	{	
		String InputFileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\Data_structure_topics.xls";
		String OutputFileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\Data_structure_topics_filter.xls";
		readExcel(InputFileName, OutputFileName);
		System.out.println("done"); 
	}
	public static void readExcel(String inputfileName, String outputfileName) throws BiffException, IOException , RowsExceededException, WriteException{
		Workbook wb=Workbook.getWorkbook(new File(inputfileName));
		Sheet sheet = wb.getSheet(0); //get sheet(0)
		WritableWorkbook wwb = Workbook.createWorkbook(new File(outputfileName));
		WritableSheet ws = wwb.createSheet("topicName", 0);
		//traversal
		for(int i=0; i<sheet.getRows(); i++)
		{
			String content = sheet.getCell(0,i).getContents();
			String[] words = content.split("\\(");
			content = words[0];
			content = content.toLowerCase();
			content = content.replaceAll("_", " ");
			content = content.replaceAll("-", " ");
			content = content.replaceAll("\\s+", " ");
			if(content.contains("data structure") && !content.trim().equals("data structure"))
				content = content.replaceAll("data structure", "");
			Label labelC = new Label(0, i, content.trim()); 
			ws.addCell(labelC); 
		}
        wwb.write();    
        wwb.close();
	}
}
    