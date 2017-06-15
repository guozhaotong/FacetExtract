package experiment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/5/25.
 */
public class test {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        String cont = "";
        for (String name : fileName) {
            if (name.toLowerCase().contains("algorithm")) {
                cont = cont + "1\n";
                continue;
            } else if (name.toLowerCase().contains("tree") || name.toLowerCase().contains("graph") || name.toLowerCase().contains("heap")) {
                cont = cont + "2\n";
                continue;
            } else if (name.toLowerCase().contains("array") || name.toLowerCase().contains("list") || name.toLowerCase().contains("queue")) {
                cont = cont + "3\n";
                continue;
            } else if (name.toLowerCase().contains("data_type")) {
                cont = cont + "4\n";
                continue;
            } else {
                cont = cont + "5\n";
                continue;
            }
        }
        try {
            FileUtils.write(new File(oriPath + "experiment\\topicCommunity.txt"), cont, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
