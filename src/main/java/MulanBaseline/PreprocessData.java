package MulanBaseline;

import experiment.BResult_delete4;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/7/13.
 */
public class PreprocessData {
    public static void main(String[] args) {
        CreateXML();
        CreateArff();
        System.out.println("done");
    }

    public static void CreateXML() {
        List<String> facetList = BResult_delete4.GetNameOrder("M:\\我是研究生\\任务\\分面树的生成\\Facet\\experiment\\facet_order.txt");
        StringBuffer cont = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<labels xmlns=\"http://mulan.sourceforge.net/labels\">\n");
        for (String s : facetList) {
            cont.append("<label name=\"" + s.replaceAll(" ", "_") + "\"></label>\n");
        }
        cont.append("</labels>\n");
        try {
            FileUtils.write(new File("C:\\Users\\tong\\Desktop\\dataset\\facet\\facet.xml"), cont.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void CreateArff() {
        List<String> facetList = BResult_delete4.GetNameOrder("M:\\我是研究生\\任务\\分面树的生成\\Facet\\experiment\\facet_order.txt");
        StringBuffer cont = new StringBuffer("@relation FacetData\n\n");
        for (String s : facetList) {
            cont.append("@attribute " + s.replaceAll(" ", "_") + " {0, 1}\n");
        }
        cont.append("\n@data\n");
        List<String> fileName = BResult_delete4.GetNameOrder("M:\\我是研究生\\任务\\分面树的生成\\Facet\\otherFiles\\Data_structure_topics.txt");

        for (String name : fileName) {
            cont.append("{");
            List<String> topicFacet = BResult_delete4.GetNameOrder("M:\\我是研究生\\任务\\分面树的生成\\Facet\\good ground truth\\" + name + ".txt");
            for (int i = 0; i < facetList.size(); i++) {
                if (topicFacet.contains(facetList.get(i))) {
                    cont.append(i + " 1,");
                }
            }
            cont.deleteCharAt(cont.length() - 1);
            cont.append("}\n");
        }
        try {
            FileUtils.write(new File("C:\\Users\\tong\\Desktop\\dataset\\facet\\facet.arff"), cont.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
