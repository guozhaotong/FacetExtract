package paper;

import me.xiaosheng.util.Segment;
import me.xiaosheng.word2vec.Word2Vec;
import newStep.BProcessSummary;
import org.apache.commons.io.FileUtils;
import utils.Word2VecUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalcSummarySim {

    public static void main(String[] args) throws Exception {
        Word2Vec vec = Word2VecUtil.getModel("M:\\我是研究生\\词表\\wiki50.en.text.vector.石磊");
        StringBuilder sb1 = new StringBuilder("");
        StringBuilder sb2 = new StringBuilder("");
        StringBuilder sb3 = new StringBuilder("");
        String[] fileList = "K-ary tree, Binary tree, Quadtree, Octree, Graph, Connected graph, Strongly connected graph, Weakly connected graph, Linear list, Array, Queue, Stack, Data type, Boolean data type, Integer data type, Double data type, Sorting algorithm, Bubble sort, Quick sort, Merge sort".split(", ");
        List<String> list = new ArrayList<>();
        for(String file : fileList){
            list.add(BProcessSummary.proc(FileUtils.readLines(new File("C:\\Users\\tong\\Desktop\\6files\\" + file + ".txt"), "utf-8")));
        }
        //分词，获取词语列表
        List<List<String>> wordLists = new ArrayList<>();
        for(String s : list){
            wordLists.add(Segment.getWords(s));
        }
        //快速句子相似度
        System.out.println("快速句子相似度:");
        for(int i = 0; i < fileList.length; i++){
            for(int j = 0; j < fileList.length; j++){
//                System.out.println(fileList[i] + " " + fileList[j] + " " + vec.fastSentenceSimilarity(wordLists.get(i), wordLists.get(j)));
                sb1.append(vec.fastSentenceSimilarity(wordLists.get(i), wordLists.get(j)));
                sb1.append(" ");
            }
            sb1.append("\n");
        }
        //句子相似度(所有词语权值设为1)
        System.out.println("句子相似度:");
        for(int i = 0; i < fileList.length; i++){
            for(int j = 0; j < fileList.length; j++){
//                System.out.println(fileList[i] + " " + fileList[j] + " " + vec.sentenceSimilarity(wordLists.get(i), wordLists.get(j)));
                sb2.append(vec.sentenceSimilarity(wordLists.get(i), wordLists.get(j)));
                sb2.append(" ");
            }
            sb2.append("\n");
        }
        //句子相似度(名词、动词权值设为1，其他设为0.8)
        System.out.println("句子相似度(名词、动词权值设为1，其他设为0.8):");
        List<float[]> weightArrays = new ArrayList<>();
        for(String s : list){
            weightArrays.add(Segment.getPOSWeightArray(Segment.getPOS(s)));
        }
        for(int i = 0; i < fileList.length; i++){
            for(int j = 0; j < fileList.length; j++){
//                System.out.println(fileList[i] + " " + fileList[j] + " " + vec.sentenceSimilarity(wordLists.get(i), wordLists.get(j), weightArrays.get(i), weightArrays.get(j)));
                sb3.append(vec.sentenceSimilarity(wordLists.get(i), wordLists.get(j), weightArrays.get(i), weightArrays.get(j)));
                sb3.append(" ");
            }
            sb3.append("\n");
        }
        FileUtils.write(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\paper\\简介相似度1.txt"), sb1.toString(), "utf-8");
        FileUtils.write(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\paper\\简介相似度2.txt"), sb2.toString(), "utf-8");
        FileUtils.write(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\paper\\简介相似度3.txt"), sb3.toString(), "utf-8");
    }


}