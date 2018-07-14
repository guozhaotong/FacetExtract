package experiment;

import me.xiaosheng.word2vec.Word2Vec;
import newStep.BProcessSummary;
import newStep.CCalcSimilarity;
import org.apache.commons.io.FileUtils;
import utils.Word2VecUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/5/16.
 */
public class CreatePByWord2Vec {
    public static String domain = "Data_structure";
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static void main(String[] args) {
        p0();
    }

    public static void p0(){
        System.out.println("loading...");
        Word2Vec model = Word2VecUtil.getModel("M:\\我是研究生\\词表\\wiki50.en.text.vector.石磊");
        try {
            List<String> fileList = FileUtils.readLines(new File(oriPath + domain + "\\otherFiles\\" + domain + "_topics.txt"), "utf-8");
            ArrayList<List<String>> paraWordList = getParaWordList();
            double[][] p0 = new double[fileList.size()][fileList.size()];
            for(int i = 0; i < fileList.size(); i++){
                for(int j = 0; j < fileList.size(); j++) {
                    System.out.println("i = " + i + "\tj = " + j);
                    if(i == j){
                        p0[i][j] = 1;
                        continue;
                    }
                    p0[i][j] = model.sentenceSimilarity(paraWordList.get(i), paraWordList.get(j));
                }
            }
            CCalcSimilarity.writeMatrixToTxt(p0, "p0", "auto", "M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\0_summary\\");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<List<String>> getParaWordList(){
        ArrayList<List<String>> res = new ArrayList<>();
        try {
            List<String> fileList = FileUtils.readLines(new File(oriPath + domain + "\\otherFiles\\" + domain + "_topics.txt"), "utf-8");
            for(String fileName : fileList){
                System.out.println("Get word list " + fileName);
                List<String> wordList = paragraph2List(oriPath + domain + "\\0_summary\\0_origin\\" + fileName + ".txt");
                res.add(wordList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<String> paragraph2List(String paragraphPath){
        List<String> paraList = null;
        try {
            paraList = FileUtils.readLines(new File(paragraphPath), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String paragraph = BProcessSummary.proc(paraList);
        String[] strings = paragraph.split(" ");
        return Arrays.asList(strings);
    }
}
