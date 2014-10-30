package main;

import game.Game;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		Game game= new Game();
		String file=null;
		int depth=0;
		int time = 0;
		Integer prune=null;
		boolean console=false, visual=false, tree=false;
		try {
			for(int i=0 ; i<args.length ; i++) {
				if(args[i].equals("-file")) {
					file=args[++i];
				} else
				if(args[i].equals("-maxtime")) {
					if(time!=0)
						throw new Exception();
					time=Integer.parseInt(args[++i]);
					if(time<=0)
						throw new Exception();
				} else
				if(args[i].equals("-depth")) {
					if(depth!=0)
						throw new Exception();
					depth=Integer.parseInt(args[++i]);
					if(depth<=0)
						throw new Exception();
				} else
				if(args[i].equals("-visual")) {
					if(visual)
						throw new Exception();
					visual=true;
				} else
				if(args[i].equals("-console")) {
					if(console)
						throw new Exception();
					console=true;
				} else
				if(args[i].equals("-prune")) {
					if(prune!=null)
						throw new Exception();
					prune=Integer.MAX_VALUE;
				} else
				if(args[i].equals("-tree")) {
					if(tree)
						throw new Exception();
					tree=true;
				} else
					//Parametro invalido
					throw new Exception();
			}
			
			if (file == null || visual == console || (visual && tree) || (time == 0 && depth == 0) || (time != 0 && depth != 0)) {
				throw new Exception();
			}
		
		} catch (Exception e) {
			System.out.println("Parámetros inválidos");
			return;
		}
		game.setParameters(time, depth, prune);
		try {
			game.loadBoardFrom(new File(file));
		} catch (Exception e) {
			System.out.println("Hubo un error al abrir el archivo");
			return;
		}
		if(visual)
			game.start();
		else
			game.getNextBestMove(tree);
	}
}
