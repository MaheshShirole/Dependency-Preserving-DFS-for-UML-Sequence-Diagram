package ScenarioGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import utils.MCFG;
import utils.Node;

public class RandomBFSScenarioGenerator extends PathGenerator {

	public RandomBFSScenarioGenerator(MCFG graph){
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
			 while((child=graph.getRandomUnvisitedChildNode(n))!=null)
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
		RandomBFSScenarioGenerator rbfs = new RandomBFSScenarioGenerator(graph);
		//rbfs.loadMCFG("src\\DesktopStartup_CFG.txt");
		//rbfs.loadMCFG("src\\MapRendering_CFG.txt");
		//rbfs.loadMCFG("src\\bankSequence1_CFG.txt");
		//rbfs.loadMCFG("src\\DiningPhilosopher_CFG.txt");
		rbfs.loadMCFG("src\\TicketReservation1_CFG.txt");

		int maxPaths=100;
		//System.out.println(graph);
		//String path=bfs.getPath();
		//System.out.println("\nBFS Path: "+path);
		String []Paths=rbfs.getPathSet(maxPaths);
		System.out.println("\nBFS Path set: ");
		for(int i=0; i<maxPaths;i++){
			System.out.println(Paths[i]);
		}
		ArrayList<String> UniquePaths=rbfs.getDistinctPathSet(Paths);
		System.out.println("\nRandom BFS Unique Path set: "+UniquePaths.size());
		for(int i=0; i<UniquePaths.size();i++){
			System.out.println(UniquePaths.get(i));
		}
		//System.out.println("\nRandom BFS Unique Path set: "+UniquePaths.size());

	}


}
