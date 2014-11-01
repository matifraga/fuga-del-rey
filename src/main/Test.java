package main;

import java.io.File;

import game.Game;

public class Test {

	public static void main(String[] args) {
		//EVALUACIONES DE PROFUNDIDAD
		Game game;
		String file;
		int depth;
		long time;
		System.out.println("*************************");
		System.out.println("*************************");
		System.out.println("       Sin podas");
		System.out.println("*************************");
		System.out.println("*************************");
		System.out.println();
		for(int i=7;i<20;i+=4) {
			System.out.println("*************************");
			System.out.println("Tablero de dimension "+i);
			System.out.println("*************************");
			for(int j=1;j<16;j++) {
				game= new Game();
				file="tablerosEvaluaciones/"+i+"/tablero"+j+".txt";
				try {
					game.loadBoardFrom(new File(file));
				} catch (Exception e) {
					System.out.println("Hubo un error al abrir el archivo");
					return;
				}
				depth=(i<12)?3:2;
				time = 0;
				game.setParameters(0, depth, false);
				time=System.currentTimeMillis();
				game.getBestNextMove(false);
				time-=System.currentTimeMillis();
				System.out.println("El Tablero"+j+" tardo: "+time);
			}
		}
	}
}
