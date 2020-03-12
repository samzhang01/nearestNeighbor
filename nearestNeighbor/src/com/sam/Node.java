package com.sam;

import java.awt.Rectangle;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Completed this class
 * @author Sam Zhang 
 *
 */
public class Node implements INode {
	
	private String name = "";
	private int upDistance = 0; 
	private int downDistance = 0; 
	private int leftDistance = 0; 
	private int rightDistance = 0; 
	
	private Rectangle rectangle = null;
	private Set<INode> upNodes = new HashSet<INode>(); 
	private Set<INode> downNodes  = new HashSet<INode>(); 
	private Set<INode> leftNodes  = new HashSet<INode>(); 
	private Set<INode> rightNodes  = new HashSet<INode>(); 
	
	@Override
    public String toString() {
		StringBuilder stringB = new StringBuilder();
		stringB.append(name);
		stringB.append(" {\n");
		// up
		stringB.append("    upNodes: [");
		stringB.append(buildName(getUpNodes()));
		stringB.append("],\n");
		// down
		stringB.append("    downNodes: [");
		stringB.append(buildName(getDownNodes()));
		stringB.append("],\n");
		// left 
		stringB.append("    leftNodes: [");
		stringB.append(buildName(getLeftNodes()));
		stringB.append("],\n");
		// right
		stringB.append("    rightNodes: [");
		stringB.append(buildName(getRightNodes()));
		stringB.append("],\n");
    	return stringB.toString();
    }	
	
	private String buildName(Set<INode> nodes) {
		INode[] nodesArray = nodes.stream().sorted(Comparator.comparing(INode::getName)).toArray(INode[]::new);
		StringBuilder stringB = new StringBuilder();
		int length = nodesArray.length;
		// first one
		if (length > 0 ) stringB.append(nodesArray[0].getName());
		// second and more 
		if (length > 1 ) {
			// loop from second to the end
			for (int i = 1; i < length; i++ ){
				stringB.append(", ");
				stringB.append(nodesArray[i].getName());
			}
		}
			return stringB.toString();		
				
	}
	
	@Override
    public String getName() {
    	return name;
    }	
    @Override
    public void setName(String name) {
    	this.name = name;
    }
    
    @Override
    public int getUpDistance(){
    	return upDistance;
    }
    
    @Override
	public void setUpDistance(int upD){
    	upDistance = upD;
    }
	
    @Override
	public int getDownDistance(){
    	return downDistance;
    }
    
    @Override
	public void setDownDistance(int downD){
    	downDistance = downD;
    }
	
    @Override
	public int getLeftDistance(){
    	return leftDistance;
    }
    
    @Override
	public void setLeftDistance(int leftD){
    	leftDistance = leftD;
    }
	
    @Override
	public int getRightDistance() {
		return rightDistance;
	}
    @Override
	public void setRightDistance(int rightD) {
		rightDistance = rightD;
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		
		return rectangle;
	}

	@Override
	public Set<INode> getUpNodes() {
		// TODO Auto-generated method stub
		return upNodes;
	}

	@Override
	public Set<INode> getDownNodes() {
		// TODO Auto-generated method stub
		return downNodes;
	}

	@Override
	public Set<INode> getLeftNodes() {
		// TODO Auto-generated method stub
		return leftNodes;
	}

	@Override
	public Set<INode> getRightNodes() {
		// TODO Auto-generated method stub
		return rightNodes;
	}
	@Override
	public void setUpNodes(Set<INode> nodes) {
		// TODO Auto-generated method stub
		upNodes = nodes;
	}

	@Override
	public void setDownNodes(Set<INode> nodes) {
		// TODO Auto-generated method stub
		downNodes = nodes;
	}

	@Override
	public void setLeftNodes(Set<INode> nodes) {
		// TODO Auto-generated method stub
		leftNodes = nodes;
	}

	@Override
	public void setRightNodes(Set<INode> nodes) {
		// TODO Auto-generated method stub
		rightNodes = nodes; 
	}
		

	@Override
	public void setRect(Rectangle rec) {
		// TODO Auto-generated method stub
		rectangle = rec;
	}

}
