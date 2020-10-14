package ScenarioGenerator;

import java.util.ArrayList;
import java.util.Stack;

import utils.MCFG;
import utils.Node;

public class DFSScenarioGenerator extends PathGenerator {
	
	DFSScenarioGenerator(MCFG graph){
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
			 Node child=graph.getUnvisitedChildNode(n);
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
			 if(child.getType().equals("End"))
				 break;	
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
		DFSScenarioGenerator dfs = new DFSScenarioGenerator(graph);
		//dfs.loadMCFG("src\\bankSequence.txt");
		//dfs.loadMCFG("src\\DesktopStartup.txt");
		//dfs.loadMCFG("src\\MapRendering.txt");
		//dfs.loadMCFG("src\\DiningPhilosopher2.txt");
		dfs.loadMCFG("src\\bankSequence_CCG.txt");
		int maxPaths=10;
		String []Paths=dfs.getPathSet(maxPaths);
		System.out.println("\nDFS Path set: ");
		for(int i=0; i<maxPaths;i++){
			System.out.println(Paths[i]);
		}
		ArrayList<String> UniquePaths=dfs.getDistinctPathSet(Paths);
		System.out.println("\nDFS Unique Path set: ");
		for(int i=0; i<UniquePaths.size();i++){
			System.out.println(UniquePaths.get(i));
		}
	}
}
