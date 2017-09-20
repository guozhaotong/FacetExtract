package model;

import java.util.List;

/**
 * 
 * @author 郭朝彤
 *
 */
public class Facet {
	private String name;
	private List<Facet> nextFacets;
	
	
	public Facet(String name) {
		super();
		this.name = name;
	}
	public Facet() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Facet> getNextFacets() {
		return nextFacets;
	}
	public void setNextFacets(List<Facet> nextFacets) {
		this.nextFacets = nextFacets;
	}

    public Facet(String name, List<Facet> nextFacets) {
        this.name = name;
        this.nextFacets = nextFacets;
    }

    public String toString() {
        String s = name + "\n";
        for (Facet f : nextFacets) {
            s = s + "    " + f.getName() + "\n";
            if (f.getNextFacets().size() > 0) {
                for (Facet f2 : f.getNextFacets()) {
                    s = s + "        " + f2.getName() + "\n";
                }
            }
        }
        return s.trim();
    }

    public boolean containsFacet(String s) {
        boolean res = false;
        if (this.getName().equals(s)) {
            return true;
        } else {
            for (Facet facet : this.getNextFacets()) {
                res = facet.containsFacet(s);
                if (res) return true;
            }
        }
        return false;
    }
}
