package newStep;

import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ASaveContentAllTopics
{

	public static void main(String[] args)
	{
        String oriP = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
//		String domain = "Data_structure";
//        String domain = "Data_mining";
        String domain = "C_programming_language";
        String oriPath = oriP + domain + "\\";
        SaveContantAllTopics(oriPath, domain);
	}

	public static void SaveContantAllTopics(String oriPath, String domain)
	{
		File dirfile =new File(oriPath + "1_origin");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		String dirPath = oriPath + "1_origin\\";
		List<String> topics = null;
		try {
			topics = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
			System.out.println("共有" + topics.size() + "个术语");

			topics.forEach(topic -> wiki(dirPath, topic));

			System.out.println("Done.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void wiki(String dirPath, String word)
	{
		System.out.println("Save content\t" + word);
		Document doc = null;
		Topic topic=new Topic();
		topic.setName(word);
		List<Facet> facets=new ArrayList<Facet>();
		try {
			doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + word).timeout(100000).get();
			Elements element_title = doc.select("#toc > ul > li");
			if (element_title.size() == 0)
			{
				System.out.println("没有content.");
			}
			for (Element ele : element_title)
			{
				Facet facet1=new Facet();
				List<Facet> facets1=new ArrayList<Facet>();
				String facet1Name=ele.select("a> span.toctext").first().text();
				facet1.setName(facet1Name);
				if (ele.select("ul").size() >= 1)
				{
					Elements ele_child = ele.select("ul >li.toclevel-2");
					for (Element element : ele_child)
					{
						Facet facet2=new Facet();
						List<Facet> facets2=new ArrayList<Facet>();
						String facet2Name=element.select("a> span.toctext").first().text();
						facet2.setName(facet2Name);
						if (element.select("ul").size() >= 1)
						{
							Elements ele_grandson = element.select("ul >li.toclevel-3");
							for (Element elementson : ele_grandson)
							{
								Facet facet3=new Facet();
								List<Facet> facets3=new ArrayList<Facet>();
								String facet3Name=elementson.select("a > span.toctext").first().text();
								facet3.setName(facet3Name);
								facet3.setNextFacets(facets3);
								facets2.add(facet3);
							}
						}
						facet2.setNextFacets(facets2);
						facets1.add(facet2);
					}
				}
				facet1.setNextFacets(facets1);
				facets.add(facet1);
			}
			topic.setFacets(facets);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("没有content.");
			topic.setFacets(facets);
		}
		TxtToObject.writeObjToTxt(topic, dirPath + word + ".txt");
	}
}
