package game;

import graphics.ClickAction;
import graphics.ClickManager;
import graphics.Drawing;

import java.io.File;

import javax.swing.JFrame;

public class VisualGame extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	
	public static void main(String[] args) {
		VisualGame game= new VisualGame();
		try {
			game.loadBoardFrom(new File("example1"));
		} catch (Exception e) {
			System.out.println("Hubo un error al abrir el archivo");
			//e.printStackTrace();
			return;
		}
		game.start();
	}
	
	public void start(){
		JFrame frame = new JFrame("Fuga Del Rey");
		Drawing drawing = new Drawing();
		ClickManager clickManager=new ClickManager();
		ClickAction clickAction= new ClickAction();		
		clickManager.setDrawing(drawing);
		clickManager.setVisualGame(this);
		clickAction.setClickManager(clickManager);
		drawing.addMouseListener(clickAction);
		drawing.setClickManager(clickManager);
		drawing.setGame(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.add(drawing);		
		frame.setVisible(true);
		drawing.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}		
}
