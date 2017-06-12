package experiment;

import method.GetHyponymy;
import model.AllHyponymy;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/6/8.
 */
public class GroundTruthComplement {
    public static String oriPath = "M:\\我是研究生\\任务\\分面树的生成\\Facet\\";
    public static String domain = "Data_structure";

    public static void main(String[] args) {
        Complementation();
    }

    public static void Complementation() {
        List<String> fileName = BResult.GetNameOrder(oriPath + "otherFiles\\" + domain + "_topics.txt");
        for (String name : fileName) {
            System.out.println(name);
            List<String> facetName = BResult.GetNameOrder(oriPath + "ground truth\\" + name + ".txt");
            HashSet<String> facets = SaveListInSet(facetName);
            String cont = "";
            for (String f : facets) {
                cont = cont + f.toLowerCase() + "\n";
            }
            if (!facets.contains("definition")) {
                cont = cont + "definition\n";
            }
            if (!facets.contains("property")) {
                cont = cont + "property\n";
            }
            if (!facets.contains("example")) {
                cont = cont + "example\n";
            }
            if (!facets.contains("application")) {
                cont = cont + "application\n";
            }
            if (!facets.contains("implementation")) {
                cont = cont + "implementation\n";
            }
            if (IsUp(name) && !facets.contains("type")) {
                cont = cont + "type\n";
            }
            cont = complement(facets, name, cont);
            //good ground truth
            try {
                FileUtils.write(new File(oriPath + "good ground truth\\" + name + ".txt"), cont, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String complement(HashSet<String> set, String name, String cont) {
        if (name.toLowerCase().contains("graph") || name.contains("tree") || name.contains("heap")) {
            if (!set.contains("representation")) {
                cont = cont + "representation\n";
            }
            if (!set.contains("construction")) {
                cont = cont + "construction\n";
            }
            if (!set.contains("operation")) {
                cont = cont + "operation\n";
            }
            if (!set.contains("method")) {
                cont = cont + "method\n";
            }
        }
        if (name.toLowerCase().contains("list") || name.toLowerCase().contains("array") || name.toLowerCase().contains("queue")) {
            if (!set.contains("dimension")) {
                cont = cont + "dimension\n";
            }
            if (!set.contains("operation")) {
                cont = cont + "operation\n";
            }
            if (!set.contains("method")) {
                cont = cont + "method\n";
            }
        }
        if (name.contains("algorithm")) {
            if (!set.contains("complexity")) {
                cont = cont + "complexity\n";
            }
            if (!set.contains("mechanism")) {
                cont = cont + "mechanism\n";
            }
        }
        if (name.contains("search")) {
            if (!set.contains("algorithm")) {
                cont = cont + "algorithm\n";
            }
        }
        return cont;
    }

    public static boolean IsUp(String name) {
        boolean isOrNot = false;
        AllHyponymy allHyponymy = GetHyponymy.GetHyponymyFromExl(oriPath + "otherFiles\\" + domain + "上下位.xls");
        ArrayList<String> upLocation = allHyponymy.getUpLocation();
        if (upLocation.contains(name)) isOrNot = true;
        return isOrNot;
    }

    public static HashSet<String> SaveListInSet(List<String> list) {
        HashSet<String> hashSet = new HashSet<>();
        for (String s : list) {
            hashSet.add(s.toLowerCase().trim());
        }
        hashSet.remove("type");
//        hashSet.remove("definition");
//        hashSet.remove("application");
//        hashSet.remove("example");
//        hashSet.remove("property");
        return hashSet;
    }
}
