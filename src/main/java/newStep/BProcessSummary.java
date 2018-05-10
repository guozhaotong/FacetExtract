package newStep;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/5/10.
 */
public class BProcessSummary {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        try {
            //构建词语和向量的对应词典
            System.out.println("Word loading...");
            List<String> wordVec = FileUtils.readLines(new File("M:\\我是研究生\\词表\\wiki50.en.text.vector"), "utf-8");
            System.out.println("Dictionary creating...");
            System.out.println(wordVec.size());
            HashMap<String, String> map = new HashMap<>(2037031);
            for (String string : wordVec) {
                String word = string.split(" ")[0];
                String vec = string.replaceAll(word + " ", "");
                map.put(word, vec);
            }
            //help gc
            wordVec = null;

            List<String> fileList = FileUtils.readLines(new File(oriPath + domain + "\\otherFiles\\" + domain + "_topics.txt"), "utf-8");
            for (String fileName : fileList) {
                System.out.println(fileName);
                List<String> fileCont = FileUtils.readLines(new File(oriPath + domain + "\\0_summary\\0_origin\\" + fileName + ".txt"), "utf-8");
                String content = proc(fileCont);
                StringBuilder matrix = new StringBuilder("");
                for (String word : content.split(" ")) {
                    if (!map.containsKey(word)) {
                        continue;
                    }
                    matrix.append(map.get(word));
                    matrix.append("\n");
                }
                FileUtils.write(new File(oriPath + domain + "\\0_summary\\1_preprocess\\" + fileName + ".txt"), matrix.toString(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String proc(List<String> string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : string) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        String str = stringBuilder.toString();
        str = str.replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll(",", "")
                .replaceAll("\\.", "")
                .replaceAll(";", "")
                .replaceAll("\\[\\d+]", "")
                .replaceAll("-", " ")
                .replaceAll("\\[", " ")
                .replaceAll("—", " ")
                .replaceAll("\\d+", "")
                .replaceAll("\"", "");
        return str;
    }
}
