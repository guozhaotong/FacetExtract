package MulanBaseline;

import mulan.classifier.lazy.MLkNN;
import mulan.classifier.meta.RAkEL;
import mulan.classifier.transformation.LabelPowerset;
import mulan.data.MultiLabelInstances;
import mulan.evaluation.Evaluator;
import mulan.evaluation.MultipleEvaluation;
import weka.classifiers.trees.J48;


/**
 * @author 郭朝彤
 * @date 2017/7/11.
 */
public class MulanExp1 {
    public static void main(String[] args) throws Exception {
//        String arffFilename = Utils.getOption("arff", args); // e.g. -arff emotions.arff
//        String xmlFilename = Utils.getOption("xml", args); // e.g. -xml emotions.xml
        String dataSetName = "facet";
        String arffFilename = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\experiment\\baseline\\引用最高的\\dataset\\" + dataSetName + "\\" + dataSetName + ".arff"; // e.g. -arff emotions.arff
        String xmlFilename = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\experiment\\baseline\\引用最高的\\dataset\\" + dataSetName + "\\" + dataSetName + ".xml"; // e.g. -xml emotions.xml

        MultiLabelInstances dataset = new MultiLabelInstances(arffFilename, xmlFilename);

        RAkEL learner1 = new RAkEL(new LabelPowerset(new J48()));

        MLkNN learner2 = new MLkNN();

        Evaluator eval = new Evaluator();
        MultipleEvaluation results;

        int numFolds = 10;
        results = eval.crossValidate(learner1, dataset, numFolds);
        System.out.println(results);
        System.out.println("=========================================.");
        results = eval.crossValidate(learner2, dataset, numFolds);
        System.out.println(results);
        System.out.println("done.");
    }
}
