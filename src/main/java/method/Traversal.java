package method;

import model.Facet;
import model.Topic;

/**
 * 用于遍历一个主题所有的分面
 *
 * @author 郭朝彤
 * @date 2017/4/17.
 */
public interface Traversal {

    default void traversalAllFacets(Topic oneTopic) {
        if (!oneTopic.getFacets().isEmpty())
            for (Facet facet : oneTopic.getFacets()) {
                process(facet);
                if (!facet.getNextFacets().isEmpty())
                    for (Facet secFacet : facet.getNextFacets()) {
                        process(secFacet);
                        if (!secFacet.getNextFacets().isEmpty())
                            for (Facet thiFacet : secFacet.getNextFacets())
                                process(thiFacet);
                    }
            }
    }

    public void process(Facet facet);
}

