package ScenarioGenerator;

import java.util.ArrayList;
import java.util.Stack;

import utils.MCFG;
import utils.Node;

public class RandomDFSScenarioGenerator extends PathGenerator {

	RandomDFSScenarioGenerator(MCFG graph){
		super(graph);
	}
	@Override
	public String getPath() {
		String path = "";	
		 //DFS uses Stack data structure
		 Stack s=new Stack();
		 Node temp =graph.getNode(graph.getRootNode()); //get root node of the tree
		 s.push(temp);// add root node in the Stack
		 temp.setVisited(true); // set visited flag true
		 //System.out.println(temp);
		 path+=temp.getName();
		 while(!s.isEmpty())
		 {
			 Node n=(Node)s.peek();
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
			 if(child != null){
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
		RandomDFSScenarioGenerator rdfs = new RandomDFSScenarioGenerator(graph);
		//rdfs.loadMCFG("src\\DesktopStartup_CFG.txt");
		//rdfs.loadMCFG("src\\DesktopStartup_SCG-CCG.txt");
		//rdfs.loadMCFG("src\\MapRendering_CFG.txt");
		//rdfs.loadMCFG("src\\MapRendering_SCG-CCG.txt");
		//rdfs.loadMCFG("src\\bankSequence1_CFG.txt");
		//rdfs.loadMCFG("src\\bankSequence1_SCG-CCG.txt");
		//rdfs.loadMCFG("src\\DiningPhilosopher_CFG.txt");
		//rdfs.loadMCFG("src\\DiningPhilosopher_SCG-CCG.txt");
		//rdfs.loadMCFG("src\\TicketReservation1_CFG.txt");
		rdfs.loadMCFG("src\\TicketReservation1_SCG-CCG.txt");
		//rdfs.loadMCFG("src\\bankSequence_CCG.txt");
		//rdfs.loadMCFG("src\\DiningPhilosopher_CCG.txt");
		//rdfs.loadMCFG("src\\TicketReservation_CCG.txt");
		int maxPaths=100;
		String []Paths=rdfs.getPathSet(maxPaths);
		System.out.println("\nRandom DFS Path set: ");
		for(int i=0; i<maxPaths;i++){
			System.out.println(Paths[i]);
		}
		ArrayList<String> UniquePaths=rdfs.getDistinctPathSet(Paths);
		System.out.println("\nRandom DFS Unique Path set: "+UniquePaths.size());
		for(int i=0; i<UniquePaths.size();i++){
			System.out.println(UniquePaths.get(i));
		}
		System.out.println("\nDFS Unique Path set: "+UniquePaths.size());
	}
}
