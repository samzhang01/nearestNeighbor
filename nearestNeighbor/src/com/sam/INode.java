package com.sam;

import java.awt.Rectangle;
import java.util.Set;

public interface INode {
	public Rectangle getRect();
	public void setRect(Rectangle rec);
	public String getName();
	    
	public void setName(String name);  
	
	public int getUpDistance(); 
	public void setUpDistance(int upDistance); 
	
	public int getDownDistance(); 
	public void setDownDistance(int downDistance); 
	
	public int getLeftDistance(); 
	public void setLeftDistance(int leftDistance); 
	
	public int getRightDistance(); 
	public void setRightDistance(int rightDistance); 
	

	public Set<INode> getUpNodes();
	public Set<INode> getDownNodes();
	public Set<INode> getLeftNodes();
	public Set<INode> getRightNodes();
	
	public void setUpNodes(Set<INode> nodes);
	public void setDownNodes(Set<INode> nodes);
	public void setLeftNodes(Set<INode> nodes);
	public void setRightNodes(Set<INode> nodes);
	
}
