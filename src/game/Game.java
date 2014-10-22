package game;

import graphics.ClickAction;
import graphics.ClickManager;
import graphics.Drawing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import Pieces.Piece;
import Pieces.PieceManager;
import clases.Board;
import clases.Move;

public class Game {

	protected Board board;
	protected int turn;
	protected boolean isKingAlive=true;

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	
	
	public void loadBoardFrom(File file) throws Exception{
		FileReader fr= new FileReader(file); //try-catch
		BufferedReader br = new BufferedReader(fr);
		String str=br.readLine(); //Aca se lee de quien es el turno
		
		//validaciones de tablero
		turn=3-Integer.parseInt(str);
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
		changeTurn();
		int dx[]={1,0,-1,0};
		int dy[]={0,1,0,-1};
		for(int i=0; i<4; i++){
			if(board.getPiece(x2+dx[i], y2+dy[i])!=null &&
				board.getPiece(x2+dx[i], y2+dy[i]).getKilled(
					board.getPiece(x2, y2),
					board.getPiece(x2+dx[i]*2, y2+dy[i]*2),
					board.getPiece(x2+dx[i]+dy[i], y2+dy[i]+dx[i]),
					board.getPiece(x2+dx[i]-dy[i], y2+dy[i]-dx[i]))){
				if(board.getPiece(x2+dx[i], y2+dy[i])==PieceManager.getKingInstance()){
					isKingAlive=false;
				}
				board.removePiece(x2+dx[i],y2+dy[i]);
				//Aca se puede agregar algo para contabilizar las fichas atrapadas
			}
		}
		isFinished();
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

	
	public boolean isFinished(){
		if(!isKingAlive)
			turn=20; //Ganan los enemigos
		if( board.getPiece(0, 0)==PieceManager.getKingInstance() ||
				board.getPiece(board.getSize()-1, 0)==PieceManager.getKingInstance() ||
				board.getPiece(0, board.getSize()-1)==PieceManager.getKingInstance() ||
				board.getPiece(board.getSize()-1, board.getSize()-1)==PieceManager.getKingInstance()){
					turn=10; //Ganan los aliados
			}
			return turn>2;
	}
	
	//Esto se deberia ejecutar despues de cada movimiento

	//Tenemos que resolver como contabilizar la muerte del rey, si recorremos todo el tablero buscando el rey, o cuando lo matamos modificamos algun flag.
	//rta:rey puede tener un estado (pied quedar fashon si usamos el patron state)
	
	public void update(){
		if(isFinished()){
			if(turn==10){
				System.out.println("Los guardianes del Rey ganaron.");
			}else{
				System.out.println("Los enemigos del Rey ganaron.");
			}
		}
		if(turn==2){
			Move move=minimaxByDepth(this,3);
			move(move);			
			update();
		}
		
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
		game.isKingAlive=this.isKingAlive;
		return game;
	}
	
	
	
	
	/*Esta medio feucho tenemos que ir mejorandolo*/
	public Move minimaxByDepth(Game game,int depth){
		if(depth==0 || game.getTurn()>2 /*Termino*/){
			return new Move(game.value());
		}
		Move answer=new Move();
		Board board=game.getBoard();
		List<Move> possibleMoves;
		int maxAcum=Integer.MIN_VALUE;
		for(int i=0; i<board.getSize(); i++){
			for(int j=0; j<board.getSize(); j++){
				if(board.getPiece(i, j).getOwner()==game.getTurn()){
					possibleMoves=getPossibleMovesFrom(board,i,j);
					for (Move move : possibleMoves) {
						Game gameAux= game.copy();
						gameAux.move(move);
				//		System.out.println(blancos(3-depth)+"Entre: "+move);
						Move resp=minimaxByDepth(gameAux,depth-1);
				//		System.out.println(blancos(3-depth)+"Sali: "+move+" con valor "+resp.getValue());
						if (resp.getValue()>maxAcum){							
							answer=move;
							maxAcum=resp.getValue();
							if(maxAcum==Integer.MAX_VALUE){
								answer.setValue(-maxAcum);
								return answer;
							}
							
						}
					}
				}
			}
		}
		answer.setValue(-maxAcum);
		return answer;
	}
	
	public List<Move> getPossibleMovesFrom(Board board ,int x, int y){
		int dx[]={1,0,-1,0};
		int dy[]={0,1,0,-1};
		List<Move> answer=new LinkedList<Move>();
		Piece piece;
		Piece pieceToMove=board.getPiece(x, y);
		for(int i=0;i<4;i++){
			int dMove=1;
			while((piece=board.getPiece(x+dx[i]*dMove, y+dy[i]*dMove))!=null 
					&& piece.canJumpBy(pieceToMove)){

				if(piece.canStepBy(pieceToMove)){
					answer.add(new Move(x,y,x+dx[i]*dMove, y+dy[i]*dMove));
				}
				dMove++;
			}

			if(piece!=null && piece.canStepBy(pieceToMove)){
				answer.add(new Move(x,y,x+dx[i]*dMove, y+dy[i]*dMove));
			}
		}
		return answer;
	}
	
	//para debugear
	private String blancos(int a){
		String b="";
		for (int i = 0; i < a; i++) {
			b+="\t";
		}
		return b;
	}
	
	public void start(){
		JFrame frame = new JFrame("Fuga Del Rey");
		Drawing drawing = new Drawing();
		ClickManager clickManager=new ClickManager();
		ClickAction clickAction= new ClickAction();		
		clickManager.setDrawing(drawing);
		clickManager.setGame(this);
		clickAction.setClickManager(clickManager);
		drawing.addMouseListener(clickAction);
		drawing.setClickManager(clickManager);
		drawing.setGame(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.add(drawing);		
		frame.setVisible(true);
		update();
		drawing.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public int value(){
		switch (turn) {
		case 1:
			return -board.value();
		case 2:
			return board.value();
		case 10:
			return Integer.MAX_VALUE;
		case 20:
			return Integer.MAX_VALUE;
		default:
			throw new IllegalStateException();
		}
	}
}
