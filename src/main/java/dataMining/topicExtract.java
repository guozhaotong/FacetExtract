package dataMining;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class topicExtract {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		HashSet<String> finalTopic = getTopic();
//		String cont = "";
//		for(String string : finalTopic)
//		{
//			cont = cont + string + "\n";
//		}
//		try {
//			FileUtils.write(new File("M:\\test.txt"), cont);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@SuppressWarnings("unchecked")
	public static HashSet<String> getTopic() {
		HashSet<String> dataMiningTopic = new HashSet<>();
		ArrayList<String> nextCycle = new ArrayList<>();
		ArrayList<String> upLocation = new ArrayList<>();
		ArrayList<String> dnLocation = new ArrayList<>();
		dataMiningTopic.add("Data_mining");
		nextCycle.add("Data_mining");
		while(nextCycle.size() > 0)
		{
			ArrayList<String> nextCycleTopic = new ArrayList<>();
			for(String categ : nextCycle)
			{
				System.out.println(categ);
				try {
					ArrayList<String> newTopic = wiki(categ);
					int i = 0;
					for(i = 0; i < newTopic.size(); i++)
					{
						if(newTopic.get(i).equals("Part 1 is over. Part two begin."))
							break;
					}
					for(int j = 0; j < i; j++)
					{
						if(dataMiningTopic.contains(newTopic.get(j)))
							continue;
						dataMiningTopic.add(newTopic.get(j));
						nextCycleTopic.add(newTopic.get(j));
						upLocation.add(categ);
						dnLocation.add(newTopic.get(j));
					}
					for(int j = i+1; j < newTopic.size(); j++)
					{
						if(dataMiningTopic.contains(newTopic.get(j)))
							continue;
						dataMiningTopic.add(newTopic.get(j));
						upLocation.add(categ);
						dnLocation.add(newTopic.get(j));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			nextCycle = (ArrayList<String>) nextCycleTopic.clone();
		}
//		System.out.println(upLocation.size());
		writeExcel(upLocation, dnLocation);
		return dataMiningTopic;
	}
	
	public static void writeExcel(ArrayList<String> upLocation, ArrayList<String> dnLocation) {
		WritableWorkbook wwb;
		try {
			wwb = Workbook.createWorkbook(new File("M:\\Data mining上下位.xls"));
	        WritableSheet ws = wwb.createSheet("sheet1", 0);
	        Label labelC = new Label(0, 0, "下位"); 
            try {
				ws.addCell(labelC);
			} catch (WriteException e) {
				e.printStackTrace();
			}    
            labelC = new Label(1, 0, "上位"); 
            try {
				ws.addCell(labelC);
			} catch (WriteException e) {
				e.printStackTrace();
			}    
	        for(int i = 0; i < upLocation.size(); i++)
	        {    
                labelC = new Label(0, i+1, dnLocation.get(i)); 
                try {
					ws.addCell(labelC);
				} catch (WriteException e) {
					e.printStackTrace();
				}        
                labelC = new Label(1, i+1, upLocation.get(i)); 
                try {
					ws.addCell(labelC);
				} catch (WriteException e) {
					e.printStackTrace();
				}    
	         }    
	         wwb.write();    
	         try {
				wwb.close();
			} catch (WriteException e) {
				e.printStackTrace();
			}  
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static ArrayList<String> wiki(String word) throws IOException
	{
		ArrayList<String> topic = new ArrayList<>();
		Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Category:" + word).timeout(100000).get();
		Elements element_title = doc.select(".CategoryTreeItem");
//		System.out.println(element_title.size());
		for(Element ele : element_title)
		{
//			System.out.println(ele.select("a").text().replaceAll(" ", "_"));
			topic.add(ele.select("a").text().replaceAll(" ", "_"));
		}
		topic.add("Part 1 is over. Part two begin.");
		element_title = doc.select("#mw-pages > div > div > div");
//		System.out.println(element_title.size());
		for(int i = 2; i <= element_title.size(); i++)
		{
			Elements element_child = doc.select("#mw-pages > div > div > div:nth-child(" + i + ") > ul > li");
//			System.out.println("-----" + element_child.size());
			for(Element ele : element_child)
			{
//				System.out.println(ele.select("a").text().replaceAll(" ", "_"));
				topic.add(ele.select("a").text().replaceAll(" ", "_"));
			}
		}
		return topic;
	}
}
