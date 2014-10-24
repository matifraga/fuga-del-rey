package treeCalls;

import clases.Move;

public class Node {

	private static String label;
	private String name;
	private Move move;
	private String color;
	private String form;
	
	public Node(Move move, String name){
		this.move=move;
	}
	//[shape=box, label="D", fillcolor=red]; 
	public void write(){
		System.out.println("[shape="+this.form+", label=\""+this.move.toString()+"\""+", fillcolor="+this.color);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setMove(Move move) {
		this.move = move;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public void setLabel(String label){
		Node.label=label;
	}
	
	public void link(Node son){ 
		System.out.println(this.label+" -> "+son.label);
	}
	
	
	
}
