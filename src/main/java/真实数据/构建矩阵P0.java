package 真实数据;

import method.FindRelationship;
import org.apache.commons.io.FileUtils;
import org.ujmp.core.Matrix;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/4/19.
 */
public class 构建矩阵P0 {
    public static String domain = "人工智能";

    public static void main(String[] args) {
        try {
            List<String> fileName = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "主题.txt"), "utf-8");
            Matrix P0 = CreateMatrixP0(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于生成矩阵P0。
     *
     * @param fileName
     */
    public static Matrix CreateMatrixP0(List<String> fileName) {
        System.out.println("正在构建矩阵P0...");
        Double[][] p0 = new Double[fileName.size()][fileName.size()];
        for (int i = 0; i < fileName.size(); i++) {
            for (int j = 0; j < fileName.size(); j++) {
                p0[i][j] = Double.valueOf(0);
            }
        }
        ArrayList<String> upLocation = 获取出现的分面.readExlCol("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "上下位.xls", 1);
        ArrayList<String> dnLocation = 获取出现的分面.readExlCol("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "上下位.xls", 0);


        HashMap<String, Integer> fileNameMap = new HashMap<>();
        int i = 0;
        for (String name : fileName) {
            fileNameMap.put(name, i++);
        }
        DecimalFormat df = new DecimalFormat("#0.0000");
        for (i = 0; i < fileName.size(); i++) {
            p0[i][i] = Double.valueOf(1);
            //find parent topic
            for (int j = 0; j < upLocation.size(); j++) {
                if (dnLocation.get(j).equals(fileName.get(i))) {
                    String parentTopic = upLocation.get(j);
                    if (!fileNameMap.containsKey(parentTopic)) {
                        break;
                    }
                    p0[i][fileNameMap.get(parentTopic)] = 0.4;
                    p0[fileNameMap.get(parentTopic)][i] = 0.9;
                    break;
                }
            }
            ArrayList<String> brotherTopic = FindRelationship.findBrother(upLocation, dnLocation, fileName.get(i));
            if (brotherTopic.size() > 0) {
                for (String brother : brotherTopic) {
                    if (!fileNameMap.containsKey(brother)) {
                        break;
                    }
                    p0[i][fileNameMap.get(brother)] = 0.5;
                }
            }
        }
        Matrix pMatrix = Matrix.Factory.importFromArray(p0);
        return pMatrix;
    }
}
