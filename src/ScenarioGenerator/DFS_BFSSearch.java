package ScenarioGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import utils.MCFG;
import utils.Node;

public class DFS_BFSSearch extends PathGenerator {

	DFS_BFSSearch(MCFG graph){
		super(graph);
	}
	@Override
	public String getPath() {
		String path = "";	
		 // DFS uses Stack data structure
		 Stack s = new Stack();
		 //BFS uses queue data structure
		 Queue q=new LinkedList();
		 Node temp =graph.getNode(graph.getRootNode()); //get root node of the tree
		 s.push(temp); // push root node on Stack
		 temp.setVisited(true); // set visited flag true
		 //System.out.println(temp);
		 path+=temp.getName();
		 while(!s.isEmpty()){
			 Node n=(Node)s.peek(); // get top element on stack
			 //check if top element of stack is ParStart node
			 if(n.getType().equals("ParStart")){
				 //Start BFS search up to next Join node
				 q.add(n);// add root node in the queue
				 n.setVisited(true); // set visited flag true
				 //System.out.println(n);
				 while(!q.isEmpty()){
					 //System.out.println("Queue not empty");
					 n=(Node)q.remove();
					 Node child=null;
					 while((child=graph.getRandomUnvisitedChildNode(n))!=null)
					 {
						 //System.out.println("Inside child");
						 child.setVisited(true); // set visited flag true
						 //System.out.println(child);
						 path+="-"+child.getName();
						 //check if child node is Join
						 if(!child.getType().equals("ParEnd")){
							 q.add(child);
						 }else{
							 n=child;
						 }
					 }
				 }
				 
				 // start the DFS search				 
			 }

			 //Node child=graph.getUnvisitedChildNode(n);
			 Node child=graph.getRandomUnvisitedChildNode(n);
			 if(child!=null)
			 {
				 child.setVisited(true);
				 //System.out.println(child);
				 path+="-"+child.getName();
				 s.push(child);
			 }
			 else
			 {
				 s.pop();
			 }
			 if(n.getType().equals("End"))
				 break;	
		 }
		 graph.clearNodes();

		return path;	}

	@Override
	public String[] getPathSet(int noOfPaths) {
		System.out.println("Algorithm: "+this.getClass().getName());
		setStartTime();
		String [] pathSet= new String[noOfPaths];
		for(int i=0;i<noOfPaths;i++){
			pathSet[i]=this.getPath();
		}
		setEndTime();
		return pathSet;
	}
	public static void main(String[] args){
		MCFG graph= new MCFG();
		DFS_BFSSearch dbfs = new DFS_BFSSearch(graph);
		//dbfs.loadMCFG("src\\DesktopStartup_CFG.txt");
		//dbfs.loadMCFG("src\\DesktopStartup_SCG-CCG.txt");
		//dbfs.loadMCFG("src\\MapRendering_CFG.txt");
		//dbfs.loadMCFG("src\\MapRendering_SCG-CCG.txt");
		//dbfs.loadMCFG("src\\bankSequence1_CFG.txt");
		//dbfs.loadMCFG("src\\bankSequence1_SCG-CCG.txt");
		//dbfs.loadMCFG("src\\DiningPhilosopher_CFG.txt");
		//dbfs.loadMCFG("src\\DiningPhilosopher_SCG-CCG.txt");
		//dbfs.loadMCFG("src\\TicketReservation1_CFG.txt");
		dbfs.loadMCFG("src\\TicketReservation1_SCG-CCG.txt");
		//dbfs.loadMCFG("src\\bankSequence_CCG.txt");
		//dbfs.loadMCFG("src\\DiningPhilosopher_CCG.txt");
		//dbfs.loadMCFG("src\\TicketReservation_CCG.txt");
		//dbfs.loadMCFG("src\\TicketReservation_CFG.txt");
		//dbfs.loadMCFG("src\\TicketReservation_SCG-CCG.txt");

		int maxPaths=100;
		//System.out.println(graph);
		//String path=bfs.getPath();
		//System.out.println("\nBFS Path: "+path);
		String []Paths=dbfs.getPathSet(maxPaths);
		System.out.println("\nDFS_BFS Path set: ");
		for(int i=0; i<maxPaths;i++){
			System.out.println(Paths[i]);
		}
		ArrayList<String> UniquePaths=dbfs.getDistinctPathSet(Paths);
		System.out.println("\nDFS_BFS Unique Path set: "+UniquePaths.size());
		for(int i=0; i<UniquePaths.size();i++){
			System.out.println(UniquePaths.get(i));
		}
		//System.out.println("\nDBF_BFS Unique Path set: "+UniquePaths.size());

	}

}
