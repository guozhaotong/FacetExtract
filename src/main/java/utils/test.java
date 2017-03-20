package utils;

import java.awt.image.TileObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;

public class test
{
	public static void main(String[] args) throws IOException
	{
		saveNews();
		System.out.println("done");
	}
	public static void saveNews() {
		System.out.println("Start.");
		BufferedReader BR = null;
		try {
			BR = new BufferedReader(new InputStreamReader(new FileInputStream("F:\\news_sohusite_xml.dat"),"gb2312"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String lineString="";  //接收一行
		String Content = "";
		String title = "";
		String news = "";
		int No = 0;
		try {
			while (   (lineString=BR.readLine()  )!=null ) 
			{
//				Content = Content + lineString;
				System.out.println("Read" + No);
				No++;
			}
			BR.close();
//			System.out.println(Content);
//			String[] everyNews = Content.split("<doc>");
//			int newsNum = 1;
//			for(int i = 1; i < everyNews.length; i++)
//			{
//				String str = everyNews[i];
//				System.out.println(str);
////				System.out.println(str);
////				title = str.split("<contenttitle>")[1].split("</contenttitle>")[0];
////				System.out.println(title);
////				news = str.split("<content>")[1].split("</content>")[0];
////				System.out.println(news);
////				if(news.length()<100)
////					continue;
////				FileUtils.write(new File("F:\\exp-news\\" + newsNum + ".txt"), title + "\n" + news.replaceAll("［详细］", ""));
////				newsNum++;
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
	}
}
