package game;

import graphics.Drawing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFrame;

import clases.Board;

public class VisualGame {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	private Board board;
	
/*	public static void main(String[] args) {
		VisualGame game= new VisualGame();
		game.start();
	}*/
	
	
	public void loadBoardFrom(File file) throws Exception{
		
		FileReader fr= new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String str=br.readLine(); //Aca se lee de quien es el turno
		str=br.readLine();
		int size=str.length();
		board=new Board(size);
		board.fillRow(str, 0);
		for(int i=1;i<size; i++){
			str=br.readLine();
			board.fillRow(str,i);
			
		}
		
		board.putTowersAndThrone();
		
	}
	
	public void start(){
		JFrame frame = new JFrame("Fuga Del Rey");
		frame.setSize(WIDTH, HEIGHT);

		Drawing drawing = new Drawing(this);
		frame.add(drawing);

		try {
			loadBoardFrom(new File("example1"));
		} catch (Exception e) {
			System.out.println("Hubo un error al abrir el archivo");
			//e.printStackTrace();
			return;
		}
		
		frame.setVisible(true);
		drawing.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Board getBoard() {
		return board;
	}
	
	
	
}
