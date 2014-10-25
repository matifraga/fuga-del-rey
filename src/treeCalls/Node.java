package treeCalls;

import game.Move;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Node {

	private static int labelNumber=0;
	private static BufferedWriter bufferedWriter;
	private String name;
	
	public static void start() throws Exception	{
		bufferedWriter = new BufferedWriter(new FileWriter(new File("tree.dot")));
		bufferedWriter.write("digraph{\n");
	}
	
	public static void close() throws Exception{
		bufferedWriter.write("}");
		bufferedWriter.close();
	}
	
	public Node(){
		this.name="A"+labelNumber;
		labelNumber++;
	}
	//[shape=box, label="D", fillcolor=red]; 
	/*
	public void write(){
		System.out.println("[shape="+this.form+", label=\""+this.move.toString()+"\""+", fillcolor="+this.color);
	}*/
	/*
	public void setName(String name) {
		System.out.println();
	}*/
	
	public void setMove(Move move) {
		try{
		bufferedWriter.write(name+" [label=\""+move+"\"]\n");
		}catch(Exception e){
			System.out.println("Error al querer escribir el tree.dot");
		}
	}
	
	public void setLabel(String str) {
		try{
		bufferedWriter.write(name+" [label=\""+str+"\"]\n");
		}catch(Exception e){
			System.out.println("Error al querer escribir el tree.dot");
		}
	}
	
	public void setColor(String color) {
		try{
			bufferedWriter.write(name+" [style=filled, fillcolor=\""+color+"\"]\n");
			}catch(Exception e){
				System.out.println("Error al querer escribir el tree.dot");
			}
	}
	public void setForm(String form) {
		try{
			bufferedWriter.write(name+" [shape="+form+"]\n");
			}catch(Exception e){
				System.out.println("Error al querer escribir el tree.dot");
			}
	}
	/*
	public void setLabel(String label){
		Node.label=label;
	}
	*/
	
	public void link(Node son){ 
		try{
			bufferedWriter.write(name+" -> "+son.name+"\n");
			}catch(Exception e){
				System.out.println("Error al querer escribir el tree.dot");
			}
	}
	
	
	
}
