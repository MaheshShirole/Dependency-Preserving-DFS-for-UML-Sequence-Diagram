package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;




public class MCFG {
	private Map<Integer, Node> vertices;
	private LinkedList<Integer> [] edges;
	private Hashtable<String, Integer> nodes;
	private int numOfVertices=0;
	private int rootNode;
	
	public MCFG(){
		numOfVertices=0;
		vertices= new Hashtable<Integer, Node>();
		nodes=new Hashtable();
		
	}
	public int getRootNode(){
		return rootNode;
	}
	public void addVertex(String id, Node node){
		//set Id of the node as a current vertex count
		node.setId(numOfVertices);
		
		//set root node if node type is Start
		if(node.getType().equals("Start"))
			rootNode=numOfVertices;
		
		vertices.put(numOfVertices, node); //add node in vertices list
		nodes.put(id, numOfVertices); //add node in node list for future reference
		numOfVertices++; // increase vertex number
	}
	
	//First craeate linked list for each node to store edges data
	public void initializeEdgeSet(){
		edges = (LinkedList<Integer>[]) new LinkedList[numOfVertices];
		for(int i=0;i<numOfVertices;i++){
			edges[i]= new LinkedList<Integer>();
			
		}
	}
	
	public void addEdge(String Source, String Destination){
		//System.out.println("Source: "+Source+" Destination :"+Destination);
		int source = (Integer) nodes.get(Source);
		int destination = (Integer) nodes.get(Destination);
		//System.out.println("Source: "+source+" Destination :"+destination);
		edges[source].addLast(destination);
	}

	public void addEdge(String Source, String Destination, int totalOrder){
		//System.out.println("Source: "+Source+" Destination :"+Destination);
		int source = (Integer) nodes.get(Source);
		int destination = (Integer) nodes.get(Destination);
		//System.out.println("Source: "+source+" Destination :"+destination);
		edges[source].addLast(destination);
		//if totalorder == 1 then add destination.parents= source
		if(totalOrder > 0){
			//System.out.println("Source: "+source+" Destination :"+destination+"Total Order :"+totalOrder);
			vertices.get(destination).addParent(source);
			//System.out.println("Parents"+vertices.get(destination).getParents());
		}
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer();
		
		s.append("Graph G(Vertices, Edges): \n");
		s.append("Vertices {\t");
		for(int i=0;i<numOfVertices;i++){
			s.append(vertices.get(i));
			if(i<numOfVertices-1)
				s.append(",\n\t\t");
		}
		s.append("\n\t}\nEdges\t{");
		String ed="";
		for(int i=0;i<numOfVertices;i++){
			int nodecount=0;
			for(int j=0;j< edges[i].size();j++){
				ed=ed+"("+i+","+edges[i].get(j)+")";
				nodecount++;
				if(j<edges[i].size()-1)
					ed=ed+",";
			}
			if(i<numOfVertices-1)
				if(nodecount>0)
					ed=ed+",\n\t";
		}	
		s.append(ed+"}");
		return s.toString();
	}
	
	public void readMCFGFromFile(String fname){
		try {
			FileInputStream fstream = new FileInputStream(fname);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
			String strLine;
			//	Read Nodes from file 
			strLine = br.readLine();
			//System.out.println(strLine);
			String [] st1 = strLine.split("#");
			int vertexCount=Integer.parseInt(st1[0]);
			while (vertexCount> 0 && (strLine = br.readLine()) != null)   {
				//System.out.println(strLine);
				String [] st = strLine.split("#");
				int type= Integer.parseInt(st[2]);
				Node a= new Node(st[0],st[1],type);
				this.addVertex(st[0], a);
				vertexCount--;
			}
			// Read edges from file
			strLine = br.readLine();
			st1 = strLine.split("#");
			int edgeCount=Integer.parseInt(st1[0]);
			//System.out.println("Acivity count "+strLine+" "+edges);
			this.initializeEdgeSet(); // generate linked list for each node to store edgeset
			//ag.addEdge("xyz", "abc");
			while (edgeCount > 0 && (strLine = br.readLine()) != null)   {
				// Print the content on the console
				//System.out.println (strLine);
				String [] st = strLine.split("#");
				if(st.length > 2){
					//System.out.println (st[2]);
					int totalOrder= Integer.parseInt(st[2]);
					this.addEdge(st[0], st[1], totalOrder);
				}
				else
					this.addEdge(st[0], st[1]);
				edgeCount--;
			}
			//Close the input stream
			in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//DFS traversal 
	 @SuppressWarnings("unchecked")
	public void dfs()
	 {
		 //DFS uses Stack data structure
		 Stack s=new Stack();
		 Node temp = vertices.get(rootNode); //get root node of the tree
		 s.push(temp);// add root node in the Stack
		 temp.setVisited(true); // set visited flag true
		 System.out.println(temp);
		 while(!s.isEmpty())
		 {
			 Node n=(Node)s.peek();
			 Node child=getUnvisitedChildNode(n);
			 if(child!=null)
			 {
				 child.visited=true;
				 System.out.println(child);
				 s.push(child);
			 }
			 else
			 {
				 s.pop();
			 }
		 }
		 this.clearNodes();
	 }
	 
	public Node getUnvisitedChildNode(Node n){
			//System.out.print(n);
			int i =n.getId(); // get id of the activity n
			//System.out.println("Inside Unvisited node");
			//System.out.println("Node n Id="+i);
			//System.out.println("number of edged of Node n ="+edges[i].size());
			for(int j=0;j< edges[i].size();j++){
				Node child = vertices.get(edges[i].get(j)); //get child node of the node n
			//	System.out.println("Child Node of Node n "+child);
			//	System.out.println("child Node visited status="+child.getVisited());
				if(!child.getVisited()){
					return child;
				}
			}
			return null;
		}
		//Utility methods for clearing visited property of node
		 public void clearNodes()
		 {
			 for(int i=0;i<numOfVertices;i++){
				 Node node = vertices.get(i); //get each node of the tree
				 node.setVisited(false); // set all visited flag to false
				 //node.clearVisitCount(); // clear visit count to zero.
			 }
			 
		 }

	public Node getNode(int destinationNode) {
		return vertices.get(destinationNode);
	}

	// random selesction of unvisited child 
	public Node getRandomUnvisitedChildNode(Node n) {
		//System.out.print(n);
		int i =n.getId(); // get id of the activity n
		//System.out.println("Inside Unvisited node");
		//System.out.println("Node n Id="+i);
		//System.out.println("number of edged of Node n ="+edges[i].size());
		for(int j=0;j< edges[i].size();j++){
			int randomNeighbour;
			Random randomGen= new Random();
			randomNeighbour=randomGen.nextInt(edges[i].size());
			//System.out.println(randomNeighbour);
			Node child = vertices.get(edges[i].get(randomNeighbour)); //get child node of the node n
		//	System.out.println("Child Node of Node n "+child);
		//	System.out.println("child Node visited status="+child.getVisited());
			if(!child.getVisited()){
				return child;
			}
		}
		return null;
	}
	//return unvisited thread total order node
	public Node getTotalOrderUnvisitedChildNode(Node n) {
		int i =n.getId(); // get id of the activity n
		for(int j=0;j< edges[i].size()*10;j++){
			int randomNeighbour;
			Random randomGen= new Random();
			randomNeighbour=randomGen.nextInt(edges[i].size());
			//System.out.println(randomNeighbour);
			Node child = vertices.get(edges[i].get(randomNeighbour)); //get child node of the node n
			//System.out.println("Child Node of Node n "+child);
		//	System.out.println("child Node visited status="+child.getVisited());
			if(!child.getVisited()){
				//System.out.println("Child Node of Node n "+child);
				//check for total orderof the child node
				Set<Integer> Parents=child.getParents();
				//System.out.println("Child Node Parents "+child.getParents());
				//System.out.println("Child Node Parents1 "+Parents);
				if(Parents !=null){
					//System.out.println("Child Node Parents1 "+Parents);
					boolean totalOrder=true;
					Iterator it = Parents.iterator();
					while(it.hasNext()){
						int parent = (Integer) it.next();
						Node Parent = vertices.get(parent);
						//System.out.println("#"+Parent);
						if(!Parent.getVisited())
							totalOrder=false;
					}
					if(totalOrder)
						return child;
				}
				else
					return child;
			}
		}
		return null;
	}
	//return unvisited thread total order node
	public Node getDependentChildNode(Node n) {
		int i =n.getId(); // get id of the activity n
		for(int j=0;j< edges[i].size()*10;j++){
			int randomNeighbour;
			Random randomGen= new Random();
			randomNeighbour=randomGen.nextInt(edges[i].size());
			//System.out.println(randomNeighbour);
			Node child = vertices.get(edges[i].get(randomNeighbour)); //get child node of the node n
			//System.out.println("Child Node of Node n "+child);
		//	System.out.println("child Node visited status="+child.getVisited());
			if(!child.getVisited()){
				//System.out.println("Child Node of Node n "+child);
				//check for total orderof the child node
				Set<Integer> Parents=child.getParents();
				//System.out.println("Child Node Parents "+child.getParents());
				//System.out.println("Child Node Parents1 "+Parents);
				//check if child is of type cfPARend
					if(Parents !=null){
						//System.out.println("Child Node Parents1 "+Parents);
						boolean totalOrder=true;
						int childType= child.getIntType();
						switch(childType){
						case 8:
						case 4:{ Iterator it = Parents.iterator();
								 while(it.hasNext()){
									int parent = (Integer) it.next();
									Node Parent = vertices.get(parent);
									//	System.out.println("#"+Parent);
									if(!Parent.getVisited())
										totalOrder=false;
								}
								if(totalOrder)
									return child;
								break;
								}
						default:{
								Iterator it = Parents.iterator();
								while(it.hasNext()){
									int parent = (Integer) it.next();
									Node Parent = vertices.get(parent);
									//	System.out.println("#"+Parent);
									if(Parent.getVisited())
										return child;
								}
							}
						}
					}
					else
						return child;
				}

		}
		return null;
	}

 }
