package model;

import java.util.HashSet;

public class sureAndCand {
	private HashSet<String> sureFacet;
	private HashSet<String> candFacet;
	public HashSet<String> getSureFacet() {
		return sureFacet;
	}
	public void setSureFacet(HashSet<String> sureFacet) {
		this.sureFacet = sureFacet;
	}
	public HashSet<String> getCandFacet() {
		return candFacet;
	}
	public void setCandFacet(HashSet<String> candFacet) {
		this.candFacet = candFacet;
	}
	public sureAndCand(HashSet<String> sureFacet, HashSet<String> candFacet) {
		super();
		this.sureFacet = sureFacet;
		this.candFacet = candFacet;
	}
	public sureAndCand() {
		super();
	}
}
