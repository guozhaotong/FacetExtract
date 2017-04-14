package method;

import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 1; j < 3; j++)
			{
				if(list.get(i).contains(j + ""))
					list.remove(i);
			}
		}
		System.out.println(list);
		
	}
}
