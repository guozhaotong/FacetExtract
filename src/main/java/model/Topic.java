package model;

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


	public Topic() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Topic [name=" + name + ", facets=" + facets + "]";
	}
	
	
	
	
}
