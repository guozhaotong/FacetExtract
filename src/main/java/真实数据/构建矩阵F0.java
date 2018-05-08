package 真实数据;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;
import org.ujmp.core.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/4/19.
 */
public class 构建矩阵F0 {
    public static String domain = "人工智能";

    public static void main(String[] args) {
        try {
            List<String> fileName = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "主题.txt"), "utf-8");
            List<String> facetName = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\出现过的分面.txt"), "utf-8");
            Matrix F = FacetRepresentation(fileName, facetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Matrix FacetRepresentation(List<String> fileName, List<String> facetName) {
        System.out.println("正在构建矩阵F...");

        Matrix F = Matrix.Factory.zeros(fileName.size(), facetName.size());
        HashMap<String, Integer> facetNameMap = new HashMap<>();
        int i = 0;
        for (String name : facetName) {
            facetNameMap.put(name, i++);
        }
        HashMap<String, Integer> fileNameMap = new HashMap<>();
        i = 0;
        for (String name : fileName) {
            fileNameMap.put(name, i++);
        }

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "原始分面.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int j = 0; j < fileName.size(); j++) {
            list.add(0);
        }
        Sheet sheet = workbook.getSheet(0);
        int x = 0;
        String lastTopic = sheet.getCell(0, 1).getContents();
        for (i = 1; i < sheet.getRows(); i++) {
            String topic = sheet.getCell(0, i).getContents();
            String facet = sheet.getCell(1, i).getContents();
            if (topic.equals(lastTopic)) {
                if (facetNameMap.containsKey(facet)) {
                    F.setAsInt(1, fileNameMap.get(topic), facetNameMap.get(facet));
                    x++;
                }
            } else {
                list.set(fileNameMap.get(lastTopic), x);
                x = 0;
                if (facetNameMap.containsKey(facet)) {
                    F.setAsInt(1, fileNameMap.get(topic), facetNameMap.get(facet));
                }
            }
            lastTopic = topic;
        }
        list.set(fileNameMap.get(lastTopic), x);
        //最后一步：关闭资源
        workbook.close();
        StringBuilder stringBuilder = new StringBuilder("");
        for (int k : list) {
            stringBuilder.append(k + "\n");
        }
        try {
            FileUtils.write(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\主题原始分面数量.txt"), stringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return F;
    }
}
