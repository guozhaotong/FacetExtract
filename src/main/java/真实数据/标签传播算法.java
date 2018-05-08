package 真实数据;

import experiment.AAppearedFacet;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/4/19.
 */
public class 标签传播算法 {
    public static String domain = "人工智能";

    public static void main(String[] args) {

    }

    public static void label_propagation_ctopk(List<String> fileName, List<String> facetList, Matrix f, Matrix p) {
        System.out.println("标签传播算法现在开始...");
        long[] f_size = f.getSize();
        long[] p_size = p.getSize();
        long m = f_size[0];
        long n = f_size[1];
        long tt = p_size[0];
        assert m == tt : "Dimension does not match.";
        p = AAppearedFacet.CreateMatrixP1(p);
        p = AAppearedFacet.CreateMatrixP2(p);
        f = AAppearedFacet.NormByRow(f);
        int k = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (f.getAsDouble(i, j) != 0) {
                    k++;
                }
            }
        }
        Matrix oneOfF = Matrix.Factory.ones(k, 3);
        k = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (f.getAsDouble(i, j) != 0) {
                    oneOfF.setAsInt(i, k, 0);
                    oneOfF.setAsInt(j, k, 1);
                    oneOfF.setAsDouble(f.getAsDouble(i, j), k, 2);
                    k++;
                }
            }
        }
        Matrix fTemp = f.clone();
        Matrix fPre = f.clone();
        ArrayList<Double> a = new ArrayList<>();
        for (int iter = 0; iter < 100; iter++) {
            System.out.println("\t正在进行第" + (iter + 1) + "次迭代...");
            fTemp = p.mtimes(fTemp);
            for (int t = 0; t < k; t++) {
                fTemp.setAsDouble(oneOfF.getAsDouble(t, 2), oneOfF.getAsInt(t, 0), oneOfF.getAsInt(t, 1));
            }
            fTemp = AAppearedFacet.NormByRow(fTemp);
            a.add(AAppearedFacet.AbsMax(fPre.minus(fTemp)));
            fPre = fTemp.clone();
            if (a.get(iter) <= 0.001) {
                break;
            }
        }
        ArrayList<Double> maxValue = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            maxValue.add(AAppearedFacet.AbsMax(fTemp.selectRows(Calculation.Ret.NEW, i)));
        }
        System.out.println("正在统计每个主题的分面个数...");
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "原始分面.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < sheet.getRows(); i++) {
            String topic = sheet.getCell(0, i).getContents();
            list.add(topic);
        }
        //最后一步：关闭资源
        workbook.close();
        List<String> facetNumTemp = null;
        try {
            facetNumTemp = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\主题原始分面数量.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Integer> facetNum = new ArrayList<>();
        for (String s : facetNumTemp) {
            facetNum.add(Integer.parseInt(s));
        }
        System.out.println("正在写入传播后的结果...");
        for (int i = 0; i < m; i++) {
            StringBuilder res = new StringBuilder("");
            int loop = 0;
            while (loop < facetNum.get(i) * 1.5) {
                long l = AAppearedFacet.MaxLocation(fTemp.selectRows(Calculation.Ret.NEW, i));
                res.append(facetList.get((int) l) + "\n");
                fTemp.setAsDouble(0.0, i, l);
                loop++;
            }
            res.append("性质\n定义\n举例\n应用\n");
            try {
                FileUtils.write(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\结果\\" + fileName.get(i).replaceAll("/", "-").replaceAll(":", "-").replaceAll("\\*", "-") + ".txt"), res, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
