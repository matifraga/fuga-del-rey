package game;

import graphics.ClickAction;
import graphics.ClickManager;
import graphics.Drawing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import pieces.PieceManager;

public class Game {

	private Board board;
	private int turn;
	private boolean isKingAlive;
	private Long time=null;
	private int depth=0;
	private boolean prune=false;
	private int humanTurn=1;
	
	public void setParameters(int time, int depth, boolean prune) {
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
		FileReader fileReader;
		try{
			fileReader= new FileReader(file);
		}catch(IOException e){
			throw new IOException("No se pudo abrir el archivo");
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String str;
		try{
			str=bufferedReader.readLine(); //Aca se lee de quien es el turno
			turn=3-Integer.parseInt(str); //Leemos al reves los turnos
			str=bufferedReader.readLine();
		}catch(IOException e){
			throw new IOException("Hubo un error al abrir el archivo");
		}
		int size=str.length();
		board=new Board(size);
		isKingAlive=board.fillRow(str, 0);
		for(int i=1;i<size; i++){
			try{
				str=bufferedReader.readLine();
			}catch(IOException e){
				throw new IOException("Hubo un error al abrir el archivo");
			}
			if(str.length()!=size){
				throw new IllegalArgumentException("Dimensiones del tablero invalidas");
			}
			if(board.fillRow(str,i))
			{
				if(isKingAlive){
					throw new IllegalArgumentException("Hay dos reyes en el tablero");
				}
				isKingAlive=true;
			}
		}
		board.putTowersAndThrone();
		bufferedReader.close();
	}
	
	public void move(Move move){
		move(move.xOrigin,move.yOrigin,move.xDest,move.yDest);
	}
	
	public void move(int xOrigin, int yOrigin, int xDest, int yDest){ 
		board.move(xOrigin,yOrigin,xDest,yDest);
		int dx[]={1,0,-1,0};
		int dy[]={0,1,0,-1};
		for(int i=0; i<4; i++){
			if(board.getPiece(xDest+dx[i], yDest+dy[i])!=null &&
				board.getPiece(xDest+dx[i], yDest+dy[i]).canGetKilled(
					board.getPiece(xDest, yDest),
					board.getPiece(xDest+dx[i]*2, yDest+dy[i]*2),
					board.getPiece(xDest+dx[i]+dy[i], yDest+dy[i]+dx[i]),
					board.getPiece(xDest+dx[i]-dy[i], yDest+dy[i]-dx[i]))){
				if(turn==1){ //Se mato un enemigo
					board.enemyKilled();
				}else{
					board.guardKilled();
				}
					
				if(board.getPiece(xDest+dx[i], yDest+dy[i])==PieceManager.getKingInstance()){
					isKingAlive=false;
				}
				board.removePiece(xDest+dx[i],yDest+dy[i]);
			}
		}
		changeTurn();
		isFinished();
	}
	
	public boolean canMove(Move move){
		return canMove(move.xOrigin, move.yOrigin, move.xDest, move.yDest);
	}
	
	public boolean canMove(int xOrigin, int yOrigin, int xDest, int yDest){ 
		if(board.getPiece(xOrigin, yOrigin).getOwner()!=turn){
			return false;
		}
		return board.canMove(xOrigin,yOrigin,xDest,yDest);
	}

	/*
	 *  Devuelve si el juego ha concluido o no.  
	 *  Si el juego termino, modifica la variable turn:
	 *  turn=20 si ganaron los enemigos o turn=10 si ganaron los guardianes 
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
	
	/*Este metodo se tiene que ejecutar despues de cada movimiento*/
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
				move=Minimax.minimaxByDepth(this, depth, prune, false);
			}else{
				move=Minimax.minimaxByTime(this, time, prune, false);
			}
			if(move!=null && move.isValid()){
				move(move);			
				update();
			}else{
				System.out.println("No hay movimientos posibles");
				turn=15; //No es el turno de nadie, el juego termina
			}
		}
	}
	
	public void getBestNextMove(boolean tree){
		Move move=null;
		if(time==null){
			move=Minimax.minimaxByDepth(this, depth, prune, tree);
		}else{
			move=Minimax.minimaxByTime(this, time, prune, tree);
		}
		if(move==null || !move.isValid()){
			System.out.println("No hay movimientos posibles");
			this.turn=15; //No es el turno de nadie, el juego termina
		}
		System.out.println("El mejor movimiento posible es: "+move);
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
	
	/*Retorna una copia de la instacia actual*/
	public Game copy(){ 
		Game game=new Game();
		game.board=this.board.copy();
		game.turn=this.turn;
		game.isKingAlive=this.isKingAlive;
		game.humanTurn=this.humanTurn;
		game.depth=this.depth;
		game.time=this.time;
		game.prune=this.prune;
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
			return board.value2();
		case 2:
			return -board.value2();
		case 10:
			return Integer.MIN_VALUE+1;
		case 20:
			return Integer.MIN_VALUE+1;
		default:
			throw new IllegalStateException();
		}
	}
}
