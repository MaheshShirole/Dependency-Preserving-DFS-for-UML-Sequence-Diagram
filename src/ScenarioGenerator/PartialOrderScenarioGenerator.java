package ScenarioGenerator;

import java.util.ArrayList;
import java.util.Stack;

import utils.MCFG;
import utils.Node;

public class PartialOrderScenarioGenerator extends PathGenerator {

	PartialOrderScenarioGenerator(MCFG graph){
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
		 //System.out.println(temp.getVisited());
		 path+=temp.getName();
		 while(!s.isEmpty())
		 {
			 Node n=(Node)s.peek();
			 Node child=graph.getTotalOrderUnvisitedChildNode(n);
			 //Node child=graph.getDependentChildNode(n);
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
		PartialOrderScenarioGenerator podfs = new PartialOrderScenarioGenerator(graph);
		//podfs.loadMCFG("src\\DesktopStartup_MCFG.txt"); // MCFG
		//podfs.loadMCFG("src\\DesktopStartup_RMCFG.txt");  //RMCFG
		//podfs.loadMCFG("src\\MapRendering_MCFG.txt"); //MCFG
		//podfs.loadMCFG("src\\MapRendering_RMCFG.txt"); //RMCFG
		//podfs.loadMCFG("src\\bankSequence1_MCFG.txt"); //MCFG
		//podfs.loadMCFG("src\\bankSequence1_RMCFG.txt"); //RMCFG
		//podfs.loadMCFG("src\\DiningPhilosopher_MCFG.txt");//MCFG
		//podfs.loadMCFG("src\\DiningPhilosopher_RMCFG.txt");//RMCFG
		//podfs.loadMCFG("src\\TicketReservation1_MCFG.txt"); //MCFG
		podfs.loadMCFG("src\\TicketReservation1_RMCFG.txt"); //RMCFG
		//podfs.loadMCFG("src\\DesktopStartup1.txt");

		//podfs.loadMCFG("src\\GOtest.txt");
		//podfs.loadMCFG("src\\GOtest1.txt");
		//podfs.loadMCFG("src\\TicketReservation1_RMCFG.txt"); //RMCFG
		int maxPaths=15000;
		//System.out.println(graph);
		String []Paths=podfs.getPathSet(maxPaths);
		System.out.println("\nPartial Order DFS Path set: ");
		//for(int i=0; i<maxPaths;i++){
			//System.out.println(Paths[i]);
		//}
		ArrayList<String> UniquePaths=podfs.getDistinctPathSet(Paths);
		System.out.println("\nPartial Order DFS Unique Path set: "+UniquePaths.size());
		for(int i=0; i<UniquePaths.size();i++){
			System.out.println(UniquePaths.get(i));
		}
		System.out.println("\nPartial Order DFS Unique Path set: "+UniquePaths.size());
	}


}
