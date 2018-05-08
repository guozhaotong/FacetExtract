package 真实数据;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/4/26.
 */
public class 结果验证 {
    public static void main(String[] args) {
        veriRes("数据结构", "Data_Structure");
    }

    public static List<String> opRes(List<String> list) {
        list.add("实现");

        return list;
    }

    public static List<String> getOriginRes(String topic, List<String> zhuti, List<String> fenmian) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < zhuti.size(); i++) {
            if (zhuti.get(i).equals(topic)) {
                list.add(fenmian.get(i));
            }
        }
        return list;
    }

    public static void veriRes(String zhongwen, String yingwen) {
        //这两个存放原始主题分面对应关系
        List<String> zhuti = new ArrayList<>();
        List<String> fenmian = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("C:\\Users\\tong\\Desktop\\" + zhongwen + "\\" + zhongwen + "原始分面.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);
        for (int i = 1; i < sheet.getRows(); i++) {
            zhuti.add(sheet.getCell(0, i).getContents());
            fenmian.add(sheet.getCell(1, i).getContents());
        }
        workbook.close();
        try {
            List<String> originName = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + zhongwen + "\\" + zhongwen + "主题.txt"), "utf-8");
            List<String> zhEnName = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + zhongwen + "\\主题及应用中文.txt"));
            HashMap<String, String> map = 中文GroundTruth.getZhEn(yingwen);
            ArrayList<Double> p = new ArrayList<>();
            ArrayList<Double> r = new ArrayList<>();
            ArrayList<Double> f = new ArrayList<>();
            int j = 0;
            double pMax = Double.MIN_VALUE;
            int pIndex = 0;
            double rMax = Double.MIN_VALUE;
            int rIndex = 0;
            double fMax = Double.MIN_VALUE;
            int fIndex = 0;
            for (int i = 0; i < zhEnName.size(); i++) {
                zhEnName.set(i, zhEnName.get(i).split(" ")[1]);
                if (zhEnName.get(i).equals("NULL")) {
                    continue;
                }
//                List<String> real = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\"+ zhongwen +"\\结果\\" + originName.get(i) + ".txt"), "utf-8");
                List<String> real = getOriginRes(originName.get(i), zhuti, fenmian);
                int realNum = real.size();
                List<String> gtEn = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + yingwen + "\\good ground truth\\" + zhEnName.get(i) + ".txt"), "utf-8");
                HashSet<String> groundTruth = new HashSet<>();
                for (String s : gtEn) {
                    groundTruth.add(map.get(s));
                }
                int gtNum = groundTruth.size();
                groundTruth.retainAll(real);
                int sameNum = groundTruth.size();
                p.add(((double) sameNum / (double) realNum));
                r.add(((double) sameNum / (double) gtNum));
                if (p.get(j) > pMax) {
                    pMax = p.get(j);
                    pIndex = j;
                }
                if (r.get(j) > rMax) {
                    rMax = r.get(j);
                    rIndex = j;
                }
                if (p.get(j) == 0 || r.get(j) == 0) {
                    f.add((double) 0);
                } else {
                    f.add((2 * r.get(j) * p.get(j) / (r.get(j) + p.get(j))));
                }
                if (f.get(j) > fMax) {
                    fMax = f.get(j);
                    fIndex = j;
                }
                j++;
            }
            double sumP = 0, sumF = 0, sumR = 0;
            for (int i = 0; i < p.size(); i++) {
                sumP += p.get(i);
                sumR += r.get(i);
                sumF += f.get(i);
            }
            double averP = sumP / p.size(), averR = sumR / r.size(), averF = sumF / f.size();
            System.out.println("平均p:" + averP + "\t最大p:" + p.get(pIndex));
            System.out.println("平均r:" + averR + "\t最大r:" + r.get(rIndex));
            System.out.println("平均f:" + averF + "\t最大f:" + f.get(fIndex));
            System.out.println("最大p时： " + p.get(pIndex) + " " + r.get(pIndex) + " " + f.get(pIndex));
            System.out.println("最大r时： " + p.get(rIndex) + " " + r.get(rIndex) + " " + f.get(rIndex));
            System.out.println("最大f时： " + p.get(fIndex) + " " + r.get(fIndex) + " " + f.get(fIndex));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
