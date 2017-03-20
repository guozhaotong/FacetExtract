package step;
//把一个Excel中所有主题的wiki Content的内容保存到TXT中，二级标题前用“*****”区分，三级标题前用“##########”区分。
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class aSaveContentAllTopics
{

	public static void main(String[] args) throws BiffException, IOException 
	{
		String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
		SaveContantAllTopics(oriPath);
	}
	
	public static void SaveContantAllTopics(String oriPath) throws BiffException, IOException
	{
		File dirfile =new File(oriPath + "1_origin");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		String dirPath = oriPath + "1_origin\\";
		Workbook wb = Workbook.getWorkbook(new File("E:\\我是研究生\\任务\\分面树的生成\\Mooc\\Data_structure_topics.xls"));
		Sheet sheet = wb.getSheet(0); // get sheet(0)
		System.out.println("共有" + sheet.getRows() + "个术语");
		for (int i = 0; i < sheet.getRows(); i++)
		{
			System.out.println("Save content\t" + i + "\t" + sheet.getCell(0, i).getContents());
			wiki(dirPath,sheet.getCell(0, i).getContents());
		}
		System.out.println("Done.");
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

	public static void wiki(String dirPath, String word) throws IOException
	{
		Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + word).timeout(100000).get();
		Elements element_title = doc.select("#toc > ul > li");
		if (element_title.size() == 0)
		{
			System.out.println("没有content.");
			writetxt(dirPath + "1\\" + word + ".txt", "\n");
			writetxt(dirPath + "2\\" + word + ".txt", "\n");
			writetxt(dirPath + "3\\" + word + ".txt", "\n");
		}
		for (Element ele : element_title)
		{
			writetxt(dirPath + "1\\" + word + ".txt", ele.select("a> span.toctext").first().text() + "\n");
			writetxt(dirPath + "2\\" + word + ".txt", ele.select("a> span.toctext").first().text() + "\n");
			writetxt(dirPath + "3\\" + word + ".txt", ele.select("a> span.toctext").first().text() + "\n");
			if (ele.select("ul").size() >= 1)
			{
				Elements ele_child = ele.select("ul >li.toclevel-2");
				for (Element element : ele_child)
				{
					writetxt(dirPath + "2\\" + word + ".txt", "*****" + element.select("a> span.toctext").first().text() + "\n");
					writetxt(dirPath + "3\\" + word + ".txt", "*****" + element.select("a> span.toctext").first().text() + "\n");
					if (element.select("ul").size() >= 1)
					{
						Elements ele_grandson = element.select("ul >li.toclevel-3");
						for (Element elementson : ele_grandson)
						{
							writetxt(dirPath + "3\\" + word + ".txt", "##########" + elementson.select("a > span.toctext").first().text() + "\n");
						}
					}
				}
			}
		}
	}
}
