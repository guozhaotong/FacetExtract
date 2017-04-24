package method;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Topic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TxtToObject {

	public static void main(String[] args) {
		PhysicalToJavaObject("M:\\我是研究生\\任务\\分面树的生成\\Facet\\1_origin\\", "Binary_tree");
	}

	public static void PhysicalToJavaObject(String InputFilePath, String name) {
		Topic oneTopic = SaveTxtToObj(InputFilePath + name + ".txt");

//		Traversal traversal=new Traversal() {
//
//            @Override
//            public void process(Object o) {
//                System.out.println(o.getName());
//            }
//        };
//
//		traversal.traversalAllFacets(oneTopic);

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
