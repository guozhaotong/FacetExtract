package newStep;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/5/10.
 */
public class CCalcSimilarity {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        sim();
    }

    public static void sim() {
        try {
            List<String> fileList = FileUtils.readLines(new File(oriPath + domain + "\\otherFiles\\" + domain + "_topics.txt"), "utf-8");
            for (String file1 : fileList) {
                double[][] matrix1 = txtToMatrix(oriPath + domain + "\\0_summary\\1_preprocess\\" + file1 + ".txt");
                for (String file2 : fileList) {
                    double[][] matrix2 = txtToMatrix(oriPath + domain + "\\0_summary\\1_preprocess\\" + file2 + ".txt");
                    double[][] similarityMatrix = simMatrix(matrix1, matrix2);
                    writeMatrixToTxt(similarityMatrix, file1, file2, oriPath + domain + "\\0_summary\\2_similarity\\");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMatrixToTxt(double[][] matrix, String file1, String file2, String path){
        StringBuilder stringBuilder = new StringBuilder("");
        for(double[] m : matrix){
            for(double a : m){
                stringBuilder.append(a);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        try {
            FileUtils.write(new File( path+ file1 + "_" + file2 + ".txt"), stringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[][] simMatrix(double[][] a, double[][] b) {
        double[][] res = new double[a.length][b.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                res[i][j] = similarity(a[i], b[j]);
            }
        }
        return res;
    }

    public static double[][] txtToMatrix(String path) {
        try {
            List<String> list = FileUtils.readLines(new File(path), "utf-8");
            if (list.size() == 0) {
                return null;
            }
            double[][] p = new double[list.size()][list.get(0).split(" ").length];
            for (int i = 0; i < list.size(); i++) {
                String[] strings = list.get(i).split(" ");
                for (int j = 0; j < strings.length; j++) {
                    p[i][j] = Double.parseDouble(strings[j]);
                }
            }
            return p;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double similarity(double[] d1, double[] d2) {
        double fenzi = 0;
        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < d1.length; i++) {
            fenzi += d1[i] * d2[i];
            sum1 += d1[i] * d1[i];
            sum2 += d2[i] * d2[i];
        }
        return (fenzi / (Math.sqrt(sum1 * sum2)));
    }
}
