package method;

import model.Facet;
import model.Topic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author 郭朝彤
 * @date 2017/4/19.
 */
public class OperationToFacet {
    public static void main(String[] args) {
        Topic t1 = TxtToObject.SaveTxtToObj("M:\\我是研究生\\任务\\分面树的生成\\Facet\\2_UselessFilter\\2-3_tree.txt");
        System.out.println(t1.toString());
        Topic t2 = TxtToObject.SaveTxtToObj("M:\\我是研究生\\任务\\分面树的生成\\Facet\\2_UselessFilter\\2-3-4_tree.txt");
//        Facet f1 = t1.getFacets().get(1);
////        Facet f1 = t1.getFacets().get(0);
//        Facet f2 = t2.getFacets().get(0);
//        List<Facet> list1 = t1.getFacets();
//        List<Facet> list2 = t2.getFacets();
//        List<Facet> list = MergeFacets(list1,list2);
//        for(Facet facet : list)
//            System.out.println(facet.toString());
//        System.out.println(SameAs(f1, f2));
    }

    /**
     * 用于把两个List<Facet> f1和f2合并成一个List<Facet>（假设已经经过消重）
     * 这里分为两种情况：
     * 1. 如果f2中的facet没有在f1中出现，name把f2中这个没有出现的facet加给f1
     * 2. 如果f2中的某个facet在f1中出现了，但是，f2中facet的下位分面与f1中facet的下位分面合并，合并成一个List，并赋给f1中facet的下位分面。
     *
     * @param f1
     * @param f2
     * @return f1
     */
    public static List<Facet> MergeFacets(List<Facet> f1, List<Facet> f2) {
        HashSet<String> facets1 = new HashSet<>();

        for (Facet fa1 : f1) {
            facets1.add(fa1.getName());
        }
        for (Facet fa2 : f2) {
            if (!facets1.contains(fa2.getName())) {
                f1.add(fa2);
            } else {
                if (!SameAs(GetFacet(f1, fa2.getName()), fa2)) {
                    List<Facet> newFacetList = MergeFacets(fa2.getNextFacets(), GetFacet(f1, fa2.getName()).getNextFacets());
                    f1 = RemoveFacet(f1, fa2.getName());
                    f1.add(new Facet(fa2.getName(), newFacetList));
                }
            }
        }
        return f1;
    }

    public static Boolean Contain(List<Facet> list, String name) {
        Boolean contain = false;
        for (Facet facet : list) {
            if (facet.getName().equals(name)) {
                contain = true;
                break;
            }
        }
        return contain;
    }

    public static List<Facet> clone(List<Facet> list) {
        List<Facet> list1 = new ArrayList<>();
        for (Facet facet : list) {
            list1.add(facet);
        }
        return list1;
    }

    public static List<Facet> RemoveFacet(List<Facet> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                list.remove(i);
                break;
            }
        }
        return list;
    }

    /**
     * 用于在一个List<Facet>中，通过facet的name找到对应的facet。
     *
     * @param list
     * @param name
     * @return facet
     */
    public static Facet GetFacet(List<Facet> list, String name) {
        Facet facet = new Facet();
        for (Facet f : list) {
            if (f.getName().equals(name)) {
                facet = f;
            }
        }
        return facet;
    }

    /**
     * 用于判断两个Facet是否相同
     *
     * @param f1
     * @param f2
     * @return boolean
     */
    public static boolean SameAs(Facet f1, Facet f2) {
        boolean theSame = false;
        if (!f1.getName().equals(f2.getName())) {
            return theSame;
        }
        if (f1.getNextFacets().size() != f2.getNextFacets().size()) {
            return theSame;
        }
        HashSet<String> facet1 = new HashSet<>();
        for (Facet facet : f1.getNextFacets()) {
            facet1.add(facet.getName());
        }
        for (Facet facet : f2.getNextFacets()) {
            if (!facet1.contains(facet.getName())) {
                return theSame;
            }
        }
        for (Facet fa1 : f1.getNextFacets()) {
            for (Facet fa2 : f2.getNextFacets()) {
                if (fa1.getName().equals(fa2.getName())) {
                    theSame = SameAs(fa1, fa2);
                    if (!theSame)
                        return theSame;
                }
            }
        }
        theSame = true;
        return theSame;
    }
}
