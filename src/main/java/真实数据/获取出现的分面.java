package 真实数据;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author 郭朝彤
 * @date 2018/4/19.
 */
public class 获取出现的分面 {
    public static String domain = "人工智能";

    public static void main(String[] args) {
        getAppearedFacets("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "原始分面.xls");
    }

    public static void getAppearedFacets(String fileName) {
        HashSet<String> hashSet = new HashSet<>();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("摘要");
        arrayList.add("进一步阅读");
        arrayList.add("性质");
        arrayList.add("概览");
        arrayList.add("定义");
        arrayList.add("举例");
        arrayList.add("参阅");
        arrayList.add("应用");
        arrayList.add("注解与参考文献");
        arrayList.add("其他数据结构");
        ArrayList<String> res = readExlCol(fileName, 1);

        for (String content : res) {
            if (!arrayList.contains(content)) {
                hashSet.add(content);
            }
        }

        //最后一步：关闭资源
        writeContToTxt(hashSet, "C:\\Users\\tong\\Desktop\\" + domain + "\\出现过的分面.txt");
    }

    public static void writeContToTxt(HashSet<String> set, String fileName) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (String s : set) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        try {
            FileUtils.write(new File(fileName), stringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readExlCol(String exlName, int col) {
        ArrayList<String> res = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(exlName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        //2:获取第一个工作表sheet
        Sheet sheet = workbook.getSheet(0);
        //3:获取数据
        for (int i = 1; i < sheet.getRows(); i++) {
            String content = sheet.getCell(col, i).getContents();
            res.add(content);
        }

        //最后一步：关闭资源
        workbook.close();
        return res;
    }
}
