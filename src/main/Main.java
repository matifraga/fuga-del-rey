package main;

import game.Game;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		Game game= new Game();
		try {
			game.loadBoardFrom(new File("example1"));
		} catch (Exception e) {
			System.out.println("Hubo un error al abrir el archivo");
			return;
		}
		game.getNextBestMove(true);
	}
}
