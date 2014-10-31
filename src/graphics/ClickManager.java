package graphics;

import game.Board;
import game.Game;

import java.awt.Point;

import javax.swing.SwingUtilities;

public class ClickManager {

	private Game game;
	private Point squareClicked;
	private Drawing drawing;
	private MyRunnable runnable=new MyRunnable();

	public void click(int i, int j) {
		Board board = game.getBoard();

		if (i >= board.getSize() || j >= board.getSize() || i < 0 || j < 0)
			return;
		
		if (board.getPiece(i, j).getOwner() == game.getTurn() &&
			game.getHumanTurn()==game.getTurn()) {
			if (squareClicked!=null && squareClicked.x == i && squareClicked.y == j) {
				squareClicked = null;
			}else{
				squareClicked = new Point(i, j);
			}
			drawing.repaint();
			return;
		} else {
			if (squareClicked != null) {
				if (game.canMove(squareClicked.x, squareClicked.y, i, j)) {
					game.move(squareClicked.x, squareClicked.y, i, j);
					squareClicked = null;
					drawing.repaint();
					SwingUtilities.invokeLater(runnable);
					return;
				}
			}
		}
	}
	
	private class MyRunnable implements Runnable{
		public void run() {
			game.update();
			drawing.repaint();			
		}
	};
		

	public Point getSquareClicked() {
		return squareClicked;
	}

	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
