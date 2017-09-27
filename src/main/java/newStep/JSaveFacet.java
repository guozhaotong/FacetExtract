package newStep;

import experiment.BResult_delete4;
import method.TxtToObject;
import model.Facet;
import model.Topic;
import utils.mysqlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/9/20.
 */
public class JSaveFacet {
    static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    static String domain = "Data_structure";

    public static void main(String args[]) {
        SaveFacet();
    }

    public static void SaveFacet() {
        String InputFilePath = oriPath + "9_TransToChinese\\";
        String domain_CN = "数据结构";
        List<String> fileName = BResult_delete4.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        for (int i = 0; i < fileName.size(); i++) {
            String name = fileName.get(i);
            System.out.println(name);
            Topic topic = TxtToObject.SaveTxtToObj(InputFilePath + name + ".txt");
            SaveTopicToDB(topic, i, domain_CN);
        }
        System.out.println("done");
    }

    public static void SaveTopicToDB(Topic oneTopic, int i, String domain_CN) {
        String tabel1 = "topicFacet";
        String tabel2 = "facetRelation";
        String topicName = oneTopic.getName();
        for (Facet facet : oneTopic.getFacets()) {
            String facet1 = facet.getName();
            insert1(tabel1, String.valueOf(i), topicName, facet1, "1", domain_CN);
            for (Facet secFacet : facet.getNextFacets()) {
                String facet2 = secFacet.getName();
                insert1(tabel1, String.valueOf(i), topicName, facet2, "2", domain_CN);
                insert2(tabel2, facet2, "2", facet1, "1", String.valueOf(i), topicName, domain_CN);
                for (Facet thiFacet : secFacet.getNextFacets()) {
                    String facet3 = thiFacet.getName();
                    insert1(tabel1, String.valueOf(i), topicName, facet3, "3", domain_CN);
                    insert2(tabel2, facet3, "3", facet2, "2", String.valueOf(i), topicName, domain_CN);
                }
            }
        }
    }

    public static boolean insert1(String tabel, String TermID, String TermName, String FacetName, String FacetLayer, String ClassName) {
        boolean result = false;

        mysqlUtils mysql = new mysqlUtils();
        String sql = "insert into " + tabel + " values(?,?,?,?,?)";
        List<Object> params = new ArrayList<Object>();
        params.add(TermID);
        params.add(TermName);
        params.add(FacetName);
        params.add(FacetLayer);
        params.add(ClassName);

        result = mysql.addDeleteModify(sql, params);
        mysql.closeconnection();

        return result;
    }

    public static boolean insert2(String tabel, String ChildFacet, String ChildLayer, String ParentFacet, String ParentLayer, String TermID, String TermName, String ClassName) {
        boolean result = false;

        mysqlUtils mysql = new mysqlUtils();
//        String sql = "update person set age = ? where name = ?";
        String sql = "insert into " + tabel + " values(?,?,?,?,?,?,?)";
        List<Object> params = new ArrayList<Object>();
        params.add(ChildFacet);
        params.add(ChildLayer);
        params.add(ParentFacet);
        params.add(ParentLayer);
        params.add(TermID);
        params.add(TermName);
        params.add(ClassName);

        result = mysql.addDeleteModify(sql, params);
        mysql.closeconnection();

        return result;
    }
}
