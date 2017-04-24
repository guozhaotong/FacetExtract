package step;
//通过英文中单复数的变换规则，用于把所有的复数单词都转换成单数，并把大写的首字母都转换成小写。

import java.io.IOException;

public class cPluralFilter
{
	public static void main(String[] args) throws IOException
	{
		String oriPath = "M:\\Data mining data set\\Content\\";
//		PluralFilter(oriPath);
    }

//	@SuppressWarnings("deprecation")
//	public static void PluralFilter(String oriPath) throws IOException
//    {
//		File dirfile =new File(oriPath + "3_pluralFilter");
//		if  (!dirfile .exists()  && !dirfile .isDirectory())
//		{
//		    dirfile .mkdir();
//		}
//		for(int order = 1; order <= 3; order++)
//		{
//			//创建用于存储分面的1  2  3 文件夹
//			File file =new File(oriPath + "3_pluralFilter\\" + order);
//			if  (!file .exists()  && !file .isDirectory())
//			{
//			    file .mkdir();
//			}
//			String InputFilePath = oriPath + "2_UselessFilter\\" + order + "\\";
//			String [] fileName = new File(InputFilePath).list();
//	        for(String name:fileName)
//	        {
//	        	System.out.println("Plural filter\t" + order + "\t" + name);
//	    		BufferedReader  BR=new BufferedReader(new InputStreamReader(new FileInputStream(InputFilePath + name),"UTF-8"));
//	    		String lineString="";  //接收一行
//	    		String txtCont = "";
//	    		while (   (lineString=BR.readLine()  )!=null  )
//	    		{
//	    			lineString=lineString.replaceAll("\\s+", " ");  //用正则表达式替换空格为空
//	    			String newStr = "";
//	    			for (String str : lineString.split(" ")) {
//	    				if(str.toLowerCase().equals("data"))
//	    					newStr  = newStr + " " + "data";
//	    				else if (str.toLowerCase().equals("analysis"))
//	    					newStr  = newStr + " " + "analysis";
//	    				else
//	    					newStr  = newStr + " " + Inflector.getInstance().singularize(str).toLowerCase();
//	    	        }
//	    			txtCont = txtCont + newStr.trim() + "\n";
//	    		}
//	    		FileUtils.write(new File(oriPath + "3_pluralFilter\\" + order + "\\" + name), txtCont.trim());
//	    		BR.close(); //关闭文件
//	        }
//		}
//		System.out.println("Done.");
//    }
}
