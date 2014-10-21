package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import Pieces.Piece;
import clases.Board;
import clases.Move;

public class Game {

	protected Board board;
	protected int turn;
	
	public void loadBoardFrom(File file) throws Exception{
		FileReader fr= new FileReader(file); //try-catch
		BufferedReader br = new BufferedReader(fr);
		String str=br.readLine(); //Aca se lee de quien es el turno
		
		//validaciones de tablero
		turn=Integer.parseInt(str);
		str=br.readLine();
		int size=str.length();
		board=new Board(size);
		board.fillRow(str, 0);
		for(int i=1;i<size; i++){
			str=br.readLine();
			board.fillRow(str,i);	
		}
		board.putTowersAndThrone();		
	}
	
	public void move(Move move){
		move(move.getX1(),move.getY1(),move.getX2(),move.getY2());
	}
	
	public void move(int x1, int y1, int x2, int y2){ //private?
		board.move(x1,y1,x2,y2);
		int dx[]={1,0,-1,0};
		int dy[]={0,1,0,-1};
		for(int i=0; i<4; i++){
			if(board.getPiece(x2+dx[i], y2+dy[i])!=null &&
				board.getPiece(x2+dx[i], y2+dy[i]).getKilled(
					board.getPiece(x2, y2),
					board.getPiece(x2+dx[i]*2, y2+dy[i]*2),
					board.getPiece(x2+dx[i]+dy[i], y2+dy[i]+dx[i]),
					board.getPiece(x2+dx[i]-dy[i], y2+dy[i]-dx[i]))){
				board.removePiece(x2+dx[i],y2+dy[i]);
				//Aca se puede agregar algo para contabilizar las fichas atrapadas
			}
		}

	}
	
	public boolean canMove(Move move){
		return canMove(move.getX1(), move.getY1(), move.getX2(), move.getY2());
	}
	
	//Aca es donde deberia pasar la magia :P
	public boolean canMove(int x1, int y1, int x2, int y2){ //private?
		Piece pieceToMove=board.getPiece(x1, y1);
		if(board.getPiece(x2, y2).canStepBy(pieceToMove)){
			if((x1!=x2 || y1!=y2) && (x1==x2 || y1==y2)){
				if(checkEmptyPath(pieceToMove,x1,y1,x2-x1,y2-y1)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	private boolean checkEmptyPath(Piece pieceToMove, int x1, int y1, int dirX,	int dirY) {
		int uniX=dirX/(dirX==0?1:Math.abs(dirX));
		int uniY=dirY/(dirY==0?1:Math.abs(dirY));
		for(int i=1; i<dirX+dirY;i++){
			if(!board.getPiece(x1+i*uniX, y1+i*uniY).canJumpBy(pieceToMove))
				return false;
		}
		return true;
	}

	//Esto se deberia ejecutar despues de cada movimiento
	public void update(){
		changeTurn();
		if( board.getPiece(0, 0).getName().equals("King") ||
			board.getPiece(board.getSize()-1, 0).getName().equals("King") ||
			board.getPiece(0, board.getSize()-1).getName().equals("King") ||
			board.getPiece(board.getSize()-1, board.getSize()-1).getName().equals("King")){
			System.out.println("Los guardianes del Rey ganaron");
			turn=4; //No es el turno de nadie
		}
		//Tenemos que resolver como contabilizar la muerte del rey, si recorremos todo el tablero buscando el rey, o cuando lo matamos modificamos algun flag.
		
		//rta:rey puede tener un estado (puede quedar fashon si usamos el patron state) 
	}
	
	public int getTurn(){
		return turn;
	}
	
	public void changeTurn(){
		turn=3-turn;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Game copy(){ 
		Game game=new Game();
		game.board=this.board.copy();
		game.turn=this.turn;
		return game;
	}
	
	public int value(){
		return (2*turn-3)*board.value();
	}
}
