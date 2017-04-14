package model;

import java.util.ArrayList;

public class oneNode {
	private String nodeName;
	private int layer;
	private int disToLeaf;
	private ArrayList<String> parentNodes;
	private ArrayList<String> childNodes;
	private ArrayList<String> brotherNodes;
	public oneNode(String nodeName, int layer, int disToLeaf, ArrayList<String> parentNodes,
			ArrayList<String> childNodes, ArrayList<String> brotherNodes) {
		super();
		this.nodeName = nodeName;
		this.layer = layer;
		this.disToLeaf = disToLeaf;
		this.parentNodes = parentNodes;
		this.childNodes = childNodes;
		this.brotherNodes = brotherNodes;
	}
	public oneNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getDisToLeaf() {
		return disToLeaf;
	}
	public void setDisToLeaf(int disToLeaf) {
		this.disToLeaf = disToLeaf;
	}
	public ArrayList<String> getParentNodes() {
		return parentNodes;
	}
	public void setParentNodes(ArrayList<String> parentNodes) {
		this.parentNodes = parentNodes;
	}
	public ArrayList<String> getChildNodes() {
		return childNodes;
	}
	public void setChildNodes(ArrayList<String> childNodes) {
		this.childNodes = childNodes;
	}
	public ArrayList<String> getBrotherNodes() {
		return brotherNodes;
	}
	public void setBrotherNodes(ArrayList<String> brotherNodes) {
		this.brotherNodes = brotherNodes;
	}

	
}
