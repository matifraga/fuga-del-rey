package treeCalls;

import game.Move;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Node {

	private static int labelNumber=0;
	private static BufferedWriter bufferedWriter;
	private String name;
	
	public static void start(String fileName) throws Exception	{
		bufferedWriter = new BufferedWriter(new FileWriter(new File(fileName)));
		bufferedWriter.write("digraph{\n");
	}
	
	/*Tienen que estar cerrados los archivos*/
	public static void rename(String toRename, String renameName){
		File file=new File(toRename);
		File renameFile = new File(renameName);
		if( ! file.renameTo(new File(renameName)) ) {
			renameFile.delete();
			file.renameTo(new File(renameName));
		}
	}

	public static void close() throws Exception{
		bufferedWriter.write("}");
		bufferedWriter.close();
		bufferedWriter=null;
	}
	
	public Node(){
		this.name="A"+labelNumber;
		labelNumber++;
	}
	
	/*Imprime el move con su valor real (invirtiendo su valor si era MIN)*/
	public void setLabelMove(Move move, boolean invert){
		int realValue=move.getValue();
		if(invert){ 
			realValue*=-1;
			setForm("box");
		}
		else
		{
			setForm("ellipse");
		}
			
		try{
			bufferedWriter.write(name+" [label=\""+move.toString()+" "+realValue+"\"]\n");
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
	
	public void link(Node son){ 
		try{
			bufferedWriter.write(name+" -> "+son.name+"\n");
			}catch(Exception e){
				System.out.println("Error al querer escribir el tree.dot");
			}
	}
	
	
	
}
