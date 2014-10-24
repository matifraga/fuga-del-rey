package graphics;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import clases.Board;


public class Drawing extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Game game;
	private ClickManager clickManager;
	private static int squareSize=40;
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		Board board = game.getBoard();
		
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {

				if ((i + j) % 2 == 0)
					g2d.setColor(Color.LIGHT_GRAY);
				else
					g2d.setColor(Color.WHITE);
				g2d.fillRect(squareSize * (1 + j), squareSize * (1 + i),
						squareSize, squareSize);
				//TODO: Tenemos que ver que hacemos con el Empty.png
				g2d.drawImage(
						Toolkit.getDefaultToolkit().getImage("images/" + board.getPiece(i, j).getName()+ ".png"),
						squareSize * (1 + j),squareSize * (1 + i), this);
			}
		}
		if(clickManager.getSquareClicked()!=null){
			g2d.setColor(Color.RED);
			int i=clickManager.getSquareClicked().x;
			int j=clickManager.getSquareClicked().y;
			g2d.fillRect((j+1)*squareSize, (i+1)*squareSize, squareSize, squareSize);
			g2d.drawImage(
			Toolkit.getDefaultToolkit().getImage("images/" + board.getPiece(i, j).getName()+ ".png"),
			squareSize * (1 + j),squareSize * (1 + i), this);
		}
		g2d.setColor(Color.black);
		g2d.drawRect(40, 40, 40*board.getSize(), 40*board.getSize());
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setClickManager(ClickManager clickManager) {
		this.clickManager = clickManager;
	}
	
	
}
