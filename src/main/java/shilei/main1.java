package shilei;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class main1
{

	public static void main(String[] args)
	{
		deduplication object = new deduplication();

		object.add("definition");
		object.add("definition", "order relation");
		object.add("operation", "searching");
		object.add("operation", "insertion");
		object.add("operation", "deletion");
		object.add("operation", "traversal");
		object.add("definition", "recursive definition");
		object.add("definition", "order relation");
		object.add("definition", "order relation", "leaf node");
		object.add("definition", "traversal", "leaf node");
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			json = mapper.writeValueAsString(object.getResult());
			String jsonProc = json.replaceAll("\"", "").replaceAll(":\\{},", "\n").replaceAll(":\\[]},", "\n").replaceAll(":\\{", "\n*****")
					.replaceAll(":\\[],", "\n*****").replaceAll(":\\[]}", "").replaceAll("}", "").replaceAll(":\\[", "\n##########")
					.replaceAll("],", "\n*****").replaceAll("\\{", "").replaceAll(",", "\n##########");
//			jsonProc = jsonProc;
			System.out.println(jsonProc);
//			FileUtils.write(new File("D://a.json"), json);
		} catch (JsonProcessingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
