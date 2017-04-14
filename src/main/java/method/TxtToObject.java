package method;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Facet;
import model.Topic;

public class TxtToObject {

	public static void main(String[] args) {
		PhysicalToJavaObject("M:\\我是研究生\\任务\\分面树的生成\\Facet\\1_origin\\", "Binary_tree");
	}

	public static void PhysicalToJavaObject(String InputFilePath, String name) {
		Topic oneTopic = SaveTxtToObj(InputFilePath + name + ".txt");
		
//		下面这段代码是在遍历一个主题的所有的分面，有需要的时候直接copy，并把其中的输出语句改成想要的语句就行了
		if(!oneTopic.getFacets().isEmpty())
			for(Facet facet : oneTopic.getFacets())
			{
				System.out.println(facet.getName());
				if(!facet.getNextFacets().isEmpty())
					for(Facet secFacet: facet.getNextFacets())
					{
						System.out.println(secFacet.getName());
						if(!secFacet.getNextFacets().isEmpty())
							for(Facet thiFacet: secFacet.getNextFacets())
								System.out.println(thiFacet.getName());
					}
			}
	}
	
	public static Topic SaveTxtToObj(String filePath) {
		Topic oneTopic = new Topic();
		try {
			String content = FileUtils.readFileToString(new File(filePath),"utf-8");
			Gson gson = new Gson();
			oneTopic = gson.fromJson(content, Topic.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return oneTopic;
	}
	
	public static void writeObjToTxt(Topic topic, String txtPath) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			FileUtils.write(new File(txtPath), gson.toJson(topic), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
