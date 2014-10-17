package graphics;

import game.VisualGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import clases.Board;

/**
 * @author Diego
 * 
 */
public class Drawing extends JPanel {

	private VisualGame game;
	private static int squareSize=40;

	public Drawing(VisualGame game){
		this.game=game;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		Board board = game.getBoard();
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {

				if ((i + j) % 2 == 0)
					g2d.setColor(Color.GRAY);
				else
					g2d.setColor(Color.BLACK);
				g2d.fillRect(squareSize * (1 + j), squareSize * (1 + i),
						squareSize, squareSize);
				//Tenemos que ver que hacemos con el Empty.png
				/*g2d.drawImage(
						Toolkit.getDefaultToolkit().getImage("Images/" + board.getPiece(i, j).getName()+ ".png"),
						squareSize * (1 + j),squareSize * (1 + i), this);*/
			}
		}

	}
}
