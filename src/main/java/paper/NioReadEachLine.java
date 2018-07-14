package paper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2018/6/14.
 */
public class NioReadEachLine {
    public static void main(String[] args) throws Exception {
        List<String> needToTraversal = new ArrayList<>();
        needToTraversal.add("yagoConteXtFacts_en");
        needToTraversal.add("yagoDBpediaInstances");
        needToTraversal.add("yagoLabels");
        needToTraversal.add("yagoRedirectLabels_en");
        needToTraversal.add("yagoSimpleTypes");
        needToTraversal.add("yagoSources");
        needToTraversal.add("yagoTransitiveType");
        needToTraversal.add("yagoTypes");
        needToTraversal.add("yagoTypesSources");
        needToTraversal.add("yagoWikidataInstances");
        needToTraversal.add("yagoWikipediaInfo_en");
        for (String curFile : needToTraversal) {
            StringBuilder out = new StringBuilder("");
            System.out.println(curFile);
            //指定读取文件所在位置
            File file = new File("M:\\我是研究生\\词表\\YAGO数据集\\" + curFile + ".ttl");
            FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            //使用temp字节数组用于存储不完整的行的内容
            byte[] temp = new byte[0];
            while (fileChannel.read(byteBuffer) != -1) {
                byte[] bs = new byte[byteBuffer.position()];
                byteBuffer.flip();
                byteBuffer.get(bs);
                byteBuffer.clear();
                int startNum = 0;
                //判断是否出现了换行符，注意这要区分LF-\n,CR-\r,CRLF-\r\n,这里判断\n
                boolean isNewLine = false;
                for (int i = 0; i < bs.length; i++) {
                    if (bs[i] == 10) {
                        isNewLine = true;
                        startNum = i;
                    }
                }

                if (isNewLine) {
                    //如果出现了换行符，将temp中的内容与换行符之前的内容拼接
                    byte[] toTemp = new byte[temp.length + startNum];
                    System.arraycopy(temp, 0, toTemp, 0, temp.length);
                    System.arraycopy(bs, 0, toTemp, temp.length, startNum);
                    String s = new String(toTemp);
                    if(s.contains("Binary_tree")){
                        out.append(s);
                        out.append("\n");
                    }
                    //将换行符之后的内容(去除换行符)存到temp中
                    temp = new byte[bs.length - startNum - 1];
                    System.arraycopy(bs, startNum + 1, temp, 0, bs.length - startNum - 1);
                    //使用return即为单行读取，不打开即为全部读取
//                return;
                } else {
                    //如果没出现换行符，则将内容保存到temp中
                    byte[] toTemp = new byte[temp.length + bs.length];
                    System.arraycopy(temp, 0, toTemp, 0, temp.length);
                    System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
                    temp = toTemp;
                }

            }
            if (temp.length > 0) {
                String s = new String(temp);
                if(s.contains("Binary_tree")){
                    out.append(s);
                    out.append("\n");
                }
            }
            FileUtils.write(new File("M:\\我是研究生\\词表\\YAGO中包含二叉树的数据\\" + curFile + ".txt"), out.toString(), "utf-8");
        }
    }
}
