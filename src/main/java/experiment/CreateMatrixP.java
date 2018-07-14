package experiment;

import newStep.CCalcSimilarity;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/5/10.
 */
public class CreateMatrixP {
    public static void main(String[] args) {
        try {
            List<String> fileList = FileUtils.readLines(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\otherFiles\\Data_structure_topics.txt"), "utf-8");
            double[][] p0 = new double[fileList.size()][fileList.size()];
            int poolSize = 10;
            for (int i = 0; i < fileList.size(); i++) {
                for (int j = 0; j < fileList.size(); j++) {
                    System.out.println("i = " + i + "\tj = " + j);
                    double[][] p = CCalcSimilarity.txtToMatrix("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\0_summary\\2_similarity\\" + fileList.get(i) + "_" + fileList.get(j) + ".txt");
                    double[][] p1 = pool(p, poolSize);
                    p0[i][j] = aver(p1);
                }
            }
            CCalcSimilarity.writeMatrixToTxt(p0, "p0", String.valueOf(poolSize), "M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\0_summary\\");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double aver(double[][] p) {
        double sum = 0;
        int total = 0;
        for (double[] d : p) {
            for (double n : d) {
                sum += n;
                total++;
            }
        }
        return (sum / (double) total);
    }

    public static double[][] pool(double[][] p, int poolSize) {
        int row = p.length;
        int col = p[0].length;
        int rowNum = row / poolSize;
        if (row % poolSize != 0) {
            rowNum++;
        }
        int colNum = col / poolSize;
        if (col % poolSize != 0) {
            colNum++;
        }
        double[][] matrix = new double[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                int rs = i * poolSize;
                int cs = j * poolSize;
                matrix[i][j] = maxValue(p, rs, cs, poolSize);
            }
        }
        return matrix;
    }

    public static double maxValue(double[][] p, int rs, int cs, int poolSize) {
        double max = 0;
        for (int i = rs; i < Math.min(rs + poolSize, p.length); i++) {
            for (int j = cs; j < Math.min(cs + poolSize, p[0].length); j++) {
                if (Math.abs(p[i][j]) > Math.abs(max)) {
                    max = p[i][j];
                }
            }
        }
        return max;
    }
}
