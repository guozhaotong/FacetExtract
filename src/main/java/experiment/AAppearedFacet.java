package experiment;

import method.FindRelationship;
import method.GetHyponymy;
import method.TxtToObject;
import model.AllHyponymy;
import model.Facet;
import model.Topic;
import org.apache.commons.io.FileUtils;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/5/25.
 */
public class AAppearedFacet {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String args[]) {
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        List<String> facetList = BResult_delete4.GetNameOrder(oriPath + "experiment\\facet_order.txt");
//        FindAppearedFacet(fileName);
        Matrix f = FacetRepresentation(fileName, facetList);
        Matrix p0 = CreateMatrixP0(fileName);
        Matrix p1 = CreateMatrixP1(p0);
        Matrix p2 = CreateMatrixP2(p1);
        label_propagation_ctopk(fileName, facetList, f, p2.transpose(Ret.LINK));
        BResult_delete4.GetWholeResult();
        resAnalysis();
        System.out.println("done.");
    }

    public static void resAnalysis() {
        Matrix micro_res = ImportMatrix(oriPath + "experiment\\microMetrics.txt");
        Matrix macro_res = ImportMatrix(oriPath + "experiment\\macroMetrics.txt");
        Matrix ham_loss = micro_res.selectColumns(Ret.NEW, 0);
        Matrix p_micro = micro_res.selectColumns(Ret.NEW, 1);
        Matrix r_micro = micro_res.selectColumns(Ret.NEW, 2);
        Matrix f_micro = micro_res.selectColumns(Ret.NEW, 3);
        Matrix should = macro_res.selectColumns(Ret.NEW, 0);
        Matrix actual = macro_res.selectColumns(Ret.NEW, 1);
        Matrix shoact = macro_res.selectColumns(Ret.NEW, 2);
        long[] micro_size = f_micro.getSize();
        long m = micro_size[0];
        double ham_loss_aver = SumMatrix(ham_loss) / m;
        double p_micro_aver = SumMatrix(p_micro) / m;
        double r_micro_aver = SumMatrix(r_micro) / m;
        double f_micro_aver = SumMatrix(f_micro) / m;
        long[] macro_size = macro_res.getSize();
        m = macro_size[0];
        Matrix p_macro = Matrix.Factory.ones(m, 1);
        Matrix r_macro = Matrix.Factory.ones(m, 1);
        Matrix f_macro = Matrix.Factory.ones(m, 1);
        int zeroNum = 0;
        for (int i = 0; i < m; i++) {
            if (actual.getAsDouble(i, 0) == 0)
                p_macro.setAsDouble(0.0, i, 0);
            else
                p_macro.setAsDouble((shoact.getAsDouble(i, 0) / actual.getAsDouble(i, 0)), i, 0);
            if (should.getAsDouble(i, 0) == 0) {
                zeroNum++;
                r_macro.setAsDouble(0.0, i, 0);
            } else
                r_macro.setAsDouble((shoact.getAsDouble(i, 0) / should.getAsDouble(i, 0)), i, 0);
            if ((p_macro.getAsDouble(i, 0) + r_macro.getAsDouble(i, 0)) == 0)
                f_macro.setAsDouble(0.0, i, 0);
            else
                f_macro.setAsDouble((2 * r_macro.getAsDouble(i, 0) * p_macro.getAsDouble(i, 0) / (r_macro.getAsDouble(i, 0) + p_macro.getAsDouble(i, 0))), i, 0);
        }
        double p_macro_aver = SumMatrix(p_macro) / (m - zeroNum);
        double r_macro_aver = SumMatrix(r_macro) / (m - zeroNum);
        double f_macro_aver = SumMatrix(f_macro) / (m - zeroNum);
        System.out.println("海明损失：" + ham_loss_aver);
        System.out.println("宏平均：\t\t\t\t\t微平均：");
        System.out.println("p = " + p_macro_aver + "\t\tp = " + p_micro_aver);
        System.out.println("r = " + r_macro_aver + "\t\tr = " + r_micro_aver);
        System.out.println("f = " + f_macro_aver + "\t\tf = " + f_micro_aver);
    }

    public static double SumMatrix(Matrix matrix) {
        double value = 0.0;
        long[] matrix_size = matrix.getSize();
        long m = matrix_size[0];
        long n = matrix_size[1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                value = value + matrix.getAsDouble(i, j);
            }
        }
        return value;
    }

    public static Matrix ImportMatrix(String txtPath) {
        List<String> list = BResult_delete4.GetNameOrder(txtPath);
        int m = list.size();
        String[] listStrings = list.get(0).split(" ");
        int n = listStrings.length;
        Matrix matrix = Matrix.Factory.ones(m, n);
        int i = 0;
        for (String string : list) {
            listStrings = list.get(i).split(" ");
            int j = 0;
            for (String str : listStrings) {
                matrix.setAsDouble(Double.parseDouble(str), i, j);
                j++;
            }
            i++;
        }
        return matrix;
    }

    public static void label_propagation_ctopk(List<String> fileName, List<String> facetList, Matrix f, Matrix p) {
        System.out.println("标签传播算法现在开始...");
        long[] f_size = f.getSize();
        long[] p_size = p.getSize();
        long m = f_size[0];
        long n = f_size[1];
        long tt = p_size[0];
        assert m == tt : "Dimension does not match.";
        f = NormByRow(f);
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
            fTemp = NormByRow(fTemp);
            a.add(AbsMax(fPre.minus(fTemp)));
            fPre = fTemp.clone();
            if (a.get(iter) <= 0.001) break;
        }
//        System.out.println(a);
        ArrayList<Double> maxValue = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            maxValue.add(AbsMax(fTemp.selectRows(Ret.NEW, i)));
        }
        List<String> tempList = BResult_delete4.GetNameOrder(oriPath + "experiment\\topicCommunity.txt");
        ArrayList<Integer> nameCommuList = StringListToInt(tempList);
        System.out.println("正在写入传播后的结果...");
        for (int i = 0; i < m; i++) {
            String res = "";
            Matrix kk = Matrix.Factory.ones(1, 5);
            kk.setAsInt(2, 0, 1);
            kk.setAsInt(2, 0, 2);
            int loop = 0;
            while (loop < kk.getAsInt(0, nameCommuList.get(i) - 1)) {
                long l = MaxLocation(fTemp.selectRows(Ret.NEW, i));
                if (f.getAsDouble(i, l) != 0) {
                    fTemp.setAsDouble(0.0, i, l);
                } else {
                    res = res + facetList.get((int) l) + "\n";
                    fTemp.setAsDouble(0.0, i, l);
                    loop++;
                }
            }
            try {
                FileUtils.write(new File(oriPath + "experiment\\result\\" + fileName.get(i) + ".txt"), res, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用于找到一个行矩阵的最大值出现在的位置。
     *
     * @param matrix：这需要是一个行矩阵。一行，n列。
     * @return
     */
    public static long MaxLocation(Matrix matrix) {
        long l = 0;
        double maxValue = AbsMax(matrix);
        long[] matrix_size = matrix.getSize();
        long n = matrix_size[1];
        for (int i = 0; i < n; i++) {
            if (matrix.getAsDouble(0, i) == maxValue) {
                l = i;
                break;
            }
        }
        return l;
    }

    /**
     * 用于把String类型的ArrayList转换成Integer类型的。
     *
     * @param list
     * @return
     */
    public static ArrayList<Integer> StringListToInt(List<String> list) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (String s : list) {
            try {
                integerArrayList.add(Integer.valueOf(s).intValue());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return integerArrayList;
    }

    /**
     * 用于计算一个矩阵的元素中，绝对值最大的那个值。
     *
     * @param matrix
     * @return
     */
    public static double AbsMax(Matrix matrix) {
        double maxValue = 0.0;
        long[] matrix_size = matrix.getSize();
        long m = matrix_size[0];
        long n = matrix_size[1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double absValue = matrix.getAsDouble(i, j);
                if (absValue < 0.0)
                    absValue = absValue * (-1);
                if (absValue > maxValue)
                    maxValue = absValue;
            }
        }
        return maxValue;
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
        AllHyponymy allHyponymy = GetHyponymy.GetHyponymyFromExl(oriPath + "otherFiles\\" + domain + "上下位.xls");
        ArrayList<String> upLocation = allHyponymy.getUpLocation();
        ArrayList<String> dnLocation = allHyponymy.getDnLocation();
        ArrayList<Double> parentToChild = new ArrayList<>();
        parentToChild.add((double) 1);
        parentToChild.add((double) 1);
        parentToChild.add(0.9819);
        parentToChild.add(0.9522);
        parentToChild.add(0.9239);
        parentToChild.add((double) 1);
        ArrayList<Double> childToParent = new ArrayList<>();
        childToParent.add((double) 0);
        childToParent.add(0.4481);
        childToParent.add(0.3923);
        childToParent.add(0.05);
        childToParent.add(0.17);
        childToParent.add(0.47);
        HashMap<String, Integer> fileNameMap = new HashMap<>();
        int i = 0;
        for (String name : fileName) {
            fileNameMap.put(name, i++);
        }
        DecimalFormat df = new DecimalFormat("#0.0000");
        for (i = 0; i < fileName.size(); i++) {
            p0[i][i] = Double.valueOf(1);
            int layer = FindRelationship.findLayer(upLocation, dnLocation, fileName.get(i), domain);
            ArrayList<String> parentTopic = FindRelationship.findDirectParent(upLocation, dnLocation, fileName.get(i));
            if (parentTopic.size() > 0) {
                for (String parent : parentTopic) {
                    p0[i][fileNameMap.get(parent)] = childToParent.get(layer);
                }
            }
            ArrayList<String> brotherTopic = FindRelationship.findBrother(upLocation, dnLocation, fileName.get(i));
            if (brotherTopic.size() > 0) {
                for (String brother : brotherTopic) {
                    p0[i][fileNameMap.get(brother)] = 0.45 + (0.075 * layer);
                }
            }
            ArrayList<String> childTopic = FindRelationship.FindDirectChild(allHyponymy, fileName.get(i));
            if (childTopic.size() > 0) {
                for (String child : childTopic) {
                    p0[i][fileNameMap.get(child)] = parentToChild.get(layer);
                }
            }
        }
        Matrix pMatrix = Matrix.Factory.importFromArray(p0);
//        printMatrix(pMatrix, oriPath + "experiment\\p0.txt");
        return pMatrix;
    }

    /**
     * 用于生成矩阵p1
     *
     * @param p0
     */
    public static Matrix CreateMatrixP1(Matrix p0) {
        System.out.println("正在构建矩阵P1...");
        long[] p0_size = p0.getSize();
        long n = p0_size[1];
        Matrix p1 = Matrix.Factory.zeros(n, n);
        Matrix pai = Matrix.Factory.zeros(n, n);
        for (int order = 0; order < n; order++) {
            Matrix s = Matrix.Factory.zeros(1, n);
            Matrix d = Matrix.Factory.zeros(1, n);
            Matrix p = Matrix.Factory.zeros(1, n);
            for (int i = 0; i < n; i++) {
                d.setAsDouble(p0.getAsDouble(order, i), 0, i);
                if (p0.getAsDouble(order, i) == 0)
                    p.setAsInt(-1, 0, i);
                else
                    p.setAsInt(order, 0, i);
            }
            s.setAsInt(1, 0, order);
            for (int i = 0; i < n; i++) {
                double temp = 0.0;
                int t = 0;
                for (int j = 0; j < n; j++) {
                    if (s.getAsInt(0, j) == 0 && d.getAsDouble(0, j) > temp) {
                        t = j;
                        temp = d.getAsDouble(0, j);
                    }
                }
                s.setAsInt(1, 0, t);
                if (s.getMinValue() == 1) break;
                for (int j = 0; j < n; j++) {
                    if (s.getAsInt(0, j) == 0 && p0.getAsDouble(t, j) > 0) {
                        if (d.getAsDouble(0, j) < d.getAsDouble(0, t) * p0.getAsDouble(t, j)) {
                            d.setAsDouble(d.getAsDouble(0, t) * p0.getAsDouble(t, j), 0, j);
                            p.setAsInt(t, 0, j);
                        }
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                p1.setAsDouble(d.getAsDouble(0, i), order, i);
                pai.setAsInt(p.getAsInt(0, i), order, i);
            }
        }
//        printMatrix(p1, oriPath + "experiment\\p1.txt");
        return p1;
    }

    public static Matrix CreateMatrixP2(Matrix p1) {
        System.out.println("正在构建矩阵P2...");
        long[] p1_size = p1.getSize();
        long n = p1_size[0];
        Matrix p2 = NormByRow(p1);
//        printMatrix(p2, oriPath + "experiment\\p2.txt");
        return p2;
    }


    /**
     * 用于把矩阵输出到文件中
     *
     * @param matrix
     * @param outputPath
     */
    public static void printMatrix(Matrix matrix, String outputPath) {
        long[] size = matrix.getSize();
        long m = size[0]; long n = size[1];
        String cont = "";
        DecimalFormat df = new DecimalFormat("#0.000000");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cont = cont + df.format(matrix.getAsDouble(i,j)) + " ";
            }
            cont = cont + "\n";
        }
        try {
            FileUtils.write(new File(outputPath), cont, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于把矩阵按行归一化
     *
     * @param oriMatrix
     * @return
     */
    public static Matrix NormByRow(Matrix oriMatrix) {
        long[] size = oriMatrix.getSize();
        long m = size[0];
        long n = size[1];
        Matrix normedMatrix = Matrix.Factory.zeros(m, n);
        for (int i = 0; i < m; i++) {
            double summary = 0.0;
            for (int j = 0; j < n; j++) {
                summary = summary + oriMatrix.getAsDouble(i, j);
            }
            if (summary == 0)
                continue;
            else {
                for (int j = 0; j < n; j++) {
                    normedMatrix.setAsDouble(oriMatrix.getAsDouble(i, j) / summary, i, j);
                }
            }
        }
        return normedMatrix;
    }

    /**
     * 用于生成表示主题有什么分面的向量。每个主题表示成一个向量，有哪个分面，那个位置相应的就为1，其他地方为0.
     * 其中，hashMap是用来存储所有分面的，key是String类型，表示出现过的分面，Value是Integer类型，表示顺序。表示顺序。表示顺序。喔~
     */
    public static Matrix FacetRepresentation(List<String> fileName, List<String> facetName) {
        System.out.println("正在构建矩阵F...");
        Matrix F = Matrix.Factory.zeros(fileName.size(), facetName.size());
        HashMap<String, Integer> facetNameMap = new HashMap<>();
        int i = 0;
        for (String name : facetName) {
            facetNameMap.put(name, i++);
        }
        long countOut = 0;
        String allCont = "";
        for (String name : fileName) {
            ArrayList<String> topicRepresentation = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                topicRepresentation.add("0");
            }
            Topic topic = TxtToObject.SaveTxtToObj(oriPath + "4_topicNameFilter\\" + name + ".txt");
            String cont = RepresentTopic(topicRepresentation, facetNameMap, topic);
            String[] conts = cont.split(" ");
            long countIn = 0;
            for (String contString : conts) {
                if (contString.equals("1")) {
                    F.setAsInt(1, countOut, countIn);
                }
                countIn++;
            }
//            try {
//                FileUtils.write(new File(oriPath + "experiment\\facet_representation\\" + name + ".txt"), cont, "utf-8");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            allCont = allCont + cont + "\n";
            countOut++;
        }
        try {
            FileUtils.write(new File(oriPath + "experiment\\f.txt"), allCont, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return F;
    }

    /**
     * 用于把ArrayList相应位置的值设置成1.
     *
     * @param arrayList
     * @param hashMap
     * @param topic
     * @return
     */
    public static String RepresentTopic(ArrayList<String> arrayList, HashMap<String, Integer> hashMap, Topic topic) {
        for (Facet facet : topic.getFacets()) {
            if (hashMap.containsKey(facet.getName())) {
                arrayList.set(hashMap.get(facet.getName()), "1");
            }
            for (Facet secFacet : facet.getNextFacets()) {
                if (hashMap.containsKey(secFacet.getName())) {
                    arrayList.set(hashMap.get(secFacet.getName()), "1");
                }
                for (Facet thiFacet : secFacet.getNextFacets())
                    if (hashMap.containsKey(thiFacet.getName())) {
                        arrayList.set(hashMap.get(thiFacet.getName()), "1");
                    }
            }
        }
        String cont = "";
        for (String s : arrayList) {
            cont = cont + s + " ";
        }
        return cont;
    }

    /**
     * 用于找到所有的主题所包含的所有分面列表。
     * 注意，其中，每个主题固有的那4个分面已经被去除。
     */
    public static void FindAppearedFacet(List<String> fileName) {
        HashSet<String> set = new HashSet<>();
        String filePath = oriPath + "4_topicNameFilter\\";
        for (String name : fileName) {
            Topic topic = TxtToObject.SaveTxtToObj(filePath + name + ".txt");
            set.addAll(FindFacetOfOneTopic(topic));
        }
        set.remove("definition");
        set.remove("property");
        set.remove("example");
        set.remove("application");
        String facetName = "";
        System.out.println(set.size());
        for (String string : set) {
            facetName = facetName + string + "\n";
        }
        System.out.println(facetName);
        try {
            FileUtils.write(new File(oriPath + "experiment\\facet_order.txt"), facetName, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done.");
    }

    /**
     * 用于统计一个主题所拥有的分面。
     *
     * @param oneTopic
     * @return
     */
    public static HashSet<String> FindFacetOfOneTopic(Topic oneTopic) {
        HashSet<String> hashSet = new HashSet<>();
        for (Facet facet : oneTopic.getFacets()) {
            hashSet.add(facet.getName());
            for (Facet secFacet : facet.getNextFacets()) {
                hashSet.add(secFacet.getName());
                for (Facet thiFacet : secFacet.getNextFacets())
                    hashSet.add(thiFacet.getName());
            }
        }
        return hashSet;
    }
}
