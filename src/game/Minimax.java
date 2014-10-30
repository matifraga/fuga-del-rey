package game;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import treeCalls.Node;
import Pieces.Piece;
import clases.Board;

public class Minimax {

	public static List<Move> getPossibleMovesFrom(Board board, int x, int y) {
		int dx[] = { 1, 0, -1, 0 };
		int dy[] = { 0, 1, 0, -1 };
		List<Move> answer = new LinkedList<Move>();
		Piece piece;
		Piece pieceToMove = board.getPiece(x, y);
		for (int i = 0; i < 4; i++) {
			int dMove = 1;
			while ((piece = board.getPiece(x + dx[i] * dMove, y + dy[i] * dMove)) != null
					&& piece.canJumpBy(pieceToMove)) {
				if (piece.canStepBy(pieceToMove)) {
					answer.add(new Move(x, y, x + dx[i] * dMove, y + dy[i] * dMove));
				}
				dMove++;
			}

			if (piece != null && piece.canStepBy(pieceToMove)) {
				answer.add(new Move(x, y, x + dx[i] * dMove, y + dy[i] * dMove));
			}
		}
		return answer;
	}

	/* Recibe el tiempo en milisegundos */
	public static Move minimaxByTime(Game state, Long time, Integer prune,
			boolean tree) {
		Long timeBound = System.currentTimeMillis() + time;
		int depth = 1;
		Move move = null, auxMove=null;
		while (System.currentTimeMillis() < timeBound) {
			// System.out.println(timeBound-System.currentTimeMillis()+" "+depth);
			Node start = null;
			if (tree) {
				try {
					Node.start("treeAux.dot");
				} catch (Exception e) {
					System.out.println("Hubo un error al abrir el tree.dot");
				}
				start = new Node();
			}
			auxMove = minimax(state, depth++, prune, start, timeBound);
			
			/*
			 * if(auxMove!=null){ if(tree){
			 * start.setLabel("START "+auxMove.getValue());
			 * start.setColor("salmon"); try { Node.close(); } catch (Exception
			 * e) { System.out.println("Hubo un error al cerrar el tree.dot"); }
			 * } move=auxMove; //Renombro if(tree)
			 * Node.rename("treeAux.dot","tree.dot"); }
			 */
			
			if (auxMove != null) {
				move = auxMove;
				if (tree) {
					start.setLabel("START " + move.getValue());
					start.setColor("salmon");
					try {
						Node.close();
					} catch (Exception e) {
						System.out.println("Hubo un error al cerrar el tree.dot");
					}
					Node.rename("treeAux.dot","tree.dot");
				}
			}
		}
		
		if (tree && auxMove==null) {
			try {
				Node.close();
			} catch (Exception e) {
				System.out.println("Hubo un error al cerrar el tree.dot");
			}
			// elimino el treeAux
			File closeFile = new File("treeAux.dot");
			closeFile.delete();
		}
		// System.out.println(timeBound-System.currentTimeMillis()+" "+depth);
		return move;
	}

	public static Move minimax(Game state, int depth, Integer prune, Node me,
			Long timeBound) {
		if (depth == 0 || state.getTurn() > 2) {
			return new Move(state.value());
		}
		Board board = state.getBoard();
		Move answer = new Move(Integer.MIN_VALUE); // Inicializo el valor del mejor movimiento como -inf para que cualquier movimiento sea mejor
		List<Move> possibleMoves;
		Game stateAux;
		Integer actualPrune = null; // poda actual
		Node son = null, nodeAnswer = null; // hijos y nodo respuesta para el arbol de llamadas
		if (prune != null)
			actualPrune = Integer.MAX_VALUE;
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				if (System.currentTimeMillis() > timeBound)
					return null;
				if (board.getPiece(i, j).getOwner() == state.getTurn()) {
					possibleMoves = getPossibleMovesFrom(board, i, j);
					for (Move move : possibleMoves) {

						stateAux = state.copy();
						stateAux.move(move);
						if (me != null) // Si es creando el arbol de llamadas
							son = new Node();
						// System.out.println(blancos(4-depth)+"Entre: "+move);
						Move resp = minimax(stateAux, depth - 1, actualPrune,
								son, timeBound);
						if (resp == null)
							return null;
						move.setValue(-resp.getValue());
						// System.out.println(blancos(4-depth)+"Sali: "+move);
						if (son != null) { // Si es creando el arbol de llamadas
							son.setLabel(move.toString());
							if (depth % 2 == 0)
								son.setForm("ellipse");
							else
								son.setForm("box");
							me.link(son);
						}
						if (move.getValue() > answer.getValue()) {
							if (me != null) { // Si es creando el arbol de llamadas
								nodeAnswer = son;
							}
							answer = move;
							if (answer.getValue() == Integer.MAX_VALUE) {
								if (nodeAnswer != null)
									nodeAnswer.setColor("salmon");
								return answer;
							}
						}
						if (prune != null) { // si es con poda
							if (move.getValue() >= prune) {
								if (nodeAnswer != null)
									nodeAnswer.setColor("salmon");
								if (me != null) { // Si es creando el arbol de llamadas
									son = new Node();
									son.setLabel("Poda");
									son.setColor("gray");
									if (depth % 2 == 0)
										son.setForm("ellipse");
									else
										son.setForm("box");
									me.link(son);
								}
								return answer;
							} else {
								actualPrune = -answer.getValue();
							}
						}
					}
				}
			}
		}
		if (nodeAnswer != null)
			nodeAnswer.setColor("salmon");
		return answer;
	}
	
	
	public static Move minimaxWithRotations(Game state, int depth, Integer prune, Node me,
			Long timeBound) {
		if (depth == 0 || state.getTurn() > 2) {
			return new Move(state.value());
		}
		Board board = state.getBoard();
		Move answer = new Move(Integer.MIN_VALUE); // Inicializo el valor del mejor movimiento como -inf para que cualquier movimiento sea mejor
		List<Move> possibleMoves;
		Game stateAux;
		Integer actualPrune = null; // poda actual
		Node son = null, nodeAnswer = null; // hijos y nodo respuesta para el arbol de llamadas
		if (prune != null)
			actualPrune = Integer.MAX_VALUE;
		
		int simmetricBoard = board.symmetrys();
		Set<Move> movesDone = new HashSet<Move>();
		
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				if (System.currentTimeMillis() > timeBound)
					return null;
				if (board.getPiece(i, j).getOwner() == state.getTurn()) {
					possibleMoves = getPossibleMovesFrom(board, i, j);
					for (Move move : possibleMoves) {

						stateAux = state.copy();
						stateAux.move(move);
						if (me != null) // Si es creando el arbol de llamadas
							son = new Node();
						// System.out.println(blancos(4-depth)+"Entre: "+move);
						
						Move resp=null;
						//checkear segun el bit, si esta en el set, si esta no lo hago						
						boolean flag=true;
						
						if((simmetricBoard&1)==1){
							if(movesDone.contains(move.rotated90(board)) || movesDone.contains(move.rotated270(board))){
								flag=false;
							}
						}
						
						if(flag && (simmetricBoard&2)==2){
							if(movesDone.contains(move.rotated180(board))){
								flag=false;
							}
						}
						
						if(flag && (simmetricBoard&4)==4){
							if(movesDone.contains(move.xSymmetric(board))){
								flag=false;
							}
						}
						
						if(flag && (simmetricBoard&8)==8){
							if(movesDone.contains(move.ySymmetric(board))){
								flag=false;
							}
						}
						
						if(flag && (simmetricBoard&16)==16){
							if(movesDone.contains(move.firstDiagSymmetric(board))){
								flag=false;
							}
						}
						
						if(flag && (simmetricBoard&32)==32){
							if(movesDone.contains(move.secondDiagSymmetric(board))){
								flag=false;
							}
						}
						
						if(flag){
							resp = minimax(stateAux, depth - 1, actualPrune,
									son, timeBound);
							if (resp == null)
								return null;
							
							move.setValue(-resp.getValue());
							// System.out.println(blancos(4-depth)+"Sali: "+move);
							if (son != null) { // Si es creando el arbol de llamadas
								son.setLabel(move.toString());
								if (depth % 2 == 0)
									son.setForm("ellipse");
								else
									son.setForm("box");
								me.link(son);
							}
							if (move.getValue() > answer.getValue()) {
								if (me != null) { // Si es creando el arbol de llamadas
									nodeAnswer = son;
								}
								answer = move;
								if (answer.getValue() == Integer.MAX_VALUE) {
									if (nodeAnswer != null)
										nodeAnswer.setColor("salmon");
									return answer;
								}
							}
							if (prune != null) { // si es con poda
								if (move.getValue() >= prune) {
									if (nodeAnswer != null)
										nodeAnswer.setColor("salmon");
									if (me != null) { // Si es creando el arbol de llamadas
										son = new Node();
										son.setLabel("Poda");
										son.setColor("gray");
										if (depth % 2 == 0)
											son.setForm("ellipse");
										else
											son.setForm("box");
										me.link(son);
									}
									return answer;
								} else {
									actualPrune = -answer.getValue();
								}
							}
						}
						
						
					}
				}
			}
		}
		if (nodeAnswer != null)
			nodeAnswer.setColor("salmon");
		return answer;
	}	
	
	// para debugear
	private static String blancos(int a) {
		String b = "";
		for (int i = 0; i < a; i++) {
			b += "\t";
		}
		return b;
	}
}
