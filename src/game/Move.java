package game;

import java.awt.Point;

public class Move {

	protected int xOrigin,xDest,yOrigin,yDest,value;
	private boolean validMove=false;
	
	public Move(Point origin, Point dest){
		this.xOrigin=origin.x;
		this.xDest=dest.x;
		this.yOrigin=origin.y;
		this.yDest=dest.y;
		validMove=true;
	}
	
	public Move(int value){
		this.value=value;
	}
	
	public Move(int x1, int y1, int x2, int y2){
		this.xOrigin=x1;
		this.yOrigin=y1;
		this.xDest=x2;
		this.yDest=y2;
		validMove=true;
	}
	
	public Move(int x1, int y1, int x2, int y2,int value){
		this.xOrigin=x1;
		this.yOrigin=y1;
		this.xDest=x2;
		this.yDest=y2;
		this.value=value;
		validMove=true;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
		
	public int getValue(){
		return value;
	}
	
	public String toString(){
		return "("+xOrigin+","+yOrigin+")("+xDest+","+yDest+")";
	}
	
	@Override
	public int hashCode() {;
		return 599*xOrigin+691*yOrigin+487*xDest+263*yDest;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (xDest != other.xDest)
			return false;
		if (xOrigin != other.xOrigin)
			return false;
		if (yDest != other.yDest)
			return false;
		if (yOrigin != other.yOrigin)
			return false;
		return true;
	}
	
	public Move xSymmetric(Board board){
		return new Move(board.xSymmetric(xOrigin,yOrigin),board.xSymmetric(xDest, yDest));
	}
	
	public Move ySymmetric(Board board){
		return new Move(board.ySymmetric(xOrigin,yOrigin),board.ySymmetric(xDest, yDest));
	}
	
	public Move firstDiagSymmetric(Board board){
		return new Move(board.firstDiagSymmetric(xOrigin,yOrigin),board.firstDiagSymmetric(xDest, yDest));
	}
	
	public Move secondDiagSymmetric(Board board){
		return new Move(board.secondDiagSymmetric(xOrigin,yOrigin),board.secondDiagSymmetric(xDest, yDest));
	}
	
	public Move rotated90(Board board){
		return new Move(board.rotated90(xOrigin, yOrigin),board.rotated90(xDest, yDest));
	}
	
	public Move rotated180(Board board){
		return new Move(board.rotated180(xOrigin, yOrigin),board.rotated180(xDest, yDest));
	}
	
	public Move rotated270(Board board){
		return new Move(board.rotated270(xOrigin, yOrigin),board.rotated270(xDest, yDest));
	}

	public boolean isValid() {
		return validMove;
	}
	
}
