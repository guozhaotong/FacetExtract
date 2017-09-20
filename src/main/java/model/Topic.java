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
		// TODO Auto-generated constructor stub
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
            if (res) return true;
        }
        return res;
    }
}
