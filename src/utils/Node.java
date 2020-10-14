package utils;

import java.util.HashSet;
import java.util.Set;

public class Node {
	int id;
	String name;
	String umlId;
	int type;
	boolean visited=false;
	Set<Integer> parents = new HashSet<Integer>();
	
	public Node(int id, String name, int type){
		this.id=id;
		this.name=name;
		this.type=type;
	}
	
	public Node(String umlId, String name, int type) {
		this.umlId=umlId;
		this.name=name;
		this.type=type;
	}

	int getId(){
		return id;
	}
	public void setVisited(boolean flag){
		visited=flag;
	}
	public boolean getVisited(){
		return visited;
	}
	public String getName(){
		return name;
	}
	public String getType(){
		String result="";
		switch(type){
		case 0: {result="Start"; break;}
		case 1: {result="End"; break;}
		case 2: {result="Message"; break;}
		case 3: {result="ParStart"; break;}
		case 4: {result="ParEnd"; break;}
		case 5: {result="CRStart"; break;}
		case 6: {result="CREnd"; break;}
		case 7: {result="SendSignal"; break;}
		case 8: {result="ReceiveSignal"; break;}
		case 9: {result="Lock"; break;}
		case 10: {result="UnLock"; break;}
		case 11: {result="Decision"; break;}
		case 12: {result="Merge"; break;}
		}
		return result;
	}
	public String toString(){
		String result="";
		result=this.getId()+":"+this.getName()+":"+this.getType()+"::"+this.getParents()+"#"+this.getVisited();
		return result;
	}

	public void setId(int Id) {
		id=Id;
		
	}
	public Set<Integer> getParents(){
		return parents;
	}

	public void addParent(int parent) {
		parents.add(parent);
		
	}

	public int getIntType() {
		return this.type;
	}

}
