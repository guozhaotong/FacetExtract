package method;

import model.Facet;
import model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于遍历一个主题所有的分面并且修改所有的分面
 *
 * @author 郭朝彤
 * @date 2017/4/17.
 */
public interface TraveralAndChange {

    default void traversalAllAndChange(Topic oneTopic) {
        List<Facet> facetList1 = new ArrayList<>();
        if (!oneTopic.getFacets().isEmpty()) {
            for (Facet facet : oneTopic.getFacets()) {
                Facet f1 = process(facet);
                List<Facet> facetList2 = new ArrayList<>();
                if (!facet.getNextFacets().isEmpty()) {
                    for (Facet secFacet : facet.getNextFacets()) {
                        Facet f2 = process(secFacet);
                        List<Facet> facetList3 = new ArrayList<>();
                        if (!secFacet.getNextFacets().isEmpty()) {
                            for (Facet thiFacet : secFacet.getNextFacets()) {
                                Facet f3 = process(thiFacet);
                                facetList3.add(f3);
                            }
                        }
                        f2.setNextFacets(facetList3);
                        facetList2.add(f2);
                    }
                }
                f1.setNextFacets(facetList2);
                facetList1.add(f1);
            }
        }
        oneTopic.setFacets(facetList1);
    }

    public Facet process(Facet facet);
}