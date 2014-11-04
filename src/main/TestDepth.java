package main;

import java.io.File;

import game.Game;

public class TestDepth {
	
	/** 
	 * Heuristica: Ir a Game, metodo value y modificar el metodo llamado alli por value1, value2 o value3
	 * Poda por simetrias: Ir a Minimax y cambiar los nombres de los metodos minimax por minimax2 y viseversa
	 */
	
	public static void main(String[] args) {
		Game game;
		String file;
		System.out.println("//////////////////////////");
		System.out.println("//////////////////////////");
		System.out.println("////////Sin podas/////////");
		System.out.println("//////////////////////////");
		System.out.println("//////////////////////////");
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
					System.out.println(e.getMessage());
					return;
				}
				game.setParameters(10, 0, false);
				System.out.println("------------------------");
				System.out.println("        Tablero"+j);
				System.out.println("------------------------");
				game.getBestNextMove(false);
				System.out.println();
			}
		}
	}
}

