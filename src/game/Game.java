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

import treeCalls.Node;
import Pieces.Piece;
import Pieces.PieceManager;
import clases.Board;

public class Game {

	private Board board;
	private int turn;
	private boolean isKingAlive=true;
	private Long time=null;//entrada - ya pasado a milisegundos (recordemos que se lee en segundos)
	private int depth=0; //entrada
	private Integer prune=null; //null es sin poda, Integer.MAX_INT es con poda
	private int humanTurn=1;
	
	public void setParameters(int time, int depth, Integer prune) {
		if(time!=0)
			this.time=time*1000L;
		this.depth=depth;
		this.prune=prune;
		
	}
	
	public int getComputerTurn() {
		return 3-humanTurn;
	}
	
	public int getHumanTurn() {
		return humanTurn;
	}
	
	public void loadBoardFrom(File file) throws Exception{
		FileReader fr= new FileReader(file); //TODO: try-catch?
		BufferedReader br = new BufferedReader(fr);
		String str=br.readLine(); //Aca se lee de quien es el turno
		
		//TODO: validaciones de tablero
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
		move(move.xOrigin,move.yOrigin,move.xDest,move.yDest);
	}
	
	public void move(int x1, int y1, int x2, int y2){ //private?
		board.move(x1,y1,x2,y2);
		changeTurn();
		int dx[]={1,0,-1,0};
		int dy[]={0,1,0,-1};
		for(int i=0; i<4; i++){
			if(board.getPiece(x2+dx[i], y2+dy[i])!=null &&
				board.getPiece(x2+dx[i], y2+dy[i]).canGetKilled(
					board.getPiece(x2, y2),
					board.getPiece(x2+dx[i]*2, y2+dy[i]*2),
					board.getPiece(x2+dx[i]+dy[i], y2+dy[i]+dx[i]),
					board.getPiece(x2+dx[i]-dy[i], y2+dy[i]-dx[i]))){
				if(board.getPiece(x2+dx[i], y2+dy[i])==PieceManager.getKingInstance()){
					isKingAlive=false;
				}
				board.removePiece(x2+dx[i],y2+dy[i]);
				//TODO: Aca se puede agregar algo para contabilizar las fichas atrapadas
			}
		}
		isFinished();
	}
	
	public boolean canMove(Move move){
		return canMove(move.xOrigin, move.yOrigin, move.xDest, move.yDest);
	}
	
	//Aca es donde deberia pasar la magia :P
	public boolean canMove(int x1, int y1, int x2, int y2){ 
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

	/**
	 *  Devuelve si el juego ha concluido o no.
	 *  
	 *  Si el juego termino, modifica la variable turn:
	 *  turn=20 si ganaron los enemigos o turn=10 si ganaron los guardianes 
	 * 
	 */
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
	
	
	public void update(){
		if(isFinished()){
			if(turn==10){
				System.out.println("Los guardianes del Rey ganaron.");
			}
			if(turn==20){
				System.out.println("Los enemigos del Rey ganaron.");
			}
		}
		if(turn==getComputerTurn()){
			Move move=null;
			if(time==null){
				move=Minimax.minimax(this,depth,prune,null,Long.MAX_VALUE);
			}else{
				move=Minimax.minimaxByTime(this, time, prune,false);
			}
			move(move);			
			update();
		}
	}
	
	public void getNextBestMove(boolean tree){
		Move move=null;
		if(time==null){
			Node start=null;
			if(tree){
				try {
				Node.start("tree.dot");
				} catch (Exception e) {
					System.out.println("Hubo un error al abrir el tree.dot");
				}
				start=new Node();
			}
			move=Minimax.minimax(this,depth,prune,start,Long.MAX_VALUE);
			if(tree){
				start.setLabel("START "+move.getValue());
				start.setColor("salmon");
				try {
					Node.close();
				} catch (Exception e) {
					System.out.println("Hubo un error al cerrar el tree.dot");
				}
			}
		}else{
			
			move=Minimax.minimaxByTime(this, time, prune, tree);
		}
		if(move==null ){
			this.turn=15; //Empate
			System.out.println("No hay movimientos posibles");
		}
		System.out.println("El mejor movimiento posible es: "+move.moveString());
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
		frame.setSize(getBoard().getSize()*40+17,getBoard().getSize()*40+39);
		frame.setLocationRelativeTo(null);
		frame.add(drawing);		
		frame.setVisible(true);
		update();
		drawing.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public int value(){
		switch (turn) {
		case 1:
			return board.value();
		case 2:
			return -board.value();
		case 10:
			return Integer.MIN_VALUE+1;
		case 20:
			return Integer.MIN_VALUE+1;
		default:
			throw new IllegalStateException();
		}
	}
}
