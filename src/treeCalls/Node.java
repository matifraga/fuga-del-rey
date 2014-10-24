package treeCalls;

import clases.Move;

public class Node {

	private static String label;
	private String name;
	private Move move;
	
	public Node(Move move, String name){
		this.move=move;
	}
	//[shape=box, label="D", fillcolor=red]; 
	public void fillNode(int value, String color, String form){
		System.out.println("[shape="+form+", label=\""+this.move.toString()+"\""+", fillcolor="+color);
	}
	
	public void setLabel(String label){
		Node.label=label;
	}
	
	public void link(Node son){ 
		System.out.println(this.label+" -> "+son.label);
	}
	
	
	
}
