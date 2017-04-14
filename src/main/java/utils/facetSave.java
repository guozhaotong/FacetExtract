package utils;
//把分面信息存到数据库。
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

 
public class facetSave {
    public static void main(String[] args) throws Exception {
    	String oriPath = "E:\\我是研究生\\任务\\分面树的生成\\Content\\";
    	String dirFilePath = "10_layerFilter";
//    	doit1(oriPath,dirFilePath);
    	doit2(oriPath,dirFilePath);
    	System.out.println("Done.");
    }
   
    public static void doit1(String oriPath,String dirFilePath)
	{
    	String [] fileName = new File(oriPath + dirFilePath + "\\1\\").list();
    	for(String name: fileName)
    	{
	    	System.out.println("facetSave\t" + name);
	    	BufferedReader BR = null;
			try {
				BR = new BufferedReader(new InputStreamReader(new FileInputStream(oriPath + dirFilePath + "\\3\\" + name),"UTF-8"));
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				e.printStackTrace();
			}
			String lineString="";  //接收一行
			try {
				while (   (lineString=BR.readLine()  )!=null ) 
				{
					if((!lineString.contains("*****")) && (!lineString.contains("##########")))
		            insert1("facet_fin",name.replaceAll(".txt",""),lineString,"1");
				    else if(lineString.contains("*****"))
		            insert1("facet_fin",name.replaceAll(".txt",""),lineString.replaceAll("\\*\\*\\*\\*\\*",""),"2");
				    else
		            insert1("facet_fin",name.replaceAll(".txt",""),lineString.replaceAll("##########",""),"3");
				}BR.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
    	}
	}
 
    public static void doit2(String oriPath, String dirFilePath)
   	{
       	String [] fileName = new File(oriPath + dirFilePath + "\\1\\").list();
       	for(String name: fileName)
       	{
   	    	System.out.println("facetSave\t" + name);
   	    	BufferedReader BR = null;
   			try {
   				BR = new BufferedReader(new InputStreamReader(new FileInputStream(oriPath + dirFilePath + "\\3\\" + name),"UTF-8"));
   			} catch (UnsupportedEncodingException | FileNotFoundException e) {
   				e.printStackTrace();
   			}
   			String lineString="";  //接收一行
   			String facet1 = "";
   			String facet2 = "";
   			try {
   				while (   (lineString=BR.readLine()  )!=null ) 
   				{
   					if((!lineString.contains("*****")) && (!lineString.contains("##########")))
   						facet1 = lineString;
   				    else if(lineString.contains("*****"))
   				    {
   				    	facet2 = lineString.replaceAll("\\*\\*\\*\\*\\*","");
   				    	insert2("facet_relation_fin",name.replaceAll(".txt",""),facet1,"1",facet2,"2");
   				    }
   		            else
   		            {
   		            	insert2("facet_relation_fin",name.replaceAll(".txt",""),facet2,"2",lineString.replaceAll("##########",""),"3");
   		            }
   				}BR.close();
   			} catch (IOException e) {
   				e.printStackTrace();
   			}
   			
       	}
   	}
    
    public static boolean insert1(String tabel,String topic, String facet,String layer) {
		boolean result=false;
		
		mysqlUtils mysql=new mysqlUtils();
//        String sql = "update person set age = ? where name = ?";
        String sql = "insert into " + tabel + " values('Data structure',?,?,?)";
     	List<Object> params = new ArrayList<Object>();
     	params.add(topic);
     	params.add(facet);
     	params.add(layer);
     	
    	result=mysql.addDeleteModify(sql, params);
    	mysql.closeconnection();

		return result;
	}
    
    public static boolean insert2(String tabel, String topic, String parentfacet,String parentlayer, String childfacet, String childlayer) {
		boolean result=false;
		
		mysqlUtils mysql=new mysqlUtils();
//        String sql = "update person set age = ? where name = ?";
        String sql = "insert into " + tabel + " values('Data structure',?,?,?,?,?)";
     	List<Object> params = new ArrayList<Object>();
     	params.add(topic);
     	params.add(parentfacet);
     	params.add(parentlayer);
     	params.add(childfacet);
     	params.add(childlayer);
     	
    	result=mysql.addDeleteModify(sql, params);
    	mysql.closeconnection();

		return result;
	}
    
}