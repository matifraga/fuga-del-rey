package game;

import java.io.File;

public class Main {

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

}
