package model;

import java.util.ArrayList;
import java.util.List;

public class Topic {
	String name;
	List<Facet> facets;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Facet> getFacets() {
		return facets;
	}


	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}

    public Topic(String name) {
        this.name = name;
    }

    public Topic(String name, List<Facet> facets) {
        this.name = name;
        this.facets = facets;
    }

	public Topic() {
		super();
	}


	@Override
	public String toString() {
        String s = "name:" + name + "\n\nfacets:\n";
        for (Facet facet : facets) {
            s = s + facet.toString() + "\n";
        }
        return s;
    }

    public void addFacet(String s) {
        List<Facet> empList = new ArrayList<>();
        Facet facet = new Facet(s, empList);
        List<Facet> facetList = this.getFacets();
        facetList.add(facet);
    }

    public boolean containsFacet(String s) {
        boolean res = false;
        for (Facet facet : this.getFacets()) {
            res = facet.containsFacet(s);
            if (res) {
                return true;
            }
        }
        return res;
    }

    public int facetNum() {
        int num = 0;
        num += this.getFacets().size();
        for (Facet facet1 : this.getFacets()) {
            num += facet1.getNextFacets().size();
            for (Facet facet2 : facet1.getNextFacets()) {
                num += facet2.getNextFacets().size();
            }
        }
        return num;
    }
}
