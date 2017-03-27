package method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;  
import java.util.Comparator;  
import java.util.Map.Entry;  

public class test {

	public static void main(String[] args) {
		boolean a = name();
	}
	
	public static boolean name() {
		int i = 0;
		while(true)
		{
			i++;
			if(i == 10)
				return true;
			else {
				System.out.println(i);
			}
		}
	}

}
