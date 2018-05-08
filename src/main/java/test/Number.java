package test;

import experiment.BResult_delete4;
import method.TxtToObject;
import model.Topic;

import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/1/15.
 */
public class Number {
    static String domain = "Data_mining";
    static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\" + domain + "\\";

    public static void main(String[] args) {
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        String InputFilePath = oriPath + "2_UselessFilter\\";
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int num;
        int sum = 0;
        for (String name : fileName) {
//            System.out.println(name);
            Topic topic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            num = topic.facetNum();
            sum += num;
            if (num < min) {
                min = num;
                continue;
            }
            if (num > max) {
                max = num;
            }
        }
        double ave = (double) sum / fileName.size();
        System.out.println("max = " + max);
        System.out.println("min = " + min);
        System.out.println("sum = " + sum);
        System.out.println("ave = " + ave);
    }
}
