package utils;

import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

 
public class facetSave2 {
    public static void main(String[] args) throws Exception {
    	String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
        String dirFilePath = "7_facetHyponymyMerge\\";
        String domain = "Data_structure";
        String tableName1 = "facet_fin";
        String tableName2 = "facet_relation_fin";
        doit1(oriPath,dirFilePath,domain,tableName1,tableName2);
    	System.out.println("Done.");
    }
   
    public static void doit1(String oriPath, String dirPath, String domain, String table1, String table2)
	{
		try {
			List<String> fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
	    	for(String name: fileName)
	    	{
		    	System.out.println("facetSave\t" + name);
		    	Topic curTopic = TxtToObject.SaveTxtToObj(oriPath + dirPath + name + ".txt");
		    	if(!curTopic.getFacets().isEmpty())
					for(Facet facet : curTopic.getFacets())
					{
						insert1(table1,domain,name,facet.getName(),"1");
						if(!facet.getNextFacets().isEmpty())
							for(Facet secFacet: facet.getNextFacets())
							{
								insert1(table1,domain,name,secFacet.getName(),"2");
								insert2(table2, domain, name, facet.getName(), "1", secFacet.getName(), "2");
								if(!secFacet.getNextFacets().isEmpty())
									for(Facet thiFacet: secFacet.getNextFacets())
									{
										insert1(table1,domain,name,thiFacet.getName(),"3");
										insert2(table2, domain, name, secFacet.getName(), "2", thiFacet.getName(), "3");
									}
							}
					}
	    	}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

    public static boolean insert1(String tabel,String domain, String topic, String facet,String layer) {
		boolean result=false;
		
		mysqlUtils mysql=new mysqlUtils();
//        String sql = "update person set age = ? where name = ?";
        String sql = "insert into " + tabel + " values(?,?,?,?)";
     	List<Object> params = new ArrayList<Object>();
     	params.add(domain);
     	params.add(topic);
     	params.add(facet);
     	params.add(layer);
     	
    	result=mysql.addDeleteModify(sql, params);
    	mysql.closeconnection();

		return result;
	}
    
    public static boolean insert2(String tabel, String domain, String topic, String parentfacet,String parentlayer, String childfacet, String childlayer) {
		boolean result=false;
		
		mysqlUtils mysql=new mysqlUtils();
//        String sql = "update person set age = ? where name = ?";
        String sql = "insert into " + tabel + " values(?,?,?,?,?,?)";
     	List<Object> params = new ArrayList<Object>();
     	params.add(domain);
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