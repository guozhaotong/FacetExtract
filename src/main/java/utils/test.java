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
import java.util.HashSet;

import org.apache.commons.io.FileUtils;

public class test
{
	public static void main(String[] args) throws IOException
	{
		HashSet<String> hashSet = new HashSet<>();
		@SuppressWarnings("deprecation")
		String[] topics = FileUtils.readFileToString(new File("M:\\Data mining data set\\topic.txt")).split("\n");
		for(String string : topics)
			hashSet.add(string);
		String [] fileName = new File("M:\\Data mining data set\\Content\\1_origin\\1\\").list();
		for(String string : fileName)
			hashSet.remove(string.replaceAll(".txt", ""));
		System.out.println(hashSet);
	}
}
