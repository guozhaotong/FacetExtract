package utils;
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

public class gDBPediaProc
{

	public static void main(String[] args) throws IOException, RowsExceededException, BiffException, WriteException
	{
		String InputFileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\topicPredicate.xls";
		String OutputFileName = "E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\subjectPredicate.xls";
		procExcel(InputFileName, OutputFileName);
		System.out.println("done"); 
	}



	public static void procExcel(String inputfileName, String outputfileName) throws BiffException, IOException , RowsExceededException, WriteException{
		Workbook wb=Workbook.getWorkbook(new File(inputfileName));
		Sheet sheet = wb.getSheet(0); //get sheet(0)
		WritableWorkbook wwb = Workbook.createWorkbook(new File(outputfileName));
		WritableSheet ws = wwb.createSheet("sheet0", 0);
		//traversal
		for(int i=0; i<sheet.getRows(); i++)
		{
			String subject = sheet.getCell(0,i).getContents();
			String topic = procSubject(subject);
			Label labelC = new Label(0, i, topic); 
			ws.addCell(labelC); 
			subject = sheet.getCell(1,i).getContents();
			String predicate = procPredicate(subject);
			labelC = new Label(1, i, predicate); 
			ws.addCell(labelC); 
			String facet = procUseful(predicate);
			labelC = new Label(2, i, facet); 
			ws.addCell(labelC); 
		}
        wwb.write();    
        wwb.close();
	}
	
	public  static String procSubject(String subject)
	{
		String[] words  = subject.split("/");
		int wordNum = words.length;
		String topic = "";
		topic = words[wordNum-1];
		if (topic.substring(0, 1).equals("O"))
			if(words[wordNum-2].equals("I"))
				topic = words[wordNum-2] + "/" + topic;
		return topic;
	}
	
	public  static String procPredicate(String predicate)
	{
		String topic=predicate.substring((predicate.lastIndexOf("#")==-1?predicate.lastIndexOf("/"):predicate.lastIndexOf("#"))+1);
		return topic;
	}
	
	public  static String procUseful(String topic)
	{
		String[] useless = {"sameAs","subject","wikiPageID","wikiPageRevisionID","wikiPageExternalLink","label","isPrimaryTopicOf",
				"wasDerivedFrom","abstract","differentFrom","seeAlso"};//thumbnail存疑，因为后面的宾语打不开，不知道内容是啥，网页报unknown error。
//		int uselessNum = useless.length;
		for (String string : useless)
		{
			if(topic.equals(string))
				topic = "";
		}
		return topic;
	}
	
}
