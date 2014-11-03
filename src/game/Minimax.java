package game;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import pieces.Piece;
import treeCalls.Node;

public class Minimax {

	public static List<Move> getPossiblesMovesFrom(Board board, int row, int col) {
		int dx[] = { 1, 0, -1, 0 };
		int dy[] = { 0, 1, 0, -1 };
		List<Move> answer = new LinkedList<Move>();
		Piece piece;
		Piece pieceToMove = board.getPiece(row, col);
		for (int i = 0; i < 4; i++) { //Direcciones del movimiento
			for(int step=1;(piece=board.getPiece(row+dx[i]*step,col+dy[i]*step))!=null;step++) { //Si no esta en el borde del tablero
				
				if (piece.canBeStepBy(pieceToMove)) {
					answer.add(new Move(row, col, row + dx[i] * step, col + dy[i] * step));
				}
				if(!piece.canBeJumpBy(pieceToMove)){
					break;
				}
			}
		}
		return answer;
	}

	public static Move minimaxByDepth(Game state, int depth, boolean prune,
			boolean tree) {
		Move answer=null;
		Node start=null;
		if(tree){
			try {
			Node.start("tree.dot");
			} catch (Exception e) {
				System.out.println("Hubo un error al abrir el tree.dot");
			}
			start=new Node();
		}
		answer=Minimax.minimax(state,0,depth,(prune?Integer.MAX_VALUE:null),start,Long.MAX_VALUE);
		if(tree){
			start.setLabel("START "+answer.getValue());
			start.setColor("salmon");
			start.setForm("box");
			try {
				Node.close();
			} catch (Exception e) {
				System.out.println("Hubo un error al cerrar el tree.dot");
			}
		}
		return answer;
	}
	
	
	/* Recibe el tiempo en milisegundos */
	public static Move minimaxByTime(Game state, Long time, boolean prune,
			boolean tree) {
		Long timeBound = System.currentTimeMillis() + time;
		int depth = 1;
		Move move = null, auxMove=null;
		while (System.currentTimeMillis() < timeBound) {
			Node start = null;
			if (tree) {
				try {
					Node.start("treeAux.dot");
				} catch (Exception e) {
					System.out.println("Hubo un error al abrir el tree.dot");
				}
				start = new Node();
			}
			auxMove = minimax(state,0, depth++, (prune?Integer.MAX_VALUE:null), start, timeBound);			
			if (auxMove != null) {
				move = auxMove;
				if (tree) {
					start.setLabel("START "+move.getValue());
					start.setColor("salmon");
					start.setForm("box");
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
		System.out.println(timeBound-System.currentTimeMillis()+" "+(depth-2));
		return move;
	}

	
	
	
	
	public static Move minimax(Game state, int depth, int maxDepth, Integer prune, Node me,
			Long timeBound) {
		if (depth == maxDepth || state.getTurn() > 2) {
			return new Move(state.value());
		}
		Board board = state.getBoard();
		Move answer = new Move(Integer.MIN_VALUE); // Inicializo el valor del mejor movimiento como -inf para que cualquier movimiento sea mejor
		List<Move> possibleMoves;
		Game stateAux;
		Integer actualPrune = null;
		Node son = null, nodeAnswer = null; // hijos y nodo respuesta para el arbol de llamadas
		if (prune != null)
			actualPrune = Integer.MAX_VALUE;
		for (int row = 0; row < board.getSize(); row++) {
			for (int col = 0; col < board.getSize(); col++) {
				if (System.currentTimeMillis() > timeBound)
					return null;
				if (board.getPiece(row, col).getOwner() == state.getTurn()) {
					possibleMoves = getPossiblesMovesFrom(board, row, col);
					for (Move move : possibleMoves) {
						stateAux = state.copy();
						stateAux.move(move);
						if (me != null) // Si es creando el arbol de llamadas
							son = new Node();
						
						Move resp = minimax(stateAux, depth+1,maxDepth, actualPrune, son, timeBound);
						if (resp == null) //Salio por tiempo
							return null;
						move.setValue(-resp.getValue()); //Intercambia entre MAX y MIN
						if (me != null) { // Si es creando el arbol de llamadas
							son.setLabelMove(move, depth%2==1); //En los nodos de profundidad impar imprimimos su valor opuesto
							me.link(son);
						}
						if (move.getValue() > answer.getValue()) {
							if (me != null) { // Si es creando el arbol de llamadas
								nodeAnswer = son;
							}
							answer = move;
							if (prune != null) { // si es con poda
								if (answer.getValue() >= prune) {
									if (me != null) { // Si es creando el arbol de llamadas
										nodeAnswer.setColor("salmon");
										son = new Node();
										son.setLabel("Poda");
										son.setColor("gray");
										if (depth%2==0)
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
							
							if (answer.getValue() == Integer.MAX_VALUE) {
								if (nodeAnswer != null)
									nodeAnswer.setColor("salmon");
								return answer;
							}
						}
					}
				}
			}
		}
		if (nodeAnswer != null)
			nodeAnswer.setColor("salmon");
		if(!answer.isValid()) return new Move(0); //Si no tiene movimiento devuelve un valor heuristico de 0.
		return answer;
	}
	
	
	public static Move minimax2(Game state, int depth,int maxDepth, Integer prune, Node me,
			Long timeBound) {
		if (depth == maxDepth || state.getTurn() > 2) {
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
		
		int simmetricBoard = board.symmetries();
		Set<Move> movesDone = new HashSet<Move>();
		
		for (int row = 0; row < board.getSize(); row++) {
			for (int col = 0; col < board.getSize(); col++) {
				if (System.currentTimeMillis() > timeBound)
					return null;
				if (board.getPiece(row, col).getOwner() == state.getTurn()) {
					possibleMoves = getPossiblesMovesFrom(board, row, col);
					for (Move move : possibleMoves) {

						stateAux = state.copy();
						stateAux.move(move);
						
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
						movesDone.add(move);
						if(flag){
							Move resp=null;
							if (me != null) // Si es creando el arbol de llamadas
								son = new Node();
							resp = minimax(stateAux, depth+1,maxDepth, actualPrune, son, timeBound);
							if (resp == null)
								return null;	
							move.setValue(-resp.getValue());
								
							if (son != null) { // Si es creando el arbol de llamadas
								son.setLabelMove(move, depth%2==1); //En los nodos de profundidad impar imprimimos su valor opuesto
								me.link(son);
							}
							if (move.getValue() > answer.getValue()) {
								if (me != null) { // Si es creando el arbol de llamadas
									nodeAnswer = son;
								}
								answer = move;
								if (prune != null) { // si es con poda
									if (answer.getValue() >= prune) {
										if (me != null) { // Si es creando el arbol de llamadas
											nodeAnswer.setColor("salmon");
											son = new Node();
											son.setLabel("Poda");
											son.setColor("gray");
											if (depth%2==0)
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
								
								if (answer.getValue() == Integer.MAX_VALUE) {
									if (nodeAnswer != null)
										nodeAnswer.setColor("salmon");
									return answer;
								}
							}
						}						
					}
				}
			}
		}
		if (nodeAnswer != null)
			nodeAnswer.setColor("salmon");
		if(!answer.isValid()) return new Move(0); //Si no tiene movimiento devuelve un valor heuristico de 0.
		return answer;
	}	
	
}
