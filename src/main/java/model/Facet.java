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
	
	
}
