package ScenarioGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import utils.MCFG;
import utils.Node;

public class BFSScenarioGenerator extends PathGenerator {
	
	public BFSScenarioGenerator(MCFG graph){
		super(graph);
	}

	@Override
	public String getPath() {
		String path = "";	
		 //	BFS uses Queue data structure
		 Queue q=new LinkedList();
		 Node temp =graph.getNode(graph.getRootNode());  //get root node of the tree
		 q.add(temp);// add root node in the queue
		 temp.setVisited(true); // set visited flag true
		 //System.out.println(temp);
		 path+=temp.getName();
		 while(!q.isEmpty()){
			 //System.out.println("Queue not empty");
			 Node n=(Node)q.remove();
			 Node child=null;
			 while((child=graph.getUnvisitedChildNode(n))!=null)
			 {
				 //System.out.println("Inside child");
				 child.setVisited(true); // set visited flag true
				 //System.out.println(child);
				 path+="-"+child.getName();
				 q.add(child);
				 if(child.getType().equals("End"))
					 break;	
			 }
			 
		 }
		 graph.clearNodes();

		return path;
	}

	@Override
	public String[] getPathSet(int noOfPaths) {
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
		BFSScenarioGenerator bfs = new BFSScenarioGenerator(graph);
		//bfs.loadMCFG("src\\bankSequence.txt");
		bfs.loadMCFG("src\\DesktopStartup_CFG.txt");
		//bfs.loadMCFG("src\\MapRendering.txt");
		//bfs.loadMCFG("src\\DiningPhilosopher2.txt");

		int maxPaths=10;
		String []Paths=bfs.getPathSet(maxPaths);
		System.out.println("\nBFS Path set: ");
		for(int i=0; i<maxPaths;i++){
			System.out.println(Paths[i]);
		}
		ArrayList<String> UniquePaths=bfs.getDistinctPathSet(Paths);
		System.out.println("\nBFS Unique Path set: ");
		for(int i=0; i<UniquePaths.size();i++){
			System.out.println(UniquePaths.get(i));
		}

	}

}
