package graphics;



import game.Game;

import java.awt.Point;

import clases.Board;

public class ClickManager {

	private Game game;
	private Point squareClicked;
	private Drawing drawing;
	
	public void click(int i,int j){
		Board board=game.getBoard();
		if(i>board.getSize() || j>board.getSize())
			return;
		i--;j--;
		if(squareClicked==null &&
			board.getPiece(i, j).getOwner()==game.getTurn()){
			squareClicked=new Point(i,j);
			drawing.repaint();
			return;
		}
		if(squareClicked!=null){
			if(squareClicked.x==i && squareClicked.y==j){
				squareClicked=null;
				drawing.repaint();
				return;
			}
			if (game.canMove(squareClicked.x,squareClicked.y,i,j)){
				game.move(squareClicked.x, squareClicked.y, i, j);
				squareClicked=null;
				drawing.repaint();
				game.update();
				return;
			}
		}
	}

	
	public Point getSquareClicked() {
		return squareClicked;
	}

	public void setDrawing(Drawing drawing){
		this.drawing=drawing;
	}

	public void setGame(Game game){
		this.game=game;
	}
}
