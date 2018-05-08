package newStep;
//当出现see also等之后，就认为这些不是分面了，去掉。

import method.TxtToObject;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BUselessFilter
{
	public static void main(String[] args)
	{
		String oriP = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
//		String domain = "Data_structure";
//		String domain = "Data_mining";
        String domain = "C_programming_language";
		String oriPath = oriP + domain + "\\";
		UselessFilter(oriPath,domain);
	}

	public static void UselessFilter(String oriPath, String domain) {
		String[] prep = {"of", "for", "at", "in", "on", "over", "to", "by", "about", "under", "after"};
		String[] StopSaveWords = {"see also", "references", "external links", "further reading", "vs."};
        String[] UselessWords = {"overview", "bibliography", "other", "type", "types", "case", "class", "summary"};
        File dirfile =new File(oriPath + "2_UselessFilter");
		if (!dirfile.exists() && !dirfile.isDirectory())
		{       
		    dirfile .mkdir();    
		} 
		String InputFilePath = oriPath + "1_origin\\";
		List<String> fileName = null;
		try {
			fileName = FileUtils.readLines(new File(oriPath + "otherFiles\\" + domain + "_topics.txt"), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String name : fileName) {
        	System.out.println("Useless filter\t" + name);
        	Topic curtopic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
			List<Facet> firFacets = curtopic.getFacets();
			int iNo = 0;
			boolean stopSave = false;
			for (iNo = 0; iNo < firFacets.size(); iNo++) {
				for (String stopSaveW : StopSaveWords) {
					if (firFacets.get(iNo).getName().toLowerCase().equals(stopSaveW)) {
						stopSave = true;
						break;
					}
				}
				if (stopSave)
					break;
			}
			int reNum = firFacets.size() - iNo;
			for (int j = 0; j < reNum; j++) {
				firFacets.remove(iNo);
			}
			curtopic.setFacets(firFacets);
			String curfacet = "";
        	boolean removeFirFacet = false;
        	boolean removeSecFacet = false;
        	boolean removeThiFacet = false;
        	if(!curtopic.getFacets().isEmpty())
        	{
    			for(int i = 0; i < curtopic.getFacets().size(); i++)
    			{
    				for(String uslessW : UselessWords)
    				{
    					if(curtopic.getFacets().get(i).getName().toLowerCase().contains(uslessW))
    					{
    						curtopic.getFacets().remove(i--);
    						removeFirFacet = true;
    						break;
    					}
    					else removeFirFacet = false;
    				}
    				if(removeFirFacet) continue;
    				curfacet = " " + curtopic.getFacets().get(i).getName().toLowerCase() + " ";
    				for(String p : prep)
    				{
    					if(curfacet.contains(" " + p + " "))
    					{
    						curfacet = curfacet.split(" " + p + " ")[0] + " ";
    						curtopic.getFacets().get(i).setName(curfacet.trim());
    					}
    				}
    				if(!curtopic.getFacets().get(i).getNextFacets().isEmpty())
    				{
    					for(int j = 0; j < curtopic.getFacets().get(i).getNextFacets().size(); j++)
    					{
    						for(String uslessW : UselessWords)
    	    				{
    	    					if(curtopic.getFacets().get(i).getNextFacets().get(j).getName().toLowerCase().contains(uslessW))
    	    					{
    	    						curtopic.getFacets().get(i).getNextFacets().remove(j--);
    	    						removeSecFacet = true;
    	    						break;
    	    					}
    	    					else removeSecFacet = false;
    	    				}
    						if(removeSecFacet) continue;
    	    				curfacet = " " + curtopic.getFacets().get(i).getNextFacets().get(j).getName().toLowerCase() + " ";
    	    				for(String p : prep)
    	    				{
    	    					if(curfacet.contains(" " + p + " "))
    	    					{
    	    						curfacet = curfacet.split(" " + p + " ")[0] + " ";
    	    						curtopic.getFacets().get(i).getNextFacets().get(j).setName(curfacet.trim());
    	    					}
    	    				}
    	    				if(!curtopic.getFacets().get(i).getNextFacets().get(j).getNextFacets().isEmpty())
    	    				{
    	    					for(int k = 0; k < curtopic.getFacets().get(i).getNextFacets().get(j).getNextFacets().size(); k++)
    	    					{
    	    						for(String uslessW : UselessWords)
    	    	    				{
    	    	    					if(curtopic.getFacets().get(i).getNextFacets().get(j).getNextFacets().get(k).getName().toLowerCase().contains(uslessW))
    	    	    					{
    	    	    						curtopic.getFacets().get(i).getNextFacets().get(j).getNextFacets().remove(k--);
    	    	    						removeThiFacet = true;
    	    	    						break;
    	    	    					}
    	    	    					else removeThiFacet = false;
    	    	    				}
    	    						if(removeThiFacet) continue;
    	    	    				curfacet = " " + curtopic.getFacets().get(i).getNextFacets().get(j).getNextFacets().get(k).getName().toLowerCase() + " ";
    	    	    				for(String p : prep)
    	    	    				{
    	    	    					if(curfacet.contains(" " + p + " "))
    	    	    					{
    	    	    						curfacet = curfacet.split(" " + p + " ")[0] + " ";
    	    	    						curtopic.getFacets().get(i).getNextFacets().get(j).getNextFacets().get(k).setName(curfacet.trim());
    	    	    					}
    	    	    				}
    	    					}
    	    				}
    					}
    				}
    			}
        	}
        	TxtToObject.writeObjToTxt(curtopic, oriPath + "2_UselessFilter\\" + name + ".txt");
        }
		System.out.println(" Done.");
    }
}
