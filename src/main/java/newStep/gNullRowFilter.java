package newStep;
//用于把空行去掉，其对应的下级标题也都去掉
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class gNullRowFilter
{
	public static void main(String[] args) throws IOException
	{
		String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
		nullRollFilter(oriPath);
	}
	
	public static void nullRollFilter(String oriPath) throws IOException, FileNotFoundException
	{
		File dirfile =new File(oriPath + "7_nullRowFilter");    
		if  (!dirfile .exists()  && !dirfile .isDirectory())      
		{       
		    dirfile .mkdir();    
		} 
		for(int order = 1; order <= 3; order++)
		{
			//创建用于存储分面的1  2  3 文件夹
			File file =new File(oriPath + "7_nullRowFilter\\" + order);    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdir();    
			} 
			String InputFilePath = oriPath + "6_topicNameFilter\\" + order + "\\";
			String OutputFilePath = oriPath + "7_nullRowFilter\\" + order + "\\";
			String [] fileName = new File(InputFilePath).list();
	        for(String name:fileName)
	        {
	        	System.out.println(order + "\t" + name);
	        	BufferedReader BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
	    		String lineString="";  //接收一行
	    		int cycle1 = 1;
	    		int cycle2 = 1;
	    		while (   (lineString=BR.readLine()  )!=null ) 
	    		{
	    			//若一级标题是空的
	    			if (lineString.trim().equals(""))
	    			{
	    				cycle1 = 0;
	    				continue;
	    			}
	    			if (lineString.trim().length() > 4)
	    			{
		    			if (lineString.trim().substring(0, 5).equals("*****")) 
		    			{
		    				if (cycle1 ==0)
		    					continue;
		    				else if (lineString.trim().equals("*****"))
		    				{
		    					cycle2 = 0;
		    					continue;
		    				}
						}
	    			}
	    			if (lineString.trim().length() > 9)
	    			{
		    			if (lineString.trim().substring(0, 10).equals("##########")) 
		    			{
							if (cycle1 == 0 || cycle2 == 0)
								continue;
							else if (lineString.trim().equals("##########")) 
							{
								continue;
							}
						}
	    			}
	    			writetxt(OutputFilePath + name, lineString.trim() + "\n");
	    			cycle1 = 1;
	    			cycle2 = 1;
	    		}
	    		BR.close(); //一定记得关闭文件
	        }
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
}
