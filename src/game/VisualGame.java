package game;

import graphics.ClickAction;
import graphics.ClickManager;
import graphics.Drawing;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import Pieces.Piece;
import clases.Board;
import clases.Move;

public class VisualGame extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 1024;
	
	public void start(){
		JFrame frame = new JFrame("Fuga Del Rey");
		Drawing drawing = new Drawing();
		ClickManager clickManager=new ClickManager();
		ClickAction clickAction= new ClickAction();		
		clickManager.setDrawing(drawing);
		clickManager.setVisualGame(this);
		clickAction.setClickManager(clickManager);
		drawing.addMouseListener(clickAction);
		drawing.setClickManager(clickManager);
		drawing.setGame(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.add(drawing);		
		frame.setVisible(true);
		drawing.repaint();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void update(){
		super.update();
		if(turn==2){
			Move move=minimaxByDepth(this,3);
			move(move);
			update();
		}
	}
	/*Esta medio feucho tenemos que ir mejorandolo*/
	public Move minimaxByDepth(Game game,int depth){
		if(depth==0){
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
						gameAux.changeTurn();
						Move resp=minimaxByDepth(gameAux,depth-1);
						if (resp.getValue()>maxAcum){							
							answer=move;
							maxAcum=resp.getValue();
							if(maxAcum==Integer.MAX_VALUE){
								answer.setValue(-maxAcum);
								return answer;
							}
							//System.out.println("Estoy en profundidad "+depth+" mejore el valor a "+maxAcum);
						}
					}
				}
			}
		}
		answer.setValue(-maxAcum);
		return answer;
	}
	
	/*public int minimax(Game game, int depth){
		Board board=game.getBoard();
		if(depth==0){
			return game.value();
		}
		List<Move> possibleMoves;
		int maxAcum=Integer.MIN_VALUE;
		for(int i=0; i<board.getSize(); i++){
			for(int j=0; j<board.getSize(); j++){
				if(board.getPiece(i, j).getOwner()==game.getTurn()){
					possibleMoves=getPossibleMovesFrom(board,i,j);
					for (Move move : possibleMoves) {
						Game gameAux= game.copy();
						gameAux.move(move);
						int resp=minimax(gameAux,depth-1);
						if (resp>maxAcum){
							maxAcum=resp;
						}
					}
				}
			}
		}
		return -maxAcum;
	}*/
	
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
	
	public VisualGame copy(){
		VisualGame game=new VisualGame();
		game.board=this.board.copy();
		game.turn=this.turn;
		return game;
	}
}
