package utils;
//把某个主题的wiki Content的内容保存到TXT中，二级标题前用“*****”区分，三级标题前用“##########”区分。
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SaveContent
{

	public static void main(String[] args) throws IOException
	{
		wiki("Binary_tree");
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

	public static void wiki(String word) throws IOException
	{
		Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + word).timeout(100000).get();
		Elements element_title = doc.select("#toc > ul > li");
//		#toc > ul > li.toclevel-1.tocsection-1 > a > span.toctext
//		#toc > ul > li.toclevel-1.tocsection-4 > a > span.toctext
		System.out.println(element_title.size());
		for (Element ele : element_title)
		{
			System.out.println(ele.select("a> span.toctext").first().text());
			writetxt("E://CalcSummarySim.txt", ele.select("a> span.toctext").first().text() + "\n");
			if (ele.select("ul").size() >= 1)
			{
				Elements ele_child = ele.select("ul >li.toclevel-2");
				for (Element element : ele_child)
				{
					System.out.println("*****" + element.select("a > span.toctext").first().text());
					writetxt("E://CalcSummarySim.txt","*****" + element.select("a > span.toctext").first().text() + "\n");
					if (element.select("ul").size() >= 1)
					{
						Elements ele_grandson = element.select("ul >li.toclevel-3");
						for (Element elementson : ele_grandson)
						{
							System.out.println("##########" + elementson.select("a > span.toctext").first().text());
							writetxt("E://CalcSummarySim.txt", "##########" + elementson.select("a > span.toctext").first().text() + "\n");
						}
					}
				}
			}
		}
	}
}
