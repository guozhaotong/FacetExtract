package utils;
//用于找到excel中某一列出现的值的出现次数。
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class timesAppearedInExcel {
	public static void main(String[] args)
	{
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(new File("E:\\我是研究生\\任务\\分面树的生成\\Content\\otherFiles\\分面可用验证.xls"));
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = wb.getSheet(0); 
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		for (int i = 1; i < sheet.getRows(); i++)
		{
			String str = sheet.getCell(3,i).getContents(); //第几列的值
			if (map.containsKey(str)) 
                map.put(str, map.get(str) + 1);  
            else 
                map.put(str, 1);  
		}
		Set<?> set = map.entrySet();
		for(Iterator<?> iter = set.iterator(); iter.hasNext();)
		{
			Map.Entry entry = (Map.Entry)iter.next();
			String key = (String)entry.getKey();
		    Integer value = (Integer)entry.getValue();
	        System.out.println(key + " :\t" + value);
		}
	}
}
