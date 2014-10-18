package graphics;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickAction implements MouseListener {

	private static int squareSize=40;
	private ClickManager clickManager;
	@Override
	public void mouseClicked(MouseEvent e) {
		Point p=e.getPoint();
		int x=p.x/squareSize;
		int y=p.y/squareSize;
		clickManager.click(y,x);		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setClickManager(ClickManager cm){
		this.clickManager=cm;
	}
}
