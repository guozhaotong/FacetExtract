package 真实数据;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/4/19.
 */
public class 统计个数 {
    public static String domain = "人工智能";

    public static void main(String[] args) {
        stastic(domain);
    }

    public static void stastic(String domain) {
        Workbook workbook = null;
        try {
            System.out.println(domain);
            System.out.println("算法之前：");
            List<String> fileList = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "主题.txt"), "utf-8");
            HashMap<String, Integer> fileNameMap = new HashMap<>();
            int i = 0;
            for (String name : fileList) {
                fileNameMap.put(name, i++);
            }
            System.out.println("主题数量：" + fileList.size());
            workbook = Workbook.getWorkbook(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "原始分面.xls"));
            //2:获取第一个工作表sheet
            Sheet sheet = workbook.getSheet(0);
            //3:获取数据
            int total = sheet.getRows();
            System.out.println("平均分面数量：" + (double) (total / fileList.size()));
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < fileList.size(); j++) {
                list.add(0);
            }
            int x = 0;
            String lastTopic = sheet.getCell(0, 1).getContents();
            for (i = 1; i < sheet.getRows(); i++) {
                String topic = sheet.getCell(0, i).getContents();
                String facet = sheet.getCell(1, i).getContents();
                if (topic.equals(lastTopic)) {
                    x++;
                } else {
                    list.set(fileNameMap.get(lastTopic), x);
                    x = 0;
                }
                lastTopic = topic;
            }
            list.set(fileNameMap.get(lastTopic), x);
            int max = 0;
            int min = Integer.MAX_VALUE;
            for (int k : list) {
                if (k < min) {
                    min = k;
                }
                if (k > max) {
                    max = k;
                }
            }
            System.out.println("最多分面数量：" + max);
            System.out.println("最少分面数量：" + min);

            System.out.println("算法之后：");
            int sum = 0;
            max = 0;
            min = Integer.MAX_VALUE;
            String kk = "";
            for (String file : fileList) {
                List<String> facets = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\结果\\" + file.replaceAll("/", "-").replaceAll(":", "-").replaceAll("\\*", "-") + ".txt"), "utf-8");
                sum += facets.size();
                if (facets.size() > max) {
                    max = facets.size();
                    kk = file;
                }
                if (facets.size() < min) {
                    min = facets.size();
                }
            }
            System.out.println(kk);
            System.out.println("平均分面数量：" + (double) (sum / fileList.size()));
            System.out.println("最多分面数量：" + max);
            System.out.println("最少分面数量：" + min);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        //最后一步：关闭资源
        workbook.close();
    }
}
