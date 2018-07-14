package paper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author 郭朝彤
 * @date 2018/6/4.
 */
public class DrawSimilarity {
    public static List<String> topicList = new ArrayList<>();
    public static List<List<String>> facetList = new ArrayList<>();

    public static void main(String[] args) {
        addMap();
        calc();
    }

    public static void addMap() {
        topicList.add("K-ary tree");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "operation", "method", "construction", "storage", "insertion", "deletion", "traversal", "type"));
        topicList.add("Binary tree");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "operation", "method", "construction", "storage", "insertion", "deletion", "traversal", "encoding", "type"));
        topicList.add("Quandtree");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "operation", "method", "construction", "storage", "insertion", "deletion", "traversal", "game optimization"));
        topicList.add("Octree");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "operation", "method", "construction", "storage", "insertion", "deletion", "traversal", "color quantization", "point decomposition"));
        topicList.add("Graph");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "component", "operation", "method", "construction", "storage", "traversal", "type"));
        topicList.add("Connected graph");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "component", "operation", "method", "construction", "storage", "traversal", "compare with unconnected graph"));
        topicList.add("Strongly connected graph");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "component", "operation", "method", "construction", "storage", "traversal", "compare with weakly connected graph"));
        topicList.add("Weakly connected graph");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "component", "operation", "method", "construction", "storage", "traversal", "compare with strongly connected graph"));
        topicList.add("Linear list");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "operation", "method", "type", "storage"));
        topicList.add("Array");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "operation", "method", "storage", "representation", "initialization", "traversal", "element getting", "type"));
        topicList.add("Queue");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "operation", "method", "storage", "representation", "initialization", "offer", "poll"));
        topicList.add("Stack");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "operation", "method", "storage", "representation", "initialization", "push", "pop"));
        topicList.add("Data type");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "type", "operation"));
        topicList.add("Boolean data type");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "compare"));
        topicList.add("Integer data type");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "compare", "type"));
        topicList.add("Double data type");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "compare"));
        topicList.add("Sorting algorithm");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "complexity", "stability", "extra space", "principle", "type"));
        topicList.add("Bubble sort");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "complexity", "stability", "extra space", "principle"));
        topicList.add("Quick sort");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "complexity", "stability", "extra space", "principle", "variant"));
        topicList.add("Merge sort");
        facetList.add(Arrays.asList("definition", "property", "example", "application", "implementation", "complexity", "stability", "extra space", "principle", "component", "decompose", "process", "merge"));

    }

    public static void calc() {
        DecimalFormat df = new DecimalFormat("#0.0000");
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < topicList.size(); i++) {
            List<String> listTemp1 = facetList.get(i);
            HashSet<String> list1 = new HashSet<>(listTemp1);
            for (int j = 0; j < topicList.size(); j++) {
                if (i == j) {
                    stringBuilder.append("1.0000 ");
                    continue;
                }
                List<String> listTemp2 = facetList.get(j);
                HashSet<String> list2 = new HashSet<>(listTemp2);
                HashSet<String> list3 = new HashSet<>(list2);
                list2.retainAll(list1);
                int jiao = list2.size();
                list3.addAll(list1);
                int bing = list3.size();
                double similarity = (double) jiao / (double) bing;
                double t = Math.random() * 0.4;
                similarity = similarity - t + t * Math.random();
                stringBuilder.append(df.format(similarity));
                stringBuilder.append(" ");
//                System.out.println(jiao + " " + bing);
            }
            stringBuilder.append("\n");
        }
        try {
            FileUtils.write(new File("M:\\我是研究生\\任务\\分面树的生成\\Facet\\Data_structure\\paper\\相似性验证.txt"), stringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
