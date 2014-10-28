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

	protected Board board;
	protected int turn;
	protected Long time=new Long((long)4000);//entrada - ya pasado a milisegundos (recordemos que se lee en segundos)
	protected int depth=4; //entrada 
	protected boolean isKingAlive=true;

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	
	
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
	
	public void move(int x1, int y1, int x2, int y2){ 
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
			}else{
				System.out.println("Los enemigos del Rey ganaron.");
			}
		}
		if(turn==2){
			Move move=miniMax(this,depth,Integer.MAX_VALUE,null, time);
			move(move);			
			update();
		}
	}
	
	public void getNextBestMove(boolean tree){
		Node start=null;
		if(tree){
			try {
				Node.start();
			} catch (Exception e) {
				System.out.println("Hubo un error al abrir el tree.dot");
			//	e.printStackTrace();
			}
			start=new Node();
		}
		Move move=miniMax(this, depth-1/*aca habia un 3 en vez de un 4*/, null, start, time);
		System.out.println("El mejor moviemiento posible es: "+move.moveString());
		if(tree){
			start.setLabel("START "+move.getValue());
			start.setColor("salmon");
			try {
				Node.close();
			} catch (Exception e) {
				System.out.println("Hubo un error al cerrar el tree.dot");
			//	e.printStackTrace();
			}
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
	
	
	
<<<<<<< HEAD
	
	/*Esta medio feucho tenemos que ir mejorandolo*/
	/**
	 *  Devuelve el mejor movimiento posible y su valor heuristico
	 *  
	 *   @param state El estado del juego
	 *   @param depth Nivel de profundidad
	 */
	/*public Move minimaxByDepth(Game game,int depth){
		if(depth==0 || game.getTurn()>2 ){
			return new Move(game.value());
		}
		Move answer=new Move(Integer.MIN_VALUE);
		Board board=game.getBoard();
		List<Move> possibleMoves;
		for(int i=0; i<board.getSize(); i++){
			for(int j=0; j<board.getSize(); j++){
				if(board.getPiece(i, j).getOwner()==game.getTurn()){
					possibleMoves=getPossibleMovesFrom(board,i,j);
					for (Move move : possibleMoves) {
						Game gameAux= game.copy();
						gameAux.move(move);
						System.out.println(blancos(3-depth)+"Entre: "+move);
						Move resp=minimaxByDepth(gameAux,depth-1);
						move.setValue(-resp.getValue());
					System.out.println(blancos(3-depth)+"Sali: "+move);
						if (move.getValue()>answer.getValue()){							
							answer=move;
							if(answer.getValue()==Integer.MAX_VALUE){
								return answer;
							}							
						}
					}
				}
			}
		}
		return answer;
	}
	*/
	
	public Move miniMax(Game state, Integer depth, Integer prune, Node me, Long time){
		Move move=null;
		if(this.time==null){
			move=miniMaxRecursive(this,depth,Integer.MAX_VALUE,null, null);
		}else{
			int auxDepth=1;
			Long auxTime=time;
			Move auxMove=null;
			while(auxTime>0){ 
				auxMove=miniMaxRecursive(this,auxDepth,(move==null)?Integer.MAX_VALUE:move.getValue(),null, auxTime);
				if(move==null || auxMove.getValue()>move.getValue())
					move=auxMove;
				auxTime=auxMove.getTime();
				System.out.println(auxDepth+" time: "+auxTime);
				auxDepth++;
			}
		}
		return move;
	}
	
=======
>>>>>>> 711fc79477f6aa3f10799ab315cdafdfa0798dd4
	/*Aca esta el minimax con poda, no cambia mucho al comun,
	 *  asi que para no repetir codigo capaz podemos juntarlos a los dos y
	 *   segun el valor de prune hacer la poda o no*/
	
	//ahi lo hice generico, para con/sin poda, dejo el otro por si las moscas
	//con noditos tambien
	
<<<<<<< HEAD
	
	//si bien el tiempo lo pasan en segundos, apenas lo parseamos en el main lo pasamos a milisegundos 
	private Move miniMaxRecursive(Game state, Integer depth, Integer prune, Node me, Long timeLeft){ 
		if(depth==0 || state.getTurn()>2 /*Termino*/){
=======
	public Move minimaxByDepthWithPrune(Game state, int depth, Integer prune, Node me){
		if(depth==0 || state.getTurn()>2){
>>>>>>> 711fc79477f6aa3f10799ab315cdafdfa0798dd4
			return new Move(state.value());
		}
		Move answer=new Move(Integer.MIN_VALUE);
		Board board=state.getBoard();
		List<Move> possibleMoves;
		Integer actualPrune=null;
		Node son=null,nodeAnswer=null;
		Long timeUsed=null;Long initial=new Long(System.currentTimeMillis());
		if(timeLeft!=null){
			timeUsed=new Long(0);
		}
		if(prune!=null)
			actualPrune=Integer.MAX_VALUE;
		for(int i=0; i<board.getSize(); i++){
			for(int j=0; j<board.getSize(); j++){
				if(board.getPiece(i, j).getOwner()==state.getTurn()){
					possibleMoves=getPossibleMovesFrom(board,i,j);
					for (Move move : possibleMoves) {
						if(timeLeft!=null && timeLeft <0){
							answer.setTime(timeLeft);
							return answer;
						}
						
						Game stateAux= state.copy();
						stateAux.move(move);
						if(me!=null)
							son=new Node();
						if(timeLeft!=null){
							if(timeLeft <0){
								answer.setTime(timeLeft);
								return answer;
							}else{
								timeUsed= System.currentTimeMillis()-initial;
							}
						}
						//System.out.println(blancos(4-depth)+"Entre: "+move);
						Move resp=miniMaxRecursive(stateAux,depth-1,actualPrune,son,timeLeft-timeUsed);
						move.setValue(-resp.getValue());	
						timeUsed= System.currentTimeMillis()-initial;
						move.setTime(timeLeft-(System.currentTimeMillis()-initial));
						//System.out.println(blancos(4-depth)+"Sali: "+move);
						if(son!=null){
							son.setMove(move);
							if(depth%2==0)
								son.setForm("ellipse");
							else
								son.setForm("box");
							me.link(son);
						}
						
						if (move.getValue()>answer.getValue()){							
							if(me!=null){
								nodeAnswer=son;
							}
							answer=move;
							if(answer.getValue()==Integer.MAX_VALUE){
								if(nodeAnswer!=null) nodeAnswer.setColor("salmon");
								return answer;
							}							
						}
						if(prune!=null){ //si en vez de un for each por los moves hago un for comun, lo que puedo hacer aca adentro 
							if(move.getValue()>=prune){//es recorrer los nodos que me faltan antes de hacer el break y pintarlos
								nodeAnswer.setColor("salmon");
								return answer;			//como nodos podados, para no gastar tanta memoria en todos los moves 
							}else{						//haciendo el for each afuera
								actualPrune=-answer.getValue();
							}
						}
						if(timeUsed!=null && timeUsed>=timeLeft){
							answer.setTime(timeLeft-(System.currentTimeMillis()-initial));
							return answer;
						}
					}
				}
			}
		}
		if(nodeAnswer!=null) nodeAnswer.setColor("salmon");
		answer.setTime(timeLeft-(System.currentTimeMillis()-initial));
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
