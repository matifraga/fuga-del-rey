package main;

import java.io.File;

import game.Game;

public class TestTime2 {
	public static void main(String[] args) {
		Game game;
		String file;
		long time,time2;
		
		for(int i=1; i<10; i+=8) {
			for(int j=1 ; j<5 ; j++) {
				game = new Game();
				file = "tablerosEvaluaciones/11/tablero"+i+".txt";
				try {
					game.loadBoardFrom(new File(file));
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
				System.out.println("*******************");
				System.out.println("Tablero "+i+" - Nivel: "+j);
				System.out.println("*******************");
				System.out.println();
				game.setParameters(0, j, false);
				time = System.currentTimeMillis();
				game.getBestNextMove(false);
				time2 = System.currentTimeMillis();
				System.out.println("Tiempo: "+(time2-time));
				System.out.println();
				System.out.println();
			}
		}
	}

}
