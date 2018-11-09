package com.sam;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TestNodes {
	private final static Logger logger = LoggerFactory.getLogger(TestNodes.class);
	
	/*
AAA   BBB
     CC DDD
  EEEE FF GGG
  EEEE    GGG
 HHHHHHHHHH
	 */
	public static void main(String[] args) {
	logger.info("Starting ");
	
		Node a = createNode("A", 0, 0, 3, 1);
		Node b = createNode("B", 6, 0, 3, 1);
		Node c = createNode("C", 5, 1, 2, 1);
		Node d = createNode("D", 8, 1, 3, 1);
		Node e = createNode("E", 2, 2, 4, 2);
		Node f = createNode("F", 7, 2, 2, 1);
		Node g = createNode("G", 10, 2, 3, 2);
		Node h = createNode("H", 1, 4, 10, 1);
		
		
		linkNodes(a, b, c, d, e, f, g, h);

		assertNodeLinked(a, new Node[] {}, new Node[] {e}, new Node[] {}, new Node[] {b});
		assertNodeLinked(b, new Node[] {}, new Node[] {c, d}, new Node[] {a}, new Node[] {});
		//assertNodeLinked(c, new Node[] {b}, new Node[] {}, new Node[] {}, new Node[] {d});
		// adding down node neighbor e  
		assertNodeLinked(c, new Node[] {b}, new Node[] {e}, new Node[] {}, new Node[] {d});
		// assertNodeLinked(d, new Node[] {b}, new Node[] {f, g}, new Node[] {}, new Node[] {});
		// adding left neighbor c 
		assertNodeLinked(d, new Node[] {b}, new Node[] {f, g}, new Node[] {c}, new Node[] {});
		assertNodeLinked(e, new Node[] {c}, new Node[] {h}, new Node[] {}, new Node[] {f});
		assertNodeLinked(f, new Node[] {d}, new Node[] {h}, new Node[] {e}, new Node[] {g});
		//assertNodeLinked(g, new Node[] {d}, new Node[] {}, new Node[] {f}, new Node[] {});
		// added down neighbor H 
		assertNodeLinked(g, new Node[] {d}, new Node[] {h}, new Node[] {f}, new Node[] {});
		assertNodeLinked(h, new Node[] {e, g}, new Node[] {}, new Node[] {}, new Node[] {});
		
		logger.info("Starting to display recursively  all");
		displayRecursivelyAllNodesFromParentNode(a);
		
	}
	private static void linkNodes(Node...nodes) {
		int size = nodes.length;
		logger.info("No. of Nodes: " + Integer.toString(size));
		
		for (int i=0; i < size; i = i + 1) {
			
			Node node = nodes[i];
			logger.info(node.getName());
			// UpDown neighbor distance >=0
			// LeftRight Neighbor Distance > 0 
			getUpDownNodes(node, nodes);
			getLeftRightNodes(node, nodes);	
		}
	}
	
	private static void getUpDownNodes(INode node, Node[] nodes) 
	{
		// multiple nearest neighbors  
		// multiple overlapped rows 
		int nodeBottom = node.getRect().x;
		int nodeTop = nodeBottom + node.getRect().width;
		logger.debug("node:  buttom: " + nodeBottom + " top " + nodeTop);
		
		int size = nodes.length;
				for (int i=0; i < size; i = i + 1) {
				//find the overlap for each n(eighbor) node 
				INode neighborNode = nodes[i];
				logger.debug("Node " + node.getName() + " to Neighbor Name " + neighborNode.getName());
				int nNodeBottom = neighborNode.getRect().x;
				int nNodeTop = nNodeBottom + neighborNode.getRect().width;
				
				logger.debug("nNode:  buttom: " + nNodeBottom + " top " + nNodeTop);
				// check if there is overlap   
				// from Max Bottom
				int overLapBottom = nodeBottom; 
				if (nodeBottom  <  nNodeBottom) {
				overLapBottom = nNodeBottom; 
				} 
				// to min top
				int overLapTop = nodeTop; 
				if (nodeTop  >  nNodeTop) {
					overLapTop = nNodeTop; 
				} 
				logger.debug("overlap from buttom: " + Integer.toString(overLapBottom) + " to top: " + Integer.toString(overLapTop));
			
				// overlapped rows 
				if ((overLapTop - overLapBottom) > 0 ) {
					//
					// add the height of a line
					//
					// if overLapTop is greater that  overLapBottom, 
					// the left distance is from this node's left side to the neighbor node's left side.    
					int upD = node.getRect().y - neighborNode.getRect().y - neighborNode.getRect().height + 1;  
					// the tight distance is from neighbor's left side to this node's right side.
					int downD = neighborNode.getRect().y - node.getRect().y - node.getRect().height + 1; 
				logger.debug("upDistance: " + Integer.toString(upD) + "\n");
				// left distance
				
				if (upD > 0) {
					if (node.getUpDistance() == 0) {		
						// first neighbor 
						node.setUpDistance(upD);
						node.getUpNodes().add(neighborNode); 
						logger.info(" new Up neighbor: " + node.getName() + " -> " + neighborNode.getName() + "\n\n");
					} else {
						// add more neighbor with same distance 
						if (upD == node.getUpDistance()) {
							node.getUpNodes().add(neighborNode);
							logger.info("add another up neighbor:" + node.getName() + " -> + " + neighborNode.getName() + "\n\n\n");
						} // same distance neighbor
						// find an nearer neighbors, replace the old neighbors
						if (upD < node.getUpDistance()) {
							node.setUpDistance(upD);  // set new distance
							node.getUpNodes().clear();  // clear all 
							node.getUpNodes().add(neighborNode);
							logger.info("Replace node Up neighbor: " + node.getName() + " > - + " + neighborNode.getName() + "\n\n\n\n");
							
						} // find nearer neighbor 
					} // added new neighbor or replace with nearer neighbor 
				} // only right neighbor 
				
				//  right neighbor 
				if (downD > 0) {
					if (node.getDownDistance() == 0) {
						
						// first neighbor 
						node.setDownDistance(downD);
						node.getDownNodes().add(neighborNode); 
						logger.info(" new down neighbor: " + node.getName() + " -> " + neighborNode.getName() + "\n\n");
					} else {
						// add more neighbor with same distance 
						if (downD == node.getDownDistance()) {
							node.getDownNodes().add(neighborNode);
							logger.info("add another down neighbor:" + node.getName() + " -> + " + neighborNode.getName() + "\n\n\n");
						} // same distance neighbor
						// find an nearer neighbors, replace the old neighbors
						if (downD < node.getDownDistance()) {
							node.setDownDistance(downD);
							node.getDownNodes().clear();  
							node.getDownNodes().add(neighborNode);
							logger.info("Replace node Down neighbor: " + node.getName() + " > - + " + neighborNode.getName() + "\n\n\n\n");
							
						} // find nearer neighbor 
					} // added new neighbor or replace with nearer neighbor 
				} // only right neighbor  
			} // overlapped rows 
				// node.getRightNodes().stream().sorted().forEachOrdered(s -> System.out.println("forEach: " + s.getName()));
		}  // for each node
	}
	
	
	private static void getLeftRightNodes(INode node, Node[] nodes) 
	{
		// multiple nearest neighbors  
		// multiple overlapped rows 
		int nodeBottom = node.getRect().y;
		int nodeTop = nodeBottom + node.getRect().height;
		logger.debug("node:  buttom: " + nodeBottom + " top " + nodeTop);
		
		int size = nodes.length;
				for (int i=0; i < size; i = i + 1) {
				//find the overlap for each n(eighbor) node 
				INode neighborNode = nodes[i];
				logger.debug("Node " + node.getName() + " to Neighbor Name " + neighborNode.getName());
				int nNodeBottom = neighborNode.getRect().y;
				int nNodeTop = nNodeBottom + neighborNode.getRect().height;
				
				logger.debug("nNode:  buttom: " + nNodeBottom + " top " + nNodeTop);
				// check if there is overlap   
				// from Max Bottom
				int overLapBottom = nodeBottom; 
				if (nodeBottom  <  nNodeBottom) {
				overLapBottom = nNodeBottom; 
				} 
				// to min top
				int overLapTop = nodeTop; 
				if (nodeTop  >  nNodeTop) {
					overLapTop = nNodeTop; 
				} 
				logger.debug("overlap from buttom: " + Integer.toString(overLapBottom) + " to top: " + Integer.toString(overLapTop));
			
				// overlapped rows 
				if ((overLapTop - overLapBottom) > 0 ) {
					// if overLapTop is greater that  overLapBottom, 
					// the left distance is from this node's left side to the neighbor node's left side.    
					int leftD = node.getRect().x - neighborNode.getRect().x - neighborNode.getRect().width;
					// the tight distance is from neighbor's left side to this node's right side.
					int rightD = neighborNode.getRect().x - node.getRect().x - node.getRect().width; 
				logger.debug("RightDistance: " + Integer.toString(rightD) + "\n");
				// left distance
				
				if (leftD > 0) {
					if (node.getLeftDistance() == 0) {		
						// first neighbor 
						node.setLeftDistance(leftD);
						node.getLeftNodes().add(neighborNode); 
						logger.info(" new Left neighbor: " + node.getName() + " -> " + neighborNode.getName() + "\n\n");
					} else {
						// add more neighbor with same distance 
						if (leftD == node.getLeftDistance()) {
							node.getLeftNodes().add(neighborNode);
							logger.info("add another left neighbor:" + node.getName() + " -> + " + neighborNode.getName() + "\n\n\n");
						} // same distance neighbor
						// find an nearer neighbors, replace the old neighbors
						if (leftD < node.getLeftDistance()) {
							node.setLeftDistance(leftD);  // set new distance
							logger.info("leftNode size " + node.getLeftNodes().size());
							node.getLeftNodes().clear();  // clear all 
							logger.info("leftNode size " + node.getLeftNodes().size());
							node.getLeftNodes().add(neighborNode);
							logger.info("leftNode size " + node.getLeftNodes().size());
							logger.info("Replace node left neighbor: " + node.getName() + " > - + " + neighborNode.getName() + "\n\n\n\n");
							
						} // find nearer neighbor 
					} // added new neighbor or replace with nearer neighbor 
				} // only right neighbor 
				
				//  right neighbor 
				if (rightD > 0) {
					if (node.getRightDistance() == 0) {
						
						// first neighbor 
						node.setRightDistance(rightD);
						node.getRightNodes().add(neighborNode); 
						logger.info(" new right neighbor: " + node.getName() + " -> " + neighborNode.getName() + "\n\n");
					} else {
						// add more neighbor with same distance 
						if (rightD == node.getRightDistance()) {
							node.getRightNodes().add(neighborNode);
							logger.info("add another neighbor:" + node.getName() + " -> + " + neighborNode.getName() + "\n\n\n");
						} // same distance neighbor
						// find an nearer neighbors, replace the old neighbors
						if (rightD < node.getRightDistance()) {
							node.setRightDistance(rightD);
							node.getRightNodes().clear();  
							node.getRightNodes().add(neighborNode);
							logger.info("Replace node right neighbor: " + node.getName() + " > - + " + neighborNode.getName() + "\n\n\n\n");
							
						} // find nearer neighbor 
					} // added new neighbor or replace with nearer neighbor 
				} // only right neighbor  
			} // overlapped rows 
				// node.getRightNodes().stream().sorted().forEachOrdered(s -> System.out.println("forEach: " + s.getName()));
		}  // for each node
	}
	
	private static void displayRecursivelyAllNodesFromParentNode(Node a) {
		
		Set<INode> wipNodes = new HashSet<INode>();
		//Set<INode> wipNodes = new LinkedHashSet<INode>();
		// LinkedList<INode> wipNodes = new LinkedList<INode>();
		wipNodes.add(a);
		
		Set<INode> completedNodes = new HashSet<INode>();
		
		long controllInt = 0;  /// Just in case, dead loop
		while (!wipNodes.isEmpty() && controllInt < 9999999) {
			controllInt++;
			
		    INode node = wipNodes.iterator().next(); 
		    logger.info(node.getName());
		    
		    completedNodes.add(node);
		    Set<INode>  up = node.getUpNodes(); 
		    Set<INode>  down = node.getDownNodes();
		    Set<INode>  lift = node.getLeftNodes(); 
		    Set<INode>  right = node.getRightNodes(); 
		  
		    wipNodes.addAll(up);
		    wipNodes.addAll(down);
		    wipNodes.addAll(lift);
		    wipNodes.addAll(right);
		    
		    wipNodes.removeAll(completedNodes);  // removed all the Node that done in AllNodes. 
		    logger.info("WIPNodes Size " + wipNodes.size() + "  allNodes Size " + completedNodes.size());
		}
		
		//while (allNodes.
		logger.info("completedSet is done");
		
		completedNodes.stream().sorted(Comparator.comparing(INode::getName)).forEachOrdered(System.out::println);
		    
		System.out.println("Done!");
		
		
	}
	private static void assertNodeLinked(Node node, Node[] up, Node[] down, Node[] left, Node[] right) {
		logger.info(node.getName());
		logger.info("size up " + up.length + " down " + down.length  + " left " + left.length + " right " + right.length );
		
		
		if (!containsAll(node.getUpNodes(), Arrays.asList(up))) {
			throw new AssertionError("the up conditions are not meet");
		}
		if (!containsAll(node.getDownNodes(), Arrays.asList(down))) {
			throw new AssertionError("the down conditions are not meet");
		}
		if (!containsAll(node.getLeftNodes(), Arrays.asList(left))) {
			throw new AssertionError("The left conditions are not meet");
		}
		if (!containsAll(node.getRightNodes(), Arrays.asList(right))) {
			throw new AssertionError("The right conditions are not meet");
		}
	}

	private static boolean containsAll(Collection<INode> nodes, Collection<INode> nodes2) {
		if (nodes.size() != nodes2.size()) {
			logger.info("nodes Size " + nodes.size() + " nodes2 size " + nodes2.size() ) ;
			return false;
		}
		if (nodes.isEmpty() && nodes2.isEmpty())
			return true;
		if (nodes.containsAll(nodes2) && nodes2.containsAll(nodes)) {
			logger.info("nodes containsAll nodes2 " + nodes.containsAll(nodes2));
			return true;
		}
		return false;
	}

	private static Node createNode(String name, int x, int y, int width, int height) {
		Node result = new Node();
		result.setName(name);
		result.setRect(new Rectangle(x, y, width, height));
		
		return result;
	}
}
