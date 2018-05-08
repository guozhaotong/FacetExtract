package 真实数据;

import org.apache.commons.io.FileUtils;
import org.ujmp.core.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/4/19.
 */
public class 总的步骤 {
    public static String domain = "人工智能";

    public static void main(String[] args) {
        Matrix P0 = null;
        Matrix F = null;
        try {
            List<String> fileName = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\" + domain + "主题.txt"), "utf-8");
            P0 = 构建矩阵P0.CreateMatrixP0(fileName);
            List<String> facetName = FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\" + domain + "\\出现过的分面.txt"), "utf-8");
            F = 构建矩阵F0.FacetRepresentation(fileName, facetName);
            标签传播算法.label_propagation_ctopk(fileName, facetName, F, P0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
