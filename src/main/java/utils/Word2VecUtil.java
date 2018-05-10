package utils;

import me.xiaosheng.word2vec.Word2Vec;

import java.io.IOException;

/**
 * @author 郭朝彤
 * @date 2018/5/10.
 */
public class Word2VecUtil {
    public static void main(String[] args) {
        Word2Vec model = Word2VecUtil.getModel("M:\\我是研究生\\词表\\wiki50.en.text.vector.石磊");
        float xxx = model.wordSimilarity("the", "or");
        //如果某一个单词在vec中不存在，则计算出xxx为0
        System.out.println(xxx);
    }

    public static Word2Vec getModel(String filePath) {
        //项目地址：https://github.com/jsksxs360/Word2Vec
        System.out.println("loading word2vec ...");
        long startTime = System.currentTimeMillis();
        Word2Vec vec = new Word2Vec();
        try {
            vec.loadGoogleModel(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("load word2vec done! used " + (System.currentTimeMillis() - startTime) + " millis.");
        return vec;


    }
}
