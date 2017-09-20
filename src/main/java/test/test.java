package test;


import java.util.HashMap;

/**
 * @author 郭朝彤
 * @date 2017/7/7.
 */
public class test {
    public static void main(String[] args) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "2");
        map.put("2", "4");
        map.put("3", "6");
        map.put("4", "8");
        System.out.println(map.get("2"));
    }


}

