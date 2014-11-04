package main;

import java.io.File;

import game.Game;

public class TestTime {
	
	public static void main(String[] args) {
		Game game;
		String file;
		int depth;
		long time;
		int acum;
		System.out.println();
		for(int i=7;i<20;i+=4) {
			System.out.println("*************************");
			System.out.println("Tablero de dimension "+i);
			System.out.println("*************************");
			acum=0;
			for(int j=1;j<16;j++) {
				game= new Game();
				file="tablerosEvaluaciones/"+i+"/tablero"+j+".txt";
				try {
					game.loadBoardFrom(new File(file));
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
				if(i==19) {
					depth=2;
				} else {
					depth=(i==7)?4:3;
				}
				time = 0;
				game.setParameters(0, depth, false);
				time=System.currentTimeMillis();
				System.out.println("------------------------");
				System.out.println("        Tablero"+j);
				System.out.println();
				game.getBestNextMove(false);
				time=System.currentTimeMillis()-time;
				acum+=time;
				
			}
			acum/=15;
			System.out.println();
			System.out.println("PROMEDIO = "+acum);
		}
	}
}

